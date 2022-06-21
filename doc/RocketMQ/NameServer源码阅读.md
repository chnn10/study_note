NameServer

方法的启动

启动NameServer还是比较简单的，核心类是NamesrvController，主要是创建一个NamesrvController对象，里面封装了MQ配置和网络层的配置信息，然后调用Netty启动。

    // 命令行的参数就是封装进args里面的
    // 启动nameServer还是很简单的，就是创建NamesrvController实例，然后调用它的start方法
    public static void main(String[] args) {
        main0(args);
    }
    
    // 启动NameServer的函数
    public static NamesrvController main0(String[] args) {
    
        try {
            // NameServer控制器，是一个启动的核心类，这里是实例化NamesrvController对象，里面主要办好的是MQ的配置信息、处理网络层对象的西信息
            NamesrvController controller = createNamesrvController(args);
    
            // start方法启动
            start(controller);
            String tip = "The Name Server boot success. serializeType=" + RemotingCommand.getSerializeTypeConfigInThisServer();
            log.info(tip);
            System.out.printf("%s%n", tip);
            return controller;
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        }
    
        return null;
    }

创建NamesrvController实例

    // 这里主要是实例化NamesrvController，里面封装MQ的配置信息，和网络层的信息
    public static NamesrvController createNamesrvController(String[] args) throws IOException, JoranException {
        System.setProperty(RemotingCommand.REMOTING_VERSION_KEY, Integer.toString(MQVersion.CURRENT_VERSION));
        //PackageConflictDetect.detectFastjson();
    
        // 创建命令行的对象，启动的参数信息封装在这个类中
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        commandLine = ServerUtil.parseCmdLine("mqnamesrv", args, buildCommandlineOptions(options), new PosixParser());
        if (null == commandLine) {
            System.exit(-1);
            return null;
        }
    
        // RocketMQ的配置信息
        final NamesrvConfig namesrvConfig = new NamesrvConfig();
    
        // Netty服务的配置，主要是与Netty相关的配置
        final NettyServerConfig nettyServerConfig = new NettyServerConfig();
    
        // NameServer监听端口修改为9876
        nettyServerConfig.setListenPort(9876);
    
        // 启动命令行c
        if (commandLine.hasOption('c')) {
            String file = commandLine.getOptionValue('c');
            if (file != null) {
                InputStream in = new BufferedInputStream(new FileInputStream(file));
                properties = new Properties();
                properties.load(in);
                MixAll.properties2Object(properties, namesrvConfig);
                MixAll.properties2Object(properties, nettyServerConfig);
    
                namesrvConfig.setConfigStorePath(file);
    
                System.out.printf("load config properties file OK, %s%n", file);
                in.close();
            }
        }
    
        // 启动命令有p，一般没有p，有p直接退出了
        if (commandLine.hasOption('p')) {
            InternalLogger console = InternalLoggerFactory.getLogger(LoggerName.NAMESRV_CONSOLE_NAME);
            MixAll.printObjectProperties(console, namesrvConfig);
            MixAll.printObjectProperties(console, nettyServerConfig);
            System.exit(0);
        }
    
        // ...... 省略一部分非核心代码 ......
    
        // 创建控制器，nameserver配置信息和netty网络层配置信息
        final NamesrvController controller = new NamesrvController(namesrvConfig, nettyServerConfig);
    
        // remember all configs to prevent discard
        controller.getConfiguration().registerConfig(properties);
    
        return controller;
    }

启动Netty

    public static NamesrvController start(final NamesrvController controller) throws Exception {
    
        if (null == controller) {
            throw new IllegalArgumentException("NamesrvController is null");
        }
    
        // 进行初始化，这里主要还是加载KV配置，开启定时扫描任务
        boolean initResult = controller.initialize();
        if (!initResult) {
            controller.shutdown();
            System.exit(-3);
        }
    
        // 注册一个JVM钩子函数，用于平滑关机的逻辑
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(log, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                controller.shutdown();
                return null;
            }
        }));
    
        // 启动nameServer的入口
        controller.start();
    
        return controller;
    }
    
    
    
        // 启动网络层服务器，下面主要就是调配用netty了
        @Override
        public void start() {
            this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(
                nettyServerConfig.getServerWorkerThreads(),
                new ThreadFactory() {
    
                    private AtomicInteger threadIndex = new AtomicInteger(0);
    
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "NettyServerCodecThread_" + this.threadIndex.incrementAndGet());
                    }
                });
    
            prepareSharableHandlers();
    
            ServerBootstrap childHa ndler =
                this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupSelector)
                    .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, nettyServerConfig.getServerSocketBacklog())
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .localAddress(new InetSocketAddress(this.nettyServerConfig.getListenPort()))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                .addLast(defaultEventExecutorGroup, HANDSHAKE_HANDLER_NAME, handshakeHandler)
                                .addLast(defaultEventExecutorGroup,
                                    encoder,
                                    new NettyDecoder(),
                                    new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                    connectionManageHandler,
                                    serverHandler
                                );
                        }
                    });
            if (nettyServerConfig.getServerSocketSndBufSize() > 0) {
                log.info("server set SO_SNDBUF to {}", nettyServerConfig.getServerSocketSndBufSize());
                childHandler.childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize());
            }
            if (nettyServerConfig.getServerSocketRcvBufSize() > 0) {
                log.info("server set SO_RCVBUF to {}", nettyServerConfig.getServerSocketRcvBufSize());
                childHandler.childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize());
            }
            if (nettyServerConfig.getWriteBufferLowWaterMark() > 0 && nettyServerConfig.getWriteBufferHighWaterMark() > 0) {
                log.info("server set netty WRITE_BUFFER_WATER_MARK to {},{}",
                        nettyServerConfig.getWriteBufferLowWaterMark(), nettyServerConfig.getWriteBufferHighWaterMark());
                childHandler.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(
                        nettyServerConfig.getWriteBufferLowWaterMark(), nettyServerConfig.getWriteBufferHighWaterMark()));
            }
    
            if (nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
                childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            }
    
            try {
                ChannelFuture sync = this.serverBootstrap.bind().sync();
                InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
                this.port = addr.getPort();
            } catch (InterruptedException e1) {
                throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
            }
    
            if (this.channelEventListener != null) {
                this.nettyEventExecutor.start();
            }
    
            this.timer.scheduleAtFixedRate(new TimerTask() {
    
                @Override
                public void run() {
                    try {
                        NettyRemotingServer.this.scanResponseTable();
                    } catch (Throwable e) {
                        log.error("scanResponseTable exception", e);
                    }
                }
            }, 1000 * 3, 1000);
        }

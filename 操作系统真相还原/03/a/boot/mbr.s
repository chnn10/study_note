;主引导程序
SECTION MBR vstart=0x7c00         
   mov ax,cs      
   mov ds,ax
   mov es,ax
   mov ss,ax
   mov fs,ax
   mov sp,0x7c00
   mov ax,0xb800
   mov gs,ax         ;gs=ax充当段基址的作用

;----------------------------
;调用中断0x06功能，进行清屏
;----------------------------
    mov ax,0x600
    mov bx,0x700
    mov cx,0          ;左上角：（0，0）
    mov dx,0x184f     ;右下角：（80，25）

    int 0x10

;---------------------------------------------------------
;直接修改显存输出字符串"hello os"，背景和字体的颜色可以查资料看
;---------------------------------------------------------

    mov byte [gs:0x00],'H'
    mov byte [gs:0x01],0xA4     ; A表示字体是绿色，4表示背景是红色

    mov byte [gs:0x02],'e'
    mov byte [gs:0x03],0xA4

    mov byte [gs:0x04],'l'
    mov byte [gs:0x05],0xA4

    mov byte [gs:0x06],'l'
    mov byte [gs:0x07],0xA4

    mov byte [gs:0x08],'0'
    mov byte [gs:0x09],0xA4

    mov byte [gs:0x10],' '
    mov byte [gs:0x11],0xA4

    mov byte [gs:0x12],'O'
    mov byte [gs:0x13],0xA4

    mov byte [gs:0x14],'S'
    mov byte [gs:0x15],0xA4

;在显示完"Hello OS"之后，我们就开始从磁盘加载loader程序，加载loader程序的功能是在mbr这个文件写的

    mov eax,0x2
    mov bx,0x900           ;目标内存位置，bx寄存器一般是用作地址存储
    mov cx,1               ;读取磁盘数，cx这个寄存器一般用作计数

    call rd_disk_m_16      ;调用加载磁盘数据的方法，这个方法是我们自己实现的
    jmp 0x900              ;直接跳到0x900执行


;-------------------------------------------------------------------------------
;功能:读取硬盘n个扇区
rd_disk_m_16:	   
;-------------------------------------------------------------------------------
				       ; eax=LBA扇区号
				       ; ebx=将数据写入的内存地址
				       ; ecx=读入的扇区数
    mov esi,eax	  ;备份eax
    mov di,cx		  ;备份cx
;读写硬盘:
;第1步：设置要读取的扇区数
    mov dx,0x1f2
    mov al,cl
    out dx,al            ;读取的扇区数

    mov eax,esi	   ;恢复ax

;第2步：将LBA地址存入0x1f3 ~ 0x1f6

    ;LBA地址7~0位写入端口0x1f3
    mov dx,0x1f3                       
    out dx,al                          

    ;LBA地址15~8位写入端口0x1f4
    mov cl,8
    shr eax,cl
    mov dx,0x1f4
    out dx,al

    ;LBA地址23~16位写入端口0x1f5
    shr eax,cl
    mov dx,0x1f5
    out dx,al

    shr eax,cl
    and al,0x0f	   ;lba第24~27位
    or al,0xe0	   ; 设置7～4位为1110,表示lba模式
    mov dx,0x1f6
    out dx,al

;第3步：向0x1f7端口写入读命令，0x20 
    mov dx,0x1f7
    mov al,0x20                        
    out dx,al

;第4步：检测硬件状态
    .not_ready:
        nop
        in al,dx
        and al,0x88
        cmp al,0x08
        jnz .not_ready    ;没有准备好，继续等待

;第5步：从0x1f0端口读取数据
    mov ax,di
    mov dx,256
    mul dx
    mov cx,ax
    mov dx,0x1f0

    .go_on_read:
        in ax,dx
        mov [bx],ax
        add bx,2
        loop .go_on_read
        ret

    
    times 510-($-$$) db 0
    db 0x55,0xaa


    






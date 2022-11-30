
  jmp near start
  
mytext db 'L',0x07,'a',0x07,'b',0x07,'e',0x07,'l',0x07,' ',0x07,'o',0x07,\
          'f',0x07,'f',0x07,'s',0x07,'e',0x07,'t',0x07,':',0x07
number db 0,0,0,0,0     ;在次偏移地址初始化5个字节

start:
    mov ax,0x7c0
    mov ds,ax             ;设置数据段基地址
  
    mov ax,0xb80
    mov es,ax             ;将附加地址设置位显存的起始地址
  
    cld                   ;清除方向标志，cld即告诉程序si，di向前移动
                          ;std指令为设置方向，告诉程序si，di向后移动。
          
    ;---- 下面移动字符 -----
    ;将偏移地址为mytext的字符串移动到显存的位置中
    mov si,mytext         ;si表示的是源偏移地址
    mov di,0              ;di表示的是目标偏移地址
    mov cx,number-mytext  ;cx表示的是移动的次数
    rep movsb             ;这里是每次移动一个字节
                        ;rep movsw表示的是每次移动一个字，那么上面cx的值就需要除于2了
            
  
  
  
    ;----- 分解数字 -----
    ;------ 16位除于8位的除法 -----
    ;被除数必须放在ax寄存器中，除数是bx寄存器的内容
    ;执行后，AL存放商，AH存放余数
    ;mov ax 453
    ;mov bx 10
    ;div bx           ;将453除于10，之后AL存放的是45，AH存放的是3
    
    ;----- 32位除于16位的除法 -----
    ;DX:AX作为被除数，dx是高16位，ax是低16位
    ;ax存放余数，dx存放商，因为除数是10，余数一定小于10，所以在dl取得余数
    mov bx,ax             ;bx存放了number的偏移地址
    mov ax,number         ;我们这次分解的是number偏移地址的值
    mov cx,5              ;循环5次，因为我们是分解一个5位的十进制数
    mov si,10             ;除数是10
digit:
    xor dx,dx             ;将dx清零
  div si                ;dx和ax组成的32位数 除于 si，ax存放的是余数，dx存放的是商
  mov [bx],dl           ;保存余数
  inc bx                ;保存之后，bx自增，为了保存下一个数
  loop digit            ;如果cx不为0，继续执行digit的代码
                          ;如果cx为0，就执行后面的代码
        
  
  
    ;----- 显示数字 -----
    mov bx,number         ;显示的是number这个数
  mov si,4              ;因为是5位数，这里表示的是偏移量
show:
    mov al,[bx+si]        
  add al,0x30
  mov ah,0x04
  mov [es:di],ax        ;在这里显示需要存放的数，ax中al表示存放的内容，ah表示内容的颜色显示
  add di,2              ;偏移地址加2
  dec si                ;si减1，继续显示后面需要存放的内容
  jns show              ;
    
  mov word [es:si],0x0744
  jmp near $

times 510-($-$$) db 0
                 db 0x55,0xaa

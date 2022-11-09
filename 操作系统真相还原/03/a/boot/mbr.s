;主引导程序
SECTION MBR vstart=0x7c00
   mov ax,cs
   mov ds,ax
   mov es,ax
   mov ss,ax
   mov fs,ax
   mov sp,0x7c00
   mov ax,0xb800       ;这里就是映射显存的地址
   moc gs,ax           ;

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

    jmp $

    times 510-($-$$) db 0
    db 0x55,0xaa


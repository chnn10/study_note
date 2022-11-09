;主引导程序
SECTION MBR vstart=0x7c00
    mov ax,cs
    mov ds,ax
    mov es,ax
    mov ss,ax
    mov fs,ax
    mov sp,0x7c00

;----------------------------
;调用中断0x06功能，进行清屏
;----------------------------
    mov ax,0x600
    mov bx,0x700
    mov cx,0          ;左上角：（0，0）
    mov dx,0x184f     ;右下角：（80，25）

    int 0x10


;----------------------------
;获取光标位置
;----------------------------
    mov ah,3         ;输入：3号子功能表示的是获取光标位置，需要存入ah寄存器
    mov bh,0         ;bj寄存器存储的是待获取光标的页号

    int 0x10

;----------------------------
;打印字符串
;----------------------------
    mov ax,message
    mov bp,ax        ;es:bp为串的首地址

    mov bx,0x2

    int 0x10

    jmp $            ;程序悬停在这里

    message db "hello os"
    times 510-($-$$) db 0
    db 0x55,0xaa

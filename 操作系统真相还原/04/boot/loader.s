%include "boot.inc"
section loader vstart=LOADER_BASE_ADDR
LOADER_STACK_TOP euq LOADER_BASE_ADDR
jmp loader_start


;------------------------------
;构建gdt以及内部的描述符
;------------------------------
    GDT_BASE:    dd   0x00000000 
                 dd   0x00000000 

    CODE_DESC:   dd   0x0000FFFF
                 dd   DESC_CODE_HIGH4

    DATA_STACK_DESC:  dd     0x0000FFFF
                      dd     DESC_DATA_HIGH4

    VIDEO_DESC:  dd   0x80000007          ;limit=(0xbffff-0xb8000)/4k=0x7
                 dd   DESC_DATA_HIGH4     ;此时dp1已经修改为0

    GDT_SZIE    equ  $-GDT_BASE
    GDT_LIMIT   equ  GDT_SIZE - 1
    times   60  dq   0

    SELECTOR_CODE    equ  (0x0001<<3) + TI_GDT + RPL0
    SELECTOR_DATA    equ  (0x0002<<3) + TI_GDT + RPL0
    SELECTOR_VIDEO    equ  (0x0003<<3) + TI_GDT + RPL0

    gdt_ptr   dw    GDT_LIMIT
              dd    GDT_BASE
    loadermsg  db  'loader to real' 


loader_start:
;打印字符，"2 LOADER"说明loader已经成功加载
; 输出背景色绿色，前景色红色，并且跳动的字符串"1 MBR"
    mov byte [gs:160],'2'
    mov byte [gs:161],0xA4     ; A表示绿色背景闪烁，4表示前景色为红色

    mov byte [gs:162],' '
    mov byte [gs:163],0xA4

    mov byte [gs:164],'L'
    mov byte [gs:165],0xA4   

    mov byte [gs:166],'O'
    mov byte [gs:167],0xA4

    mov byte [gs:168],'A'
    mov byte [gs:169],0xA4

    mov byte [gs:170],'D'
    mov byte [gs:171],0xA4

    mov byte [gs:172],'E'
    mov byte [gs:173],0xA4

    mov byte [gs:174],'R'
    mov byte [gs:175],0xA4


;------------------------------
;打印字符串
;------------------------------
    mov sp,LOADER_BASE_ADDR
    mov bp,loadermsg
    mov cx,17
    mov ax,0x1301
    mov bx,0x001f
    mov dx,0x1800
    int 0x10


;---------------------------------------  准备进入保护模式   ---------------------------------------

    ;----------- 打开A20 ----------
    in al,0x92
    or al,0000_0010B
    out 0x92,al


    ;----------- 打开A20 ----------
    lgdt [gdt_ptr]

    ;----------- cr0第0位置1 ----------
    mov eax,cr0
    or eax,0x00000001
    mov cr0,eax

    jmp SELECTOR_CODE:p_mode_start

    [bits 32]
    p_mode_start:
        mov ax,SELECTOR_DATA
        mov ds,ax
        mov es,ax
        mov ss,ax
        mov esp,LOADER_STACK_TOP
        mov ax,SELECTOR_VIDEO
        mov gs,ax

        mov byte [gs:320],'p'

        jmp $

  


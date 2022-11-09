LOADER_START_SECTOR equ 0x2

section loader vstart=0x900
;加载loader的程序过程中，我们需要在显示屏输出 "system loading ... "

    mov byte [gs:0x20], 'S'
    mov byte [gs:0x21],0xA4

    mov byte [gs:0x22], 'y'
    mov byte [gs:0x23],0xA4

    mov byte [gs:0x24], 's'
    mov byte [gs:0x25],0xA4

    mov byte [gs:0x26], 't'
    mov byte [gs:0x27],0xA4

    mov byte [gs:0x28], 'e'
    mov byte [gs:0x29],0xA4

    mov byte [gs:0x2a], 'm'
    mov byte [gs:0x2b],0xA4

    mov byte [gs:0x2c], ' '
    mov byte [gs:0x2d],0xA4

    mov byte [gs:0x2e], 'l'
    mov byte [gs:0x2f],0xA4

    mov byte [gs:0x30], 'o'
    mov byte [gs:0x31],0xA4

    mov byte [gs:0x32], 'a'
    mov byte [gs:0x33],0xA4

    mov byte [gs:0x34], 'd'
    mov byte [gs:0x35],0xA4

    mov byte [gs:0x36], 'i'
    mov byte [gs:0x21],0xA4

    mov byte [gs:0x37], 'n'
    mov byte [gs:0x38],0xA4

    mov byte [gs:0x39], 'g'
    mov byte [gs:0x3a],0xA4

    mov byte [gs:0x3b], ' '
    mov byte [gs:0x3c],0xA4

    mov byte [gs:0x3d], '.'
    mov byte [gs:0x3e],0xA4

    mov byte [gs:0x3f], '.'
    mov byte [gs:0x40],0xA4

    mov byte [gs:0x41], '.'
    mov byte [gs:0x42],0xA4

    jmp $


  %include "boot.inc"
  section loader vstart=LOADER_BASE_ADDR
  LOADER_STACK_TOP equ LOADER_BASE_ADDR

;构建gdt和内部的描述符
  GDT_BASE:    dd    0x00000000
               dd    0x00000000

  CODE_DESC:   dd    0x0000FFFF 
               dd    DESC_CODE_HIGH4

  DATA_STACK_DESC:    dd    0x0000FFFF
                      dd    DESC_DATA_HIGH4 

  VIDEO_DESC:  dd    0x80000007
               dd    DESC_VIDEO_HIGH4

  GDT_SIZE  equ  $ - GDT_BASE
  GDT_LIMIT equ  GDT_SIZE - 1
  times 60 dq 0                                        ;这里预留60个描述符的空位
  SELECTOR_CODE    equ  (0x0001<<3) + TI_GDT + RPL0    ;相当于(CODE_DESC - GDT_BASE)/8 + TI_GDT + RPL0 
  SELECTOR_DATA    equ  (0x0001<<3) + TI_GDT + RPL0
  SELECTOR_VIDEO    equ  (0x0001<<3) + TI_GDT + RPL0   
  
  total_mem_bytes dd 0    ;用于保存内容容量，total_mem_bytes内存中的地址是0xb00，将来在内核中我们会引用此地址
  
  gdt_ptr  dw  GDT_LIMIT
           dd  GDT_BASE
		   
  ards_buf  times  244  db  0
  ards_nr  dw  0         ;用来记录ards结构体数量
  
  loader_start:
  
  xor ebx,ebx
  mov edx,0x534d4150	 ;edx只赋值一次，循环体中不会变
  mov di,ards_buf        ;ards结构体缓冲区
.e820_mem_get_loop:      ;循环获取每个ARDS内存范围描述结构
  mov eax,0x0000e820	 ;执行了int 0x15之后，eax值变为0x534d4150，所以每次执行int前都要更新子功能好号
  mov ecx,20             ;ARDS地址范围描述符结构大小是20字节
  int 0x15
  jc .e820_failed_so_try_e801    ;若cf位为1，则有错误发生，尝试0xe801子功能
  add di,cx                      ;
  inc word [ards_nr]             ;记录ARDS数量
  cmp ebx,0                      ;若ebx为0且cf不为1，说明rads全部返回，当前已经是最后一个
  jnz .e820_mem_get_loop
  
;


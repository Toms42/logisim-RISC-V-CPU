
#define STATE_TABLE_SIZE 528
#define WD_SET_MODE_SHORT 

.text
_start:
	li sp, 0x1000
	call main
	.long 0x0
	j keyboard_interrupt
	.long 0x0
	j watchdog_interrupt
	.long 0x0

watchdog_interrupt:
	addi sp, sp, -8
	sw a0, 0(sp)
	li a0, 0x40
	ecall
	lw a0, 0(sp)
	addi sp, sp, 8
	hret

keyboard_interrupt:
	
	addi sp, sp, -12
	sw a0, 0(sp)
	lw a0, 4(sp)
	ecall
	lw a0, 0(sp)
	addi sp, sp, 12
	hret
.weak main
main:

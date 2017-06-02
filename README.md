# logisim RISC-V CPU
literally just a collection of logisim projects I've been working on.

### goals:
The goal of these projects is to create a full datapath according to the RISC-V ISA.

### background:
This is for a class I'm taking about computer architecture. (Taught by [Sol Boucher](https://www.cs.cmu.edu/~sboucher/))

### Expanded Features:
This version features an interrupt controller, which handles clock interrupts as well as keyboard interrupts. The interrupts jump to a table at the beginning of memory, which has the addresses of the interrupt handlers.

It is possible to control the cpu clock interrupt timer delay, by running one of these commands: (binary)
* 0x304B: Short
* 0x204B: Medium-short
* 0x104B: Medium-long
* 0x004B: Long

### Bugs:
* Interupt return addresses are stored on the stack, and the pointer is incremented AFTER the interrupt is called, by the interrupt handler. This means that interrupts immediately subsequently can overwrite the return address of the first one.
* The keyboard interrupt has a weird bug, where it repeatededly prints the last character for unknown reasons.
* The stack pointer is defaulyed to 0x1000, so programs longer than 0x1000 lines need to set the stack pointer to be farther down.

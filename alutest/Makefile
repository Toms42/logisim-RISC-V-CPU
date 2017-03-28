ifeq ($(OS),Windows_NT)
	LOGISIM_BINARY := /usr/local/bin/logisim.exe
	PATH := $(wildcard /c/Program\ Files/Java/jdk*/bin):$(PATH)
else
	LOGISIM_BINARY := $(HOME)/bin/logisim
endif

.PHONY: compare
compare: alutest_reference.txt alutest.txt
	diff $^

alutest.txt: alutest_reference.txt alutest_student.txt
	cut -f1-4 "$(word 1,$^)" | paste - "$(word 2,$^)" >"$@"

alutest_student.txt: alutest.circ $(LOGISIM_BINARY) alu.circ
	rm -f "$@"
	logisim -tty table "$<" >"$@"
	@while [ ! -e "$@" ]; do :; done
	@while [ "`wc -l $@ | cut -d\  -f1`" -ne "256" ]; do :; done

alutest_reference.txt: AluTest.class
	java -cp . "$(basename $<)" >"$@"

alu.circ:
	@echo
	@echo ERROR: No ALU implementation found!
	@echo Please copy your Logisim project into this folder and rename it to 'alu.circ'
	@echo Then open the 'alutest.circ' project, ensure everything is connected correctly, and save.
	@echo
	@false

/usr/local/bin/logisim.exe $(HOME)/bin/logisim.jar:
	mkdir -p "$(dir $@)"
	wget -O "$@" sf.net/projects/circuit/files/latest

$(HOME)/bin/logisim: $(HOME)/bin/logisim.jar
	echo "#!/bin/sh" >"$@"
	echo 'java -jar $< "$$@"' >>"$@"
	chmod +x "$@"

java.check:
	@which javac >/dev/null || (\
		echo ;\
		echo ERROR: No Java installation found! ;\
		echo Please download the JDK from http://www.oracle.com/technetwork/java/javase/downloads ;\
		echo Then run the installer to completion, keeping the default installation path. ;\
		echo ;\
		false )
	touch java.check

.PHONY: clean
clean:
	rm -f *.check *.class *.txt

%.class: %.java java.check
	javac "$<"

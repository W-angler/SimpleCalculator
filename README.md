# SimpleCalculator
A simple calculator for compiler principle

## Syntax

	<program>::= (<assign>|<print>)*
	<assign>::= VARIABLE "=" <expression> ";"
	<print>::= PRINT "(" <expression> ")" ";"
	<expression>::= <factor> (+|-|*|/|%|**) <factor>
	<factor>::= OPRAND | "(" <expression> ")" | VARIABLE
	VARIABLE::= [a-zA-Z][a-zA-Z0-9]*
	PRINT::= "print"
	OPRAND::= INT | DOUBLE
	INT::= [0-9]+(_[0-9]+)* | 0(x|X)[0-9a-fA-F]+
	DOUBLE::= [0-9]+(_[0-9]+)* "." [0-9]+(_[0-9]+)*
## Features

* A stack-based virtual machine
* A stack-based ISA
* Operator Precedence Analysis
* Abstract Syntax Tree
* Power operation
* Hexadecimal number
* Underscores in numeric literals

## Others

JSR 223 support can be found in branch [jsr223](https://github.com/W-angler/SimpleCalculator/tree/jsr223).
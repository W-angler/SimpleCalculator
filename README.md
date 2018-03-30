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

* Operator Precedence Analysis
* Abstract Syntax Tree
* Power operation
* Hexadecimal number
* Underscores in numeric literals
* JSR 223
Author: Peter Hess
CSC 173 - Project 2 - Parsing
Oct. 2017

Compile/Run Instructions: In terminal, navigate to folder containing project, then enter: 

>> make

This will generate and run the executable: a.out
To remove object files, enter: 

>> make clean


PROGRAM DESCRIPTION:

This program reads in arithmetic expressions from a text file, then generates parse trees for the expressions if it is valid. First, the program prints out a parse tree generated using parsing functions. Next, it prints out the parse tree generated from a table driven parser. Finally, it evaluates the parse tree and displays the value of the expression.

Note: If the expression is invalid, the program will not produce a parse tree and instead will print an error message. The valid terminals are: 0-9, +, -, *, /, (, )

Test expressions are provided in: testexpr.txt
Note that the last two expressions are invalid.
New expressions may be added to the file. They must each be on their own line and contain no whitespace.

The parse table and grammar production rules are in: AG.pdf
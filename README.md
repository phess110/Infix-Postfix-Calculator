# Infix-Postfix-Calculator

First argument should be a text file with properly formatted expressions. Program converts the expression on that line to postfix notation, before evaluting the expression. The values of the evaluated expressions are stored in an arraylist, until all expressions in the textfile have been processed, at which point the program writes the answers in the arraylist to the textfile provided as the second argument.
The expressions are converted to postfix using the shunting-yard algorthm. An operator class is used to define each valid operation and give its precedence and whether it is right-associative. The implementation supports the follow operations: 
+, - , *, parenthesis, ^, logic and/or/not, >, <, =, modulo, as well as sine, cosine, and tangent.

All operations follow the traditional order of operations. The program does incorporate error handling. For instance, the program auto-formats the given input string, so spacing doesn't affect output, so 2+3*4 yields the same output as 2 + 3 * 4. Furthermore, it will output: "Divide by 0 error", in place of an answer, if the given expression contains a division by 0. Additionally, if there are unmatched parenthesis, it will output: "Invalid expression. Make sure all parenthesis are balanced". Finally, the program will also print an IO Exception to the command prompt if the input/output file is not found.

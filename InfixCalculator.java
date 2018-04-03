/*
 * Peter Hess
 * March 2017
 * Infix Calculator
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InfixCalculator {

	/** Define operators.*/
	static Operator openPar = new Operator("(", 0, false);
	static Operator closePar = new Operator(")", 0, false);
	static Operator sin = new Operator("sin", 1, true);
	static Operator cos = new Operator("cos", 1, true);
	static Operator tan = new Operator("tan", 1, true);
	static Operator lNot = new Operator("!", 1, true);
	static Operator exp = new Operator("^", 1, true);
	static Operator divide = new Operator("/", 2, false);
	static Operator multiply = new Operator("*", 2, false);
	static Operator modulo = new Operator("%", 2, false);
	static Operator add = new Operator("+", 3, false);
	static Operator sub = new Operator("-", 3, false);
	static Operator lessThan = new Operator("<", 4, false);
	static Operator greaterThan = new Operator(">", 4, false);
	static Operator equals = new Operator("=", 5, false);
	static Operator lAnd = new Operator("&", 6, false);
	static Operator lOr = new Operator("|", 7, false);

	static final Operator[] operations = { openPar, closePar, sin, cos, tan, lNot, exp, divide, multiply, modulo, add,
			sub, lessThan, greaterThan, equals, lAnd, lOr };

	private Stack<String> myStack;
	private Queue<String> myQueue;
	public ArrayList<String> answers; // Corresponding values of each expression

	public InfixCalculator() {
		myStack = new Stack<>();
		myQueue = new Queue<>();
		answers = new ArrayList<>();
	}

	/**
	 * Method will read text file line by line. Storing each line as a String
	 * array of operands and operators. String array is then converted to
	 * postfix, before being evaluated and stored in answers.
	 * 
	 * @param fileName
	 *            text file to be read.
	 * @throws Exception 
	 */
	public void readIn(String fileName) throws IOException {
		String linetoRead = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((linetoRead = bufferedReader.readLine()) != null) {

				String temp = linetoRead;
				toPostfix(whitespace(temp).split(" ")); // Convert expression to postfix. Store in queue.
				answers.add(postfixEval()); // Evaluate expression.
			}
			bufferedReader.close();
		} catch (IOException ex) {
			throw new IOException("Input error - possible file not found. IOException thrown."); //Possible FileNotFound, must throw exception
		}
	}

	/*
	 * Converts tokens array to postfix, storing expression in myQueue.
	 * See shunting-yard algorithm.
	 */
	private void toPostfix(String[] tokens) {
		for (String i : tokens) {
			if (i.equals("(")) {
				myStack.push(i);
			} else if (i.equals(")")) {
				while (!myStack.peek().equals("(")) {
					myQueue.enqueue(myStack.pop());
				}
				myStack.pop();
			} else if (isOperator(i)) {
				Operator temp = getOperator(i);
				while (!myStack.isEmpty() && !myStack.peek().equals("(")) {
					Operator temp2 = getOperator(myStack.peek());
					if (temp.isRightAssoc && temp.preced > temp2.preced) {
						myQueue.enqueue(myStack.pop());
					} else if (temp.preced > temp2.preced) {
						myQueue.enqueue(myStack.pop());
					} else
						break;
				}
				myStack.push(i);
			} else {
				myQueue.enqueue(i);
			}
		}
		while (!myStack.isEmpty()) {
			myQueue.enqueue(myStack.pop());
		}
	}

	/**
	 * @param orig:
	 *            Unformatted expression
	 * @return Adds whitespace before/after an operation symb, to ensure correct splitting.
	 */
	public static String whitespace(String orig) {
		orig = orig.replace(" ", "");
		for (Operator op : operations) {
			orig = orig.replace(op.symbol, " " + op.symbol + " ");
		}
		return orig.replace("  ", " ").trim();
	}

	/**
	 * @param symb
	 * @return True if symb is in operations, else false.
	 */
	public static boolean isOperator(String symb) {
		for (Operator op : operations) {
			if (symb.equals(op.symbol)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param symb
	 * @return Returns operator in operations with given symb
	 */
	public static Operator getOperator(String symb) {
		for (Operator op : operations) {
			if (symb.equals(op.symbol)) {
				return op;
			}
		}
		// Unreachable, method only called if isOperator(symb) returns true
		return null;
	}

	/**
	 * Evaluates expression in myQueue.
	 * @return value of the expression.
	 */
	private String postfixEval(){

		while (!myQueue.isEmpty()) {
			String temp = myQueue.dequeue();
			if (!isOperator(temp)) { // Add numbers to stack.
				myStack.push(temp);
			} else {
				Double a, b, c;
				// Perform the correct operation, when applicable.
				switch (temp) {
				case "+":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					c = b + a;
					myStack.push(String.valueOf(c));
					break;
				case "-":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					c = b - a;
					myStack.push(String.valueOf(c));
					break;
				case "*":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					c = b * a;
					myStack.push(String.valueOf(c));
					break;
				case "/":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if (a.equals(0.0)) {
						return "Divide by 0 error";
					}
					c = b / a;
					myStack.push(String.valueOf(c));
					break;
				case "^":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					c = Math.pow(b, a);
					myStack.push(String.valueOf(c));
					break;
				case "&":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if (a == 1. && b == 1.)
						c = 1.0;
					else
						c = 0.0;
					myStack.push(String.valueOf(c));
					break;
				case "!":
					a = Double.parseDouble(myStack.pop());
					if (a == 1.)
						c = 0.0;
					else
						c = 1.0;
					myStack.push(String.valueOf(c));
					break;
				case "|":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if (a == 1 || b == 1)
						c = 1.0;
					else
						c = 0.0;
					myStack.push(String.valueOf(c));
					break;
				case "%":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					c = b % a;
					myStack.push(String.valueOf(c));
					break;
				case "<":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if(b < a)
						c = 1.0;
					else
						c = 0.0;
					myStack.push(String.valueOf(c));
					break;
				case ">":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if(b > a)
						c = 1.0;
					else
						c = 0.0;
					myStack.push(String.valueOf(c));
					break;
				case "=":
					a = Double.parseDouble(myStack.pop());
					b = Double.parseDouble(myStack.pop());
					if(Math.abs(a-b) < 0.0000001)	//Account for round-off error
						c = 1.0;
					else
						c = 0.0;
					myStack.push(String.valueOf(c));
					break;
				case "sin":
					a = Double.parseDouble(myStack.pop());
					c = Math.sin(a);
					myStack.push(String.valueOf(c));
					break;
				case "cos":
					a = Double.parseDouble(myStack.pop());
					c = Math.cos(a);
					myStack.push(String.valueOf(c));
					break;
				case "tan":
					a = Double.parseDouble(myStack.pop());
					c = Math.tan(a);
					myStack.push(String.valueOf(c));
					break;
				default:
					return "Invalid expression. Make sure all parenthesis are balanced.";
				}
			}
		}
		return myStack.pop();
	}

	/**
	 * Writes expr. values to a textfile.
	 * @param fileName
	 *            text file where answers are stored
	 * @throws IOException 
	 */
	public void writeToFile(String fileName) throws IOException {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// write to file line by line
			for (int i = 0; i < (answers).size(); i++) {
				bufferedWriter.write(answers.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException ex) {
			throw new IOException("Output error - possible file not found. IOException thrown."); //Possible FileNotFound, must throw Exception
		}

	}

	public static void main(String[] args) throws Exception {
		InfixCalculator calc = new InfixCalculator();
		if (args.length >= 0) {
			// read text file
			calc.readIn(args[0]);

			// write answers
			calc.writeToFile(args[1]);
			System.out.println("Output is stored in: " + args[1]);
		}
	}
}

public class Test {
	static Stack<String> myStack = new Stack<>();
	static Queue<String> myQueue = new Queue<>();

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
	static Operator plus = new Operator("+", 3, false);
	static Operator sub = new Operator("-", 3, false);
	static Operator lessThan = new Operator("<", 4, false); ////////////////////////////////
	static Operator greaterThan = new Operator(">", 4, false);///////////////////////
	static Operator equals = new Operator("=", 5, false);////////////////////////
	static Operator lAnd = new Operator("&", 6, false);
	static Operator lOr = new Operator("|", 7, false);

	static final Operator[] operations = { openPar, closePar, sin, cos, tan, lNot, exp, divide, multiply, modulo, plus,
			sub, lessThan, greaterThan, equals, lAnd, lOr };

	public static void main(String[] args) throws Exception {
		String temp = "2 a+ 1";
		//String [] S = whitespace(temp).split(" ");
		//for(String st: S)
		//	System.out.println(st);
		toPostfix(whitespace(temp).split(" "));
		//while(!myQueue.isEmpty())
		//	System.out.println(myQueue.dequeue());
		Double eval = postfixEval();
	    System.out.println(eval);
	}
	
	public static String whitespace(String orig){
		orig  = orig.replace(" ", "");
		for(Operator op: operations){
			orig  = orig.replace(op.symbol, " " + op.symbol + " ");
		}
		return orig.replace("  ", " ").trim();
	}

	private static void toPostfix(String[] tokens) throws Exception{
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

	private static Double postfixEval() throws Exception {

		while (!myQueue.isEmpty()) {
			String temp = myQueue.dequeue();
			if (!isOperator(temp)) {
				myStack.push(temp);
			} else {
				Double a;
				Double b, c;
				// Decides and performs the correct operation.
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
						throw new Exception("Divide by 0");
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
					throw new Exception("Invalid expression");
				}
			}
		}
		return Double.parseDouble(myStack.pop());
	}

	public static boolean isOperator(String symb) {
		for (Operator op : operations) {
			if (symb.equals(op.symbol)) {
				return true;
			}
		}
		return false;
	}

	public static Operator getOperator(String symb) {
		for (Operator op : operations) {
			if (symb.equals(op.symbol)) {
				return op;
			}
		}
		// Unreachable
		return null;
	}
}

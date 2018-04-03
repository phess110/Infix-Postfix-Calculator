/*
 * Peter Hess
 * March 2017
 * Operator class
 */

public class Operator {
	public String symbol;			//Character representing the operator.
	public int preced;				//Precedence of the operator
	public boolean isRightAssoc;
	
	public Operator(String str, int val, boolean isRA){
		symbol = str;
		preced = val;
		isRightAssoc = isRA;
	}
}

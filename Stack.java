/*
 * Peter Hess
 * March 2017
 * Stacks
 */

public class Stack<E> {
	private MyLinkedList<E> theStack;
	private int size;
	
	public Stack(){
		theStack = new MyLinkedList<E>();
		size = 0;
	}
	
	public int getSize(){
		return this.size;
	}
	
	/**
	 * @return True if list is empty, false otherwise.
	 */
	public boolean isEmpty(){return theStack.isEmpty();}
	
	/**
	 * Add item to top of stack.
	 */
	public void push(E x){
		theStack.insertDupl(x);
		size++;
	}
	
	/**
	 * Remove item from top of stack.
	 * @return item removed.
	 */
	public E pop(){
		if(theStack.isEmpty())
			return null;
		E x = theStack.getData();
		theStack.delete();
		size--;
		return x;
	}
	
	/**
	 * @return item on top of stack.
	 */
	public E peek(){
		if(theStack.isEmpty())
			return null;
		return theStack.getData();}
}

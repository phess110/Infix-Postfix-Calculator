/*
 * Peter Hess
 * March 2017
 * Queues
 */

public class Queue<E> {
	private DoubleLinkedList<E> myQueue;
	
	public Queue(){
		myQueue = new DoubleLinkedList<>();
	}
	
	/**
	 * @return true if queue is empty. 
	 */
	public boolean isEmpty(){return myQueue.isEmpty();}
	
	/**Adds item to end of queue.*/
	public void enqueue(E item){myQueue.insert(item);}
	
	/**
	 * Removes item from front of non-empty queue.
	 * @return item removed.
	 */
	public E dequeue(){
		if(myQueue.isEmpty()){
			return null;	
		}
		E item = myQueue.getFirst().next.data;
		myQueue.delete();
		return item;	
	}
	
	/**
	 * @return Data stored in first node in the queue.
	 */
	public E peek(){return myQueue.getFirst().next.data;}
}

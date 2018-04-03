/*
 * Peter Hess
 * March 2017
 */

public class DoubleLinkedList<E> {
	
	@SuppressWarnings("hiding")
	class MyDoubleNode<E> {
		public E data;
		public MyDoubleNode<E> next;
		public MyDoubleNode<E> prev;
		
		public E getData(){return data;}
	} 
	
	private MyDoubleNode<E> first;
    private MyDoubleNode<E> last;

    public DoubleLinkedList(){
        first =  new MyDoubleNode<E>();
        last = new MyDoubleNode<E>();
        first.next = last;
        last.prev = first;
    }

    public MyDoubleNode<E> getFirst(){
    	return first;
    }
    
    public MyDoubleNode<E> getLast(){
    	return last;
    }
    
    /*
     * Inserts new node to end of list.
     */
    public void insert(E x){
        MyDoubleNode<E> ins = new MyDoubleNode<E>();
        ins.data = x;
        ins.prev = last.prev;
        ins.next = last;
        (last.prev).next = ins;
        last.prev = ins;
        
    }

    /*
     * Deletes first node in list, i.e. node adjacent to first. Use for queues.
     */
    public void delete(){
    	(first.next.next).prev = first;
        first.next = (first.next).next;  
    }
    
    /*
     * Deletes existing node from list with given data.
     */
    public void delete(E x){
        MyDoubleNode<E> iter = first.next;
        while(iter.next != null){
            if((iter.data).equals(x)){
                (iter.prev).next = iter.next;
                (iter.next).prev = iter.prev;
                return;
            }
            iter = iter.next;
        }
    }

    /*
     * Searches list for given data. 
     * Returns true if present, false otherwise.
     */
    public boolean contains(E x){
        MyDoubleNode<E> iter = first.next;
        while(iter.next != null){
            if((iter.data).equals(x))
                return true;
            iter = iter.next;
        }
        return false;
    }

    /*
     * Searches list for given data. 
     * Returns data is present, null otherwise.
     */
    public E lookup(E x){
        MyDoubleNode<E> iter = first.next;
        while(iter.next != null){
            if((iter.data).equals(x))
                return iter.data;
            iter = iter.next;
        }
        return null;
    }

    /*
     * Returns true if list is empty, false otherwise.
     */
    public boolean isEmpty(){
        return first.next == last;
    }

    /*
     * Prints contents of list.
     * Runs in O(n), n = size of list.
     */
    public void printList(){
        MyDoubleNode<E> iter = first.next;
        while(iter.next != null){
            System.out.print(iter.data + " ");
            iter = iter.next;
        }
        System.out.println();
    }

    /*
     * Prints contents of list in reverse.
     * Runs in O(n), n = size of list.
     */
    public void printListRev(){
        MyDoubleNode<E> iter = last.prev;
        while(iter.prev != null){
            System.out.print(iter.data + " ");
            iter = iter.prev;
        }
        System.out.println();
    }

}

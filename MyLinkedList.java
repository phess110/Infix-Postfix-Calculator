/*
 * Peter Hess
 * March 2017
 */

public class MyLinkedList<E> {
	
	@SuppressWarnings("hiding")
	class Link<E>{
		public E data;
		public Link<E> next;
		
		public E getData(){return data;}
	}
	
	private Link<E> head;
	
	public MyLinkedList(){
		head = null;
	}
	
	public boolean isEmpty(){
		return head == null;
	}
	
	/**
	 * @return data stored in head of list.
	 */
	public E getData(){
		return head.data;
	}
	
    /*
     * Inserts new node to front of list with data x. Use for stacks.
     * O(1)
     */
	public void insertDupl(E x){
		Link<E> newLink = new Link<E>();
		newLink.data = x;
		        if(head == null) 		//Case where list is empty.
		            head = newLink;
		        else{					//Case where list is non-empty.
		            newLink.next = head;
		            head = newLink;
		        }
	}
	
	/*
     * Inserts new node to front of list with data x, unless it's already in the list.
     * O(n)
     */
    public void insert(E x){
        if(this.contains(x)) 	//Don't insert if list contains x.
            return;
        Link<E> newLink = new Link<E>();
        newLink.data = x;
        if(head == null) 		//Case where list is empty.
            head = newLink;
        else{					//Case where list is non-empty.
            newLink.next = head;
            head = newLink;
        }
    }
    
    /*
     * Deletes node at front of list. Does nothing if list is empty. Use for stacks.
     * O(1)
     */
    public void delete(){
        head = head.next;
    }
    
    /*
     * Deletes node from list with given data. Does nothing if not present.
     */
    public void delete(E x){
        if(this.isEmpty()) 					//Exits if list is empty.
            return;
        if(x.equals(head.data)){ 			//Case where element to be removed is at the front of list.
            head = head.next;
            return;
        }
        Link<E> iter = head;
        while((iter.next).next != null){ 	//Traverses rest of list, deleting if found.
            if(x.equals((iter.next).data)){
                iter.next = (iter.next).next;
                return;
            }
            iter = iter.next;
        }
        if(x.equals(iter.next.data)) 		//Case where element to be removed is at the end of list.
        	 iter.next = (iter.next).next;
    }

    /*
     * Returns true if x (data) is found in the list, false otherwise.
     */
    public boolean contains(E x){
        Link<E> iter = head;
        while(iter != null){ 
            if((iter.data).equals(x))
                return true;
            iter = iter.next;
        }
        return false;
    }

    /*
     * Returns data of the node containing x (data) if found, null otherwise.
     */
    public E lookup(E x){
        Link<E> iter = head; //Prevents changes to head for some reason
        while(iter != null){
            if(x.equals(iter.data)){
                return iter.data;
            }
            iter = iter.next;
        }
        return null;
    }

    /*
     * Prints contents of the list.
     */
    public void printList(){
        Link<E> iter = head;
        while(iter != null){
            System.out.print(iter.data + " ");
            iter = iter.next;
        }
        System.out.println();
    }
}



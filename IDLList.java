import java.util.ArrayList;
import java.util.NoSuchElementException;

public class IDLList<E> {
	
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	
	private class Node<E> {
		
		E data;
		Node<E> next;
		Node<E> prev;
		
		Node(E elem) {
			
			this.data = elem;
			this.next = null;
			this.prev = null;
		}

		Node(E elem, Node<E> prev, Node<E> next) {
			
			this.data = elem;
			this.next = next;
			this.prev = prev;
		}
		
	}
	
	//method that creates an empty double-linked list.
	public IDLList() {
		 
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.indices = new ArrayList<>();
		
	}
	
	// adds element at position index (counting from wherever head is)
	public boolean add(int index, E elem) {
		// throw exception if index is out of bonds
		if(index < 0 || index > size) {
			
			throw new IndexOutOfBoundsException();
		}
		else {
			// if index equal 0 call add method adding element at head
			if(index == 0) {
				add(elem);
			}
			else if(index == size) {
				//if index equals size insert at tail
				append(elem);
			}
			else {
				/* create two nodes: one node at index and other one
				 * with current given element,prev and next pointers
				 * update prev and next pointers, and add node at index
				 * position, after update nodes at index positions
				 * increase size of array
				 */
				Node<E> currNode = indices.get(index);
				Node<E> newNode = new Node<E>(elem,currNode.prev,currNode);
				newNode.prev.next = newNode;
				currNode.prev = newNode;
				indices.add(index,newNode);
				indices.set(index - 1, newNode.prev);
				indices.set(index+1, currNode);
				size++;	
			}
			
			return true;
		}	
	}
	
	// adds element at the head, becomes the first element of the list
	public boolean add(E elem) {
		// create new node
		Node<E> newNode = new Node<E>(elem);
		//if head is null, assign new node to head and tail
		if(head == null) {
			head = newNode;
			tail = newNode;
			indices.add(newNode);
			size++;
		}
		else {
			// set prev pointer to head, update first node, increase size
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			indices.add(0,newNode);
			size++;
			
		}
		
		return true;
	}
	
	// adds element as the new last element (i.e. at the tail)
	public boolean append(E elem) {
		// create new node with element
		Node<E> newNode = new Node<E>(elem);
		// if tail is null assign new node to tail and head
		if(tail == null) {
			tail = newNode;
			head = newNode;
			indices.add(newNode);
			size++;
		}
		else {
			//update tail next pointer to new node
			//update tail in indices, set tail to node
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
			indices.add(tail);
			size++;	
		}
		
		return true;
		
	}
	
	// returns the object at position index from the head
	public E get(int index) {
		// check if index is valid
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			// return the node data at index
			Node<E> getNode = indices.get(index);
			return getNode.data;
		}
	}
	
	// returns the object at the head
	public E getHead() {
		// if head is null throw exception,otherwise return head
		if(head == null) {
			throw new NullPointerException();
		}
		else {
			return head.data;
		}
	} 
	
	// returns the object at the tail
	public E getLast() {
		// if tail is null,throw exception, otherwise return tail
		if(tail==null) {
			throw new NullPointerException();
		}
		else {
			return tail.data;
		}	
	} 
	
	// returns the list size.
	public int size() {
		
		if(size == 0) {
			throw new NullPointerException();
		}
		else {
			
			return size;
		}
		
	} 
	
	// removes and returns the element at the head
	public E remove() {
		// check if head not null
		if(head == null) {
			throw new NullPointerException();
		}
		// if tail equal head assign it to null
		if(tail == head) {
			Node<E> temp = head;
			head = null;
			tail = null;
			size--;
			return temp.data;
		}
		//set head to node next to head
		//update prev and set to null, decrease size
		head = head.next;
		head.prev = null;
		size--;
		
		return indices.remove(0).data;

	} 
	
	// removes and returns the element at the tail
	public E removeLast() {
		// check if tail not null
		if(tail == null) {
			
			throw new NullPointerException();
		}
		//if size equal to 1, update tail and head
		else if(size == 1) {
			
			E temp = tail.data;
			indices.remove(size - 1);
			tail = null;
			head = null;
			size--;
			return temp;
		}
		else {
			//update pointers of tail, decrease size
			tail = tail.prev;
			tail.next = null;
		}
		size--;
		
		return indices.remove(size).data;
	} 
	
	
	// removes and returns the element at the index index
	public E removeAt(int index) {
		// check head and tail if not null
		//validate index positions
		if(head == null || tail == null) {
			
			throw new NullPointerException();
		}
		else if(index < 0 || index >= size) {
			
			throw new IndexOutOfBoundsException();
		}
		else {
			// call method remove at head, if index is 0
			if(index == 0) {
				
				return remove();
			}
			//call method remove tail, if index is last
			else if(index == size - 1) {
				
				return removeLast();
			}
			else {
				/* create node at index, update prev pointer
				 * of new node at index, update next pointer
				 * of node at index - 1, to next node
				 * remove node from list
				 * decrease size
				 */
				Node<E> newNode = indices.get(index);
				indices.remove(index);
				indices.get(index).prev = newNode.prev;
				indices.get(index - 1).next = newNode.next;
				newNode.next = null;
				newNode.prev = null;
				size--;
				
				return newNode.data;
				
			}	
		}
	} 
	
	// removes the first occurrence of element in the list and returns true
	public boolean remove(E elem) {
		// iterate through indices, if element in it remove
		// and return true, otherwise return false
		for(int i = 0; i < indices.size();i++) {
			if(indices.get(i).data.equals(elem)) {
				removeAt(i);
				
				return true;
			}
		}
		
		return false;
		
		
	} 
	
	// presents a string representation of the list
	public String toString() {
		// if list is empty, output message
		if(size == 0) {
			System.out.println("List is empty!");
		}
		// create string, create node, loop over list
		String str = "";
		Node<E> curr = head;
		
		while(curr!=null) {
			
			str = str + curr.data.toString();
			if(curr!=tail) {
				str = str + " --> ";
			}
			curr = curr.next;
		}
		
		return str;
	} 
}

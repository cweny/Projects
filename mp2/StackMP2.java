/*
 * Implementation of a simple stack for HtmlTags.
 * You should implement this class.
 */

package MP2;

import java.util.ArrayList;

public class StackMP2 {
	// An ArrayList to hold HtmlTag objects.
	// Use this to implement StackMP2.
	private ArrayList<HtmlTag> stack_internal;
	
	/*
	 * Create an empty stack.
	 * 
	 * @param none. Initializes this arraylist to a new empty arraylist
	 * @return none
	 * @throws none
	 */
	public StackMP2( ) {
		this.stack_internal = new ArrayList<HtmlTag>( );
	}
	
	/* 
	 * Push a tag onto the top of the stack.
	 * 
	 * @param tag the HtmlTag to be inserted at the top of the stack
	 * @return none
	 * @throws none
	 */
	public void push( HtmlTag tag ) {
		stack_internal.add(tag);
	}
	
	/*
	 * Removes the tag at the top of the stack and then returns it.
	 * Should throw an exception if the stack is empty.
	 * 
	 * @param none. This arraylist gets modified; it loses its last element inserted
	 * @return the tag that is being removed from the stack.
	 * @throws IndexOutOfBoundsException	 
	 */
	public HtmlTag pop( ) {
			HtmlTag stored = stack_internal.get(stack_internal.size() - 1);
			stack_internal.remove(stack_internal.size() - 1);
			return stored;
		
	}
	
	/*
	 * Looks at the object at the top of the stack but does not actually remove the object. 
	 * Should throw an exception if the stack is empty.
	 * 
	 * @param none.
	 * @return The HtmlTag at the top of the stack
	 * @throws IndexOutOfBoundsException
	 */
	public HtmlTag peek() {
			return stack_internal.get(stack_internal.size() - 1);
		
	}
	
	/*
	 * Tests if the stack is empty.
	 * Returns true if the stack is empty; false otherwise.
	 * 
	 * @param none
	 * @return true if the stack is empty, false otherwise
	 * @throws none
	 */
	public boolean isEmpty( ) {
		if(stack_internal.size() == 0)
			return true;
		
		else
			return false;
	}
}

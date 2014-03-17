package mp2;

import java.util.Queue;
import java.util.LinkedList;

public class HtmlValidator {
	
	Queue<HtmlTag> internal_tags;
	
	/*Initialize validator to store empty queue of tags
	 *@param none
	 *@return none. modifies internal_tags to a new LinkedList queue of HtmlTags
	 *@throws none
	 */
	public HtmlValidator() {
		internal_tags = new LinkedList<HtmlTag>();
	}
	
	/*Initializes validater with entirely separate copy of queue that was passed in. 
	 *@param tags: Queue to initialize this queue
	 *@return none.
	 *@throws if tags has a null reference, an exception is thrown
	 */
	public HtmlValidator(Queue<HtmlTag> tags) {
		
		if(tags == null)
			throw new IllegalArgumentException ("Queue passed is empty.");
	
	
		internal_tags = new LinkedList<HtmlTag>();
		int size_tags = tags.size();

		Queue<HtmlTag> tempQueue = new LinkedList<HtmlTag>();
		
		for(int i=0; i < size_tags; i++){

			internal_tags.add(new HtmlTag(tags.peek().getElement(), tags.peek().isOpenTag()));
			tempQueue.add((HtmlTag)tags.remove());
		}
		
		for(int i=0; i < size_tags; i++){
			tags.add((HtmlTag)tempQueue.remove());
		}
		

	}

	// Adds the tag passed in the parameter to the queue
	 /*
	 *@param tag: HtmlTag object to add. tag must not have a null reference
	 *@return none. modifies internal_tags by adding tag at the end of this queue
	 *@throws if tag has a null reference, an exception is thrown
	 */
	public void addTag(HtmlTag tag){
		if(tag == null)
			throw new IllegalArgumentException ("No tag passed!");
			
		internal_tags.add(tag);
	}
	
	// Return the validator's queue of HTML tags
	/*
	 * @param none
	 * @return reference to this queue 
	 * @throws none
	 */
	public Queue<HtmlTag> getTags(){
		return internal_tags;	
	}
	
	// Remove from the validator's queue *any* tag mathing the imput string "element" 
	/*
	 * @param element: string to find in the queue. String must not have a null reference. elements in this queue that matches the string are removed
	 * @return none
	 * @throws if element refers to a null argument, an exception is thrown
	 */

	public void removeAll(String element){
		if(element == null)
			throw new IllegalArgumentException ("String is null.");
		int size = internal_tags.size();
		Queue<HtmlTag> temporary = new LinkedList<>();
		
		for(int i=0; i < size; i++){
				
			if(internal_tags.peek().getElement() == element)
				internal_tags.remove();
			else
				temporary.add(internal_tags.remove());	
		}
		if(size != internal_tags.size()){
			size = temporary.size();
			for(int i=0; i<size ;i++){
				internal_tags.add(temporary.remove());
			}
		}
	}
	
	/* Prints an indented text representation of the HTML tags in this queue. 
	 * Each tag is displayed on its own line and increases the indent - unless it is a self-closing tag. 
	 * A closing tag is needed to decrease the indent.
	 * @param none 
	 * @return none
	 * @throws none
	 */
	public void validate() {
		int IndentLevel = 0;
		StackMP2 validationstack = new StackMP2();
		
		for (int i=0;i<internal_tags.size();i++){
		
				if (internal_tags.peek().isOpenTag() == true && internal_tags.peek().isSelfClosing() == false){
				validationstack.push(internal_tags.peek());
				IndentLevel++;
				
				for (int j=1; j < IndentLevel; j++){
					System.out.print("    ");
				}
				System.out.println (internal_tags.peek());
			}
		
			
			 //Compare closing tag with the most recent opening tag,
			 //If tags match indentation goes down
			 
			if (internal_tags.peek().isOpenTag() == false){
				if(internal_tags.peek().getElement().equals( validationstack.peek().getElement())){
					for (int j=1;j<IndentLevel;j++){
						System.out.print("    ");
					}
					
				System.out.println( internal_tags.peek() );
				validationstack.pop();
				IndentLevel--;
				}
				
				else {
					System.out.println("Tag is UNEXPECTED error: " + internal_tags.peek());
				}
			}
			
			
			//Indentation for self-closing tag 
			if ( internal_tags.peek().isSelfClosing() == true ){
				for ( int j=1; j < IndentLevel; j++ ) {
					System.out.print("    ");
				}
				System.out.println ( internal_tags.peek() );
			}
		
			internal_tags.add(internal_tags.remove());
		
		}
		
		//Takes care of error of stack being empty. Prints  tags that aren't closed.
		while ( validationstack.isEmpty() == false ){
			System.out.println( "Tag is NOT closed error: " + validationstack.pop() );
		}
	
	}
	
	
}

package MP2;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;


public class StackMP2Test extends TestCase {
	
	@Test
	public void testStackMP2(){
		StackMP2 test = new StackMP2();
		assertEquals(true, test.isEmpty());
	}
	
	@Test
	public void testpeek(){
		StackMP2 test = new StackMP2();
		HtmlTag tag = new HtmlTag("b");
		test.push(tag);
		assertEquals(tag.getElement(),test.peek().getElement());
		HtmlTag tag2 = new HtmlTag("title");
		test.push(tag2);
		assertEquals(tag2.getElement(), test.peek().getElement());
	}

	@Test
	public void testpush() {
		StackMP2 test2 = new StackMP2();
		HtmlTag tag2 = new HtmlTag("html");
		test2.push(tag2);
		assertEquals(tag2.getElement(),test2.peek().getElement());
	}

	
	@Test
	public void testpop(){
		StackMP2 test3 = new StackMP2();
		HtmlTag tag4 = new HtmlTag("body");
		HtmlTag tag3 = new HtmlTag("head");
		HtmlTag tag = new HtmlTag("title");
		test3.push(tag);
		test3.push(tag3);
		test3.push(tag4);
		test3.pop();
		assertEquals(tag3.getElement(),test3.peek().getElement());
		test3.pop();
		assertEquals(tag.getElement(),test3.peek().getElement());
	}
	
	@Test
	public void testisEmpty(){
		StackMP2 test4 = new StackMP2();
		assertEquals(true,test4.isEmpty());
		HtmlTag tag4 = new HtmlTag("title");
		test4.push(tag4);
		assertEquals(false,test4.isEmpty());
	}


}

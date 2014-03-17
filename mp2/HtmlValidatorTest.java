package MP2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import junit.framework.TestCase;

import org.junit.Test;


public class HtmlValidatorTest extends TestCase {
	//public void main(String[] args){;}
	/*
	@Test 
	public void test() {
		fail("Not yet implemented");
	}
	*/
	
	@Test
	public void testHtmlValidator(){
		HtmlTag tag = new HtmlTag("html");
		HtmlTag tag2 = new HtmlTag("title");
		HtmlTag tag3 = new HtmlTag("a");
		HtmlTag tag4 = new HtmlTag("p");
		HtmlTag tag5 = new HtmlTag("link");
		
		Queue<HtmlTag> myqueue = new LinkedList<HtmlTag>();
		
		myqueue.add(tag);
		myqueue.add(tag2);
		myqueue.add(tag3);
		myqueue.add(tag4);
		myqueue.add(tag5);
		
		HtmlValidator test = new HtmlValidator(myqueue);
		assertEquals(tag.getElement(), test.internal_tags.peek().getElement());
		
		HtmlValidator test2 = new HtmlValidator();
		assertEquals(0, test2.internal_tags.size());
	}
	
	@Test
	public void testgetTags(){
		Queue<HtmlTag> stack ;
		HtmlValidator test = new HtmlValidator();
		HtmlTag tag = new HtmlTag("html");
		HtmlTag tag2 = new HtmlTag("title");
		HtmlTag tag3 = new HtmlTag("a");
		HtmlTag tag4 = new HtmlTag("p");
		HtmlTag tag5 = new HtmlTag("link");
		test.addTag(tag);
		test.addTag(tag2);
		test.addTag(tag3);
		test.addTag(tag4);
		test.addTag(tag5);
		stack = test.getTags();
		assertEquals(true,stack.contains(tag));
		assertEquals(true,stack.contains(tag2));
		assertEquals(true,stack.contains(tag3));
		assertEquals(true,stack.contains(tag4));
		assertEquals(true,stack.contains(tag5));
	}

	@Test
	public void testaddTag(){
		Queue<HtmlTag> stack;
		HtmlValidator test = new HtmlValidator();
		HtmlTag tag = new HtmlTag("html");
		test.addTag(tag);
		stack = test.getTags();
		assertEquals(true,stack.contains(tag));
	}

	@Test
	public void testremoveAll() {
		String s = "title";
		Queue<HtmlTag> stack;
		HtmlValidator test = new HtmlValidator();
		HtmlTag tag = new HtmlTag("html");
		HtmlTag tag2 = new HtmlTag("title");
		HtmlTag tag3 = new HtmlTag("a");
		HtmlTag tag4 = new HtmlTag("p");
		HtmlTag tag5 = new HtmlTag("link");
		test.addTag(tag);
		test.addTag(tag2);
		test.addTag(tag3);
		test.addTag(tag4);
		test.addTag(tag5);
		test.removeAll(s);
		stack = test.getTags();
		
		System.out.println(stack.peek().getElement());
		
		assertEquals(true,stack.contains(tag3));
		assertEquals(true,stack.contains(tag4));
		assertEquals(true,stack.contains(tag5));
		assertEquals(true,stack.contains(tag));
		assertEquals(false,stack.contains(tag2));
	}
	
}



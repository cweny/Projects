package mp5;

import static org.junit.Assert.*;

import org.junit.Test;

public class EdgeTest {


	@Test
	public void testGetOppVertex() {
		Graph graph = new Graph();
		Vertex v1 = new Vertex("v1", graph);
		Vertex a = new Vertex("a", graph);
		Edge e = new Edge("bookname", v1, a);
		assertEquals("a", e.getOppVertex(v1).getName());
		assertEquals("v1", e.getOppVertex(a).getName());
	}

	@Test
	public void testAddBook() {
		Graph graph = new Graph();
		Vertex v1 = new Vertex("v1", graph);
		Vertex a = new Vertex("a", graph);
		Edge e = new Edge("sdf", v1, a);
		
		e.addBook("xbfg");
		assertEquals("sdf", e.getName());
		e.addBook("hed");
		assertEquals("hed", e.getName());
	}

}

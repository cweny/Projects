package mp5;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class VertexTest {

	@Test
	public void testVertex() {

	}

	@Test
	public void testFind() {

	}

	@Test
	public void testAddComicBook() {
		Vertex v = new Vertex("v", new Graph());
		v.addComicBook("v");
		v.addComicBook("f");
		v.addComicBook("s");
		v.addComicBook("wxy");
		v.addComicBook("wxyz");
		v.addComicBook("e");
		v.addComicBook("rimh");
		v.addComicBook("ri");
		v.addComicBook("t");
		v.addComicBook("h");
		v.addComicBook("w");
		v.addComicBook("q");
		assertEquals(true, Vertex.isFirstAlphabetically(v.getBooks().get(2), v.getBooks().get(1)));
	}

	@Test
	public void testIsFirstAlphabetically() {
		assertEquals(true, Vertex.isFirstAlphabetically("b", "a"));
		assertEquals(true, Vertex.isFirstAlphabetically("ab", "a"));
		assertEquals(true, Vertex.isFirstAlphabetically("a", ""));
		assertEquals(false, Vertex.isFirstAlphabetically("A", "A"));
		assertEquals(false, Vertex.isFirstAlphabetically("A", "a"));
		assertEquals(false, Vertex.isFirstAlphabetically("ABcDe", "abcdE"));
		assertEquals(false, Vertex.isFirstAlphabetically("AB", "abc"));
		assertEquals(false, Vertex.isFirstAlphabetically("", ""));
		assertEquals(false, Vertex.isFirstAlphabetically("axed", "bxed"));
		assertEquals(true, Vertex.isFirstAlphabetically("zxcd", "zc"));
		assertEquals(false, Vertex.isFirstAlphabetically("sddf", "sfe"));
		assertEquals(true, Vertex.isFirstAlphabetically("a", "1"));
		assertEquals(true, Vertex.isFirstAlphabetically("m'shulla", "frost, carmilla"));
	}

	@Test
	public void testHasBook() {
		Vertex v = new Vertex("v", new Graph());
		v.addComicBook("v");
		v.addComicBook("f");
		v.addComicBook("s");
		v.addComicBook("wxy");
		v.addComicBook("wxyz");
		v.addComicBook("e");
		v.addComicBook("rimh");
		v.addComicBook("ri");
		v.addComicBook("t");
		v.addComicBook("h");
		v.addComicBook("w");
		v.addComicBook("q");
		assertEquals(true,v.hasBook("h"));
		assertEquals(true,v.hasBook("rimh"));
		assertEquals(true,v.hasBook("wxy"));
		assertEquals(false,v.hasBook("weqwe"));
		assertEquals(true,v.hasBook("q"));
		assertEquals(true,v.hasBook("s"));
	}

	@Test
	public void testAddEdge() {
		Graph graph = new Graph();
		Vertex v1 = new Vertex("v1", graph);
		Vertex a = new Vertex("a", graph);
		Vertex ab = new Vertex("ab", graph);
		Vertex b = new Vertex("b", graph);
		Vertex f = new Vertex("f", graph);
		Vertex s = new Vertex("s", graph);
		Vertex qre = new Vertex("qre", graph);
		
		v1.addEdge(new Edge("book name",  s, v1), s.getName());
		v1.addEdge(new Edge("book name", v1, b), b.getName());
		v1.addEdge(new Edge("book name", v1, qre), qre.getName());
		v1.addEdge(new Edge("book name", v1, a), a.getName());
		v1.addEdge(new Edge("book name", v1, ab), ab.getName());
		v1.addEdge(new Edge("book name", f , v1), f.getName());
		
		List<Edge> list = v1.getEdges();
		for(int i=0; i<list.size()-1; i++){
			assertEquals(true,Vertex.isFirstAlphabetically(list.get(i+1).getOppVertex(v1).getName(), list.get(i).getOppVertex(v1).getName()));
		}
	}

	@Test
	public void testGetEdgeTo() {
		Graph graph = new Graph();
		Vertex v1 = new Vertex("v1", graph);
		Vertex a = new Vertex("a", graph);
		Vertex ab = new Vertex("ab", graph);
		Vertex b = new Vertex("b", graph);
		Vertex f = new Vertex("f", graph);
		Vertex s = new Vertex("s", graph);
		Vertex qre = new Vertex("qre", graph);
		
		v1.addEdge(new Edge("book name",  s, v1), s.getName());
		v1.addEdge(new Edge("book name", v1, b), b.getName());
		v1.addEdge(new Edge("book name", v1, qre), qre.getName());
		v1.addEdge(new Edge("book name", v1, a), a.getName());
		v1.addEdge(new Edge("book name", v1, ab), ab.getName());
		v1.addEdge(new Edge("book name", f , v1), f.getName());
		
		assertEquals(null, v1.getEdgeTo(new Vertex("sd", graph)));
		assertEquals(null, v1.getEdgeTo(new Vertex("sad", graph)));
		assertEquals(null, v1.getEdgeTo(new Vertex("wqe", graph)));
		assertEquals(null, v1.getEdgeTo(new Vertex("dfg", graph)));
		Edge e = v1.getEdgeTo(b);
		assertEquals("v1", e.getOppVertex(b).getName());
		assertEquals("b", e.getOppVertex(v1).getName());
		
		e = v1.getEdgeTo(f);
		assertEquals("v1", e.getOppVertex(f).getName());
		assertEquals("f", e.getOppVertex(v1).getName());
		
		e = v1.getEdgeTo(f);
		assertEquals("v1", e.getOppVertex(f).getName());
		assertEquals("f", e.getOppVertex(v1).getName());
		
		e = v1.getEdgeTo(ab);
		assertEquals("v1", e.getOppVertex(ab).getName());
		assertEquals("ab", e.getOppVertex(v1).getName());
		
		e = v1.getEdgeTo(qre);
		assertEquals("v1", e.getOppVertex(qre).getName());
		assertEquals("qre", e.getOppVertex(v1).getName());
	}

}

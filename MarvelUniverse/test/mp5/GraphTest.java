package mp5;

import static org.junit.Assert.*;

package mp5;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class GraphTest {

	@Test
	public void testGraph() throws IOException {
		Graph graph = new Graph("list.txt");
		String pathString = "";
		
		
		List<Edge> path = graph.path("C","C");
		assertEquals(null, path);
		
		path = graph.path("A","C");
		Iterator<Edge> iter = path.iterator();
		while (iter.hasNext()) {
			pathString = pathString.concat(iter.next().getName());
		}
		assertEquals("e1c1", pathString);
		pathString = "";
		
		
		path = graph.path("C","A");
		iter = path.iterator();
		while (iter.hasNext()) {
			pathString = pathString.concat(iter.next().getName());
		}
		assertEquals("c1e1", pathString);
		pathString = "";
		
		path = graph.path("B","C");
		iter = path.iterator();
		while (iter.hasNext()) {
			pathString = pathString.concat(iter.next().getName());
		}
		assertEquals("b1c1", pathString);


		
		graph = new Graph("list2.txt");
		
		assertEquals("B",graph.central().getName());
	
		/*Graph graph = new Graph("labeled_edges.tsv");
		System.out.println("getting path...");
		ArrayList<Edge> list = (ArrayList<Edge>)graph.path("FROST, CARMILLA", "KILLRAVEN/JONATHAN R");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName());
		}*/
		//System.out.println(graph.central().getName());
		
	}
}

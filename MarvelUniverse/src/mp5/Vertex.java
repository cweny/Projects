package mp5;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	private List<Edge> edges;
	private List<String> comicBooks; 
	private String name;
	static int i =0;
	public String getName(){
		return name;
	}

	/**
	 * Constructs a new vertex 
	 * @param name of the vertex
	 * @param graph. graph where the vertex must be added
	 */
	public Vertex(String name, Graph graph){
		this.name = name;
		comicBooks = new ArrayList<String>();
		edges = new ArrayList<Edge>();
		graph.add(this);
		i++;
		System.out.println("created "+i+"vertices");
	}

	
	/**
	 * adds book to comicBook list
	 * @param name. title of book to add
	 */
	public void addComicBook(String name){

		int low = 0;
		int high = comicBooks.size()-1;
		int mid;
		
		mid = (low+high)/2;
		
		while(low <= high){
			mid = (low+high)/2;
			if(Vertex.isFirstAlphabetically(comicBooks.get(mid), name))
				high = mid-1;
			else if(Vertex.isFirstAlphabetically(name, comicBooks.get(mid)))
				low = mid+1;
			else
				return;
		}
		if(comicBooks.isEmpty())
			comicBooks.add(name);
		else{
			if(Vertex.isFirstAlphabetically(comicBooks.get(mid), name))//insert<graph.get(mid))
				comicBooks.add(mid, name);
			else if(mid < comicBooks.size())
				comicBooks.add(mid+1, name);
			else
				comicBooks.add(name);
		}
	}
	
	/**
	 * compares two strings
	 * @param s2
	 * @param s1
	 * @return true if s1 is first alphabetically. false otherwise
	 */
	public static boolean isFirstAlphabetically(String s2, String s1){
		s2 = s2.toLowerCase();
		s1 = s1.toLowerCase();
		int index = 0;
		if (s1.equals(s2)) return false;
		while(true){
			if(index >= s1.length()) return true;
			if(index >= s2.length()) return false;
			if(s1.charAt(index)<s2.charAt(index)) return true;
			if(s1.charAt(index)>s2.charAt(index)) return false;
			index++;
		}

	}
	
	/**
	 * 
	 * @param name. title of book to search
	 * @return true if book is present. false otherwise
	 */
	public boolean hasBook(String name){
		if(bookIndex(name)<0) return false;
		else return true;
	}
	
	/**
	 * 
	 * @param name. title of book to be searched
	 * @return -1 if there is no book. else returns the index of the book
	 */
	private int bookIndex(String name){
		int low=0;
		int high=comicBooks.size()-1;
		int mid;
		
		while(low<=high){
			mid = (low+high)/2;
			if(isFirstAlphabetically(comicBooks.get(mid),name)) 
				high = --mid;
			else if(isFirstAlphabetically(name,comicBooks.get(mid))) 
				low = ++mid;
			else
				return mid;
		}
		return -1;
	}
	
	
	/**
	 * 
	 * @param e edge to add
	 * @param name string containing the name of the opposite vertex
	 */
	public void addEdge( Edge e, String name){

		int low = 0;
		int high = edges.size()-1;
		int mid;
		
		mid = (low+high)/2;
		
		while(low <= high){
			mid = (low+high)/2;
			if(Vertex.isFirstAlphabetically(edges.get(mid).getOppVertex(this).getName(), name))
				high = mid-1;
			else if(Vertex.isFirstAlphabetically(name, edges.get(mid).getOppVertex(this).getName()))
				low = mid+1;
			else
				return;
		}
		if(edges.isEmpty())
			edges.add(e);
		else{
			if(Vertex.isFirstAlphabetically(edges.get(mid).getOppVertex(this).getName(), name))//insert<graph.get(mid))
				edges.add(mid, e);
			else if(mid < edges.size())
				edges.add(mid+1, e);
			else
				edges.add(e);
		}
	}
	
	/**
	 * 
	 * @param v vertex that connects to this
	 * @return the edge that connects this vertex to V. null if it does not exist
	 */
	public Edge getEdgeTo(Vertex v){
		int index = edgeIndex(v.getName());
		if(index<0) return null;
		else return edges.get(index);
	}
	
	/**
	 * 
	 * @param name. tile of the vertex that connects to this vertex
	 * @return index of edge that connects to the vertex whose title is name. -1 if the edge does not exist
	 */
	private int edgeIndex(String name){
		int low = 0;
		int high = edges.size()-1;
		int mid;
		
		if(edges.isEmpty()) return -1;

		
		while(low <= high){
			mid = (low+high)/2;
			if(isFirstAlphabetically(edges.get(mid).getOppVertex(this).getName(), name))
				high = --mid;
			else if(isFirstAlphabetically(name, edges.get(mid).getOppVertex(this).getName()))
				low = ++mid;
			else
				return mid;
		}
		return -1;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getBooks(){
		return comicBooks;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Edge> getEdges(){
		return edges;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((comicBooks == null) ? 0 : comicBooks.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vertex)) {
			return false;
		}
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!name.equals(other.getName())) {
			return false;
		}
		return true;
	}
}

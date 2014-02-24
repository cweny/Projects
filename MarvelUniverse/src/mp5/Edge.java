package mp5;

public class Edge{
	final private Vertex v1, v2;
	private String name;
	
	/**
	 * Constructs a new Edge
	 * @param name of the book that represents this Edge
	 * @param v1
	 * @param v2
	 */
	public Edge(String name, Vertex v1, Vertex v2){
		this.name = name; 
		this.v1 = v1;
		this.v2 = v2;
	}
	/**
	 * 
	 * @return name of the book that represents this edge
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * returns the opposite vertex to the one specified
	 * @param V vertex opposite to the one returned
	 * @return V1 vertes opposite to the parameter
	 */
	public Vertex getOppVertex(Vertex V){
		if(V.getName().equals(v1.getName())) return v2;
		else return v1;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}

	/**
	 * @ returns true if the Edges are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Edge)) {
			return false;
		}
		Edge other = (Edge) obj;
		if (v1 == null) {
			if (other.getOppVertex(v2) != null) {
				return false;
			}
		} else if (!v1.equals(other.getOppVertex(v2))) {
			return false;
		}
		if (v2 == null) {
			if (other.getOppVertex(v1) != null) {
				return false;
			}
		} else if (!v2.equals(other.getOppVertex(v1))) {
			return false;
		}
		return true;
	}

	/**
	 * Changes the book only if it is first alphabetically
	 * @param name. title of book to be added
	 */
	public void addBook(String name){
		if(Vertex.isFirstAlphabetically(this.name, name))
			this.name = name;
	}
	
	/**
	 * prints the titles of the vertices. only used for testing
	 */
	public void print(){
		System.out.println(v1.getName());
		System.out.println(v2.getName());
	}
}

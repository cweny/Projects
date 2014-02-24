package mp5;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;



public final class Graph {
	private List<Vertex> graph;
	private HashMap<String,List<Vertex>> books;
	
	/**
	 * Creates a new empty graph
	 */
	public Graph(){
		graph = new ArrayList<Vertex>();
		books = new HashMap<String, List<Vertex>>();
	}
	
	/**
	 * creates a new graph according to the file
	 * @param filename name of file to be used
	 * @throws IOException
	 */
	public Graph(String filename) throws IOException{
		graph = new ArrayList<Vertex>();
		books = new HashMap<String, List<Vertex>>();
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currentLine;
		
		while((currentLine = br.readLine())!=null){
			getLine(currentLine);
			
			
		}
		
		br.close();
	}
	
	/**
	 * Creates new vertices and edges given by the line
	 * @param line. string to be read
	 */
	private void getLine(String line){
		if(line.isEmpty() || line.length()<4) return;
		int i = 1;
		
		while(line.charAt(i) != '\"') i++;
		
		Vertex v = matchVertex(line.substring(1,i));
		v.addComicBook(line.substring(i+3,line.length()-1).trim());
		if(books.containsKey(line.substring(i+3,line.length()-1).trim())){
			ArrayList<Vertex> newList = (ArrayList<Vertex>)books.get(line.substring(i+3,line.length()-1).trim());//////////////////////
			createEdge(v, newList, line.substring(i+3,line.length()-1).trim());
			newList.add(v);
		}
		else{
			ArrayList<Vertex> newList = new ArrayList<Vertex>();
			newList.add(v);
			books.put(line.substring(i+3,line.length()-1), newList);
		}
		
	}
	
	/**
	 * 
	 * @param s name of start
	 * @param d name of destination
	 * @return list of edges for path
	 */
	public List<Edge> path(String s, String d){
		Vertex start, dest;
		
		start = matchVertex(s);
		dest = matchVertex(d);
		if(start.getBooks().isEmpty() || dest.getBooks().isEmpty()) return null; //one of the characters does not exist
		return getPath(start, dest);
	}
	
	/**
	 * returns the list of path if it exists
	 * @param start
	 * @param dest
	 * @return null if path does not exist else return list of paths
	 */
	private List<Edge> getPath(Vertex start, Vertex dest){
		
		Vertex v;
		ArrayList<Edge> e, previousEdges;
		
		Queue<Vertex> vertices = new LinkedList<Vertex>();
		vertices.add(start);
		Map<Vertex, ArrayList<Edge>> map = new HashMap<Vertex, ArrayList<Edge>>();
		map.put(start, new ArrayList<Edge>());
		while(!vertices.isEmpty()){
			v = vertices.remove();
			e = (ArrayList<Edge>)v.getEdges();
			previousEdges = map.get(v);
			for (int i = 0; i < e.size(); i++) {
				if(!previousEdges.isEmpty() && previousEdges.get(previousEdges.size()-1).equals(e.get(i))) continue;
				if (dest.equals(e.get(i).getOppVertex(v))) {
					previousEdges.add(e.get(i));
					return previousEdges;
				}
				else if(!map.containsKey(e.get(i).getOppVertex(v))){
					ArrayList<Edge> currentEdges = new ArrayList<Edge>();
					currentEdges.addAll(previousEdges);
					currentEdges.add(e.get(i));
					map.put(e.get(i).getOppVertex(v), currentEdges);
					vertices.add(e.get(i).getOppVertex(v));
				}
			}
		}
		return null;
	}
	
	/**
	 * adds the vertex in alphabetical order
	 * @param v vertex to add
	 */
	public void add(Vertex v){

		int low = 0;
		int high = graph.size()-1;
		int mid;
		
		mid = (low+high)/2;
		
		while(low <= high){
			mid = (low+high)/2;
			if(Vertex.isFirstAlphabetically(graph.get(mid).getName(), v.getName()))
				high = mid-1;
			else if(Vertex.isFirstAlphabetically(v.getName(), graph.get(mid).getName()))
				low = mid+1;
			else
				return;
		}
		if(graph.isEmpty())
			graph.add(v);
		else{
			if(Vertex.isFirstAlphabetically(graph.get(mid).getName(), v.getName()))//insert<graph.get(mid))
				graph.add(mid, v);
			else if(mid < graph.size())
				graph.add(mid+1, v);
			else
				graph.add(v);
		}
	}
	
	/**
	 * finds the vertex if it exists
	 * @param name
	 * @return vertex if it exists. otherwise returns null
	 */
	public Vertex find(String name){
		int low = 0;
		int high = graph.size()-1;
		int mid;
		
		if(graph.isEmpty()) return null;
		
		while(low <= high){
			mid = (low+high)/2;
			if(Vertex.isFirstAlphabetically(graph.get(mid).getName(), name))
				high = --mid;
			else if(Vertex.isFirstAlphabetically(name, graph.get(mid).getName()))
				low = ++mid;
			else
				return graph.get(mid);
		}
		return null;
	}
	
	/**
	 * creates an edge with the vertex if vertices share the same book
	 * @param v. vertex to be connected with
	 * @param name. title of book
	 */
	private void createEdge(Vertex v, ArrayList<Vertex> list, String name){
		Edge e;
		
		for (int i = 0; i < list.size(); i++) {
			//if(v.getName().equals(list.get(i).getName())) continue;
			e = v.getEdgeTo(list.get(i));
			if(e == null){
				e = new Edge(name, list.get(i), v);
				list.get(i).addEdge(e, v.getName());
				v.addEdge(e, list.get(i).getName());
			}
			else 
				e.addBook(name);
		}

	}
	
	/**
	 * 
	 * @return list of vertices
	 */
	public List<Vertex> getList(){
		return graph;
	}
	
	/**
	 * returns the matching vertex. else returns a new vertex with the name
	 * @param name. title of the vertex to be searched
	 * @return vertex if it exists else returns a new vertex
	 */
	private Vertex matchVertex(String name){
		int low = 0;
		int high = graph.size()-1;
		int mid;

		while(low <= high){
			mid = (low+high)/2;
			if(Vertex.isFirstAlphabetically(graph.get(mid).getName(), name))
				high = --mid;
			else if(Vertex.isFirstAlphabetically(name, graph.get(mid).getName()))
				low = ++mid;
			else
				return graph.get(mid);
		}
		return new Vertex(name, this);
	}
	/**
	 * finds central character
	 * @return central character
	 */
	public Vertex central(){
		int smallestMaxPath;//////
		int maxPath=0;
		int currentPathSize;
		ArrayList<Edge> path;
		Vertex central;
		
		
		for (int k = 1; k < graph.size(); k++) {
			path = (ArrayList<Edge>) getPath(graph.get(0),graph.get(k));
			
			if(path == null)
				currentPathSize = 0;
			else
				currentPathSize = path.size();
			
			if(maxPath<currentPathSize)
				maxPath = currentPathSize;
		}
		
		smallestMaxPath = maxPath;
		central = graph.get(0);
		
		for (int i = 1; i < graph.size(); i++) {///////////////
			maxPath = 0;
			for (int j = 0; j < graph.size(); j++) {
				path = (ArrayList<Edge>) getPath(graph.get(i),graph.get(j));
				
				if(path == null)
					currentPathSize = 0;
				else
					currentPathSize = path.size();
				
				if(maxPath<currentPathSize)
					maxPath = currentPathSize;
			}
			if(smallestMaxPath>maxPath){
				smallestMaxPath = maxPath;
				central = graph.get(i);
			}
		}
		return central;
	}
}

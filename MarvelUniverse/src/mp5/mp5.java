/*
	Carlos Wen
	This file uses the graph implementation
*/
package mp5;

import java.io.IOException;
import java.util.ArrayList;

public class mp5 {
	public static void main(String[] args) throws IOException {
		Graph graph = new Graph("labeled_edges.tsv");
		if(args[0].length()==0 || args[1].length()==0){
			System.out.println("No characters to search");
			System.out.println("Input must contain name of two characters, separated by space. No quotes");
		}
		else{

			ArrayList<Edge> list = (ArrayList<Edge>) graph.path(args[0], args[1]);
			if(list == null){
		                System.out.println("One or both characters doesn't exist" );
		                System.out.println("Input must contain name of two characters, separated by space. No quotes");
		                return;
			}
			System.out.println("path from: "+ args[0] + "to " + args[1] + " is");
			for (int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j).getName());
			}
		}
	}
}

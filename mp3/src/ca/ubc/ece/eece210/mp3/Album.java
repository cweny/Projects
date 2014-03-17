package ca.ubc.ece.eece210.mp3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * This class contains the information needed to represent 
 * an album in our application.
 * 
 */

public final class Album extends Element {
	String title; 
	String performer;
	Arraylist<String> songlist;
	
	/**
	 * Builds a book with the given title, performer and song list
	 * 
	 * @param title - the title of the album
	 * @param author - the performer 
	 * @param songlist - the list of songs in the album
	 */
	
	private final String author, title;
	private ArrayList<String> songlist;
	
	public Album(String title, String performer, ArrayList<String> songlist) {
		// TODO implement this
		this.title = title;
		this.author = performer;
		this.songlist = songlist;
		
		//Adds songs to list
		for(int i=0; i<songlist.size(); i++){
			String acquire = songlist.get(i);
			this.songlist.add(acquire);
		}
	}

	/**
	 * Builds an album from the string representation of the object. It is used
	 * when restoring an album from a file.
	 * 
	 * @param stringRepresentation
	 *            the string representation
	 */
	public Album(String stringRepresentation) {
		// TODO implement this
		Scanner stringRep = new Scanner(stringRepresentation);
		title = stringRep.next();
		author = stringRep.next();
		
		while(!stringRep.hasNext()){
			songlist.add(stringRep.next());
		}
		stringRep.close();
	}

	/**
	 * Returns the string representation of the given album. The representation
	 * contains the title, performer and songlist, as well as all the genre
	 * that the book belongs to.
	 * 
	 * @return the string representation
	 */
	public String getStringRepresentation() {
		// TODO implement this
		String stringRep = new String(title + " " + author);
		
		for(int i = 0; i<songlist.size(); i++){
			stringRep.concat(" " + songlist.get(i));
		}
		
		return stringRep;
	}

	/**
	 * Add the book to the given genre
	 * 
	 * @param genre
	 *            the genre to add the album to.
	 */
	public void addToGenre(Genre genre) {
		// TODO implement this
		addChild(genre);
		genre.addChild(this);
	}

	/**
	 * Returns the genre that this album belongs to.
	 * 
	 * @return the genre that this album belongs to
	 */
	public Genre getGenre() {
		// TODO implement this
		//Carlos what does this do here?
		return (Genre)getChildren().get(0);
	}

	/**
	 * Returns the title of the album
	 * 
	 * @return the title
	 */
	public String getTitle() {
		// TODO implement this
		return title;
	}

	/**
	 * Returns the performer of the album
	 * 
	 * @return the performer
	 */
	public String getPerformer() {
		// TODO implement this
		return author;
	}

	/**
	 * An album cannot have any children (it cannot contain anything).
	 */
	@Override
	public boolean hasChildren() {
		return false;
	}
}

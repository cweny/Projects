package ca.ubc.ece.eece210.mp3;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class to represent an entity in the catalogue. The element (in this
 * implementation) can either be an album or a genre.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public abstract class Element {

	private List<Element> children = new ArrayList<Element>();
	//private List<Element> subgenre = new ArrayList<Element>();
	//private Genre parent;
	
	/**
	 * Returns all the children of this entity. They can be albums or
	 * genres. In this particular application, only genres can have
	 * children. Therefore, this method will return the albums or genres
	 * contained in this genre.
	 * 
	 * @return the children
	 */
	
	public List<Element> getChildren() {
		// TODO implement this
		return children;
	}

	/**
	 * Adds a child to this entity. Basically, it is adding an album or genre
	 * to an existing genre
	 * 
	 * @param b
	 *            the entity to be added.
	 */
	protected void addChild(Element b) {
		// TODO implement this (should throw an exception if trying to add
		// to a leaf object);
		children.add(b);
	}

	/**
	 * Abstract method to determine if a given entity can (or cannot) contain
	 * any children.
	 * 
	 * @return true if the entity can contain children, or false otherwise.
	 */
	public abstract boolean hasChildren();
}
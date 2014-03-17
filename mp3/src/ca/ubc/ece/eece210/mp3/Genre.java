package ca.ubc.ece.eece210.mp3;

/**
 * Represents a genre (or collection of albums/genres).
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public final class Genre extends Element {

	/**
	 * Creates a new genre with the given name.
	 * 
	 * @param name
	 *            the name of the genre.
	 */
	private final String name;
	
	public Genre(String name) {
		// TODO implement
		this.name = name;
	}

	/**
	 * Restores a genre from its given string representation.
	 * 
	 * @param stringRepresentation
	 */
	public static Genre restoreCollection(String stringRepresentation) {
		// TODO implement
		return null;
	}

	/**
	 * Returns the string representation of a genre
	 * 
	 * @return
	 */
	public String getStringRepresentation() {
		// TODO implement
		return null;
	}

	/**
	 * Adds the given album or genre to this genre
	 * 
	 * @param b
	 *            the element to be added to the collection.
	 */
	public void addToGenre(Element b) {
		addChild(b);
	}

	/**
	 * Returns true, since a genre can contain other albums and/or
	 * genres.
	 */
	@Override
	public boolean hasChildren() {
		return true;
	}
}
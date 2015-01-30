/**
 *A heuristic class that takes a text word, adds it to the front, and keeps
 *moving the word up until it finds word with a equal or greater word count
 *so that the list can be sorted by decreasing word count.
 * @author 
 *
 */
public class AccessCount extends Naive {

	/**
	 * constructor for AccessCount class that creates a new 
	 * input Scanner from a file in the system to use as needed
	 * by the class.
	 * 
	 * @param inputFileName the name of the file that is needed
	 * to allow the class to create a scanner to recieve information
	 * from the specific file.
	 */
	public AccessCount(String inputFileName) {
		super(inputFileName, "AccessCount");
	}

	/**
	 * Takes a node representation of a text word where it adds it to
	 * the end of a list and increments its count. Afterwards
	 * it keeps moving the word up towards the front until it encounters a word 
	 * with a word count equal or greater than its own count.
	 * Afterwards it uses its transpose method to make the needed changes
	 * to the words list count.
	 * 
	 * @node a node representation of a text word that is used to update 
	 * the list in increasing order of word count.
	 */
	protected void updateList(DNode<WordWithCount> node) {
		node.getEntry().incrementCount();
		while (node.getPrev().getEntry() != null
				&& node.getEntry().getCount() > node.getPrev().getEntry()
						.getCount()) {
			transpose(node);
		}
	}

	/**
	 * takes a node parameter representation of a text word and swaps it count with the word 
	 * before it.
	 * 
	 * @param node a node representation of a text word that is
	 * used to swap its word count with the word before it.
	 */
	private void transpose(DNode<WordWithCount> node) {
		// Set Previous Node to Next Node
		node.getPrev().setNext(node.getNext());
		// Set Next Node to Previous Node
		node.getNext().setPrev(node.getPrev());
		// Set Previous' Previous Node to Node
		node.getPrev().getPrev().setNext(node);
		// Set Node to Previous' Previous Node
		node.setPrev(node.getPrev().getPrev());
		// Set Previous Node to Node
		node.getNext().getPrev().setPrev(node);
		// Set Node to Previous Node
		node.setNext(node.getNext().getPrev());
	}

}

/**
 * A heuristic class that takes a text word from a txt file and swaps its word
 * counter with the text word before it in the list unless it is a new word
 * being added to the list.
 * 
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class Transpose extends Naive {

	/**
	 * constructor for Transpose class that creates a new input Scanner from a
	 * file in the system to use as needed by the class.
	 * 
	 * @param inputFileName
	 *            the name of the file that is needed to allow the class to
	 *            create a scanner to receive information from the specific
	 *            file.
	 */
	public Transpose(String inputFileName) {
		super(inputFileName, "Transpose");
	}

	/**
	 * Updates the list in the class so that it takes a node parameter
	 * representation of a text word and swaps it count with the word before it
	 * were afterwards it increments its count. If the word is new it simply
	 * adds the word to the front of the list.
	 * 
	 * @param node
	 *            a node representation of a text word that is either added to
	 *            the list if new and/or has its count incremented in the list.
	 */
	protected void updateList(DNode<WordWithCount> node) {
		if (node.getPrev().getEntry() != null) {
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
		node.getEntry().incrementCount();
	}

}

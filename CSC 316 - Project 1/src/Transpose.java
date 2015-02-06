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
		if (super.list.hasPrev(node.getPrev())) {
			WordWithCount temp = node.getPrev().getEntry();
			node.getPrev().setEntry(node.getEntry());
			node.setEntry(temp);
		}
		node.getEntry().incrementCount();
	}

}

/**
 * A heuristic class that takes a text word and 
 * moves that word to the front on the List
 * 
 * @author 
 *
 */
public class MoveToFront extends Naive {

	/**
	 * constructor for MoveToFront class that creates a new 
	 * input Scanner from a file in the system to use as needed
	 * by the class.
	 * 
	 * @param inputFileName the name of the file that is needed
	 * to allow the class to create a scanner to recieve information
	 * from the specific file.
	 */
	public MoveToFront(String inputFileName) {
		super(inputFileName, "MoveToFront");
	}

	/**
	 * Updates the list so that it a word of text that adds the word
	 * to the front of the list if the word is new and increments its
	 * count. If the word is already in the list then its count is 
	 * incremented by one.
	 * 
	 * @param node a node representation of a text word that is
	 * either added to the list if new and/or has its count incremented
	 * in the list.
	 */
	protected void updateList(DNode<WordWithCount> node) {
		if (node.getPrev().getEntry() != null) {
			// Set Previous Node to Next Node
			node.getPrev().setNext(node.getNext());
			// Set Next Node to Previous Node
			node.getNext().setPrev(node.getPrev());
			super.list.addFirst(node);
		}
		node.getEntry().incrementCount();
	}

}

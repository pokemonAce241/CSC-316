public class AccessCount extends Naive {

	public AccessCount(String inputFileName) {
		super(inputFileName, "AccessCount");
		// TODO Auto-generated constructor stub
	}

	protected void updateList(DNode<WordWithCount> node) {
		node.getEntry().incrementCount();
		while (node.getPrev().getEntry() != null
				&& node.getEntry().getCount() > node.getPrev().getEntry()
						.getCount()) {
			transpose(node);
		}
	}

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

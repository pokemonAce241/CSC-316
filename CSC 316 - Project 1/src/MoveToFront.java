public class MoveToFront extends Naive {

	public MoveToFront(String inputFileName) {
		super(inputFileName, "MoveToFront");
		// TODO Auto-generated constructor stub
	}

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

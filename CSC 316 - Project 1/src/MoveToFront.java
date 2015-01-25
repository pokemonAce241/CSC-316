public class MoveToFront extends Naive {

	public MoveToFront(String inputFileName) {
		super(inputFileName, "MoveToFront");
		// TODO Auto-generated constructor stub
	}

	protected void preProcess() {

	}

	protected void updateList(DNode<WordWithCount> node) {
		if (node.getPrev().getEntry() != null) {
			node.getPrev().setNext(node.getNext());
			node.getNext().setPrev(node.getPrev());
			super.list.addFirst(node);
		}
		node.getEntry().incrementCount();
	}

	protected void postProcess() {

	}

}

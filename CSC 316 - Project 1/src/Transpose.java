public class Transpose extends Naive {

	public Transpose(String inputFileName) {
		super(inputFileName, "Transpose");
		// TODO Auto-generated constructor stub
	}

	protected void updateList(DNode<WordWithCount> node) {
		if (node.getPrev().getEntry() != null) {
			node.getPrev().setNext(node.getNext());
			node.getNext().setPrev(node.getPrev());
			node.getPrev().getPrev().setNext(node);
			node.setPrev(node.getPrev().getPrev());
			node.getNext().getPrev().setPrev(node);
			node.setNext(node.getNext().getPrev());
		}
		node.getEntry().incrementCount();
	}

}

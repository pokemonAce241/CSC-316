public class MoveToFront extends Naive {

	
	public MoveToFront(String inputFileName) {
		super(inputFileName, "MoveToFront");
		// TODO Auto-generated constructor stub
	}
	
	protected void preProcess() {
		
	}
	
	protected void updateList(DNode<WordWithCount> node) {
        node.getPrev().setNext(node.getNext());
        list.addFirst(node);
    }
	
	protected void postProcess() {
		
	}
		
}

result()
ArrayIterator<E>import java.util.Iterator;


public class MapWords extends Heuristic{

	public MapWords(String inputFileName) {
		super(inputFileName, "MapWords");
	}

	protected void preProcess() {
		
	}

	@Override
	protected void lookup(String word) {
		// TODO Auto-generated method stub
		
	}

	protected void postProcess() {
		
	}
	
	/**
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		return new Iterator<WordWithCount>(list);
	}	

}

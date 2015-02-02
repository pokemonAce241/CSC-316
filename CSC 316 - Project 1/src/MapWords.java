import java.util.Iterator;
import java.util.TreeMap;

/**
 *A heuristic class that takes the words in a text file and stores each
 *word in a tree map using each text word as a key with a word count object
 *as a value. 
 * 
 * @author Gitesh Agarwal, Mohammed Saleh, Jason Benckert 
 *
 */
public class MapWords extends Heuristic {

	/**
	 * The tree map that stores and manages the WordWithCount objects
	 * passed into it.
	 */
	TreeMap<String, WordWithCount> map;

	/**
	 * constructor for MapWords class that creates a new 
	 * input Scanner from a file in the system to use as needed
	 * by the class.
	 * 
	 * @param inputFileName the name of the file that is needed
	 * to allow the class to create a scanner to receive information
	 * from the specific file.
	 */
	public MapWords(String inputFileName) {
		super(inputFileName, "MapWords");
	}

	/** 
	 * sets up and initializes the TreeMap and wordComparator
	 * needed for this class.
	 */
	protected void preProcess() {
		WordComparator comparator = new WordComparator();
		map = new TreeMap<String, WordWithCount>(comparator);
	}

	
	/**
	 * This either adds a new word key or increases the word count of a 
	 * word object in a word key depending on wither the word in already
	 * in the list or is a new word. 
	 */
	protected void lookup(String word) {
		if (map.containsKey(word)) {
			map.get(word).incrementCount();
		} else {
			map.put(word, new WordWithCount(word));
		}
	}

	/**
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		return map.values().iterator();
	}

}

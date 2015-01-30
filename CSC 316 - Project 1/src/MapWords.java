import java.util.Iterator;
import java.util.TreeMap;

public class MapWords extends Heuristic {

	TreeMap<String, WordWithCount> map;

	public MapWords(String inputFileName) {
		super(inputFileName, "MapWords");
	}

	protected void preProcess() {
		WordComparator comparator = new WordComparator();
		map = new TreeMap<String, WordWithCount>(comparator);
	}

	@Override
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

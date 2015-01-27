import java.util.Arrays;
import java.util.Iterator;

public class BinarySearch extends Heuristic {

	public static final int INITIAL_SIZE = 10;

	private WordWithCount[] list;

	private int size;

	private int count;

	public BinarySearch(String inputFileName) {
		super(inputFileName, "BinarySearch");
		// TODO Auto-generated constructor stub
	}

	protected void preProcess() {
		count = 0;
		size = INITIAL_SIZE;
		list = new WordWithCount[size];
	}

	@Override
	protected void lookup(String word) {
		if (count == 0) {
			list[count] = new WordWithCount(word);
			count++;
			return;
		}

		checkSize();
		int center = count / 2;
		binarySearch(word, center);
		count++;
	}

	private void binarySearch(String word, int loc) {
		int first = super.compareWords(list[loc].getWord(), word);
		int second = super.compareWords(list[loc + 1].getWord(), word);
		if (first < 0) {
			if (second >= 0)
				insert(word, loc + 1);
			else {
				int center = count + loc / 2;
				binarySearch(word, center);
			}
		} else if (first == 0) {
			insert(word, loc + 1);
		} else {
			int center = loc / 2;
			binarySearch(word, center);
		}
	}

	private void insert(String word, int loc) {
		for (int i = count; i > 0; i--) {
			if (i == loc) {
				list[count] = new WordWithCount(word);
				return;
			} else {
				list[count] = list[count - 1];
			}
		}
	}

	private void checkSize() {
		if (size < count + 1) {
			WordWithCount[] temp = new WordWithCount[size * 2];
			for (int i = 0; i < count; i++) {
				temp[i] = list[i];
			}
			list = temp;
		}
	}

	/**
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		return Arrays.asList(list).iterator();
	}

}

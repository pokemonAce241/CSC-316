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

		if (size < count + 1) {
			modifySize(size * 2);
		}
		binarySearch(word, 0, count - 1);
	}

	private void binarySearch(String word, int low, int high) {
		int mid = (low + high) / 2;
		int first = super.compareWords(list[mid].getWord(), word);

		if (first == 0) {
			list[mid].incrementCount();
		} else if (first < 0) {
			if (mid + 1 == count) {
				insert(word, mid + 1);
				return;
			}

			int second = super.compareWords(list[mid + 1].getWord(), word);
			if (second > 0) {
				insert(word, mid + 1);
				return;
			}
			binarySearch(word, mid + 1, high);
		} else {
			binarySearch(word, low, mid - 1);
		}
	}

	private void insert(String word, int loc) {
		for (int i = count; i > loc; i--) {
			list[count] = list[count - 1];
		}
		list[count] = new WordWithCount(word);
		count++;
	}

	private void modifySize(int c) {
		WordWithCount[] temp = new WordWithCount[c];
		for (int i = 0; i < count; i++) {
			temp[i] = list[i];
		}
		list = temp;
	}

	/**
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		modifySize(count);
		return Arrays.asList(list).iterator();
	}

}

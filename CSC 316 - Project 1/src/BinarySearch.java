import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
		System.out.println(Arrays.toString(list) + "\tAdd: " + word);
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
			if (mid == 0) {
				insert(word, mid);
				return;
			}

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
		size = c;
	}

	/**
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		return new ArrayIterator<WordWithCount>(list);
	}

	/**
	 * A simple iterator class for lists. The elements of a list are returned by
	 * this iterator. No copy of the list is made, so any changes to the list
	 * are reflected in the iterator.
	 * 
	 * @author Michael Goodrich, Eric Zamore, Roberto Tamassia, Matt Stallmann,
	 *         Specialized for Array Lists by Gitesh Agarwal
	 */
	// begin#fragment Iterator
	public class ArrayIterator<E> implements Iterator<E> {

		private E[] list; // the underlying list
		private int cursor; // the next position

		/** Creates an element iterator over the given list. */
		public ArrayIterator(E[] L) {
			list = L;
			cursor = (count == 0) ? null : 0;
		}

		/**
		 * Returns whether the iterator has a next object.
		 */
		public boolean hasNext() {
			if (cursor < count)
				return true;
			else
				return false;
		}

		/**
		 * Returns the next object in the iterator.
		 */
		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No next element");
			E toReturn = list[cursor];
			cursor = (cursor == count) ? null : cursor + 1;
			return toReturn;
		}

		/**
		 * Throws an {@link UnsupportedOperationException} in all cases, because
		 * removal is not a supported operation in this iterator.
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("remove");
		}

	}

}

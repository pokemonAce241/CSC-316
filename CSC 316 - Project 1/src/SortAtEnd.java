import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortAtEnd extends Heuristic {

	public static final int INITIAL_SIZE = 10;

	private WordWithCount[] list;

	private int size;

	private int count;

	public SortAtEnd(String inputFileName) {
		super(inputFileName, "SortAtEnd");
	}

	protected void preProcess() {
		count = 0;
		size = INITIAL_SIZE;
		list = new WordWithCount[size];
	}

	@Override
	protected void lookup(String word) {
		modifyList();
		list[count] = new WordWithCount(word);
		count++;
	}

	protected void postProcess() {
		sort();
		merge();
	}

	private void sort() {
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < count - 1; j++) {
				int comp = super.compareWords(list[j].getWord(),
						list[j + 1].getWord());
				if (comp > 0) {
					WordWithCount temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
				}
			}
		}
	}

	private void merge() {
		WordWithCount[] merge = new WordWithCount[count];
		int i = 0;
		int words = 0;
		while (i != (count - 1)) {
			int count = 0;
			int comp;
			do {
				comp = super.compareWords(list[i].getWord(),
						list[i + 1].getWord());
				i++;
				count++;
			} while (comp == 0 && i != (count - 1));
			merge[words] = new WordWithCount(list[i - 1].getWord(), count);
			words++;
		}
		list = merge;
		count = words;
	}

	private void transpose() {

	}

	private void modifyList() {
		if (size < count + 1) {
			WordWithCount[] temp = new WordWithCount[size * 2];
			for (int i = 0; i < count; i++) {
				temp[i] = list[i];
			}
			list = temp;
			size *= 2;
		}
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

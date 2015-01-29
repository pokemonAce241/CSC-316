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

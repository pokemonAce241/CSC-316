import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A heuristic class that takes a text word from a text file 
 * and adds it to a word list by using a binary search in order
 * to place the word in the proper place in the list. 
 * 
 * @author Gitesh Agarwal, Mohammed Saleh, Jason Benckert
 *
 */
public class BinarySearch extends Heuristic {

	/**
	 * The starting size of the list when first initialized.
	 */
	public static final int INITIAL_SIZE = 10;

	/**
	 * This array stores all text words found in the file.
	 */
	private WordWithCount[] list;

	/**
	 * This represents the overall size of the list.
	 */
	private int size;

	/**
	 * Representation of the word count to help with
	 * incrementing the count of a word in the list.
	 */
	private int count;

	/**
	 * constructor for BinarySearch class that creates a new 
	 * input Scanner from a file in the system to use as needed
	 * by the class.
	 * 
	 * @param inputFileName the name of the file that is needed
	 * to allow the class to create a scanner to receive information
	 * from the specific file.
	 */
	public BinarySearch(String inputFileName) {
		super(inputFileName, "BinarySearch");
	}

	/** 
	 * Initializes and sets up the list for when the file is scanned and
	 * text words are added to the list. 
	 */
	protected void preProcess() {
		count = 0;
		size = INITIAL_SIZE;
		list = new WordWithCount[size];
	}

	/** 
	 * Adds the word parameter to the end of the list.
	 * 
	 * @param word the word thats to be added to the list.
	 */
	protected void lookup(String word) {
		if (count == 0) {
			list[count] = new WordWithCount(word);
			count++;
			return;
		}

		modifyList();
		binarySearch(word, 0, count - 1);
	}

	/**
	 * Performs a binary search in the list in order to add the word into
	 * the list so that the list is in increasing lexicographic order.
	 * 
	 * Text Book-code fragment 9.7 pg. 409 5th edition
	 * 
	 * @param word the word to insert into the List.
	 * @param low the first element in the list.
	 * @param high the last element in the list.
	 */
	private void binarySearch(String word, int low, int high) {
		int mid = (low + high) / 2;
		int first = super.compareWords(list[mid].getWord(), word);

		if (first == 0) {
			list[mid].incrementCount();
		} else if (first < 0) {
			if (mid + 1 == count) {
				insert(word, count);
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

	/**
	 * Inserts the word in the parameter in a certain location in the list
	 * based on the loc parameter. 
	 * @param word The word to add to the list.
	 * @param loc The location in the list to add the word.
	 */
	private void insert(String word, int loc) {
		for (int i = count; i > loc; i--) {
			list[i] = list[i - 1];
		}
		list[loc] = new WordWithCount(word);
		count++;
	}

	/**
	 * This increases the capacity of the list in case the list reaches its maximum
	 * capacity during the scanning and word placing process.
	 */
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
		 * This checks if the current node in the iterator has a another node in its
		 *next node and returns true or false depending on whether it does or not. 
		 * 
		 * @return True or false depending on if the current node in the iterator has 
		 * a null next node.
		 */
		public boolean hasNext() {
			if (cursor < count)
				return true;
			else
				return false;
		}

		/**
		 * If the iterator has non null next it move over to represent its next list node
		 * in the list. Once changed it returns the new object it now represents. Otherwise
		 * if the next node is null it throws a NoSuchElementException. 
		 * 
		 * @return toReturn The next WordWithCount object in the list.
		 * 
		 * @throws NoSuchElementException if their is no WordWithCount object in the current nodes
		 * next node.
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
		 * 
		 *  @throws UnsuppotredException whenever this method is used.
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("remove");
		}

	}

}

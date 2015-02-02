import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A heuristic class that takes a text file and adds every word in the file to
 * the end of a word list. Afterwards the list is sorted out and every duplicate
 * word is merged into one word with appropriate word count.
 * 
 * @author Gitesh Agarwal, Mohammed Saleh, Jason Benckert
 * 
 */
public class SortAtEnd extends Heuristic {

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
	 * Representation of the word count to help with incrementing the count of a
	 * word in the list.
	 */
	private int count;

	/**
	 * constructor for SortAtEnd class that creates a new input Scanner from a
	 * file in the system to use as needed by the class.
	 * 
	 * @param inputFileName
	 *            the name of the file that is needed to allow the class to
	 *            create a scanner to recieve information from the specific
	 *            file.
	 */
	public SortAtEnd(String inputFileName) {
		super(inputFileName, "SortAtEnd");
	}

	/**
	 * Initializes and sets up the list for when the file is scanned and text
	 * words are added to the list.
	 */
	protected void preProcess() {
		count = 0;
		size = INITIAL_SIZE;
		list = new WordWithCount[size];
	}

	/**
	 * Adds the word parameter to the end of the list.
	 * 
	 * @param word
	 *            the word thats to be added to the list.
	 */
	protected void lookup(String word) {
		modifyList();
		list[count] = new WordWithCount(word);
		count++;
	}

	/**
	 * Sorts all the words in the list in descending order then takes all
	 * duplicate words in the list to a single word with the appropriate word
	 * count.
	 */
	protected void postProcess() {
		sort();
		merge();
	}

	/**
	 * Sorts all the words in the list in descending order.
	 */
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

	/**
	 * Takes a sorted list and merges all duplicate words into one word with the
	 * word count equal to the amount of duplicates their was.
	 */
	private void merge() {
		WordWithCount[] merge = new WordWithCount[count];
		int i = 0;
		int words = 0;
		while (i != count) {
			int count = 0;
			int comp;
			do {
				if (list[i + 1] == null)
					comp = 1;
				else
					comp = super.compareWords(list[i].getWord(),
							list[i + 1].getWord());
				i++;
				count++;
			} while (comp == 0 && i != this.count);
			merge[words] = new WordWithCount(list[i - 1].getWord(), count);
			words++;
		}
		list = merge;
		count = words;
	}

	/**
	 * This increases the capacity of the list in case the list reaches its
	 * maximum capacity during the scanning and word placing process.
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
	 * @param <E> the type of object to be used in the iterator.
	 * 
	 * @author Michael Goodrich, Eric Zamore, Roberto Tamassia, Matt Stallmann,
	 *         Specialized for Array Lists by Gitesh Agarwal
	 */
	public class ArrayIterator<E> implements Iterator<E> {

		/**
		 * The list that holds the WordWithCount objects.
		 */
		private E[] list; // the underlying list
		/**
		 * The next position in the list when traversing it.
		 */
		private int cursor; // the next position

		/**
		 * The constructor for the iterator in this class.
		 * 
		 * @param L The list of WordWithCount objects for the 
		 * iterator to use.
		 */
		public ArrayIterator(E[] L) {
			list = L;
			cursor = (count == 0) ? null : 0;
		}

		
		/** 
		 *This checks if the current node in the iterator has a another node in its
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
		 * 
		 * Throws an {@link UnsupportedOperationException} in all cases, because
		 * removal is not a supported operation in this iterator.
		 * 
		 * @throws UnsupportedOperationException whenever this method is used.
		 * 
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("remove");
		}

	}

}

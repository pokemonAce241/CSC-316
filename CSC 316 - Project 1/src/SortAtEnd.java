import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A heuristic class that takes a text file and adds every word in the file to
 * the end of a word list. Afterwards the list is sorted out and every duplicate
 * word is merged into one word with appropriate word count.
 * 
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class SortAtEnd extends Heuristic {

	/**
	 * The main list where all WordWithCount objects are stored
	 * int the heuristic.
	 */
	private AList list;

	/**
	 * constructor for SortAtEnd class that creates a new input Scanner from a
	 * file in the system to use as needed by the class.
	 * 
	 * @param inputFileName
	 *            the name of the file that is needed to allow the class to
	 *            create a scanner to receive information from the specific
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
		list = new AList();
	}

	/**
	 * Adds the word parameter to the end of the list.
	 * 
	 * @param word
	 *            the word thats to be added to the list.
	 */
	protected void lookup(String word) {
		list.add(new WordWithCount(word));
	}

	/**
	 * Sorts all the words in the list in descending order then takes all
	 * duplicate words in the list to a single word with the appropriate word
	 * count.
	 */
	protected void postProcess() {
		list = sort(list, 0, list.size());
		merge();
	}

	/**
	 * Sorts all the words in the list in ascending order.
	 * 
	 * @param ls
	 *            The list to sort.
	 * @param start
	 *            The index of the list from which to start sorting.
	 * @param finish
	 *            The index of the list at which to stop sorting, excluding the
	 *            given index.
	 * @return The sorted list.
	 */
	private AList sort(AList ls, int start, int finish) {
		int count = finish - start;
		int mid = count / 2 + start;
		if (count <= 1) {
			return new AList(ls.get(mid));
		} else {
			AList L1 = sort(ls, start, mid);
			AList L2 = sort(ls, mid, finish);
			AList solu = new AList(count);
			int i = 0;
			int j = 0;
			for (int k = 0; k < count; k++) {
				if (j == L2.size()) {
					solu.add(L1.get(i));
					i++;
				} else if (i == L1.size()) {
					solu.add(L2.get(j));
					j++;
				} else {
					int comp = super.compareWords(L1.get(i).getWord(), L2
							.get(j).getWord());
					if (comp <= 0) {
						solu.add(L1.get(i));
						i++;
					} else if (comp > 0) {
						solu.add(L2.get(j));
						j++;
					}
				}
			}
			return solu;
		}
	}

	/**
	 * Takes a sorted list and merges all duplicate words into one word with the
	 * word count equal to the amount of duplicates their was.
	 */
	private void merge() {
		AList merge = new AList(list.size());
		int i = 0;
		while (i != list.size()) {
			int count = 0;
			int comp;
			do {
				if (list.get(i + 1) == null)
					comp = 1;
				else
					comp = super.compareWords(list.get(i).getWord(),
							list.get(i + 1).getWord());
				i++;
				count++;
			} while (comp == 0 && i != list.size());
			merge.add(new WordWithCount(list.get(i - 1).getWord(), count));
		}
		list = merge;
	}

	/**
	 * Returns the final result of the list with all words from the file
	 * properly added and sorted.
	 * 
	 * @return an iterator for the list of WordWithCount objects.
	 */
	@Override
	public Iterator<WordWithCount> result() {
		return list.iterator();
	}

	/**
	 * A inner class that is the main storage for the WordwithCount objects 
	 * added and managed in the heuristic.
	 * 
	 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
	 *
	 */
	public class AList {

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
		 * Representation of the word count to help with incrementing the count
		 * of a word in the list.
		 */
		private int count;

		/**
		 * The constructor for AList with a pre set size to it.
		 */
		public AList() {
			this(INITIAL_SIZE);
		}

		/**
		 * The constructor for AList with a size based on the
		 * size parameter.
		 * 
		 * @param size the overall size of the list.
		 */
		public AList(int size) {
			count = 0;
			this.size = size;
			list = new WordWithCount[size];
		}

		/**
		 * A constructor for Alist that creates the list with a pre set size
		 * and count of one that adds the WordWithCount object in the parameter
		 * to the newly constructed list.   
		 * 
		 * @param element first WordWithCount object to be added to list.
		 */
		public AList(WordWithCount element) {
			count = 1;
			size = 1;
			list = new WordWithCount[] { element };
		}

		/**
		 * Adds the WordWithCount object in the parameter to the list 
		 * increments the count by one.
		 * 
		 * @param element the word to be added to the list.
		 */
		public void add(WordWithCount element) {
			modify();
			list[count] = element;
			count++;
		}

		/**
		 * Swaps WordWithCount objects at the
		 * locations described by the parameters 
		 * with each other. 
		 * 
		 * @param i position of first word.
		 * @param j position of second word.
		 */
		public void replace(int i, int j) {
			if (i < count && j < count) {
				WordWithCount temp = list[i];
				list[i] = list[j];
				list[j] = temp;
			}
		}

		/**
		 * Returns the word in the list at the position in the parameter as 
		 * long as the position is not greater than the lists size.
		 * 
		 * @param i the position of the word
		 * @return the word at position i
		 */
		public WordWithCount get(int i) {
			if (i < size)
				return list[i];
			else
				return null;
		}

		/**
		 * Returns the total amount of words currently stored in the list. 
		 * 
		 * @return count the word count for the list
		 */
		public int size() {
			return count;
		}

		/**
		 * Returns the iterator that is pointing to the current word in the 
		 * list being analyzed. 
		 * 
		 * @return the current iterator.
		 */
		public Iterator<WordWithCount> iterator() {
			return new ArrayIterator<WordWithCount>(list);
		}

		/**
		 * This increases the capacity of the list in case the list reaches its
		 * maximum capacity during the scanning and word placing process.
		 */
		private void modify() {
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
		 * A simple iterator class for lists. The elements of a list are
		 * returned by this iterator. No copy of the list is made, so any
		 * changes to the list are reflected in the iterator.
		 * 
		 * @param <E>
		 *            the type of object to be used in the iterator.
		 * 
		 * @author Michael Goodrich, Eric Zamore, Roberto Tamassia, Matt
		 *         Stallmann, Specialized for Array Lists by Gitesh Agarwal
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
			 * @param L
			 *            The list of WordWithCount objects for the iterator to
			 *            use.
			 */
			public ArrayIterator(E[] L) {
				list = L;
				cursor = 0;
			}

			/**
			 * This checks if the current node in the iterator has a another
			 * node in its next node and returns true or false depending on
			 * whether it does or not.
			 * 
			 * @return True or false depending on if the current node in the
			 *         iterator has a null next node.
			 */
			public boolean hasNext() {
				if (cursor < count)
					return true;
				else
					return false;
			}

			/**
			 * If the iterator has non null next it move over to represent its
			 * next list node in the list. Once changed it returns the new
			 * object it now represents. Otherwise if the next node is null it
			 * throws a NoSuchElementException.
			 * 
			 * @return toReturn The next WordWithCount object in the list.
			 * 
			 * @throws NoSuchElementException
			 *             if their is no WordWithCount object in the current
			 *             nodes next node.
			 */
			public E next() throws NoSuchElementException {
				if (!hasNext())
					throw new NoSuchElementException("No next element");
				E toReturn = list[cursor];
				cursor++;
				return toReturn;
			}

			/**
			 * 
			 * Throws an {@link UnsupportedOperationException} in all cases,
			 * because removal is not a supported operation in this iterator.
			 * 
			 * @throws UnsupportedOperationException
			 *             whenever this method is used.
			 * 
			 */
			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException("remove");
			}

		}

	}

}

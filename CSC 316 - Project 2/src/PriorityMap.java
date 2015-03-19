import java.util.Comparator;

/**
 * @author Gitesh Agarwal
 * 
 */
public class PriorityMap<K, P, V> {

	private Entry<K, P, V> head;

	public PriorityMap(Comparator<P> comparator) {
		head = null;
	}

	protected class Entry<K, P, V> {

		private K key;

		private P priority;

		private V value;
		
		private Entry parent;
		
		private Entry left;
		
		private Entry right;

		public Entry(K key, P priority, V value) {
			this.key = key;
			this.priority = priority;
			this.value = value;
		}

		/**
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return the priority
		 */
		public P getPriority() {
			return priority;
		}

		/**
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @return the parent
		 */
		public Entry getParent() {
			return parent;
		}

		/**
		 * @param parent the parent to set
		 */
		public void setParent(Entry parent) {
			this.parent = parent;
		}

		/**
		 * @return the left
		 */
		public Entry getLeft() {
			return left;
		}

		/**
		 * @param left the left to set
		 */
		public void setLeft(Entry left) {
			this.left = left;
		}

		/**
		 * @return the right
		 */
		public Entry getRight() {
			return right;
		}

		/**
		 * @param right the right to set
		 */
		public void setRight(Entry right) {
			this.right = right;
		}
	}
}

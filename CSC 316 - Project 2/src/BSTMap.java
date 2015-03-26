import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * @author Gitesh Agarwal
 * 
 */
public class BSTMap<K, V> implements Map<K, V> {

	private Entry<K, V> head;

	private int size;

	private Comparator<K> comparator;

	public BSTMap(Comparator<K> comparator) {
		head = null;
		size = 0;
		this.comparator = comparator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		Entry<K, V> temp = head;
		int c;
		while (true) {
			if (temp == null)
				throw null;

			c = comparator.compare((K) key, temp.getKey());
			if (c < 0) {
				temp = temp.left;
			} else if (c > 0) {
				temp = temp.right;
			} else {
				break;
			}
		}

		return temp.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	private Entry<K, V> getEntry(Object key) {
		Entry<K, V> temp = head;
		int c;
		while (true) {
			if (temp == null)
				return null;

			c = comparator.compare((K) key, temp.getKey());
			if (c < 0) {
				temp = temp.left;
			} else if (c > 0) {
				temp = temp.right;
			} else {
				break;
			}
		}

		return temp;
	}

	/**
	 * @return
	 */
	public V lastValue() {
		if (head == null)
			return null;

		Entry<K, V> temp = head;
		while (temp.right != null) {
			temp = temp.right;
		}

		return temp.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		if (head == null) {
			head = new Entry<K, V>(key, value, null);
		} else {
			Entry<K, V> temp = head;
			int c;
			while (true) {
				c = comparator.compare(key, temp.getKey());
				temp.count++;
				if (c < 0 && temp.left != null) {
					temp = temp.left;
				} else if (c > 0 && temp.right != null) {
					temp = temp.right;
				} else if (c < 0) {
					temp.left = new Entry<K, V>(key, value, temp);
					break;
				} else if (c > 0) {
					temp.right = new Entry<K, V>(key, value, temp);
					break;
				} else {
					return null;
				}
			}

		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		Entry<K, V> temp = head;
		int c;
		while (true) {
			if (temp == null)
				return null;

			temp.count--;
			c = comparator.compare((K) key, temp.getKey());
			if (c < 0) {
				temp = temp.left;
			} else if (c > 0) {
				temp = temp.right;
			} else {
				break;
			}
		}

		if (temp.parent == null) {
			if (temp.left == null && temp.right == null)
				head = null;
			else if (temp.right == null) {
				head = temp.left;
				head.parent = null;
			} else {
				Entry<K, V> con = removeEntry(temp);
				head = con;
				head.parent = null;
			}
			return temp.getValue();
		}

		boolean isLeft = isLeft(temp);
		if (temp.right == null && temp.left == null) {
			if (isLeft) {
				temp.parent.left = null;
			} else {
				temp.parent.right = null;
			}
		} else if (temp.right == null) {
			if (isLeft) {
				temp.parent.left = temp.left;
			} else {
				temp.parent.right = temp.left;
			}
			temp.left.parent = temp.parent;
		} else {
			Entry<K, V> con = removeEntry(temp);

			if (isLeft) {
				temp.parent.left = con;
			} else {
				temp.parent.right = con;
			}
			con.parent = temp.parent;
		}

		size--;
		return temp.getValue();
	}

	private Entry<K, V> removeEntry(Entry<K, V> temp) {
		Entry<K, V> con = temp.right;
		if (con.left != null) {
			while (con.left != null) {
				con.count--;
				con = con.left;
			}
			con.parent.left = con.right;
			if (con.right != null)
				con.right.parent = con.parent;

			con.count = 1;
			con.right = temp.right;
			if (con.right != null) {
				con.right.parent = con;
				con.count += con.right.count;
			}
		}

		con.left = temp.left;
		if (con.left != null) {
			con.left.parent = con;
			con.count += con.left.count;
		}

		return con;
	}

	public int getPosition(K key) {
		Entry<K, V> entry = getEntry(key);
		if (entry == null)
			return -1;

		int position = 1;
		if (entry.right != null)
			position += entry.right.count;

		while (entry != head) {
			boolean isLeft = isLeft(entry);
			if (isLeft) {
				position++;
				if (entry.parent.right != null) {
					position += entry.parent.right.count;
				}
			}
			entry = entry.parent;
		}
		return position;
	}

	private boolean isLeft(Entry<K, V> entry) {
		K p = entry.parent.key;
		K t = entry.key;
		int c = comparator.compare(p, t);

		if (c > 0)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		head = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		return null;
	}

	protected class Entry<K, V> implements Map.Entry<K, V> {

		protected K key;

		protected V value;

		protected int count;

		protected Entry<K, V> parent;

		protected Entry<K, V> left;

		protected Entry<K, V> right;

		public Entry(K key, V value, Entry<K, V> parent) {
			this.key = key;
			this.value = value;
			count = 1;
			this.parent = parent;
			left = null;
			right = null;
		}

		/**
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 * @return the value
		 */
		public V setValue(V value) {
			this.value = value;
			return value;
		}
	}
}
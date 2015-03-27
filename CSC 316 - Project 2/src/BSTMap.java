import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * This takes object data and stores it in a balanced map and also manages
 * any inserts or removes of objects bases on certain command.
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class BSTMap<K, V> implements Map<K, V> {
	/**
	 *This entry represents the root of the map or where
	 *it starts. 
	 */
	private Entry<K, V> head;
	/**
	 * This is the total amount of entries in the 
	 * tree including the root.
	 */
	private int size;
	/**
	 * This Comparator is used to compare two node values
	 * to see whether a value is bigger,smaller, or equal 
	 * to another value.
	 */
	private Comparator<K> comparator;
	/**
	 * The main constructor of the class with a null head and a 
	 * size of zero.
	 * 
	 * @param comparator
	 * 					the comparator the tree will use for comparisons.
	 */
	public BSTMap(Comparator<K> comparator) {
		head = null;
		size = 0;
		this.comparator = comparator;
	}

	/** 
	 * returns the size of number of entries in the list.
	 * @return int
	 *            Returns the total count of entries in the list.
	 */
	public int size() {
		return size;
	}

	/**
	 *Returns true or false depending whether or not the tree is empty.
	 *@return boolean
	 *               returns true if the tree has no entries. Else it 
	 *               returns false. 
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	/**
	 * Not used
	 * @param key
	 *           The key of a entry the function tries to find.
	 * @return boolean
	 * 				  Always returns false.
	 */
	public boolean containsKey(Object key) {
		return false;
	}

	/**
	 * Not used
	 * @param value
	 *             The value of a entry the function tries to find.
	 * @return boolean
	 * 				  Always returns false.
	 */
	public boolean containsValue(Object value) {
		return false;
	}

	/** 
	 * This function finds the the entry that has the same key as the key
	 * in the parameter and returns that entries value. 
	 * 
	 * @param key
	 *            The key of a entry the function tries to get 
	 *            in order to return the value of the entry.
	 * @return V
	 *          Returns the value of the entry retrieved from the tree.
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

	/**
	 * This function finds the the entry that has the same key as the key
	 * in the parameter and returns that entry. 
	 * 
	 * @param key
	 *            The key of a entry the function tries to get 
	 *            in order to return the entry.
	 * @return Entry
	 *              Returns the entry retrieved from the tree.
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
	 * This function returns the value of the entry with the highest
	 * value in the list.
	 * 
	 * @return V
	 *          The value of the entry with the highest value in the
	 *          tree.
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

	/** 
	 * This function creates and adds a new Entry in the tree with the key and value 
	 * described in the parameter. The function will return null if there is already
	 * a entry with the same key as the key provided in the parameter.
	 * 
	 * @param key
	 * 			 The key used to create the new entry.
	 * @param value
	 *             The value used to create the new entry.
	 * @return V
	 *          Returns the value of the newly created entry or null if no
	 *          new entry is created.
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

	/**
	 *This function takes the key in the parameter and removes the entry with the same
	 *key as the key provided in the parameter. Once removed the function will do any needed.
	 *reorganizing of entries in order to keep the tree in AVL format. The function returns the 
	 *value of the removed entry or null if the parameter key is either out of bounds or 
	 *doesn't match any entry keys in the map.  
	 * 
	 * @param key
	 * 			 The key of the entry the function will remove from the tree.
	 * @return V
	 *    		Returns of the value of the entry the function removed from the tree.
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
	/**
	 * This function works exactly like the remove function except that it finds
	 * the entry matching the entry in the parameter, removes the entry, and returns
	 * the entire entry just removed.
	 * 
	 * @param temp
	 *            The entry the user wants removed from the map.
	 * @return Entry
	 *              returns the entry removed by function.
	 */
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
	/**
	 * The function take the key parameter, finds the entry who's key matches the 
	 * parameter, and returns the AVL position of the entry.
	 * 
	 * @param key
	 *           The key of the entry the user want the position of.
	 * @return int
	 *            the AVL position of the requested entry. 
	 */
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
	/**
	 * Used during the map traversal to see if the entry in the parameter has a left child.
	 * @param entry
	 *             The entry the function checks for a left child.
	 * @return boolean
	 *                returns true or false depending on wiether or not the parameter entry 
	 *                has a left child.
	 */
	private boolean isLeft(Entry<K, V> entry) {
		K p = entry.parent.key;
		K t = entry.key;
		int c = comparator.compare(p, t);

		if (c > 0)
			return true;
		else
			return false;
	}

	/** 
	 * not used.
	 */
	public void putAll(Map<? extends K, ? extends V> m) {

	}

	/**
	 * Clears out or removes every entry in the map.
	 */
	public void clear() {
		head = null;
	}

	/** 
	 * not used.
	 * @return
	 *        returns null.
	 */
	public Set<K> keySet() {
		return null;
	}

	/** 
	 * not used.
	 * @return
	 *        returns null.
	 */
	public Collection<V> values() {
		return null;
	}

	/** 
	 * not used.
	 * @return
	 *        returns null.
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		return null;
	}
	/**
	 * This is a protected class that represents a entry to be stored inside the map.
	 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
	 *
	 * @param <K>
	 * @param <V>
	 */
	protected class Entry<K, V> implements Map.Entry<K, V> {
		/**
		 * This represents the entry id in the map. 
		 */
		protected K key;
		/**
		 * This represents the value stored inside the entry.
		 */
		protected V value;
		/**
		 * This represents the amount of decendents the entry has including itself.
		 */
		protected int count;
		/**
		 * This represents the entries parent or the root of the map.
		 */
		protected Entry<K, V> parent;
		/**
		 * This represents the entries left child.
		 */
		protected Entry<K, V> left;
		/**
		 * This represents the entries right child.
		 */
		protected Entry<K, V> right;
		/**
		 * This is the constructor of the entry. 
		 * 
		 * @param key
		 *           The key for the entry.
		 * @param value
		 *             The value for the entry.
		 * @param parent
		 *              The parent of the entry.
		 */
		public Entry(K key, V value, Entry<K, V> parent) {
			this.key = key;
			this.value = value;
			count = 1;
			this.parent = parent;
			left = null;
			right = null;
		}

		/**
		 * gets the key of the entry.
		 * @return the key
		 *                returns the entries key.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * * gets the value of the entry.
		 * @return the value
		 *                returns the entries value.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * sets the value of the entry.
		 * @param value
		 *             the value we want to set the entries value too.
		 * @return the value
		 *                returns the values the user set to the entry.
		 */
		public V setValue(V value) {
			this.value = value;
			return value;
		}
	}
}

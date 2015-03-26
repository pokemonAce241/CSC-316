import java.io.PrintStream;

import AVLMap.Entry;

public class TreePrint {

	private Entry<K, V> root;

	public TreePrint(Entry<K, V> root) {
		this.root = root;
	}

	/**  
	 * prints the tree using inorder indenting subtree below each node; uses
	 * backward inorder so that turning the printout sideways has the correct
	 * left/right orientation
	 * 
	 * @param ps
	 *            where to print
	 * @param indentString
	 *            what to print for each indentation level
	 */
	public void inOrderPrint(PrintStream ps, String indentString) {
		if (root == null) {
			ps.println("EMPTY TREE");
			return;
		}
		IndentPrinter printer = new IndentPrinter(ps, indentString);
		recursiveInOrderPrint(root, printer);
	}

	/**
	 * prints the subtree rooted at v using the given indenting printer
	 */
	protected void recursiveInOrderPrint(Entry<K, V> v, IndentPrinter printer) {
		if (v.right != null) {
			printer.increaseIndent();
			Entry<K, V> rv = v.right;
			recursiveInOrderPrint(rv, printer);
			printer.decreaseIndent();
		}

		printer.println("-- value  = " + v.key);
		if (v.left != null) {
			printer.increaseIndent();
			Entry<K, V> lv = v.left;
			recursiveInOrderPrint(lv, printer);
			printer.decreaseIndent();
		}
	}

}

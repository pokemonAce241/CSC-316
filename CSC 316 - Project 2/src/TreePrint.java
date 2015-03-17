/**
 * This is just a code fragment to illustrate a way to print a binary search
 * tree.
 */

  /**
   * prints the subtree rooted at v using the given indenting printer
   */
  protected void recursiveInOrderPrint( BTPosition v, IndentPrinter printer )
  {
    checkPosition(v);
    if ( hasRight(v) )
      {
        printer.increaseIndent();
        BTPosition rv = right(v);
        recursiveInOrderPrint( rv, printer );
        printer.decreaseIndent();
      }
    printer.println( "-- value  = " + v.element() );
    if ( hasLeft(v) )
      {
        printer.increaseIndent();
        BTPosition lv = left(v);
        recursiveInOrderPrint( lv, printer );
        printer.decreaseIndent();
      }
  }

  /**
   * prints the tree using inorder indenting subtree below each node;
   * uses backward inorder so that turning the printout sideways has the
   * correct left/right orientation
   * @param ps where to print
   * @param indentString what to print for each indentation level
   */
  public void inOrderPrint( PrintStream ps, String indentString )
  {
    if( root == null )
      {
        ps.println("EMPTY TREE");
        return;
      } 
    IndentPrinter printer = new IndentPrinter( ps, indentString );
    recursiveInOrderPrint( root, printer ); 
  } 

 

//  [Last modified: 2009 10 22 at 19:09:55 GMT]

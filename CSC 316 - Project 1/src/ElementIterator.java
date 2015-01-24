import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

/**
 *  A simple iterator class for lists.  The elements of a list are
 *  returned by this iterator.  No copy of the list is made, so any
 *  changes to the list are reflected in the iterator.
 *
 *  @author Michael Goodrich, Eric Zamore, Roberto Tamassia
 *  Specialized for DList by Matt Stallmann
 */
//begin#fragment Iterator
public class ElementIterator<E> implements Iterator<E> {
  protected DList<E> list; // the underlying list
  protected DNode<E> cursor; // the next position
  /** Creates an element iterator over the given list. */
  public ElementIterator(DList<E> L) {
    list = L;
    cursor = ( list.isEmpty() ) ? null : list.getFirst();
  }
//end#fragment Iterator
  /** Returns whether the iterator has a next object. */
//begin#fragment Iterator
  public boolean hasNext() { return cursor != null; }
//end#fragment Iterator
  /** Returns the next object in the iterator. */
//begin#fragment Iterator
  public E next() throws NoSuchElementException {
      if ( ! list.hasNext(cursor) )
      throw new NoSuchElementException("No next element");
      E toReturn = cursor.getEntry();
      cursor = (cursor == list.getLast()) ? null : list.getNext(cursor);
      return toReturn;
  }
//end#fragment Iterator
  /** Throws an {@link UnsupportedOperationException} in all cases,
   * because removal is not a supported operation in this iterator.
   */
  public void remove() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("remove");
  }
//begin#fragment Iterator

//end#fragment Iterator

  public static void main( String args [] ) {
      DList<Integer> dI = new DList<Integer>();
      System.out.println( "Empty -" );
      ElementIterator<Integer> it = new ElementIterator<Integer>( dI );
      while ( it.hasNext() ) {
          System.out.println( "Next integer = " + it.next() );
      }
      dI.addFirst( new DNode<Integer>( 2, null, null ) );
      dI.addLast( new DNode<Integer>( 4, null, null ) );
      dI.addLast( new DNode<Integer>( 3, null, null ) );
      dI.addFirst( new DNode<Integer>( 1, null, null ) );
      it = new ElementIterator<Integer>( dI );
      while ( it.hasNext() ) {
          System.out.println( "Next integer = " + it.next() );
      }
  }
}

//  [Last modified: 2010 08 24 at 13:42:06 GMT]

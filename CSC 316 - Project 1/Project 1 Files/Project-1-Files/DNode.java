/** 
  * @author Goodrich & Tamassia
  */
public class DNode<E> {
  protected E entry;	
  protected DNode<E> next, prev;	
  
  public DNode(E e, DNode<E> p, DNode<E> n) {
    entry = e;
    prev = p;
    next = n;
  }
  
  public E getEntry() { return entry; }
  
  public DNode<E> getPrev() { return prev; }
  
  public DNode<E> getNext() { return next; }
  
  public void setEntry(E entry) { this.entry = entry; }
  
  public void setPrev(DNode<E> newPrev) { prev = newPrev; }
  
  public void setNext(DNode<E> newNext) { next = newNext; }
  
  public String toString() { return entry.toString(); }
}

//  [Last modified: 2010 09 18 at 00:58:42 GMT]

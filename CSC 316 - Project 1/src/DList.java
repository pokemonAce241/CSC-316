/**
 * @author Goodrich &amp; Tamassia
 */
public class DList<E> {
    /** maximum number of list items to print */
    protected int size;			
    protected DNode<E> header, trailer;	
     
    public DList() { 
        size = 0;
        header = new DNode<E>(null, null, null);	
        trailer = new DNode<E>(null, header, null);	
        header.setNext(trailer);	
    }
      
    public int size() { return size; }
      
    public boolean isEmpty() { return (size == 0); }
      
    public DNode<E> getFirst() throws IllegalStateException {
        if (isEmpty()) 
            throw new IllegalStateException("List is empty");
        return header.getNext();
    }
    
    public DNode<E> getLast() throws IllegalStateException {
        if (isEmpty()) 
            throw new IllegalStateException("List is empty");
        return trailer.getPrev();
    }
      
    public DNode<E> getPrev(DNode<E> v) throws IllegalArgumentException {
        if (v == header) 
            throw new IllegalArgumentException
                ("Cannot move back past the header of the list");
        return v.getPrev();
    }
     
    public DNode<E> getNext(DNode<E> v) throws IllegalArgumentException {
        if (v == trailer) 
            throw new IllegalArgumentException
                ("Cannot move forward past the trailer of the list");
        return v.getNext();
    }

    /** Inserts the given node z before the given node v. An error occurs
     * if v is the header 
     */
    public void addBefore(DNode<E> v, DNode<E> z)
        throws IllegalArgumentException {
        DNode<E> u = getPrev(v);	// may throw an IllegalArgumentException
        z.setPrev(u);
        z.setNext(v);
        v.setPrev(z);
        u.setNext(z);
        size++;
    }

    /** Inserts the given node z after the given node v. An error occurs
     * if v is the trailer 
     */
    public void addAfter(DNode<E> v, DNode<E> z) {
        DNode<E> w = getNext(v);	// may throw an IllegalArgumentException
        z.setPrev(v);
        z.setNext(w);
        w.setPrev(z);
        v.setNext(z);
        size++;
    }
      
    public void addFirst(DNode<E> v) {
        addAfter(header, v);
    }
      
    public void addLast(DNode<E> v) {
        addBefore(trailer, v);
    }
      
    public void remove(DNode<E> v) {
        DNode<E> u = getPrev(v);	// may throw an IllegalArgumentException
        DNode<E> w = getNext(v);	// may throw an IllegalArgumentException
        w.setPrev(u);
        u.setNext(w);
        v.setPrev(null);
        v.setNext(null);
        size--;
    }
      
    public boolean hasPrev(DNode<E> v) { return v != header; }
      
    public boolean hasNext(DNode<E> v) { return v != trailer; }

    /**
     * @return a string representation of the list for debugging and seeing
     * how the heuristic works
     */
    public String toString() {
        String s = "";
        DNode<E> v = header.getNext();
        int count = 0;
        while ( v != trailer ) {
            s += v.getEntry().toString() + "\n";
            v = v.getNext();
        }
        return s;
    }
      
    public static void main(String[] args) {
        DList<WordWithCount> dl = new DList<WordWithCount>();
        dl.addLast(new DNode<WordWithCount>(new WordWithCount("apple", 3),
                                            null, null));
        dl.addLast(new DNode<WordWithCount>(new WordWithCount("ball", 2),
                                            null, null));
        dl.addLast(new DNode<WordWithCount>(new WordWithCount("cat", 1),
                                            null, null));
        System.out.println(dl);
    }
}

//  [Last modified: 2010 08 25 at 13:18:46 GMT]

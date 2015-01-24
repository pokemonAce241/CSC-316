import java.util.*;
import java.io.*;

/**
 * Implements a list heuristic that simply appends new word to the end of the
 * linked list.
 * @author Suzanne Balik, 30 Aug 2007
 * modified by Matt Stallmann, 2010/08/13 to conform to abstract class Heuristic
 */
public class Naive extends Heuristic {
    protected DList<WordWithCount> list;

    /**
     * Creates an instance with a name that is displayed as "Naive"
     */
    public Naive( String filename ) {
        super( filename, "Naive" );
        list = new DList<WordWithCount>();
    }

   /**
    * May be used if the client program wants to use a different name
    */
    public Naive( String filename, String alternateName ) {
        super( filename, alternateName );
        list = new DList<WordWithCount>();
    }
    
    /**
     * Required by the Heuristic class.
     * Increases count of the WordWithCount object for "word" if it's in the
     * list, adds it if not 
     */
    protected void lookup( String word ) {
        DNode<WordWithCount> wordNode = findWord( word );
        if ( wordNode == null )
            insertNewWord( word );
        else
            updateList( wordNode );
    }

    /**
     * @param word the string that is being looked for in the list
     * @return a WordWithCount object if one with "word" is found, null
     * otherwise.
     *
     * Note: this method can be used by all classes that extend Naive, i.e.,
     * all list-based heuristics.
     */
    protected DNode<WordWithCount> findWord( String word ) {
        // do a simple linear search for the word
        DNode<WordWithCount> wordNode = null;
        DNode<WordWithCount> curr = null;
        if ( ! list.isEmpty() ) {
            // do nothing until word is found or the end of the list is reached
            for ( curr = list.getFirst(); 
                  list.hasNext( curr )
                      && compareWords( curr.getEntry().getWord(), word) !=0;  
                  curr = curr.getNext() ) {
            }
            if ( list.hasNext(curr) ) {
                wordNode = curr;
            }
        }
        return wordNode;
    }

    /**
     * Adds a new WordWithCount object at the end of the list; make it's
     * count = 0.
     *
     * Note: Any class that extends Naive does not need to add a
     * WordWithCount object; it only needs to move the object in a manner
     * appropriate for the heuristic.
     */
    protected void insertNewWord(String word){
        list.addLast(new DNode<WordWithCount>(new WordWithCount(word, 1),
                                              null, null));
    }
  
    /**
     * Increments the count of the given WordWithCount object (actually a
     * list node containing the object since other heuristics may need this)
     *
     * Note: Any class that extends Naive should override this method so as
     * to not only increment the count but also move the node appropriately
     * within the list.
     */
    protected void updateList(DNode<WordWithCount> node) {
        node.getEntry().incrementCount();
    }
  
    public int size()
    {
        return list.size;
    }

    /**
     * @return an iterator for the list of WordWithCount objects.
     *
     * Note: This method can be used by any class that extends Naive.
     */
    public Iterator<WordWithCount> result() {
        return new ElementIterator<WordWithCount>( list );
    }
  
    /**
     * A simple test program; the input file will need to be changed based on
     * the environment where the program is run
     */
   public static void main(String[] args) {
        Naive nh = new Naive("InputData/prog1-easy.txt");
        nh.run();
        System.out.println( "**********\n" );
        nh.printResult( System.out );
        System.out.println( "----------" );
        System.out.println( "list length = " + nh.size() );
        System.out.println( "comparisons = " + nh.getNumberOfComparisons() );
    }
}

//  [Last modified: 2010 09 18 at 00:28:10 GMT]

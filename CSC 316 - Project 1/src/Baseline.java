import java.util.*;
import java.io.*;

/**
 * Implements an "empty" heuristic that only reads the file (to compare
 * others against and to put the file into a buffer); result is simply the
 * list of words (count = 0 for each).
 * @author Matt Stallmann, 2010/08/13
 */
public class Baseline extends Heuristic {
    DList<WordWithCount> list;

    public Baseline( String filename ) {
        super( filename, "Baseline" );
        list = new DList<WordWithCount>();
    }
    
    public Baseline( String filename, String altName ) {
        super( filename, altName );
        list = new DList<WordWithCount>();
    }
    
    protected void lookup( String word ) {
       // just so that there are some comparisions (one per lookup)
        int dummy = compareWords( word, "x" );
    }

    protected ElementIterator<WordWithCount> result() {
        return new ElementIterator<WordWithCount>( list );
    }
  
    public static void main(String[] args) {
        Baseline bh = new Baseline("InputData/prog1-easy.txt");
        bh.run();
        System.out.println("__________");
        bh.printResult( System.out );
        System.out.println("----------");
        System.out.println( "comparisons = " + bh.getNumberOfComparisons() );
        System.out.println( "lookups     = " + bh.getNumberOfLookups() );
    }
}

//  [Last modified: 2010 09 03 at 13:58:32 GMT]

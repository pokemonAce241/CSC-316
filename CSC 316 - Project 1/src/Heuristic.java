import java.util.*;
import java.io.*;

/**
 * Common code for all word count heuristics.
 *
 * @author Matt Stallmann, 2010/08/13,
 * uses code written by Suzanne Balik in August 2007
 */
public abstract class Heuristic {
    /** pattern that separates words */
    // multiple non-word characters
    private static final String WORD_SEPARATOR = "\\W++";
    // various delimiters/operators
    //    private static final String WORD_SEPARATOR = "[\\.,\\*%\\\"()&$\\?<>!\\-:;\\s]++";
    /** Use when experimental results are displayed */
    private String name;
    private int numberOfComparisons;
    private int numberOfLookups;
    private Scanner inputScanner;
    private double time;

    // getters
    public String getName() { return name; }
    public int getNumberOfComparisons() { return numberOfComparisons; }
    public int getNumberOfLookups() { return numberOfLookups; }
    public double getTime() { return time; } 

    public Heuristic( String inputFileName, String name ) {
        this.name = name; 
        numberOfComparisons = 0;
        numberOfLookups = 0;
        try {
            inputScanner = getInputFileScanner( inputFileName );
        } catch ( FileNotFoundException e ) {
            System.out.println( e );
            System.exit( 1 );
        }
    }

    /**
     * Attaches a scanner to an input file
     * @param fileName name of the input file
     */
   public static Scanner getInputFileScanner( String fileName )
        throws FileNotFoundException
    {
        File inputFile = new File( fileName );
        return new Scanner( inputFile );
    }

    /**
     * Does a comparison and increments the number of comparisons
     * Always use this method when comparing two words
     *
     * @return an integer &lt;, ==, or &gt; 0 depending on whether the first argument is
     * lexicographically less than, equal to or greater than the second.
     */
    protected int compareWords( String wordOne, String wordTwo ) {
        numberOfComparisons++;
        return wordOne.compareTo( wordTwo );
    }

    /**
     * Allows compareWords to be used in a context that requires an
     * implementation of Comparator for sorting WordWithCount objects
     */
    protected class WordWithCountComparator implements Comparator<WordWithCount> {
        public int compare( WordWithCount wwcOne, WordWithCount wwcTwo ) {
            return compareWords( wwcOne.getWord(), wwcTwo.getWord() );
        }
    }

    /**
     * Allows compareWords to be used in a context that requires an
     * implementation of Comparator when the word is used as a key in a map
     */
    protected class WordComparator implements Comparator<String> {
        public int compare( String sOne, String sTwo ) {
            return compareWords( sOne, sTwo );
        }
    }

    /**
     * Used to implement the lookup operation by each subclass
     */
    protected abstract void lookup( String word );

    /**
     * deals with a word from the input and increments lookups;
     * delegates the actual work to the appropriate subclass via lookup()
     * @param word a word to be added to the list or have its count
     * incremented 
     */
    public void lookupWordFromFile( String word ) {
        numberOfLookups++;
        lookup( word );
    }

    /**
     * Runs the heuristic, keeping track of time
     */
    protected void run() {
        // use various forms of punctuation and other symbols as delimiters
        inputScanner.useDelimiter( WORD_SEPARATOR );

        StopWatch timer = new StopWatch();
        timer.start();
        preProcess();
        while ( inputScanner.hasNext() ) {
            String word = inputScanner.next();
            // process only real words: non-empty and starting with a letter
            // or digit
            if ( word.length() > 0
                && Character.isLetterOrDigit( word.charAt(0) ) ) {
                word = word.toLowerCase();
                lookupWordFromFile( word );
            }
        }
        postProcess();
        timer.stop();
        time = timer.elapsedTimeSeconds();
        System.out.printf( "Time for %s = %20.16f\n", name, time );
    }

    /**
     * Accomplishes any processing that takes place at the beginning of a run.
     * A specific heuristic can override this if appropriate
     */
    protected void preProcess() { }

    /**
     * Accomplishes any processing that takes place at the end of a run.
     * A specific heuristic can override this if appropriate
     */
    protected void postProcess() { }

    /**
     * @return An iterator for the list of words and their counts as provided
     * by each heuristic.
     */
    abstract protected Iterator<WordWithCount> result();

    /**
     * Prints the result.
     * @param stream the stream to which the output is to be printed
     */
    public void printResult( PrintStream stream ) {
        Iterator<WordWithCount> iterator = result();
        while ( iterator.hasNext() ) {
            stream.println( "" + iterator.next() );
        }
    }

    /**
     * Prints an initial portion of the result.
     * @param stream the stream to which the output is to be printed
     * @param numberOfLines the number of lines to be printed
     */
    public void printResult( PrintStream stream, int numberOfLines ) {
        Iterator<WordWithCount> iterator = result();
        int linesSoFar = 0;
        while ( iterator.hasNext() && linesSoFar < numberOfLines ) {
            stream.println( "" + iterator.next() );
            linesSoFar++;
        }
    }

    /**
     * Prints a heading to be used when reporting experimental results
     */
    public static void printHeader() {
        System.out.println("Runtime is in milliseconds, the rest are in microseconds");
        System.out.printf("%-20s %8s %12s %8s %7s %9s %9s\n",
                          "Heuristic",
                          "lookups",
                          "comparisons",
                          "comp/LU",
                          "time",
                          "time/LU",
                          "time/comp");
        System.out.printf("-----------------------------------------"
                      + "---------------------------------------\n");
    }

    /**
     * Prints a line reporting information about the most recent run of this
     * heuristic.
     */
    public void printStatistics() {
        System.out.printf("%-20s %8d %12d %8.2f %7.1f %9.4f %9.5f\n",
                          name,
                          numberOfLookups,
                          numberOfComparisons,
                          (double) numberOfComparisons / numberOfLookups,
                          1000 * time,
                          1000000 * time / numberOfLookups,
                          1000000 * time / numberOfComparisons
                          );
    }
}

//  [Last modified: 2010 09 24 at 16:13:35 GMT]

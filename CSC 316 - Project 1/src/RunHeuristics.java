/**
 * Framework for running a variety of heuristics/algorithms that count the
 * number of occurrences of each word in a file.
 *
 * @author Matt Stallmann 2010/08/16, based on earlier programs by Suzanne
 * Balik and Dennis Bahler.
 */

public class RunHeuristics {
    /**
     * Number of lines to print for each output (instead of full result)
     */
    public static final int LINES_TO_PRINT = 10;

    public static void main (String[] args ) {
        if ( args.length != 1 ) {
            System.out.println( "Usage: java RunHeuristics input_file" );
            return;
        }

        String inputFileName = args[0];
 
        // The baseline heuristic has to be run first - any heuristic that is
        // run first will take a lot more time than the ones that are run
        // thereafter. My guess is that the first heuristic has the effect of
        // loading the input file into a buffer. 
        Heuristic baseline = new Baseline( inputFileName );
        baseline.run();
        System.out.printf( "%s:\n", baseline.getName() );
        baseline.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        Heuristic naive = new Naive( inputFileName );
        naive.run();
        System.out.printf( "%s:\n", naive.getName() );
        naive.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        // Comment out the rest of these and uncomment as they are
        // implemented
        // The last two are not in the list because their outputs are the
        // same as for BinarySearch

        Heuristic mtf = new MoveToFront( inputFileName );
        mtf.run();
        System.out.printf( "%s:\n", mtf.getName() );
        mtf.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        Heuristic xp = new Transpose( inputFileName );
        xp.run();
        System.out.printf( "%s:\n", xp.getName() );
        xp.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        Heuristic ac = new AccessCount( inputFileName );
        ac.run();
        System.out.printf( "%s:\n", ac.getName() );
        ac.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        Heuristic bs = new BinarySearch( inputFileName );
        bs.run();
        System.out.printf( "%s:\n", bs.getName() );
        bs.printResult( System.out, LINES_TO_PRINT );
        System.out.println();

        Heuristic.printHeader();

        baseline.printStatistics();
        naive.printStatistics();
        mtf.printStatistics();
        xp.printStatistics();
        ac.printStatistics();
        //bs.printStatistics();
    }
}

//  [Last modified: 2010 09 18 at 01:37:45 GMT]

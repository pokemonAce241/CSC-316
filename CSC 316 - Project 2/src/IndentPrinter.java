/**
 * Allows printing with indentation. Especially handy for tracing recursive
 * programs or printing recursively defined structures.
 * @author Matt Stallmann, 2008/08/01
 * @version $Id: IndentPrinter.java 7 2009-08-03 17:49:07Z mfms $
 *
 */

import java.io.*;

public class IndentPrinter {

  /**
   * Where to print
   */
  private PrintStream printDestination;

  /**
   * String to print once per indentation level
   */
  private String indentString;

  /**
   * How many times to print indent string before text
   */
  private int indentation;

  /**
   * @param printDestination where to print
   * @param indentString string to print once per indentation level
   */
  public IndentPrinter( PrintStream printDestination, String indentString )
  {
    this.printDestination = printDestination;
    this.indentString = indentString;
    this.indentation = 0;
  }

  /**
   * Increase the indentation level.
   */
  public void increaseIndent()
  {
    indentation++;
  }

  /**
   * Decrease the indentation level.
   */
  public void decreaseIndent()
  {
    indentation--;
  }

  /**
   * Prints using the indentation, followed by a newline
   * @param theString what to print
   */
  public void println( String theString )
  {
    for( int i = 0; i < indentation; i++ )
      {
        printDestination.print( indentString );
      }
    printDestination.println( theString );
  }

  /**
   * Test method to see how the class works for recursive tracing.
   */
  private void recursiveTest( int depth )
  {
    if( depth == 0 )
      {
        println("Bottom");
        return;
      }
    println(" -> depth = " + depth);
    increaseIndent();
    recursiveTest( depth - 1 );
    decreaseIndent();
    println(" <- depth = " + depth);
  }

  /**
   * Test driver: calls recursive test method recursiveTest()
   */
  public static void main(String [] args)
  {
    IndentPrinter ip = new IndentPrinter( System.out, "  | " );
    ip.recursiveTest( 5 );
  } // end, main
} // end, class

//  [Last modified: 2009 08 07 at 21:02:44 GMT]

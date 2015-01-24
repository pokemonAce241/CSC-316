import java.util.*;

/**
 * The basic list entry in any word counting program.
 * @author Matt Stallmann, Fall 2009 
 */
public class WordWithCount {

  private String word;
  private int count;
    /** This allows comparison with 'sort | uniq -c | sort -nrs' for a list
     * of these -- when using the Iterator in Heuristic.java that prints these
     * one per line.
     *
     * NOTE: The 's' (stable) option is very important in the last sort;
     * without it, words that have the same counts can be in different
     * orders; the Java sort utility is stable but the Unix qsort is not
     */
  public static final int FIELD_SIZE = 4;
  
  public WordWithCount( String w ) {
    word = w;
    // creation implies one occurrence
    count = 1;
  }
  
  public WordWithCount(String w, int c) {
    word = w;
    count = c;
  }
  
  public String getWord() {
    return word;
  }
  
  public int getCount() {
    return count;
  }
  
  public void incrementCount() {
    count++;
  }
  
    /**
     * @param n any integer, even negative
     * @return the number of digits in n
     */
    private int printSize( int n ) {
        if ( n == 0 ) return 1;
        int size = 0;
        if ( n < 0 ) {
            size = 1;          // for the '-' sign
            n = -n;
        }
        while ( n > 0 ) {
            size++;
            n /= 10;
        }
        return size;
    }

  public String toString() {
      String s = "";
      int leadingSpaces = FIELD_SIZE - printSize( count );
      for ( int i = 0; i < leadingSpaces; i++ ) s += " ";
      s += count;
      s += " ";
      s += word;
      return s;
  }
  
  public static void main(String[] args) {
    WordWithCount a = new WordWithCount("apple", 5);
    WordWithCount b = new WordWithCount("bear", 5);
    WordWithCount c = new WordWithCount("cat", 5);
    WordWithCount d = new WordWithCount("apple", 5);
    WordWithCount e = new WordWithCount("apple", 5);
    WordWithCount f = new WordWithCount("cat", 5);
    System.out.println(a);
    System.out.println(b);
    System.out.println(c);
    System.out.println(d);
    System.out.println(e);
    System.out.println(f);
    e.incrementCount();
    System.out.println(e);
  }
}

/** for sorting in descending order of count */
class CountComparator implements Comparator<WordWithCount> {
    public int compare( WordWithCount wwcOne, WordWithCount wwcTwo ) {
        return ((Integer) wwcTwo.getCount())
            .compareTo((Integer) wwcOne.getCount());
    }
}

//  [Last modified: 2010 09 20 at 14:46:51 GMT]

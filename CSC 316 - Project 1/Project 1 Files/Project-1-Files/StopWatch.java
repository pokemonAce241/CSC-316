/**
 * Can be used to time the execution of part of a program.
 * Caution: This is 'wall clock' time and should be used only in an
 * environment where no other processes are running in the foreground.
 *
 * @author Copyright (c) 2000-2008 Gaute Lykkenborg. All rights reserved.
 * Official Site: http://www.lykkenborg.no/
 * E-mail: glykkenborg@yahoo.no
 * Last Updated: Wed, 21 May 2008 10:50:33 GMT
 *
 * Modified by Matt Stallmann to allow return of seconds as double
 */
public class StopWatch {
    private long start;
    private long stop;
    
    public void start() {
        start = System.currentTimeMillis(); // start timing
    }
    
    public void stop() {
        stop = System.currentTimeMillis(); // stop timing
    }
    
    public long elapsedTimeMillis() {
        return stop - start;
    }
    
    public double elapsedTimeSeconds() {
      return (stop - start) / 1000.0;
    }
    
    public String toString() {
        return "elapsedTimeMillis: " + Long.toString(elapsedTimeMillis()); // print execution time
    }
}

//  [Last modified: 2008 08 05 at 16:33:42 GMT]

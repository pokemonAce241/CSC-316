/**
 * @file mce.alg
 * @author Matthias Stallmann
 * @date 2014/3/13
 * An implementation of the mce algorithm for crossing minimization.
 *
 * Caution: assumes that the graph is paritioned into layers, defined by
 * y-coordinates of the nodes and that all the edges are between nodes that
 * lie on adjacent layers. Edges are directed from lower numbered layers to
 * higher numbered ones. See LayeredGraph.java for more information
 * 
 * $Id: mce.alg 104 2015-03-27 16:23:25Z mfms $
 */

import edu.ncsu.csc.Galant.local.LayeredGraph;

final int MAX_ITERATIONS = 200;
class Globals {
    int minCrossings = Integer.MAX_VALUE;
    int iteration = 0;
    int pass = 0;
    int minIteration = 0;
    int minPass = 0;
}
final Globals globals = new Globals();

beginStep();
final LayeredGraph layeredGraph
    = new LayeredGraph( getGraph() );

setDirected( true );

function checkCrossings() {
    int crossings = layeredGraph.numberOfCrossings();
    if ( crossings < globals.minCrossings ) {
        globals.minCrossings = crossings;
        globals.minIteration = globals.iteration;
        globals.minPass = globals.pass;
        layeredGraph.savePositions();
    }
}

/**
 * @param v the node to be highlighted
 * @param e the edge to be highlighted
 */
function displayCrossings() {
    int crossings = layeredGraph.numberOfCrossings();
    layeredGraph.displayPositions();
    layeredGraph.displayMarks();
    graph.writeMessage( "pass = " + globals.pass
                            + ", iteration = " + globals.iteration
                            + ", crossings = " + crossings 
                            );
}

function edgeSift( Node x, Edge maxCrossingsEdge ) {
    int pos = layeredGraph.getPosition( x );
    int layer = layeredGraph.getLayer( x );
    int min_crossings = layeredGraph.getCrossings( maxCrossingsEdge );
    int min_position = pos;
    // maximum distance from the current position of a place where the swap
    // achieved the minimum bottleneck crossings
    int max_distance = 0;
    // try positions to the left of x
    for ( int i = pos - 1; i >= 0; i-- ) {
        Node y = layeredGraph.getNodeAt( layer, i );
        int crossings = layeredGraph.bottleneckSwap( y, x );
        if ( crossings < min_crossings
             || ( crossings == min_crossings
                  && pos - i > max_distance ) ) {
            min_crossings = crossings;
            min_position = i;
            max_distance = pos - i;
        }
    }
    // undo swaps related to positions to the left
    for ( int i = 0; i < pos; i++ ) {
        Node y = layeredGraph.getNodeAt( layer, i );
        int crossings = layeredGraph.bottleneckSwap( x, y );
    }
    // try positions to the right of x
    for ( int i = pos + 1; i < layeredGraph.getLayerSize( layer ); i++ ) {
        Node y = layeredGraph.getNodeAt( layer, i );
        int crossings = layeredGraph.bottleneckSwap( x, y );
        if ( crossings < min_crossings
             || ( crossings == min_crossings
                  && Math.abs( pos - i ) > max_distance ) ) {
            min_crossings = crossings;
            min_position = i;
            max_distance = Math.abs( pos - i );
        }
    }
    layeredGraph.insert( layer, pos, min_position );
}

while ( globals.iteration < MAX_ITERATIONS ) {
    globals.pass++;
    if ( globals.pass % 100 == 0 ) System.out.println( "pass = " + globals.pass );
    layeredGraph.clearMarks();
    Edge maxCrossingsEdge = layeredGraph.getMaxCrossingsEdge( true );
    while ( maxCrossingsEdge != null ) {
        endStep(); beginStep();
        maxCrossingsEdge.highlight();
        globals.iteration++;
        Node v = maxCrossingsEdge.getSourceNode();
        Node w = maxCrossingsEdge.getDestNode();
        beginStep();
        if ( ! layeredGraph.isMarked( v ) ) {
            v.highlight();
            endStep(); beginStep();
            edgeSift( v, maxCrossingsEdge );
            layeredGraph.mark( v );
        }
        checkCrossings();
        displayCrossings();
        endStep(); beginStep();
        v.unHighlight();
        if ( ! layeredGraph.isMarked( w ) ) {
            w.highlight();
            endStep(); beginStep();
            edgeSift( w, maxCrossingsEdge );
            layeredGraph.mark( w );
        }
        checkCrossings();
        displayCrossings();
        w.unHighlight();
        endStep(); beginStep();

        maxCrossingsEdge.unHighlight();
        maxCrossingsEdge = layeredGraph.getMaxCrossingsEdge();
    }
} 
endStep();

layeredGraph.displaySavedPositions();
graph.writeMessage( "min pass = " + globals.minPass
                    + ", min iteration = " + globals.minIteration
                    + ", min crossings = " + globals.minCrossings 
                    );

System.out.println( "Minimum crossings = " + globals.minCrossings
                    + " at iteration " + globals.minIteration );

//  [Last modified: 2014 07 14 at 21:18:11 GMT]

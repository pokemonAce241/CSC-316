/** quicksort.alg
 * An animation of quick sort.
 *
 * Caution: assumes that the graph is a collection of nodes with no edges
 * weight of node = sorting key
 */

/**
 * Distances used when lining up nodes
 */
final int HORIZONTAL_GAP = 100;
final int VERTICAL_GAP = 100;
final int TOP_GAP = 2 * VERTICAL_GAP;

/**
 * Lines up the nodes in a single horizontal row
 */
function lineUpNodes() {
    int xPosition = HORIZONTAL_GAP;
    int yPosition = TOP_GAP;
    for_nodes( n ) {
        n.setPosition( xPosition, yPosition );
        xPosition += HORIZONTAL_GAP;
    }
}

/**
 * sorts the nodes
 */
function quicksort( Node [] list ) {
    Node [] left = new Node[ getNodes().size() ];
    int l = 0;
    Node [] right = new Node[ getNodes().size() ];
    int r = 0;
    Node piv = list[0];
     int xPosition = HORIZONTAL_GAP;
     int yPosition = TOP_GAP;
    beginStep();
        piv.setPosition( xPosition, yPosition );
        xPosition += HORIZONTAL_GAP;
    endStep(); 
    for (i = 0; i < n; i++) {
        Node n_i = list[ i + 1 ];  
        beginStep();
        piv.setSelected( true );
        n_i.setSelected( true );
       endStep();
        if ( n_i.getWeight() < piv.getWeight() ) {
            left[l] = n_i;
            i = i + 1;
        } else if (n_i.getWeight() > piv.getWeight() ) {
            right[r] = n_i;
            r = r + 1;
        } else if (n_i.getWeight() = piv.getWeight() ) {
             beginStep(); 
       	 n_i.setPosition( xPosition, yPosition );
       	 xPosition += HORIZONTAL_GAP;
             endStep(); 
        }
        beginStep(); 
        n_i.setSelected( false );
        endStep(); 
    }
        beginStep(); 
        piv.setSelected( false );
        endStep(); 
}

beginStep();
lineUpNodes();
endStep();

final int size = getNodes().size();
final Node [] list = new Node[size];
for (int i = 0; i < size; i++) {
    list[i] = getNodeById(i);
}
quicksort(list);

//  [Last modified: 2013 08 05 at 15:20:01 GMT]

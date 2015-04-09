/**
 * @file GraphMLParser.java
 * @brief code for creating a graph from a GraphML file/string
 *
 * @todo Would probably be best to make the x and y attributes of nodes act
 * as surrogates for positionInLayer and layer, respectively, when a graph
 * is layered. The only part of the code that needs to know about these
 * attributes is the display in GraphPanel. The change would require changes
 * in GraphPanel, Node, NodeState, and possibly Graph and GraphState.
 *
 * @todo A lot of attributes of nodes, edges, and graphs should be set to
 * null if they don't exist (and then not printed when the graph is saved or
 * exported). Default values can be used during display in GraphPanel instead
 * of forced at time of creation. Numerical attributes should be Integer or
 * Double instead of int or double. Changes would be required here, in
 * GraphPanel, in Node, NodeState, Edge, EdgeState, Graph, GraphState, and
 * various places where the numerical attributes are referred to.
 *
 * $Id: GraphMLParser.java 103 2015-03-26 20:46:06Z mfms $
 */

package edu.ncsu.csc.Galant.graph.parser;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.ncsu.csc.Galant.GraphDispatch;
import edu.ncsu.csc.Galant.graph.component.Edge;
import edu.ncsu.csc.Galant.graph.component.Graph;
import edu.ncsu.csc.Galant.graph.component.GraphState;
import edu.ncsu.csc.Galant.graph.component.Node;
import edu.ncsu.csc.Galant.gui.util.ExceptionDialog;
import edu.ncsu.csc.Galant.logging.LogHelper;

/**
 * Parses a text file and creates a <code>Graph</code> from it. Allows for graph-to-editor
 * and editor-to-graph manipulation.
 * @author Ty Devries
 *
 */
public class GraphMLParser {
	
	Graph graph;
	File graphMLFile;
	Document document;
	
	public GraphMLParser(File graphMLFile) {
		this.graph = generateGraph(graphMLFile);
	}
	
	public GraphMLParser(String xml) {
		this.graph = generateGraph(xml);
	}
	
	public DocumentBuilder getDocumentBuilder(DocumentBuilderFactory dbf) {
		DocumentBuilder db = null;
		
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ExceptionDialog.displayExceptionInDialog(e);
		}
		
		return db;
	}
	
    /**
     * @todo Not clear the this is ever called
     */
	public void setDocument(DocumentBuilder db, File file) {
		try {
			this.document = db.parse(file);
		} catch (SAXException e) {
            System.out.println( "File parser setDocument SAXException: " + e );
			ExceptionDialog.displayExceptionInDialog(e);
		} catch (IOException e) {
            System.out.println( "File parser setDocument IOexception: " + e );
			ExceptionDialog.displayExceptionInDialog(e);
		}
	}
	
    /**
     * @todo For some bizarre reason, a "premature end of file" error occurs
     * whenever both layer and positionInLayer attributes occur for a node;
     * using different names makes the problem go away. Suggestion, use x and
     * y for layered graphs but interpret them as positionInLayer and
     * layer, respectively, when actually drawing the graph.
     */
	public void setDocument(DocumentBuilder db, String xml) {
		InputSource is = new InputSource(new StringReader(xml));
		try {
			this.document = db.parse(is);
		} catch (SAXException e) {
            System.out.println( "String parser setDocument SAXException: " + e );
			//ExceptionDialog.displayExceptionInDialog(e);
		} catch (IOException e) {
            System.out.println( "String parser setDocument IOexception: " + e );
			//ExceptionDialog.displayExceptionInDialog(e);
		}
	}
	
	public Graph buildGraphFromInput(DocumentBuilder db) {
        LogHelper.enterMethod( getClass(), "buildGraphFromInput" );
		GraphDispatch dispatch = GraphDispatch.getInstance();
		
		//TODO populate Graph g
		Graph g = new Graph();
		NodeList nodes;
		NodeList edges;
		NodeList graph;
		GraphState gs = g.getGraphState();
		gs.setLocked(true);
		nodes = getNodes();
		edges = getEdges();
		graph = getGraphNode();

        boolean [] idHasBeenSeen = new boolean[ nodes.getLength() ];
		
		NamedNodeMap attributes = graph.item(0).getAttributes(); //only one graph => hardcode 0th index

        // the awkward ?: construction is needed because org.w3c.dom.Node
        // conflicts with Galant Node
		String directed = ((attributes.getNamedItem("edgedefault") != null)
                           ? attributes.getNamedItem("edgedefault").getNodeValue()
                           : "undirected");
		g.setDirected(directed.equalsIgnoreCase("directed"));

        String name = (attributes.getNamedItem( "name" ) != null )
            ? attributes.getNamedItem("name").getNodeValue()
            : null;
        g.setName( name );
        String comment = ( attributes.getNamedItem( "comment" ) != null )
                       ? attributes.getNamedItem("comment").getNodeValue()
                       : null;
        g.setComment( comment );
        String type = ( attributes.getNamedItem( "type" ) != null )
                       ? attributes.getNamedItem("type").getNodeValue()
                       : null;
        String typename = ( attributes.getNamedItem( "type" ) != null )
                       ? attributes.getNamedItem("type").getNodeValue()
                       : null;
        if ( typename != null && typename.equalsIgnoreCase( "layered" ) ) {
            g.setLayered( true );
        }
        else {
            g.setLayered( false );
        }
		
        LogHelper.logDebug( "Created new graph:\n" + g );
        LogHelper.logDebug( " number of nodes = " + nodes.getLength() );
        LogHelper.logDebug( " number of edges = " + edges.getLength() );

        /** @todo instead of retrieving specific attributes by name, go
         * through all the attributes and use special handling for the ones
         * below if and when they occur, setting up default values initially */
        LogHelper.beginIndent();
		for(int i = 0; i < nodes.getLength(); i++) {
            LogHelper.logDebug( " processing node " + i );
			attributes = nodes.item(i).getAttributes();
            String idString = ((attributes.getNamedItem("id") != null)
                               ? attributes.getNamedItem("id").getNodeValue()
                               : "_");
            int id = -1;
            try {
                id = Integer.parseInt( idString );
            }
            catch (NumberFormatException e) {
                System.out.println( "Bad or missing id for node: " + idString );
                System.exit(1);
            }
            if ( idHasBeenSeen[id] ) {
                System.out.println( "Duplicate id: " + id );
                System.exit(1);
            }
            idHasBeenSeen[id] = true;
			String color = ((attributes.getNamedItem("color") != null) ? attributes.getNamedItem("color").getNodeValue() : "#000000");
			String label = ((attributes.getNamedItem("label") != null) ? attributes.getNamedItem("label").getNodeValue() : Graph.NOT_A_LABEL);
            String weightString
                = (attributes.getNamedItem("weight") != null )
                ? (attributes.getNamedItem("weight").getNodeValue())
                : null;
            double weight = Graph.NOT_A_WEIGHT;
            if ( weightString != null ) { 
                try {
                    weight = Double.parseDouble( weightString );
                }
                catch (NumberFormatException e) {
                    weight = Graph.NOT_A_WEIGHT;
                }
            }
			Boolean highlighted = (attributes.getNamedItem("highlighted") != null) ? Boolean.parseBoolean(attributes.getNamedItem("highlighted").toString()) : false;
			
			Point position = null;
			if (attributes.getNamedItem("x") != null && attributes.getNamedItem("y") != null) {
				String sX = attributes.getNamedItem("x").getNodeValue();
				String sY = attributes.getNamedItem("y").getNodeValue();
				
				try {
					int x = Integer.parseInt(sX);
					int y = Integer.parseInt(sY);
					
					position = new Point(x, y);
				} catch (Exception e) {
                    System.out.println( "Warning: "
                                        + sX + "," + sY
                                        + "is not a legitimate point.");
                    System.out.println( "Choosing a random point instead" );
                    position = Node.genRandomPosition();
                }
			}

            LogHelper.logDebug( "standard attributes set" );
            LogHelper.logDebug( " id = " + id );
            LogHelper.logDebug( " color = " + color );
            LogHelper.logDebug( " weight = " + weight );
            LogHelper.logDebug( " label = " + label );
            LogHelper.logDebug( " position = " + position );

            Node n = null;

            /**
             * Added this for layered graphs.
             * @todo The right way to do it is to read all named attributes
             * and do the following with each:
             * - if it parses as an int, make it an integer attribute
             * - if it parses as a double, make it a double
             * - otherwise make it a string
             */
            if ( g.isLayered() ) {
                LogHelper.logDebug( "adding node to layered graph" );
				String layerString
                    = attributes.getNamedItem( "layer" ).getNodeValue();
				String positionString
                    = attributes.getNamedItem( "positionInLayer" ).getNodeValue();
				
				try {
					int layer = Integer.parseInt( layerString );
					int positionInLayer = Integer.parseInt( positionString );
                    
                    n = new Node(gs, highlighted, false, id, weight, color, label,
                                 layer, positionInLayer );

				} catch (Exception e) {
                    System.out.println( "Warning: something went wrong with layering:" );
                    System.out.println( "" + e );
                    System.out.println( "Treating as not layered." );
                    g.setLayered( false );
                    n = new Node(gs, id, weight, color, label, highlighted, false);
                }

                LogHelper.logDebug( "done adding node to layered graph: " + n );
            }
            else {
                // not layered
                n = new Node(gs, id, weight, color, label, highlighted, false);
            }
            if (position != null) {
                n.setFixedPosition(position);
            }
            LogHelper.logDebug( "adding node " + n );
            g.addNode(n);
		}
        LogHelper.endIndent();

		for(int i = 0; i < edges.getLength(); i++) {
			attributes = edges.item(i).getAttributes();
			Node source = g.getNodeById(Integer.parseInt(attributes.getNamedItem("source").getNodeValue()));
			Node target = g.getNodeById(Integer.parseInt(attributes.getNamedItem("target").getNodeValue()));
			String color = ((attributes.getNamedItem("color") != null) ? attributes.getNamedItem("color").getNodeValue() : "#000000");
			String label = ((attributes.getNamedItem("label") != null) ? attributes.getNamedItem("label").getNodeValue() : Graph.NOT_A_LABEL);
			Boolean highlighted = (attributes.getNamedItem("highlighted") != null) ? Boolean.parseBoolean(attributes.getNamedItem("highlighted").toString()) : false;
            String weightString
                = (attributes.getNamedItem("weight") != null )
                ? (attributes.getNamedItem("weight").getNodeValue())
                : null;
            double weight = Graph.NOT_A_WEIGHT;
            if ( weightString != null ) { 
                try {
                    weight = Double.parseDouble( weightString );
                }
                catch (NumberFormatException e) {
                    weight = Graph.NOT_A_WEIGHT;
                }
            }

			Edge e = new Edge(gs, Integer.valueOf(i), source, target, highlighted, weight, color, label);
			g.addEdge(e);
			source.addEdge(e);
			target.addEdge(e);
            LogHelper.logDebug( "adding edge " + e );
		}
		g.getGraphState().setLocked(false);
        LogHelper.exitMethod( getClass(), "generateGraphFromInput:\n" + g );
		return g;
	}
	
	public Graph generateGraph(String xml) {
        LogHelper.enterMethod( getClass(), "generateGraph( String )" );
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = getDocumentBuilder(dbf);
		
		if (db != null) {
			setDocument(db, xml);
		}
		
        Graph newGraph = buildGraphFromInput(db);
        LogHelper.exitMethod( getClass(), "generateGraph( String )" );
		return newGraph;
	}
	
	public Graph generateGraph(File file) {
        LogHelper.enterMethod( getClass(), "generateGraph( File )" );
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = getDocumentBuilder(dbf);
		
		if (db != null) {
			setDocument(db, file);
		}
		
        Graph newGraph = buildGraphFromInput(db);
        LogHelper.exitMethod( getClass(), "generateGraph( String )" );
        return newGraph;
	}
	
	public void addNode(Node n) {
		//TODO add node to front of Nodes, return complete graph string
		graph.addNode(n, 0);
	}
	
	public void addEdge(Edge e) {
		//TODO add edge to front of Edges, return complete graph string
		graph.addEdge(e, 0);
	}
	
	public NodeList getGraphNode() {
		return this.document.getElementsByTagName("graph");
	}
	
	public NodeList getNodes() {
		return this.document.getElementsByTagName("node");
	}
	
	public NodeList getEdges() {
		return this.document.getElementsByTagName("edge");
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
}

//  [Last modified: 2015 03 26 at 20:30:28 GMT]

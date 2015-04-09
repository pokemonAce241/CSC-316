package edu.ncsu.csc.Galant.gui.editor;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import edu.ncsu.csc.Galant.GraphDispatch;
import edu.ncsu.csc.Galant.graph.component.Graph;
import edu.ncsu.csc.Galant.graph.parser.GraphMLParser;
import edu.ncsu.csc.Galant.logging.LogHelper;

/**
 * Each instance of GGraphEditorPanel corresponds to a particular
 * edit session of a particular graph file, or unsaved graph.
 * The panel displays in the GTabbedPane.
 * 
 * Compare to GAlgorithmEditPanel.
 * 
 * @author Jason Cockrell
 */
public class GGraphEditorPanel extends GEditorPanel {

	private final UUID uuid = UUID.randomUUID();
	
	/**
	 * Create a new edit session of a graph.
	 * @param gTabbedPane The parent tabbed pane, of which there is only ever one.
	 * @param filename The name of the file to be edited, which may be an unsaved file with a dummy name.
	 * @param content The text which constitutes the contet of the file to be edited. It is either the result of reading in the file, or the empty string.
	 */
	public GGraphEditorPanel(GTabbedPane gTabbedPane, String filename, String content) {
		super(gTabbedPane, filename, content);
		LogHelper.enterConstructor(getClass());
		
		GraphDispatch.getInstance().addChangeListener(this);
		
		//TODO clean up parser call
		
		try {
			GraphMLParser parser = new GraphMLParser(content);
			GraphDispatch.getInstance().setWorkingGraph(parser.getGraph(), uuid);
		} catch (Exception e) {
			GraphDispatch.getInstance().setWorkingGraph(new Graph(), uuid);
		}
		
		syntaxHighlighter = new GGraphSyntaxHighlighting(textPane);
		documentUpdated();
		
		LogHelper.exitConstructor(getClass());
	}
	
	/**
	 * Handle property changes to the graph, which occur when
	 * the user modifies a graph through the visual editor (toolbar).
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GraphDispatch.ANIMATION_MODE)) {
			if ( (Boolean) evt.getNewValue() ) { //animation mode
				this.textPane.setEnabled(false);
			} else { //edit mode
				this.textPane.setEnabled(true);
				if (GraphDispatch.getInstance().getGraphSource().equals(uuid)) {
					try {
						GraphMLParser parser = new GraphMLParser(this.getText());
						GraphDispatch.getInstance().setWorkingGraph(parser.getGraph(), uuid);
					} catch (Exception e) {
						GraphDispatch.getInstance().setWorkingGraph(new Graph(), uuid);
					}
				}
			}
		} else if (GraphDispatch.getInstance().getGraphSource().equals(uuid)) {
			if (evt.getPropertyName().equals(GraphDispatch.TEXT_UPDATE)) {
				textPane.setText(GraphDispatch.getInstance().getWorkingGraph().toString());
			} 
		}
		
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
}

package facebook.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphViewer extends JFrame {
	private static final long serialVersionUID = 1L; // For serialization purposes.

	private void AddFriendsToGraph(FacebookUser User, Graph<FacebookUser, String> FacebookGraph, int FBLevel,
			int MaxFBLevel) {
		for (FacebookUser Friend : User.getFriends()) { // Loops through all of the friends.
			FacebookGraph.addVertex(Friend); // Adds friend to graph.
			FacebookGraph.addEdge(User + "->" + Friend, User, Friend, EdgeType.DIRECTED); // Links friends with User.
			if (FBLevel < MaxFBLevel) // If the level is less than the max level...
				AddFriendsToGraph(Friend, FacebookGraph, FBLevel++, MaxFBLevel); // It adds friends to graph.
		}
	}

	public GraphViewer(final FacebookUser User, int MaxFBLevel) {

		Graph<FacebookUser, String> FacebookGraph = new SparseMultigraph<>(); // Makes graph.

		FacebookGraph.addVertex(User); // Adds User to graph.

		AddFriendsToGraph(User, FacebookGraph, 0, MaxFBLevel); // Adds all of the User's friends to graph.

		Layout<FacebookUser, String> FBGraphLayout = new ISOMLayout<>(FacebookGraph); // Sets the layout.
		FBGraphLayout.setSize(new Dimension(400, 400)); // Sets the size.

		BasicVisualizationServer<FacebookUser, String> FBVisualization = new BasicVisualizationServer<>(FBGraphLayout); // Sets
																														// up
																														// the
																														// visualization
																														// of
																														// the
																														// graph.
		FBVisualization.setPreferredSize(new Dimension(425, 425)); // Sets the visualization's size.
		FBVisualization.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<FacebookUser>()); // Sets the
																											// label to
																											// the
																											// Username.
		FBVisualization.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); // Puts all of the labels in
																							// the center of the window.
//Sets the size and shape of the vertex.
		Transformer<FacebookUser, Shape> VertexSize = new Transformer<FacebookUser, Shape>() {
			public Shape transform(FacebookUser u) {
				Graphics GraphGraphics = getGraphics(); // Gets the graphics.
				FontMetrics GraphFontMetrics = GraphGraphics.getFontMetrics(); // Gets the font metrics.
				int GraphRadius = (int) (GraphFontMetrics.stringWidth(u.toString()) * 1.25); // Sets the vertex radius
																								// depending on the size
																								// of the label.
				return new Ellipse2D.Double(-15, -15, GraphRadius, GraphRadius); // Returns the new shape.
			}
		};
//Sets the vertex color.
		Transformer<FacebookUser, Paint> GraphVertexColor = new Transformer<FacebookUser, Paint>() {
			public Paint transform(FacebookUser u) {
				if (u.equals(User)) // If it's the User...
					return Color.RED; // Returns the color Red.
				return Color.BLUE; // Otherwise, returns Blue.
			}
		};
//Sets the color of the label's font.
		DefaultVertexLabelRenderer GraphLabelRenderer = new DefaultVertexLabelRenderer(Color.BLACK) {
			private static final long serialVersionUID = 1L;

			@Override
			public <V> Component getVertexLabelRendererComponent(JComponent jc, Object value, Font font,
					boolean isSelected, V vertex) {
				super.getVertexLabelRendererComponent(jc, value, font, isSelected, vertex); // Creates the renderer.
				if (vertex.equals(User)) // If Vertex equals User...
					setForeground(Color.BLACK); // Color is set to Black.
				else // Otherwise...
					setForeground(Color.WHITE); // The color is set to White.
				return this; // Returns the renderer.
			}
		};

		FBVisualization.getRenderContext().setVertexShapeTransformer(VertexSize); // Sets size
		FBVisualization.getRenderContext().setVertexFillPaintTransformer(GraphVertexColor); // Sets the color.
		FBVisualization.getRenderContext().setVertexLabelRenderer(GraphLabelRenderer); // Sets the label renderer.

		setTitle(User + "'s Friends"); // Sets the title of the window.
		setLocationRelativeTo(null); // Puts window annywhere.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // When window is closed, disposes of information.
		getContentPane().add(FBVisualization); // Adding the graph to the window.
		pack(); // Sets the windows size to the graphs size.
		setVisible(true); // Shows the window.
	}
}

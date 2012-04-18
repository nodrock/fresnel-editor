package cz.muni.fi.fresneleditor.common.reposconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

import cz.muni.fi.fresneleditor.common.ContextHolder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class RDFGraph extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RDFGraph(java.awt.Frame parent, boolean modal) {
		super(parent, modal);

		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new DefaultCellViewFactory());

		JGraph graph = new JGraph(model, view);

		// DefaultGraphCell[] cells = new DefaultGraphCell[1];
		// cells[0] = new DefaultGraphCell(new String("Hello"));
		//
		// GraphConstants.setBounds(cells[0].getAttributes(), new
		// Rectangle2D.Double(20,20,40,20));
		// GraphConstants.setGradientColor(
		// cells[0].getAttributes(),
		// Color.orange);
		// GraphConstants.setOpaque(cells[0].getAttributes(), true);

		// DefaultPort port0 = new DefaultPort();
		// cells[0].add(port0);
		// cells[1] = new DefaultGraphCell(new String("World"));

		// GraphConstants.setBounds(cells[1].getAttributes(), new
		// Rectangle2D.Double(140,140,40,20));
		// GraphConstants.setGradientColor(cells[1].getAttributes(),
		// Color.red);
		// GraphConstants.setOpaque(cells[1].getAttributes(), true);

		// DefaultPort port1 = new DefaultPort();
		// cells[1].add(port1);
		// DefaultEdge edge = new DefaultEdge();
		// edge.setSource(cells[0].getChildAt(0));
		// edge.setTarget(cells[1].getChildAt(0));
		// cells[2] = edge;
		// int arrow = GraphConstants.ARROW_CLASSIC;
		// GraphConstants.setLineEnd(edge.getAttributes(), arrow);
		// GraphConstants.setEndFill(edge.getAttributes(), true);
		// graph.getGraphLayoutCache().insert(cells);

		Map<String, Map<URI, String>> result = new HashMap<String, Map<URI, String>>();

		List<DefaultGraphCell> cells = new ArrayList<DefaultGraphCell>();

		try {
			RepositoryConnection con = ContextHolder.getInstance()
					.getDataRepositoryDao().getRepository().getConnection();
			RepositoryResult<Statement> statements = con.getStatements(null,
					null, null, true);
			while (statements.hasNext()) {
				Statement next = statements.next();

				Map<URI, String> resource = result.get(next.getSubject()
						.toString());
				if (resource == null) {
					resource = new HashMap<URI, String>();
					DefaultGraphCell cell = new DefaultGraphCell(next
							.getSubject().toString());
					cells.add(cell);
				}
				resource.put(next.getPredicate(), next.getObject().toString());

				result.put(next.getSubject().toString(), resource);
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getContentPane().add(new JScrollPane(graph));
		this.pack();
		// this.setVisible(true);
	}

}

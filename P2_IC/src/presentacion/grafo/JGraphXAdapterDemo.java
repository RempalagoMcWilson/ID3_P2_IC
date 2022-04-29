package presentacion.grafo;

import java.awt.Dimension;
import javax.swing.JApplet;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;

@SuppressWarnings("deprecation")
public class JGraphXAdapterDemo extends JApplet {
	private static final long serialVersionUID = 1L;

	private JGraphXAdapter<Nodo, EdgeWithString> jgxAdapter;


	public void meteGrafo(ListenableGraph<Nodo, EdgeWithString> g, Dimension dimAct) {
		
		jgxAdapter = new JGraphXAdapter<>(g);

		setPreferredSize(dimAct);
		mxGraphComponent component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);
		resize(dimAct);

		mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);
		
		layout.execute(jgxAdapter.getDefaultParent());
	}
}

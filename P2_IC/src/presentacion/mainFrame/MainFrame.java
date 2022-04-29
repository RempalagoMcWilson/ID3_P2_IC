package presentacion.mainFrame;

import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;

import presentacion.grafo.EdgeWithString;
import presentacion.grafo.Nodo;
import presentacion.mainFrame.mainFrameImp.MainFrameImp;

public abstract class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	
	public synchronized static MainFrame getInstance()  {
		if (instance == null)
			instance = new MainFrameImp();
		return instance;
	}
	
	public abstract ListenableGraph<Nodo, EdgeWithString> getGrafoM();
	public abstract Nodo getNodoPadre();
}

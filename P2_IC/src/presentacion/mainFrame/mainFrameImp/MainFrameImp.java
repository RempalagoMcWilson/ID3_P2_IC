package presentacion.mainFrame.mainFrameImp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.jgrapht.ListenableGraph;

import negocio.id3.Id3;
import negocio.objetos.Tabla;
import presentacion.grafo.EdgeWithString;
import presentacion.grafo.JGraphXAdapterDemo;
import presentacion.grafo.Nodo;
import presentacion.mainFrame.MainFrame;
import presentacion.paneles.PanelPedirResultado;

public class MainFrameImp extends MainFrame {
	private static final long serialVersionUID = 1L;

	private JToolBar superior;
	private JButton cNombres;
	private JButton cValores;
	private JTextField tFNom;
	private JTextField tFVal;
	private File archivoNom;
	private File archivoVal;

	private JPanel centro;

	private JPanel grafoPanel;
	private JGraphXAdapterDemo aa;
	private ListenableGraph<Nodo, EdgeWithString> grafo;
	private Nodo nodoPadre;

	private PanelPedirResultado panelDerecha;

	private JButton execute;
	
	private Id3 id3;

	public MainFrameImp() {
		iniGUI();
	}

	private void iniGUI() {
		setVisible(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1000, 500));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setTitle("ID3");

		creaBarraSup();

		centro = new JPanel();
		centro.setLayout(new GridLayout(0, 2));
		add(centro, BorderLayout.CENTER);

		grafoPanel = new JPanel();
		aa = new JGraphXAdapterDemo();
		grafoPanel.add(aa);
		centro.add(grafoPanel);

		panelDerecha = new PanelPedirResultado();
		centro.add(panelDerecha);

		execute = new JButton();
		execute.setText("Ejecutar");
		executeListener();
		add(execute, BorderLayout.PAGE_END);

		repaint();
		revalidate();
	}

	private void creaBarraSup() {
		superior = new JToolBar();
		superior.setFloatable(false);

		tFNom = new JTextField("Seleccionar archivo para los nombres.");
		tFNom.setEditable(false);
		superior.add(tFNom);

		cNombres = new JButton();
		cNombres.setText("Cargar archivo nombres");
		cNombresListener();
		superior.add(cNombres);

		superior.addSeparator();

		tFVal = new JTextField("Seleccionar archivo para los valores.");
		tFVal.setEditable(false);
		superior.add(tFVal);

		cValores = new JButton();
		cValores.setText("Cargar archivo valores");
		cValoresListener();

		superior.add(cValores);

		add(superior, BorderLayout.PAGE_START);
	}

	private void cNombresListener() {
		cNombres.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(fileChooser);
				if (fileChooser.getSelectedFile() != null) {
					archivoNom = fileChooser.getSelectedFile();
					tFNom.setEditable(true);
					tFNom.setText(archivoNom.getAbsolutePath());
					tFNom.setEditable(false);
				}
			}
		});
	}

	private void cValoresListener() {
		cValores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(fileChooser);
				if (fileChooser.getSelectedFile() != null) {
					archivoVal = fileChooser.getSelectedFile();
					tFVal.setEditable(true);
					tFVal.setText(archivoVal.getAbsolutePath());
					tFVal.setEditable(false);
				}
			}
		});
	}

	private void executeListener() {
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				id3 = new Id3(new Tabla(archivoNom,archivoVal));
				grafo = id3.getGrafo();
				nodoPadre = id3.getNodoPadre();
				aa.meteGrafo(grafo, new Dimension(grafoPanel.getWidth() - 3, grafoPanel.getHeight() - 3));
				repaint();
				revalidate();
			}
		});
	}

	@Override
	public ListenableGraph<Nodo, EdgeWithString> getGrafoM() {
		return grafo;
	}

	@Override
	public Nodo getNodoPadre() {
		return nodoPadre;
	}
}

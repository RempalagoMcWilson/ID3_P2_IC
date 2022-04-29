package presentacion.paneles;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jgrapht.ListenableGraph;

import presentacion.grafo.EdgeWithString;
import presentacion.grafo.Nodo;
import presentacion.mainFrame.MainFrame;

public class PanelPedirResultado extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel indicaciones;
	private JTextField entrada;
	private JTextField salida;
	private JButton execute;

	private JPanel centro;

	public PanelPedirResultado() {
		iniGUI();
	}

	private void iniGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createRigidArea(new Dimension(0, 10)));

		indicaciones = new JLabel("Introduce los valores separados por comas aquí debajo");
		indicaciones.setFont(new Font("Arial", Font.PLAIN, 15));
		indicaciones.setAlignmentX(CENTER_ALIGNMENT);
		add(indicaciones);

		add(Box.createRigidArea(new Dimension(0, 10)));

		centro = new JPanel();
		centro.setLayout(new GridLayout(2, 0));
		add(centro);

		entrada = new JTextField("ejemplo1,ejemplo2,ejemplo3");
		centro.add(entrada);

		salida = new JTextField();
		salida.setEditable(false);
		centro.add(salida);

		add(Box.createRigidArea(new Dimension(0, 10)));

		execute = new JButton();
		execute.setText("VER RESULTADO");
		executeListener();
		execute.setAlignmentX(CENTER_ALIGNMENT);
		add(execute);

		add(Box.createRigidArea(new Dimension(0, 10)));
	}

	private void executeListener() {
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ent = entrada.getText();
				ArrayList<String> variables = new ArrayList<String>();
				for (int i = 0, act = 0; i < ent.length(); i++) {
					if (ent.charAt(i) == ',' || i == ent.length() - 1) {
						if (i == ent.length() - 1)
							variables.add(ent.substring(act, i + 1));
						else
							variables.add(ent.substring(act, i));
						act = i + 1;
					}
				}
				salida.setEditable(true);
				salida.setText(compruebaSol(MainFrame.getInstance().getGrafoM(), MainFrame.getInstance().getNodoPadre(), variables));
				salida.setEditable(false);
			}
		});
	}

	private String compruebaSol(ListenableGraph<Nodo, EdgeWithString> grafo, Nodo actual, ArrayList<String> variables) {
		
		if(actual.toString().equals("si") || actual.toString().equals("no")) {
			return actual.toString();
		}
		else {
			for(var e:grafo.outgoingEdgesOf(actual)) {
				if(variables.contains(e.toString())) {
					return compruebaSol(grafo, grafo.getEdgeTarget(e),variables);
				}
			}
		}
		return "";
	}
}

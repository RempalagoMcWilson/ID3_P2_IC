package presentacion.grafo;

import org.jgrapht.graph.DefaultEdge;

public class EdgeWithString extends DefaultEdge {
	private static final long serialVersionUID = 1L;
	String nombre;

	public EdgeWithString() {
		nombre = "hola";
	}

	public EdgeWithString(String entrada) {
		nombre = entrada;
	}

	public void setNombre(String ent) {
		nombre = ent;
	}
	@Override
	public String toString() {
		return nombre;
	}

}
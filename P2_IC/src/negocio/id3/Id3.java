package negocio.id3;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import negocio.objetos.Tabla;
import presentacion.grafo.EdgeWithString;
import presentacion.grafo.Nodo;

public class Id3 {

	private ListenableGraph<Nodo, EdgeWithString> grafo;
	private Nodo nodoPadre;
	private int cont;

	public Id3(Tabla tabla) {
		grafo = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(EdgeWithString.class));
		cont = 0;
		nodoPadre = null;
		algoritmoID3(tabla, null, null);
	}

	private void algoritmoID3(Tabla tabla, Nodo nodoAnt, String aristaAnt) {
		cont++;
		Nodo nodo;
		String todosIguales = tabla.todosIguales();

		if (todosIguales == null) {
			if (tabla.getNumeroNombres() > 2) {// Se puede continuar

				String nodoS = tabla.calculaNodo();// calculo el nodo que toca ahora en esta tabla
				nodo = new Nodo(nodoS, cont);
				if(nodoPadre == null)
					nodoPadre = nodo;
				meteNodoGrafo(nodo); // meto en el grafo este nodo

				if (nodoAnt != null && aristaAnt != null) // uno este nodo con el anterior
					meteAristaGrafo(nodoAnt, nodo, aristaAnt);

				for (String val : tabla.getTipos(nodo.toString())) // Hago las llamadas recursivas
					algoritmoID3(tabla.getSubTabla(val, nodo.toString()), nodo, val);
				
				
			} else {// Hay que parar
				String nodoS = tabla.getNombre(0);// cojo el nombre del último nodo
				nodo = new Nodo(nodoS, cont);
				meteNodoGrafo(nodo); // meto en el grafo este nodo

				if (nodoAnt != null && aristaAnt != null) // uno este nodo con el anterior
					meteAristaGrafo(nodoAnt, nodo, aristaAnt);

				for (String r : tabla.getResultados()) {
					cont++;
					Nodo aux = new Nodo(r, cont);
					meteNodoGrafo(aux);
					meteAristaGrafo(nodo, aux, tabla.getNomRes(r));
				}
			}
		} else {
			nodo = new Nodo(todosIguales, cont);
			meteNodoGrafo(nodo);
			meteAristaGrafo(nodoAnt, nodo, aristaAnt);
		}
	}

	private void meteNodoGrafo(Nodo nodo) {
		grafo.addVertex(nodo);

	}

	private void meteAristaGrafo(Nodo nodo1, Nodo nodo2, String nombre) {
		grafo.addEdge(nodo1, nodo2);
		grafo.getEdge(nodo1, nodo2).setNombre(nombre);
	}

	public ListenableGraph<Nodo, EdgeWithString> getGrafo() {
		return grafo;
	}

	public Nodo getNodoPadre() {
		return nodoPadre;
	}

}

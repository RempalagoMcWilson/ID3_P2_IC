package negocio.objetos;

import java.util.ArrayList;
import java.util.HashMap;

public class EstadisticaDeVariable {

	private String nombre;
	private ArrayList<String> valores;
	private HashMap<Integer, Integer> a;
	private HashMap<Integer, Integer> p;
	private HashMap<Integer, Integer> n;
	private Double merito;
	private int N;
	private int posRes;

	public EstadisticaDeVariable(ArrayList<ArrayList<String>> matriz, int i, String nombre) {
		this.nombre = nombre;
		N = matriz.size();
		valores = new ArrayList<String>();
		a = new HashMap<Integer, Integer>();
		p = new HashMap<Integer, Integer>();
		n = new HashMap<Integer, Integer>();
		merito = 0.0;
		posRes = matriz.get(0).size() - 1;
		calcula(matriz, i);
	}

	private void calcula(ArrayList<ArrayList<String>> matriz, int i) {
		String aux = "";
		int index = -1;
		for (int t = 0; t < N; t++) {// Saco los datos de a, p y n
			aux = matriz.get(t).get(i);
			index = valores.indexOf(aux);
			if (index == -1) {
				valores.add(aux);
				index = valores.indexOf(aux);
				a.put(index, 1);
				if (matriz.get(t).get(posRes).equals("si")) {
					p.put(index, 1);
					n.put(index, 0);
				} else {
					p.put(index, 0);
					n.put(index, 1);
				}
			} else {
				
				a.put(index, a.get(index) + 1);
				if (matriz.get(t).get(posRes).equals("si")) {
					p.put(index, p.get(index) + 1);
				} else {
					n.put(index, n.get(index) + 1);
				}
			}
		}

		// Calculo el mérito
		merito = 0.0;
		for (int it = 0; it < valores.size(); it++) {
			merito += (((double) a.get(it) / N)
					* infor((double) p.get(it) / a.get(it), (double) n.get(it) / a.get(it)));
		}
	}

	public Double getMerito() {
		return merito;
	}

	public ArrayList<String> getValores() {
		return valores;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "EstVa [nombre=" + nombre + ", valores=" + valores + ", merito=" + merito.toString() + "]";
	}

	private double infor(double p, double n) {
		if (p == 0) {
			if (n != 0) {
				return -n * (Math.log(n) / Math.log(2));
			} else {
				return 0;
			}
		}
		if (n != 0) {
			return -p * (Math.log(p) / Math.log(2)) - n * (Math.log(n) / Math.log(2));
		}
		return -p * (Math.log(p) / Math.log(2));
	}
}

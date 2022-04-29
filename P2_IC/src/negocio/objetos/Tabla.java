package negocio.objetos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Tabla {
	ArrayList<String> nombres;
	ArrayList<ArrayList<String>> matriz;

	PriorityQueue<EstadisticaDeVariable> estadisticasNombres;

	public Tabla(ArrayList<ArrayList<String>> tablaNueva, ArrayList<String> nombresNuevos) {
		nombres = nombresNuevos;
		matriz = tablaNueva;
		estadisticasNombres = new PriorityQueue<EstadisticaDeVariable>(nombres.size(),
				new Comparator<EstadisticaDeVariable>() {
					@Override
					public int compare(EstadisticaDeVariable o1, EstadisticaDeVariable o2) {
						return o1.getMerito().compareTo(o2.getMerito());
					}
				});
	}

	public Tabla(File archivoNom, File archivoVal) {
		matriz = new ArrayList<ArrayList<String>>();
		nombres = new ArrayList<String>();
		try {
			cargaNombres(archivoNom);
			cargaValores(archivoVal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		estadisticasNombres = new PriorityQueue<EstadisticaDeVariable>(nombres.size(),
				new Comparator<EstadisticaDeVariable>() {
					@Override
					public int compare(EstadisticaDeVariable o1, EstadisticaDeVariable o2) {
						return o1.getMerito().compareTo(o2.getMerito());
					}
				});
	}

	public void meteFila(ArrayList<String> f) {
		matriz.add(f);
	}

	public String getDato(int i, int j) {
		return matriz.get(i).get(j);
	}

	public String getNombre(int i) {
		return nombres.get(i);
	}

	private void cargaNombres(File archivoNom) throws IOException {

		// CARGA ARCHIVO CON LOS NOMBRES
		InputStream inFileArchivoNom = new FileInputStream(archivoNom);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFileArchivoNom));
		while (reader.ready()) {
			String linea = reader.readLine();
			for (int i = 0, ant = 0; i < linea.length(); i++) {
				if (linea.charAt(i) == ',') {
					nombres.add(linea.substring(ant, i));
					ant = i + 1;
				} else if (i == linea.length() - 1) {
					nombres.add(linea.substring(ant, i + 1));
				}
			}
		}
		reader.close();
	}

	private void cargaValores(File archivoVal) throws IOException {

		//// CARGA ARCHIVO CON LOS VALORES
		InputStream inFileArchivoVal = new FileInputStream(archivoVal);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFileArchivoVal));
		while (reader.ready()) {
			String linea = reader.readLine();
			ArrayList<String> aux = new ArrayList<String>();
			for (int i = 0, ant = 0; i < linea.length(); i++) {
				if (linea.charAt(i) == ',') {
					aux.add(linea.substring(ant, i));
					ant = i + 1;
				} else if (i == linea.length() - 1) {
					aux.add(linea.substring(ant, i + 1));
				}
			}
			if (aux.size() != 0)
				meteFila(aux);
		}
		reader.close();
	}

	@Override
	public String toString() {
		return "Tabla [nombres=" + nombres + ", matriz=" + matriz + "]";
	}

	public String calculaNodo() {
		for (int i = 0; i < nombres.size() - 1; i++) {// Calculo las estadisticas y las ordeno por el mérito
			estadisticasNombres.add(new EstadisticaDeVariable(matriz, i, nombres.get(i)));
		}
		return estadisticasNombres.peek().getNombre();
	}

	public ArrayList<String> getTipos(String nodo) {
		return estadisticasNombres.peek().getValores();
	}

	public Tabla getSubTabla(String val, String nom) {
		ArrayList<String> nombresN = new ArrayList<String>();
		ArrayList<ArrayList<String>> matrizN = new ArrayList<ArrayList<String>>();

		for (String n : nombres)// Meto los nombres
			if (!n.equals(nom))
				nombresN.add(n);

		for (var v : matriz) {// Creo la nueva subtabla de val
			if (v.contains(val)) {
				ArrayList<String> aux = new ArrayList<String>();
				for (String s : v)
					if (!s.equals(val))
						aux.add(s);
				matrizN.add(aux);
			}
		}

		Tabla tablaN = new Tabla(matrizN, nombresN);
		// System.out.println(matrizN);
		return tablaN;
	}

	public int getNumeroNombres() {
		return nombres.size();
	}

	public ArrayList<String> getResultados() {
		// System.out.println(matriz);
		ArrayList<String> resultados = new ArrayList<String>();
		String ant = matriz.get(0).get(1);
		resultados.add(ant);
		for (int i = 1; i < matriz.size(); i++) {
			if (!matriz.get(i).get(1).equals(ant))
				resultados.add(matriz.get(i).get(1));
		}
		return resultados;
	}

	public String getNomRes(String r) {
		for (var v : matriz) {
			if (v.contains(r)) {
				return v.get(1);
			}
		}
		return "mal";
	}

	public String todosIguales() {
		int nRes = matriz.get(0).size() - 1;
		String ant = matriz.get(0).get(nRes);
		for (int i = 1; i < matriz.size(); i++) {
			if (!matriz.get(i).get(nRes).equals(ant))
				return null;
		}
		return ant;
	}
}

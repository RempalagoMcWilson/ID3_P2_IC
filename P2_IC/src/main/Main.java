package main;

import com.formdev.flatlaf.FlatDarkLaf;
import presentacion.mainFrame.MainFrame;

public class Main {

	public static void main(String[] args) {
		FlatDarkLaf.setup();
		MainFrame.getInstance();
	}
}
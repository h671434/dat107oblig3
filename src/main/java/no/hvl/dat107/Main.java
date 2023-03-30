package no.hvl.dat107;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import no.hvl.dat107.dao.DepartmentDAO;
import no.hvl.dat107.gui.AppWindow;
import no.hvl.dat107.util.ServerReplacer;

public class Main {

	public static final int PORT = 8564;

	public static void main(String[] args) {
		System.out.println("Hello world!");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});

		new Thread(new ServerReplacer(PORT)).run();
	}

	private static void createAndShowGui() {
		AppWindow frame = new AppWindow();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
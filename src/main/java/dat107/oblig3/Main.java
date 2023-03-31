package dat107.oblig3;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.gui.AppWindow;
import dat107.oblig3.util.ServerReplacer;

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
package corp.dbapp.client;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import corp.dbapp.client.gui.AppWindow;

public class Main {

	public static final int PORT = 8564;

	public static void main(String[] args) {
		System.out.println("Hello world!");

//        DAO<Employee> employeeDAO = new EmployeeDAO();
//
//        employeeDAO.get(1).ifPresent(e -> e.print());
//        employeeDAO.getAll().forEach(e -> e.print());
//
//        employeeDAO.getBy("username", "pto").forEach(e -> e.print());

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
package corp.dbapp.client;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import corp.dbapp.client.gui.AppWindow;
import corp.dbapp.data.access.DataAccess;
import corp.dbapp.data.access.EmployeeDAO;
import corp.dbapp.data.model.Employee;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DataAccess<Employee> employeeDAO = new EmployeeDAO();

        employeeDAO.get(1).ifPresent(e -> e.print());
        employeeDAO.getAll().forEach(e -> e.print());

        employeeDAO.getBy("username", "pto").forEach(e -> e.print());
    

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	private static void createAndShowGui() {
		AppWindow frame = new AppWindow();
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
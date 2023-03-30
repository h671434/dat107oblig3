package no.hvl.dat107.gui.widget;

import javax.swing.JPanel;

import no.hvl.dat107.dao.EmployeeDAO;
import no.hvl.dat107.entity.Employee;

@SuppressWarnings("serial")
public class EmployeeInfoWidget extends InfoWidget {
	
	private EmployeeDAO dao = new EmployeeDAO();
	
	private JPanel idPanel;
	private JPanel usernamePanel;
	private JPanel namePanel;
	private JPanel datePanel;
	private JPanel positionPanel;
	private JPanel salaryPanel;
	private JPanel departmentPanel;
	private JPanel projectsPanel;
	
	public EmployeeInfoWidget(JPanel parent) {
		super(parent,"Employee Info");
		
		JPanel[] idAndUsernamePanels = addSplitSection("ID:", "Username:");
		idPanel = idAndUsernamePanels[0];
		usernamePanel = idAndUsernamePanels[1];
		namePanel = addSection("Full Name:");
		datePanel = addSection("Date of Employment:");
		positionPanel = addSection("Position");
		salaryPanel = addSection("Monthly Salary:");
		departmentPanel = addSection("Department");
	}
	
	public void setEmployee(Employee employee) {
		// TODO
	}
	
	public void setEditable() {
		
	}
	
	public Employee createEmployee() {
		// TODO
		return null;
	}
	
}

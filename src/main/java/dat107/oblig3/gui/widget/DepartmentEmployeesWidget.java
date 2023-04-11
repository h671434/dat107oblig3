package dat107.oblig3.gui.widget;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.collection.EmployeeList;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class DepartmentEmployeesWidget extends Widget {
	
	private final Screen screen;
	
	private final EmployeeList employeesList;
	private final JScrollPane listScrollPane;
	
	private Department department;
	
	public DepartmentEmployeesWidget(Screen screen) {
		super("Employees");
		this.screen = screen;
		this.employeesList = new EmployeeList();
		this.listScrollPane = new JScrollPane(employeesList);
		
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}
	
	private void addComponents() {
		addFullWidthField(listScrollPane);
	}
	
	public void setDepartment(Department department, boolean includeManager) {
		this.department = department;
		
		updateEmployeesList(includeManager);
		
		screen.validate();
	}
	
	private void updateEmployeesList(boolean includeManager) {
		List<Employee> employees = Collections.emptyList();
		
		if(department != null) {
			 employees = department.getEmployees();
				
			 if(!includeManager) {
				 employees.remove(department.getManager());
			 }	
		} 
		
		employeesList.updateContent(employees);
	}
	
}

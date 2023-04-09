package dat107.oblig3.gui.widget;

import java.util.Collections;
import java.util.List;

import javax.swing.JScrollPane;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.collection.EmployeeList;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class DepartmentEmployeesWidget extends InfoWidget {
	
	private final Screen screen;
	
	private EmployeeList employeesList = new EmployeeList();
	
	private Department department;
	
	public DepartmentEmployeesWidget(Screen screen) {
		super("Employees");
		this.screen = screen;
		
		JScrollPane listScrollPane = new JScrollPane(employeesList);
		listScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		addFullWidthField(listScrollPane);
	}
	
	public void setDepartment(Department department, boolean includeManager) {
		this.department = department;
		
		updateEmployeesList(includeManager);
		
		screen.validate();
	}
	
	private void updateEmployeesList(boolean includeManager) {
		List<Employee> employees;
		if(department != null) {
			 employees = department.getEmployees();
				
			 if(!includeManager) {
				 employees.remove(department.getManager());
			 }	
		} else {
			employees = Collections.emptyList();
		}
		
		employeesList.updateContent(employees);
	}
	
}

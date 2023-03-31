package dat107.oblig3.gui.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.widget.DataRepresentation;
import dat107.oblig3.gui.widget.EmployeeTable;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {

	private EmployeeDAO dao = new EmployeeDAO();

	public EmployeesScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		addSearchOption("Username", s -> searchByUsername(s));

		addButton("Edit Position", e -> onEditPosition(), true);
		addButton("Edit Salary", e -> onEditSalary(), true);
		addButton("Edit Department",e -> onEditDepartment(), true);
	}
	
	@Override
	protected DataRepresentation<Employee> getDataRepresentation() {
		return new EmployeeTable();
	}

	private List<Employee> searchById(String search) {
		List<Employee> result;
		try {
			int id = Integer.parseInt(search);
			result = Collections.singletonList(dao.get(id).get());
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
			result = Collections.emptyList();
		}

		return result;
	}

	private List<Employee> searchByUsername(String search) {
		Employee result = dao.getByUsername(search).get();
		if(result == null) {
			return Collections.emptyList();
		} 
		
		return Collections.singletonList(result);
	}
	
	private void onEditPosition() {
		String newPosition = showEditFieldDialog("Position");
		if (!newPosition.isBlank())
			dao.updatePosition(getSelected(), newPosition);
	}
	
	private void onEditSalary() {
		String input = showEditFieldDialog("Salary");
		try {
			double newSalary = Double.parseDouble(input);
			dao.updateSalary(getSelected(), newSalary);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
	private void onEditDepartment() {
		String input = showEditFieldDialog("Salary");
		try {
			int newDepartment = Integer.parseInt(input);
			dao.updateDepartment(getSelected(), newDepartment);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
}

package dat107.oblig3.gui.screen;

import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.widget.entitysets.EmployeeTable;
import dat107.oblig3.gui.widget.entitysets.EntitySet;

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
	protected EntitySet<Employee> getDatasetWidget() {
		return new EmployeeTable();
	}
	
	@Override
	public void display() {
		if(dataset.isEmpty()) {
			dataset.updateContent(dao.getAll());
		}
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
			dao.updatePosition(getSelected().getId(), newPosition);
	}
	
	private void onEditSalary() {
		String input = showEditFieldDialog("Salary");
		try {
			double newSalary = Double.parseDouble(input);
			dao.updateSalary(getSelected().getId(), newSalary);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
	private void onEditDepartment() {
		String input = showEditFieldDialog("Salary");
		try {
			int newDepartment = Integer.parseInt(input);
			dao.updateDepartment(getSelected().getId(), newDepartment);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
}

package no.hvl.dat107.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import no.hvl.dat107.dao.EmployeeDAO;
import no.hvl.dat107.entity.Employee;

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
		addButton("Add Employee", e -> onAddEmployee(), false);

		tableModel.updateContent(dao.getAll());
	}

	private List<Employee> searchById(String search) {
		List<Employee> result;
		try {
			int id = Integer.parseInt(search);
			result = Collections.singletonList(dao.get(id).get());
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
			result = new ArrayList<>();
		}

		return result;
	}

	private List<Employee> searchByUsername(String search) {
		Employee result = dao.getByUsername(search).get();

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
			Double newSalary = Double.parseDouble(input);
			dao.updateSalary(getSelected(), newSalary);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
	private void onEditDepartment() {
		String input = showEditFieldDialog("Salary");
		try {
			Integer newDepartment = Integer.parseInt(input);
			dao.updateDepartment(getSelected(), newDepartment);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input: " + input);
		}
	}
	
	private void onAddEmployee() {
		// TODO
	}

	@Override
	protected SearchScreen<Employee>.DataTableModel getTableModel() {
		return new DataTableModel() {
			@Override
			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
				case 0: return "ID";
				case 1: return "Username";
				case 2: return "First Name";
				case 3: return "Last Name";
				case 4:	return "Position";
				case 5: return "Date of Employment";
				case 6: return "Monthly Salary";
				case 7: return "Department ID";
				}
				return "";
			}

			@Override
			public int getColumnCount() {
				return 8;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Employee e = content.get(rowIndex);
				switch (columnIndex) {
				case 0: return e.getEmployeeId();
				case 1: return e.getUsername();
				case 2: return e.getFirstName();
				case 3: return e.getLastName();
				case 4:	return e.getPosition();
				case 5: return e.getEmploymentDate();
				case 6:	return e.getMonthlySalary();
				case 7: return e.getDepartment();
				}
				return "";
			}
		};
	}

}

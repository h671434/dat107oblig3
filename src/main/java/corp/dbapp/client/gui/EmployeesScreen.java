package corp.dbapp.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import corp.dbapp.data.access.EmployeeDAO;
import corp.dbapp.data.model.Employee;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {

	private EmployeeDAO dao = new EmployeeDAO();

	public EmployeesScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		addSearchOption("Username", s -> searchByUsername(s));

		addBottomPanel(new ButtonPanel());

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

	@Override
	protected SearchScreen<Employee>.DataTableModel getTableModel() {
		return new DataTableModel() {
			@Override
			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return "ID";
				case 1:
					return "Username";
				case 2:
					return "First Name";
				case 3:
					return "Last Name";
				case 4:
					return "Position";
				case 5:
					return "Date of Employment";
				case 6:
					return "Monthly Salary";
				case 7:
					return "Department ID";
				}
				return "";
			}

			@Override
			public int getRowCount() {
				return content.size();
			}

			@Override
			public int getColumnCount() {
				return 8;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Employee e = content.get(rowIndex);
				switch (columnIndex) {
				case 0:
					return e.getEmployeeId();
				case 1:
					return e.getUsername();
				case 2:
					return e.getFirstName();
				case 3:
					return e.getLastName();
				case 4:
					return e.getPosition();
				case 5:
					return e.getEmploymentDate();
				case 6:
					return e.getMonthlySalary();
				case 7:
					return e.getDepartment();
				}
				return "";
			}
		};
	}

	private final class ButtonPanel extends JPanel {

		public ButtonPanel() {
			setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);

			JButton editPositionButton = new JButton("Edit Position");
			editPositionButton.addActionListener(e -> onEditPosition());

			JButton editSalaryButton = new JButton("Edit Salary");
			editSalaryButton.addActionListener(e -> onEditSalary());

			editSalaryButton.setEnabled(false);
			editPositionButton.setEnabled(false);

			JButton editDepartmentButton = new JButton("Edit Department");
			editDepartmentButton.setEnabled(false);
			editDepartmentButton.addActionListener(e -> onEditDepartment());

			addSelectionListener(selected -> {
				boolean enable = selected != null;
				editPositionButton.setEnabled(enable);
				editSalaryButton.setEnabled(enable);
				editDepartmentButton.setEnabled(enable);
			});

			JButton addEmployeeButton = new JButton("Add Employee");
			addEmployeeButton.addActionListener(e -> onAddEmployee());

			add(editPositionButton);
			add(editSalaryButton);
			add(editDepartmentButton);
			add(addEmployeeButton);
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
				JOptionPane.showMessageDialog(null, input + " is not valid input.");
			}
		}

		private void onEditDepartment() {
			String input = showEditFieldDialog("Salary");
			try {
				Double newSalary = Double.parseDouble(input);
				dao.updateSalary(getSelected(), newSalary);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, input + " is not valid input.");
			}
		}

		private void onAddEmployee() {

		}

		private String showEditFieldDialog(String field) {
			return JOptionPane.showInputDialog(table, "Insert new " + field);
		}

	}

}

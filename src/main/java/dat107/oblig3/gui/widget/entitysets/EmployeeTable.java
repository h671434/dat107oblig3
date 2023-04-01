package dat107.oblig3.gui.widget.entitysets;

import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.widget.entitysets.EntityTable.DataTableModel;

@SuppressWarnings("serial")
public class EmployeeTable extends EntityTable<Employee>{
	
	protected EntityTable<Employee>.DataTableModel getTableModel() {
		return new EmployeeTableModel();
	}
	
	private class EmployeeTableModel extends EntityTable<Employee>.DataTableModel {

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
			Employee e = get(rowIndex);
			switch (columnIndex) {
			case 0: return e.getId();
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
		
	}

}

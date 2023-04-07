package dat107.oblig3.gui.collection;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;

@SuppressWarnings("serial")
public class DepartmentTable extends EntityTable<Department> {

	@Override
	protected DepartmentTable.DepartmentTableModel getTableModel() {
		return new DepartmentTableModel();
	}

	public class DepartmentTableModel extends EntityTable<Department>.EntityTableModel {

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0: return "ID";
			case 1: return "Name";
			case 2: return "Manager";
			}
			return "";
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Department d = get(rowIndex);
			switch (columnIndex) {
			case 0: return d.getId();
			case 1: return d.getName();
			case 2: 
				Employee manager = d.getManager();
				return manager.getFirstName() + " " + manager.getLastName();
			}
			return "";
			
		}
		
	}

}

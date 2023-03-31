package dat107.oblig3.gui.widget;

import dat107.oblig3.entity.Department;

@SuppressWarnings("serial")
public class DepartmentTable extends DataTable<Department> {

	@Override
	protected DataTable<Department>.DataTableModel getTableModel() {
		return new DepartmentTableModel();
	}

	public class DepartmentTableModel extends DataTable<Department>.DataTableModel {

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
			case 2: return d.getManager();
			}
			return "";
			
		}
		
	}

}

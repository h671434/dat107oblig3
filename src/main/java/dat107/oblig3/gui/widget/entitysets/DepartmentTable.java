package dat107.oblig3.gui.widget.entitysets;

import dat107.oblig3.entity.Department;
import dat107.oblig3.gui.widget.entitysets.EntityTable.DataTableModel;

@SuppressWarnings("serial")
public class DepartmentTable extends EntityTable<Department> {

	@Override
	protected EntityTable<Department>.DataTableModel getTableModel() {
		return new DepartmentTableModel();
	}

	public class DepartmentTableModel extends EntityTable<Department>.DataTableModel {

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

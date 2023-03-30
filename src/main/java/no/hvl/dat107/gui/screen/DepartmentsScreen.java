package no.hvl.dat107.gui.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.hvl.dat107.dao.DepartmentDAO;
import no.hvl.dat107.entity.Department;

@SuppressWarnings("serial")
public class DepartmentsScreen extends SearchScreen<Department> {

	private DepartmentDAO dao = new DepartmentDAO();
	
//	private DepartmentInfoWidget infoWidget = new DepartmentInfoWidget();
	
	public DepartmentsScreen() {
		addSearchOption("ID", s -> searchById(s));
		
		addButton("Add Department", e -> onAddDepartment(), false);
		
		tableModel.updateContent(dao.getAll());
	}
	
	private List<Department> searchById(String search) {
		List<Department> result;
		try {
			int id = Integer.parseInt(search);
			result = Collections.singletonList(dao.get(id).get());
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
			result = new ArrayList<>();
		}

		return result;
	}

	private void onAddDepartment() {
		// TODO
	}
	
	@Override
	public void display() {
		if(tableModel.isEmpty()) {
			tableModel.updateContent(dao.getAll());
		}
	}

	@Override
	public void cache() throws Exception {
//		infoWidget.cache();
	}

	
	@Override
	protected SearchScreen<Department>.DataTableModel getTableModel() {
		return new DataTableModel() {

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
				case 0: return d.getDepartmentId();
				case 1: return d.getDepartmentName();
				case 2: return d.getDepartmentManager();
				}
				return "";
				
			}
		};
	}

}

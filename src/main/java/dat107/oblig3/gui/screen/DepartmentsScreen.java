package dat107.oblig3.gui.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.gui.widget.DataRepresentation;
import dat107.oblig3.gui.widget.DepartmentTable;

@SuppressWarnings("serial")
public class DepartmentsScreen extends SearchScreen<Department> {

	private DepartmentDAO dao = new DepartmentDAO();
	
	public DepartmentsScreen() {
		addSearchOption("ID", s -> searchById(s));
		
		addButton("Add Department", e -> onAddDepartment(), false);
		
		dataview.updateContent(dao.getAll());
	}
	
	@Override
	protected DataRepresentation<Department> getDataRepresentation() {
		return new DepartmentTable();
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

}

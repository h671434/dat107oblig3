package dat107.oblig3.gui.screen;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.gui.collection.DepartmentTable;
import dat107.oblig3.gui.collection.EntityCollection;

@SuppressWarnings("serial")
public class DepartmentsScreen extends SearchScreen<Department> {

	private DepartmentDAO dao = new DepartmentDAO();
	
	public DepartmentsScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
	}
	
	@Override
	protected EntityCollection<Department> getDatasetComponent() {
		return new DepartmentTable();
	}
	
	private List<Department> searchById(String search) {
		try {
			int id = Integer.parseInt(search);
			Optional<Department> result = dao.get(id);
			
			if(result.isPresent()) {
				return Collections.singletonList(result.get());
			} 
			
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		return Collections.emptyList();
	}

	@Override
	public void display() {
		if(dataset.isEmpty()) {
			dataset.updateContent(dao.getAll());
		}
	}
}

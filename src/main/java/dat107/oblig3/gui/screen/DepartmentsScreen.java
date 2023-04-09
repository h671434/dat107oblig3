package dat107.oblig3.gui.screen;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.gui.collection.DepartmentTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.widget.DepartmentEmployeesWidget;
import dat107.oblig3.gui.widget.EditDepartmentWidget;

@SuppressWarnings("serial")
public class DepartmentsScreen extends SearchScreen<Department> {

	private final EditDepartmentWidget editDepartmentWidget =
			new EditDepartmentWidget(this);
	private final DepartmentEmployeesWidget employeesWidget = 
			new DepartmentEmployeesWidget(this);
	
	private final JButton viewEmployeesButton;
	
	private DepartmentDAO dao = new DepartmentDAO();
	
	public DepartmentsScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		
		addSelectionListener(selected -> {
			setDepartment(selected);
		});
		
		viewEmployeesButton = addButton("View Employees", e -> onViewEmployees(), true);
		addButton("Delete Department", e -> onDeleteEmployee(), true);
		addButton("Edit Department", e -> onEditDepartment(), true);
		addButton("Add New Department", e -> onAddNewDepartment(), false);
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
	
	public void setDepartment(Department department) {
		editDepartmentWidget.setDepartment(department);
		employeesWidget.setDepartment(department, false);
		
		hideWidget(editDepartmentWidget);
		
		if(department == null) {
			hideWidget(employeesWidget);
		}
	}
	
	public void onViewEmployees() {
		if(!employeesWidget.isShowing()) {
			showWidget(employeesWidget, 1);
			viewEmployeesButton.setText("Hide Employees");
			
			if(!editDepartmentWidget.isShowing()) {
				showWidget(editDepartmentWidget, 0);
			}	
		} else {
			hideWidget(employeesWidget);
			viewEmployeesButton.setText("Show Employees");
		}
	}
	
	public void onEditDepartment() {
		if(!editDepartmentWidget.isShowing()) {
			showWidget(editDepartmentWidget, 0);
		}
		
		editDepartmentWidget.editDepartment();
	}
	
	public void onDeleteEmployee() {
		try {
			dao.delete(getSelected().getId());		
			
			refresh();
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error occured deleting department.");
		}
	}
	
	public void onAddNewDepartment() {
		if(!editDepartmentWidget.isShowing()) {
			showWidget(editDepartmentWidget, 0);
		}
		
		editDepartmentWidget.newDepartment();
	}
	
}

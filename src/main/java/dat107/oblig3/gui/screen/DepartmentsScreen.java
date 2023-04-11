package dat107.oblig3.gui.screen;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.collection.DepartmentTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.widget.DepartmentEmployeesWidget;
import dat107.oblig3.gui.widget.DepartmentEditorWidget;

@SuppressWarnings("serial")
public class DepartmentsScreen extends SearchScreen<Department> {

	private final DepartmentEditorWidget editDepartmentWidget;
	private final DepartmentEmployeesWidget employeesWidget;
	private final JButton viewEmployeesButton;
	private final JButton editButton;
	private final JButton newDepartmentButton;
	
	private final DepartmentDAO dao;
	
	public DepartmentsScreen() {
		this.editDepartmentWidget = new DepartmentEditorWidget(this);
		this.employeesWidget = new DepartmentEmployeesWidget(this);
		this.viewEmployeesButton = 
				createScreenButton("View Employees", e -> onViewEmployees(), true);
		this.editButton =
				createScreenButton("Edit Department", e -> onEditDepartment(), true);
		this.newDepartmentButton =
				createScreenButton("Add New Department", e -> onAddNewDepartment(), false);
		this.dao = new DepartmentDAO();
		
		configureScreen();
		addComponents();
	}
	
	private void configureScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		
		addSelectionListener(selected -> setDepartment(selected));
	}
	
	private void addComponents() {
		addButton(viewEmployeesButton);
		addButton(editButton);
		addButton(newDepartmentButton);
	}
	
	@Override
	protected EntityCollection<Department> getDatasetComponent() {
		return new DepartmentTable();
	}
	
	private List<Department> searchById(String search) {
		Optional<Department> result = Optional.empty();
		
		try {
			result = dao.get(Integer.parseInt(search));
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		// Return single department in list, empty if no department is found.
		return result.map(Collections::singletonList).orElse(Collections.emptyList());
	}

	@Override
	public void display() {
		dataset.updateContent(dao.getAll());
		
		searchBar.removeText();
	}
	
	public void setDepartment(Department department) {
		editDepartmentWidget.setDepartment(department);
		employeesWidget.setDepartment(department, false);
		
		hideWidget(editDepartmentWidget);
		
		if(department == null) {
			hideWidget(employeesWidget);
			viewEmployeesButton.setText("Show Employees");
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
			hideWidget(editDepartmentWidget);
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
	
	public void onAddNewDepartment() {
		if(!editDepartmentWidget.isShowing()) {
			showWidget(editDepartmentWidget, 0);
		}
		
		editDepartmentWidget.createNewDepartment();
	}
	
}

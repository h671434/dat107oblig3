package dat107.oblig3.gui.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.collection.EmployeeTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.widget.EmployeeEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {
	
	private final EmployeeEditorWidget editEmployeeWidget;
	private final ParticipationsWidget projectsWidget;
	private final JButton viewProjectsButton;
	private final JButton deleteButton;
	private final JButton editButton;
	private final JButton newEmployeeButton;
	
	private final EmployeeDAO dao;

	public EmployeesScreen() {
		this.editEmployeeWidget = new EmployeeEditorWidget(this);
		this.projectsWidget = new ParticipationsWidget("Projects", this);
		this.viewProjectsButton = 
				createScreenButton("View Projects", e -> onViewProjects(), true);
		this.deleteButton = 
				createScreenButton("Delete Employee", e -> onDeleteEmployee(), true);
		this.editButton =		
				createScreenButton("Edit Employee", e -> onEditEmployee(), true);
		this.newEmployeeButton = 
				createScreenButton("Add New Employee", e -> onNewEmployee(), false);
		this.dao = new EmployeeDAO();

		configureScreen();
		addComponents();
	}
	
	private void configureScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		addSearchOption("Username", s -> searchByUsername(s));
		
		addSelectionListener(selected -> setEmployee(selected));
	}
	
	private void addComponents() {
		addButton(viewProjectsButton);
		addButton(deleteButton);
		addButton(editButton);
		addButton(newEmployeeButton);
	}

	@Override
	protected EntityCollection<Employee> getDatasetComponent() {
		return new EmployeeTable();
	}

	private List<Employee> searchById(String search) {
		Optional<Employee> result = Optional.empty();
		
		try {
			result = dao.get(Integer.parseInt(search));
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		// Return as list, empty if no employee is found.
		return result.map(Collections::singletonList).orElse(Collections.emptyList());
	}

	private List<Employee> searchByUsername(String search) {
		Optional<Employee> result = dao.getByUsername(search);
		
		return result.map(Collections::singletonList).orElse(Collections.emptyList());
	}
	
	public void setEmployee(Employee selected) {
		editEmployeeWidget.setEmployee(selected);
		projectsWidget.setEmployee(selected);
		
		hideWidget(editEmployeeWidget);
		
		if(selected == null) {
			hideWidget(projectsWidget);
			viewProjectsButton.setText("View Projects");
		}
	}
	
	public void onViewProjects() {
		if(!projectsWidget.isShowing()) {
			showWidget(projectsWidget, 1);
			viewProjectsButton.setText("Hide Projects");
		} else {
			hideWidget(projectsWidget);
			viewProjectsButton.setText("View Projects");
		}
	}
		
	public void onEditEmployee() {	
		editEmployeeWidget.editEmployee();
		
		if(!editEmployeeWidget.isShowing()) {
			showWidget(editEmployeeWidget, 0);
		}
	}
	
	public void onDeleteEmployee() {
		try {
			dao.delete(getSelected().getId());
			
			refresh();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error occured deleting employee.");
		}
	}
	
	public void onNewEmployee() {
		dataset.clearSelection();
		setEmployee(null);
		
		if(!editEmployeeWidget.isShowing()) {
			showWidget(editEmployeeWidget, 0);
		}
		
		editEmployeeWidget.newEmployee();
	}
	
	@Override
	public void display() {
		dataset.updateContent(dao.getAll());
		
		searchBar.removeText();
	}
	
}

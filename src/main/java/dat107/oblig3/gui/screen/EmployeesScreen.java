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
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.collection.EmployeeTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.widget.EmployeeEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {
	
	private final EmployeeEditorWidget editEmployeeWidget = 
			new EmployeeEditorWidget(this);
	private final ParticipationsWidget projectsWidget = 
			new ParticipationsWidget("Projects", this);
	
	private JButton viewProjectsButton;
	
	private EmployeeDAO dao = new EmployeeDAO();

	public EmployeesScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		addSearchOption("Username", s -> searchByUsername(s));

		addSelectionListener(selected -> {
			setEmployee(selected);
		});
		
		viewProjectsButton = addButton("View Projects", e -> onViewProjects(), true);
		addButton("Delete Employee", e -> onDeleteEmployee(), true);
		addButton("Edit Employee", e -> onEditEmployee(), true);
		addButton("Add New Employee", e -> onNewEmployee(), false);
	}

	@Override
	protected EntityCollection<Employee> getDatasetComponent() {
		return new EmployeeTable();
	}

	private List<Employee> searchById(String search) {
		try {
			int id = Integer.parseInt(search);
			
			Optional<Employee> result = dao.get(id);
			
			if(result.isPresent()) {
				return Collections.singletonList(result.get());
			} 
			
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		return Collections.emptyList();
	}

	private List<Employee> searchByUsername(String search) {
		Optional<Employee> result = dao.getByUsername(search);
		
		if(result.isPresent()) {
			return Collections.singletonList(result.get());
		} 

		return Collections.emptyList();
	}
	
	@Override
	public void display() {
		dataset.updateContent(dao.getAll());
		
		searchBar.removeText();
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
		} catch(Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, 
					"Error occured deleting employee.");
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
	
}

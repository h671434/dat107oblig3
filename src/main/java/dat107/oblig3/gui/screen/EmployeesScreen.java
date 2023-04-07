package dat107.oblig3.gui.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.collection.EmployeeTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.widget.AboutEmployeeWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {
	
	private final EmployeeWidgetsPanel widgets = new EmployeeWidgetsPanel();
	
	private JButton viewProjectsButton;
	
	private EmployeeDAO dao = new EmployeeDAO();

	public EmployeesScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		addSearchOption("Username", s -> searchByUsername(s));

		viewProjectsButton = addButton("View Projects", e -> widgets.onViewProjects(), true);
		addButton("Edit Employee", e -> widgets.onEditEmployee(), true);
		addButton("Add New Employee", e -> widgets.onNewEmployee(), false);
		
		addSelectionListener(selected -> {
			widgets.hideAboutWidget();
			widgets.setEmployee(selected);
		});
		
		
		setRightPanel(widgets);
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
	
	public class EmployeeWidgetsPanel extends JPanel {
		
		private AboutEmployeeWidget aboutWidget = new AboutEmployeeWidget(EmployeesScreen.this);
		private ParticipationsWidget projectsWidget = new ParticipationsWidget("Projects", EmployeesScreen.this);
		
		GridBagConstraints positions = new GridBagConstraints() {{
				ipady = 16;
				ipadx = 20;
				insets = new Insets(20, 20, 20, 20);
				weightx = 1;
				weighty = 1;
				fill = GridBagConstraints.HORIZONTAL;
		}};
		
		public EmployeeWidgetsPanel() {
			setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
			setLayout(new GridBagLayout());
		}
		
		public void onViewProjects() {
			if(!projectsWidgetIsShowing()) {
				showProjectsWidget();
			} else {
				hideProjectsWidget();
			}
		}
		
		private boolean projectsWidgetIsShowing() {
			return List.of(getComponents()).contains(projectsWidget);
		}
		
		private void showProjectsWidget() {
			positions.gridy = 1;
			add(projectsWidget, positions);
			
			viewProjectsButton.setText("Hide Projects");
			
			getRootPane().validate();
		}
		
		private void hideProjectsWidget() {
			remove(projectsWidget);
			
			viewProjectsButton.setText("View Projects");
			
			getRootPane().validate();
		}
 		
		public void onEditEmployee() {	
			aboutWidget.editEmployee();
			
			if(!aboutWidgetIsShowing()) {
				showAboutWidget();
			}
		}
		
		private boolean aboutWidgetIsShowing() {
			return List.of(getComponents()).contains(aboutWidget);
		}
		
		private void showAboutWidget() {
			positions.gridy = 0;
			add(aboutWidget, positions);	
			
			getRootPane().validate();
		}
		
		private void hideAboutWidget() {
			remove(aboutWidget);
			
			getRootPane().validate();
		}
		
		public void onNewEmployee() {
			dataset.clearSelection();
			setEmployee(null);
			
			if(!aboutWidgetIsShowing()) {
				showAboutWidget();
			}
			
			aboutWidget.newEmployee();
		}
		
		public void setEmployee(Employee selected) {
			aboutWidget.setEmployee(selected);
			projectsWidget.setEmployee(selected);
			
			if(selected == null) {
				hideProjectsWidget();
				hideAboutWidget();
			}
		}
		
	}
	
}

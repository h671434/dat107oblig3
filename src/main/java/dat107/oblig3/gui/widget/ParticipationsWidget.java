package dat107.oblig3.gui.widget;

import java.awt.Dimension;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.collection.ProjectParticipationList;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class ParticipationsWidget extends InfoWidget {
	
	private final Screen screen;
	
	private final ProjectParticipationList list = new ProjectParticipationList();
	
	private final JButton addEmployeeToProjectButton = 
			createWidgetButton("Add New Participation", e -> onAddNewParticipation());
	private final JButton saveButton = 
			createWidgetButton("Save", 	e -> onSave());
	private final JButton cancelButton = 
			createWidgetButton("Cancel", e -> onCancel());
	
	private InfoWidget newParticipationWidget;
	
	private EntityComboBox<Employee> employeeComboBox = 
			EntityComboBox.createEmployeeComboBox();
	private EntityComboBox<Project> projectComboBox = 
			EntityComboBox.createProjectComboBox();
	private NumericField hoursField = new NumericField(4);
	
	public ParticipationsWidget(String title, Screen screen) {
		super(title);
		this.screen = screen;
		
		JScrollPane listScrollPane = new JScrollPane(list);
		
		listScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		addFullWidthField(listScrollPane);
		
		setButtons(addEmployeeToProjectButton);
	}
	
	private void onAddNewParticipation() {
		addFullWidthField(createNewParticipationWidget());
		
		setButtons(cancelButton, saveButton);
		
		screen.validate();
	}
	
	private InfoWidget createNewParticipationWidget() {
		newParticipationWidget = new InfoWidget("New Participation");
		
		newParticipationWidget.addLabeledField(
				"Employee", employeeComboBox);
		newParticipationWidget.addLabeledField(
				"Project", projectComboBox);
		newParticipationWidget.addLabeledField(
				"Hours", hoursField, "Optional");
		
		return newParticipationWidget;
	}
	
	private void onSave() {
		EmployeeDAO dao = new EmployeeDAO();
		
		try {
			ProjectParticipation newParticipation;
			if(hoursField.getText().isBlank()) {
				newParticipation = dao.addEmployeeToProject(
							(Employee)employeeComboBox.getSelectedItem(),
							(Project)projectComboBox.getSelectedItem());
			} else {
				newParticipation = dao.addEmployeeToProject(
						(Employee)employeeComboBox.getSelectedItem(),
						(Project)projectComboBox.getSelectedItem(),
						hoursField.getInt());
			}
			
			list.addAdditionalEntry(newParticipation);
			
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(screen, 
					"Error saving new project participation.");
		}
		
		onCancel();
	}
	
	private void onCancel() {
		removeNewParticipationWidget();
		
		setButtons(addEmployeeToProjectButton);
		
		screen.validate();
	}
	
	private void removeNewParticipationWidget() {
		removeField(newParticipationWidget);
		newParticipationWidget = null;
	}

	public void setEmployee(Employee employee) {
		employeeComboBox.setSelectedItem(employee);
		employeeComboBox.setEditable(false);
		projectComboBox.setSelectedItem(null);
		projectComboBox.setEditable(true);
		
		if(newParticipationWidget != null) {
			removeNewParticipationWidget();
		}
		
		setTitle("Projects");
		
		list.setListType(ProjectParticipationList.ListContent.PROJECT);
		
		if(employee != null) {
			list.updateContent(employee.getParticipations());
		} else {
			list.updateContent(Collections.emptyList());
		}
		
		setButtons(addEmployeeToProjectButton);
		
		screen.validate();
	}
	
	public void setProject(Project project) {
		projectComboBox.setSelectedItem(project);
		projectComboBox.setEditable(false);
		employeeComboBox.setSelectedItem(null);
		employeeComboBox.setEditable(true);
		
		if(newParticipationWidget != null) {
			removeNewParticipationWidget();
		}
		
		setTitle("Participants");
		
		list.setListType(ProjectParticipationList.ListContent.EMPLOYEE);
		
		if(project != null) {
			list.updateContent(project.getParticipations());
		} else {
			list.updateContent(Collections.emptyList());
		}
		
		setButtons(addEmployeeToProjectButton);
		
		screen.validate();
	}
	
}

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
	
	private final ProjectParticipationList participationsList = 
			new ProjectParticipationList();
	
	private final JButton addNewPartcipationButton = 
			createWidgetButton("Add New Participation", e -> onAddParticipation());
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
		
		JScrollPane listScrollPane = new JScrollPane(participationsList);
		
		listScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		addFullWidthField(listScrollPane);
		
		setButtons(addNewPartcipationButton);
	}
	
	private void onAddParticipation() {
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
			Employee employee = (Employee)employeeComboBox.getSelectedItem();
			Project project = (Project)projectComboBox.getSelectedItem();
			
			ProjectParticipation newParticipation;
			
			if(hoursField.getText().isBlank()) {
				newParticipation = dao.addEmployeeToProject(
						employee.getId(), project.getId());
			} else {
				newParticipation = dao.addEmployeeToProject(
						employee.getId(), project.getId(),
						hoursField.getInt());
			}
			
			participationsList.createEntryAndAdd(newParticipation);
			
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(screen, 
					"Error saving new project participation.");
		}
		
		onCancel();
	}
	
	private void onCancel() {
		removeNewParticipationWidget();
		
		setButtons(addNewPartcipationButton);
		
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
		
		participationsList.setListType(ProjectParticipationList.ListContent.PROJECT);
		
		if(employee != null) {
			participationsList.updateContent(employee.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
		
		setButtons(addNewPartcipationButton);
		
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
		
		participationsList.setListType(ProjectParticipationList.ListContent.EMPLOYEE);
		
		if(project != null) {
			participationsList.updateContent(project.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
		
		setButtons(addNewPartcipationButton);
		
		screen.validate();
	}
	
}

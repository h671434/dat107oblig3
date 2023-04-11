package dat107.oblig3.gui.widget;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;
import jakarta.persistence.EntityExistsException;

@SuppressWarnings("serial")
public class ParticipationEditorWidget extends Widget {

	private final Screen screen;
	
	private final EntityComboBox<Employee> employeeComboBox;
	private final EntityComboBox<Project> projectComboBox;
	private final JTextField roleField;
	private final NumericField hoursField;
	
	private ProjectParticipation participationToEdit;
	
	public ParticipationEditorWidget(Screen screen) {
		super("About Participation");
		this.screen = screen;
		this.employeeComboBox = EntityComboBox.createEmployeeComboBox();
		this.projectComboBox = EntityComboBox.createProjectComboBox();
		this.roleField = new StyledTextField(12);
		this.hoursField = new NumericField(12);

		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		employeeComboBox.setPreferredSize(hoursField.getPreferredSize());
		projectComboBox.setPreferredSize(hoursField.getPreferredSize());
	}
	
	private void addComponents() {
		addLabeledField("Employee:", employeeComboBox);
		addLabeledField("Project:", projectComboBox);
		addLabeledField("Role:", roleField);
		addLabeledField("Hours:", hoursField);
	}

	public void edit(ProjectParticipation participationToEdit) {
		setParticipationToEdit(participationToEdit);
		
		setTitle("Edit Participation");
		updateFields();
		enableComboBoxes(false);
	}
	
	public void setParticipationToEdit(ProjectParticipation participationToEdit) {
		this.participationToEdit = participationToEdit;
	}
	
	private void updateFields() {
		Employee employee = null;
		Project project = null;
		
		if(participationToEdit != null) {
			employee = participationToEdit.getEmployee();
			project = participationToEdit.getProject();
		}
		
		employeeComboBox.setSelectedItem(employee);
		projectComboBox.setSelectedItem(project);
	}
	
	private void enableComboBoxes(boolean enable) {
		employeeComboBox.setEditable(enable);
		projectComboBox.setEditable(enable);
	}
	
	public void newParticipation() {
		setParticipationToEdit(null);
		
		setTitle("New Participation");
		updateFields();
		enableComboBoxes(true);
	}
	
	public void newParticipation(Employee preselected) {
		newParticipation();
		
		employeeComboBox.setSelectedItem(preselected);
	}
	
	public void newParticipation(Project preselected) {
		newParticipation();
		
		projectComboBox.setSelectedItem(preselected);
	}
	
	public void save() {
		if(participationToEdit != null) {
			saveExistingParticipation();
		} else {
			saveNewParticipation();
		}
		
		setTitle("About Participation");
	}
	
	private void saveExistingParticipation() {
		if(!validqteFieldInputsForExistingEmployee()) {
			return;
		}
		
		Employee employee = (Employee) employeeComboBox.getSelectedItem();
		Project project = (Project) projectComboBox.getSelectedItem();
		String role = roleField.getText();
		int hours = hoursField.getInt();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		boolean shouldSaveAsNew = false;
		
		try {
			dao.updateProjectParticipation(employee.getId(), project.getId(), role, hours);
		} catch (IllegalArgumentException e) {
			shouldSaveAsNew = askUserOnException(
					"Project partcicpation doesn't exist.",
					"Do you wish to save as new?");
		} catch (Throwable e) {
			e.printStackTrace();
			showErrorMessage("Unexpected error updating project participation.");
		}
		
		if(shouldSaveAsNew) {
			saveNewParticipation();
		}
	}
	
	private boolean validqteFieldInputsForExistingEmployee() {
		if(!employeeIsValid()) {
			showErrorMessage("No employee selected");
			return false;
		}
		
		if(!projectIsValid()) {
			showErrorMessage("No project selected");
			return false;
		}
		
		if(!hoursIsValid()) {
			showErrorMessage("Hours field is empty or invalid.");
			return false;
		}
		
		return true;
	}
	
	private boolean employeeIsValid() {
		Employee employee = (Employee) employeeComboBox.getSelectedItem();
		
		return employee != null;
	}
	
	private boolean projectIsValid() {
		Project project = (Project) projectComboBox.getSelectedItem();
		
		return project != null;
	}
	
	private boolean roleIsValid() {
		return !roleField.getText().isBlank();
	}
	
	private boolean hoursIsValid() {
		try {
			hoursField.getInt();
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void saveNewParticipation() {
		if(!validateFieldInputsForNewParticipation()) {
			return;
		}
		
		EmployeeDAO dao = new EmployeeDAO();
		
		try {
			if(hoursIsValid() && roleIsValid()) {
				dao.addEmployeeToProject(
						((Employee) employeeComboBox.getSelectedItem()).getId(), 
						((Project) projectComboBox.getSelectedItem()).getId(), 
						roleField.getText(), 
						hoursField.getInt());
				
			} else if(roleIsValid())  {
				dao.addEmployeeToProject(
						((Employee) employeeComboBox.getSelectedItem()).getId(), 
						((Project) projectComboBox.getSelectedItem()).getId(), 
						roleField.getText());
				
			} else if (hoursIsValid()) {
				dao.addEmployeeToProject(
						((Employee) employeeComboBox.getSelectedItem()).getId(), 
						((Project) projectComboBox.getSelectedItem()).getId(), 
						hoursField.getInt());	
				
			} else {
				dao.addEmployeeToProject((
						(Employee) employeeComboBox.getSelectedItem()).getId(), 
						((Project) projectComboBox.getSelectedItem()).getId());
			}
			
		} catch (EntityExistsException e) {
			e.printStackTrace();
			String error = "Participation already exists.";
			
			if(hoursIsValid()) {
				askUserToUpdateExisting(error);
			} else {
				showErrorMessage(error);
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			showErrorMessage("Unexpected error updating project participation.");
		}
	}
	
	private boolean validateFieldInputsForNewParticipation() {
		if(!employeeIsValid()) {
			showErrorMessage("No employee selected.");
			return false;
		}
		
		if(!projectIsValid()) {
			showErrorMessage("No project selected.");
			return false;
		}
		
		return true;
	}
 	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(screen, message, 
				"Error saving project participation", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void askUserToUpdateExisting(String error) {
		boolean doUpdate = askUserOnException(error, 
				"Do you wish to update the existing participation?");
		
		if(doUpdate) {
			saveExistingParticipation();
		}
	}
	
	private boolean askUserOnException(String error, String question) {
		int answer = JOptionPane.showConfirmDialog(screen, 
				error + "\n" + question, "Error saving project participation", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		
		return answer == JOptionPane.YES_OPTION;
	}
	
}

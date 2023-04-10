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
import dat107.oblig3.gui.inputcontrols.ToggleableTextField;
import dat107.oblig3.gui.screen.Screen;
import jakarta.persistence.EntityExistsException;

@SuppressWarnings("serial")
public class ParticipationEditorWidget extends Widget {

	private final Screen screen;
	
	private final EntityComboBox<Employee> employeeComboBox = 
			EntityComboBox.createEmployeeComboBox();
	private final EntityComboBox<Project> projectComboBox = 
			EntityComboBox.createProjectComboBox();
	private final JTextField roleField = new ToggleableTextField(12);
	private final NumericField hoursField = new NumericField(12);
	
	private ProjectParticipation participationToEdit;
	
	public ParticipationEditorWidget(Screen screen) {
		super("About Participation");
		this.screen = screen;
		
		Dimension fieldSize = hoursField.getPreferredSize();
		employeeComboBox.setPreferredSize(fieldSize);
		projectComboBox.setPreferredSize(fieldSize);
		
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
			saveChanges();
		} else {
			saveNewParticipation();
		}
		
		setTitle("About Participation");
	}
	
	/**
	 * Trys to udate project participation with values from fields.
	 * Asks user to save as new project participation if particpation doesn't exist.
	 */
	private void saveChanges() {
		if(!employeeIsValid()) {
			showErrorMessage("No employee selected");
			return;
		}
		if(!projectIsValid()) {
			showErrorMessage("No project selected");
			return;
		}
		if(!hoursIsValid()) {
			showErrorMessage("Hours field is empty or invalid.");
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
	
	/**
	 * Trys to save a new project participation with values from fields.
	 * Asks to update existing participation of participation exists.
	 */
	private void saveNewParticipation() {
		if(!employeeIsValid()) {
			showErrorMessage("No employee selected.");
			return;
		}
		if(!projectIsValid()) {
			showErrorMessage("No project selected.");
			return;
		}
		
		Employee employee = (Employee) employeeComboBox.getSelectedItem();
		Project project = (Project) projectComboBox.getSelectedItem();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		String error = null;
		boolean shouldAskToUpdate = false;
		
		try {
			if(hoursIsValid() && roleIsValid()) {
				String role = roleField.getText();
				int hours = hoursField.getInt();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), role, hours);
			} else if(roleIsValid())  {
				String role = roleField.getText();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), role);
			} else if (hoursIsValid()) {
				int hours = hoursField.getInt();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), hours);	
			} else {
				dao.addEmployeeToProject(employee.getId(), project.getId());
			}
			
		} catch (EntityExistsException e) {
			e.printStackTrace();
			error = "Participation already exists.";
			shouldAskToUpdate = hoursIsValid();
		} catch (Throwable e) {
			e.printStackTrace();
			error = "Unexpected error updating project participation.";
		}
		
		if(error != null && shouldAskToUpdate) {
			askUserToUpdateExisting(error);
		}
		if(error != null & !shouldAskToUpdate) {
			showErrorMessage(error);
		}
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
	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(screen, message, 
				"Error saving project participation", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void askUserToUpdateExisting(String error) {
		boolean doUpdate = askUserOnException(error, 
				"Do you wish to update the existing participation?");
		
		if(doUpdate) {
			saveChanges();
		}
	}
	
	private boolean askUserOnException(String error, String question) {
		int answer = JOptionPane.showConfirmDialog(screen, 
				error + "\n" + question, "Error saving project participation", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		
		return answer == JOptionPane.YES_OPTION;
	}
	
}

package dat107.oblig3.gui.widget;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.ToggleableTextField;
import dat107.oblig3.gui.screen.Screen;

/**
 * Widget to view and edit the fields of a department.
 */
@SuppressWarnings("serial")
public class DepartmentEditorWidget extends Widget {

	private final Screen screen;
	
	private final JTextField idField = new ToggleableTextField(12);
	private final JTextField nameField = new ToggleableTextField(12);
	private final EntityComboBox<Employee> managerComboBox = 
			new EntityComboBox<>(() -> getEmployees());
	
	private final JButton saveButton = createWidgetButton("Save", e -> onSave());
	private final JButton cancelButton = createWidgetButton("Cancel", e -> onCancel());
	
	private DepartmentDAO dao = new DepartmentDAO();
	private Department department;
	
	public DepartmentEditorWidget(Screen screen) {
		super("About Department");
		this.screen = screen;
		
		idField.setEditable(false);
		nameField.setEditable(false);
		managerComboBox.setPreferredSize(nameField.getPreferredSize());
		
		addLabeledField("ID:", idField);
		addLabeledField("Name:", nameField);
		addLabeledField("Manager:", managerComboBox);
	}
	
	/**
	 * Returns the employees from the current department to the employees-combobox.
	 */
	private List<Employee> getEmployees() {
		if(department == null) {
			return new EmployeeDAO().getAll();
		}
		
		List<Employee> employees = department.getEmployees();
		
		employees.add(department.getManager());
		
		return employees;
	}

	public void setDepartment(Department department) {
		this.department = department;
		
		if(department != null) {
			fillAllFields();
		} else {
			emptyAllFields();
		}
			
		setAllFieldsEditable(false);
		
		screen.validate();
	}
	
	private void emptyAllFields() {
		idField.setText("Generated");
		nameField.setText("");
		
		managerComboBox.refresh();
		managerComboBox.setSelectedItem(null);
	}
	
	/**
	 * Fills the widgets fields with info from the current department.
	 */
	private void fillAllFields() {
		idField.setText(department.getId() + "");
		nameField.setText(department.getName());
		
		managerComboBox.refresh();
		managerComboBox.setSelectedItem(department.getManager());
	}
	
	private void setAllFieldsEditable(boolean editable) {
		nameField.setEditable(editable);
		managerComboBox.setEditable(editable);
	}
	
	/**
	 * Updates gui components to allow editing.
	 */
	public void editDepartment() {		
 		managerComboBox.setEditable(true);
		
		setTitle("Edit Department");
		setButtons(cancelButton, saveButton);
	}
	
	/**
	 * Sets department to null and updates gui components.
	 */
	public void newDepartment() {
		setDepartment(null);
		
		idField.setText("Generated");
		setTitle("New Department");
		setAllFieldsEditable(true);
		setButtons(cancelButton, saveButton);
	}

	private void onSave() {
		if(department != null) {
			saveChanges();
		} else {
			saveNewDepartment();
		}
		
		onCancel();
	}
	
	/**
	 * Updates manager if the field has been changed.
	 */
	private void saveChanges() {
		Employee newManager = (Employee) managerComboBox.getSelectedItem();
		
		if(!newManager.equals(department.getManager())) {
			saveManager();
		}
	}
	
	private void saveManager() {
		try {
			dao.updateManager(department.getId(), 
					(Employee) managerComboBox.getSelectedItem());
		} catch (Throwable e) {
			handleSaveException(e, "Error occured updating manager.");
		}
	}
	
	private void saveNewDepartment() {
		try {
			dao.saveNewDepartment(nameField.getText(),
					(Employee) managerComboBox.getSelectedItem());
		} catch (Throwable e) {
			handleSaveException(e, "Error occured saving new department.");
		}
	}
	
	private void handleSaveException(Throwable e, String message) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(screen, message);
	}
	
	private void onCancel() {
		setTitle("About Department");
		setAllFieldsEditable(false);
		setButtons();
		
		getParent().remove(this);
		screen.refresh();
	}
	
}

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
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;

/**
 * Widget to view and edit the fields of a department.
 */
@SuppressWarnings("serial")
public class DepartmentEditorWidget extends Widget {

	private final Screen screen;
	
	private final JTextField idField;
	private final JTextField nameField;
	private final EntityComboBox<Employee> managerComboBox;
	private final JButton saveButton;
	private final JButton cancelButton;
	
	private DepartmentDAO dao = new DepartmentDAO();
	private Department department;
	
	public DepartmentEditorWidget(Screen screen) {
		super("About Department");
		this.screen = screen;
		this.idField = new StyledTextField(12);
		this.nameField = new StyledTextField(12);
		this.managerComboBox = new EntityComboBox<>(() -> getEmployees());
		this.saveButton = createWidgetButton("Save", e -> onSave());
		this.cancelButton = createWidgetButton("Cancel", e -> onCancel());
		this.dao = new DepartmentDAO();

		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		idField.setEditable(false);
		nameField.setEditable(false);
		managerComboBox.setPreferredSize(nameField.getPreferredSize());
	}
	
	private void addComponents() {
		addLabeledField("ID:", idField);
		addLabeledField("Name:", nameField);
		addLabeledField("Manager:", managerComboBox);
	}
	

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
	
	public void editDepartment() {		
 		managerComboBox.setEditable(true);
		
		setTitle("Edit Department");
		setButtons(cancelButton, saveButton);
	}

	public void createNewDepartment() {
		setDepartment(null);
		
		idField.setText("Generated");
		setTitle("New Department");
		setAllFieldsEditable(true);
		setButtons(cancelButton, saveButton);
	}

	private void onSave() {
		if(department != null) {
			saveExistingDepartment();
		} else {
			saveNewDepartment();
		}
		
		onCancel();
	}
	
	private void saveExistingDepartment() {
		Employee newManager = (Employee) managerComboBox.getSelectedItem();
		
		if(!newManager.equals(department.getManager())) {
			try {
				dao.updateManager(department.getId(), 
						(Employee) managerComboBox.getSelectedItem());
			} catch (Throwable e) {
				handleSaveException(e, "Error occured updating manager.");
			}
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

package dat107.oblig3.gui.widget;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.inputcontrols.DateField;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.inputcontrols.ToggleableTextField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class EmployeeEditorWidget extends InfoWidget {
	
	private final Screen screen;
	
	private final JTextField id = new ToggleableTextField(10);
	private final JTextField username = new ToggleableTextField(10);
	private final JTextField firstName = new ToggleableTextField(10);
	private final JTextField lastName = new ToggleableTextField(10);
	private final DateField employmentDate = new DateField();
	private final JTextField position = new ToggleableTextField(10);
	private final NumericField salary = new NumericField(10, true);
	private final EntityComboBox<Department> department = 
			EntityComboBox.createDepartmentComboBox();
	
	private final JButton saveButton = createWidgetButton("Save", e -> onSave());
	private final JButton cancelButton = createWidgetButton("Cancel", e -> onCancel());
	
	private EmployeeDAO dao = new EmployeeDAO();
	private Employee employee;
	
	public EmployeeEditorWidget(Screen screen) {
		super("About Employee");
		this.screen = screen;
		
		id.setEditable(false);
		employmentDate.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);	
		department.setPreferredSize(position.getPreferredSize());
		
		addLabeledField("ID:", id, "Auto-generated");
		addLabeledField("Username:", username, "3 characters");
		addLabeledField("First Name:", firstName);
		addLabeledField("Last Name", lastName);
		addLabeledField("Date of Employment:", employmentDate, "Day/Month/Year");
		addLabeledField("Position:", position);
		addLabeledField("Monthly Salary:", salary);
		addLabeledField("Department:", department);
		
		setAllFieldsEditable(false);
	}
	
	public void setAllFieldsEditable(boolean editable) {
		username.setEditable(editable);
		firstName.setEditable(editable);
		lastName.setEditable(editable);
		employmentDate.setEditable(editable);
		position.setEditable(editable);
		salary.setEditable(editable);
		
		if(employee != null && employee.isManager()) {
			department.setEditable(false);
		} else {
			department.setEditable(editable);
		}
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		
		if(employee != null) {
			fillFields(employee);
		} else {
			emptyAllFields();
		}	
		
		setAllFieldsEditable(false);
		setButtons();
	}
	
	private void fillFields(Employee employee) {
		id.setText(employee.getId() + "");
		username.setText(employee.getUsername());
		firstName.setText(employee.getFirstName());
		lastName.setText(employee.getLastName());
		employmentDate.setDate(employee.getEmploymentDate());
		position.setText(employee.getPosition());
		salary.setText(employee.getMonthlySalary() + "");
		department.setSelectedItem(employee.getDepartment());
	}
	
	public void emptyAllFields() {
		id.setText("");
		username.setText("");
		firstName.setText("");
		lastName.setText("");
		employmentDate.setDateEmpty();
		position.setText("");
		salary.setText("");
		department.setSelectedItem(null);
	}
	
	public void editEmployee() {
		if(employee != null) {
			setTitle("Edit Employee");
			
			position.setEditable(true);
			salary.setEditable(true);
			department.setEditable(true);
			
			setButtons(cancelButton, saveButton);
		}
	}
	
	public void newEmployee() {
		employee = null;
		emptyAllFields();
		setAllFieldsEditable(true);
		
		titleLabel.setText("New Employee");
		id.setText("Generated");
		setButtons(cancelButton, saveButton);
	}
	
	private void onSave() {
		if(employee != null) {
			saveChanges();
		} else {
			saveNewEmployee();
		}
		
		onCancel();
	}
	
	private void saveChanges() {
		if(salary.getDouble() != employee.getMonthlySalary()) {
			saveSalary();
		}
		if(position.getText() != employee.getPosition()) {
			savePosition();
		}
		if(!department.getSelectedItem().equals(employee.getDepartment())) {
			saveDepartment();	
		}
	}
	
	private void saveSalary() {
		try {
			dao.updateSalary(employee.getId(), salary.getDouble());
		} catch(Throwable e) {
			handleSaveException(e, "Error occured while updating salary.");
		}
	}
	
	private void savePosition() {
		try {
			dao.updatePosition(employee.getId(), position.getText());
		} catch(Throwable e) {
			handleSaveException(e, "Error occured while updating position.");
		}
	}
	
	private void saveDepartment() {
		try {
			dao.updateDepartment(employee.getId(), (Department)department.getSelectedItem());
		} catch(Throwable e) {
			handleSaveException(e, "Error occured while updating department.");
		}
	}
	
	private void saveNewEmployee() {
		try {
			 dao.saveNewEmployee(username.getText(), firstName.getText(), 
					lastName.getText(), employmentDate.getDate(), 
					position.getText(), salary.getDouble(),
					(Department) department.getSelectedItem());
			 
		} catch(Throwable e) {
			handleSaveException(e, "Error occured while saving new employee.");
		}
	}
	
	private void handleSaveException(Throwable e, String message) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(screen, message);
	}
	
	private void onCancel() {
		getParent().remove(this);
		
		screen.refresh();
		screen.validate();
	}
	
}

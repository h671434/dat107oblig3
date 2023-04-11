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
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class EmployeeEditorWidget extends Widget {
	
	private final Screen screen;
	
	private final JTextField id;
	private final JTextField username;
	private final JTextField firstName;
	private final JTextField lastName;
	private final DateField employmentDate;
	private final JTextField position;
	private final NumericField salary;
	private final EntityComboBox<Department> department;
	private final JButton saveButton;
	private final JButton cancelButton;
	
	private final EmployeeDAO dao;
	private Employee employee;
	
	public EmployeeEditorWidget(Screen screen) {
		super("About Employee");
		this.screen = screen;
		this.id = new StyledTextField(10);
		this.username = new StyledTextField(10);
		this.firstName = new StyledTextField(10);
		this.lastName = new StyledTextField(10);
		this.employmentDate = new DateField();
		this.position = new StyledTextField(10);
		this.salary = new NumericField(10, true);
		this.department = EntityComboBox.createDepartmentComboBox();
		this.saveButton = createWidgetButton("Save", e -> onSave());
		this.cancelButton = createWidgetButton("Cancel", e -> onCancel());
		this.dao = new EmployeeDAO();
		
		configureComponents();
		addComponents();
	}
	 
	private void configureComponents() {
		id.setEditable(false);
		employmentDate.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);	
		department.setPreferredSize(position.getPreferredSize());
		
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

	private void addComponents() {
		addLabeledField("ID:", id, "Auto-generated");
		addLabeledField("Username:", username, "3 characters");
		addLabeledField("First Name:", firstName);
		addLabeledField("Last Name", lastName);
		addLabeledField("Date of Employment:", employmentDate, "Day/Month/Year");
		addLabeledField("Position:", position);
		addLabeledField("Monthly Salary:", salary);
		addLabeledField("Department:", department);
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

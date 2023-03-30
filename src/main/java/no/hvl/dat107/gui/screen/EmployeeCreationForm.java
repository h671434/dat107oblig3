package no.hvl.dat107.gui.screen;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import no.hvl.dat107.dao.DepartmentDAO;
import no.hvl.dat107.entity.Department;
import no.hvl.dat107.gui.UITheme;

@SuppressWarnings("serial")
public class EmployeeCreationForm extends JPanel {
	
	private JTextField usernameField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField[] employmentDateFields;
	private JTextField positionField;
	private JTextField salaryField;
	private JComboBox<String> departmentField;
	
	public EmployeeCreationForm() {
		setBackground(UITheme.DEFAULT_BACKGROUND_COLOR);
		setPreferredSize(new Dimension(150, 550));
		setLayout(new GridLayout(0, 2));
		
		usernameField = new JTextField(20);
		firstNameField = new JTextField(20);
		lastNameField = new JTextField(20);
		employmentDateFields = new JTextField[] {
				new JTextField(2), 
				new JTextField(2), 
				new JTextField(4)
		};
		positionField = new JTextField(20);
		salaryField = new JTextField(20);
		departmentField = new JComboBox<>();
		
		
		
		List<String> departmentnames = new DepartmentDAO().getNames();
		departmentnamess.forEach(department -> {
			departmentField.addItem(department);
		});
		
	}
}

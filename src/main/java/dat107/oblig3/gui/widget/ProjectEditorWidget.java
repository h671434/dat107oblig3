package dat107.oblig3.gui.widget;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;

import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class ProjectEditorWidget extends Widget {

	private final Screen screen;
	
	private final JTextField idField;
	private final JTextField nameField;
	private final JLabel descriptionLabel;
	private final JTextArea descriptionArea;
	private final JButton saveButton;
	private final JButton cancelButton;
	
	private ProjectDAO dao = new ProjectDAO();
	private Project project;
	
	public ProjectEditorWidget(Screen screen) {
		super("About Project");
		this.screen = screen;
		this.idField = new StyledTextField(12);
		this.nameField = new StyledTextField(12);
		this.descriptionLabel = new JLabel("Description:");
		this.descriptionArea = new JTextArea(5, 20);
		this.saveButton = createWidgetButton("Save", e -> onSave());
		this.cancelButton = createWidgetButton("Cancel", e -> onCancel());
		
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		idField.setEditable(false);
		
		descriptionLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
		descriptionLabel.setBorder(BorderFactory.createEmptyBorder(8, 4, 16, 0));
		
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
	}
	
	private void addComponents() {
		addLabeledField("ID:", idField);
		addLabeledField("Name:", nameField);
		addFullWidthField(descriptionLabel);
		addFullWidthField(descriptionArea);
	}
	
	public void setProject(Project project) {
		this.project = project;
		
		if(project != null) {
			fillAllFields();
		} else {
			emptyAllFields();
		}
			
		setAllFieldsEditable(false);
		
		descriptionArea.setColumns(30);
		
		screen.validate();
	}
	
	private void emptyAllFields() {
		idField.setText("Generated");
		nameField.setText("");
		descriptionArea.setText("");
	}
	
	private void fillAllFields() {
		idField.setText(project.getId() + "");
		nameField.setText(project.getName());
		descriptionArea.setText(project.getDescription());
	}
	
	private void setAllFieldsEditable(boolean editable) {
		nameField.setEditable(editable);
		descriptionArea.setEditable(editable);
	}
	
	public void editProject() {
		setTitle("Edit Project");
		
		descriptionArea.setEditable(true);
		
		setButtons(cancelButton, saveButton);
	}
	
	public void newProject() {
		setTitle("New Project");
		
		emptyAllFields();
		
		idField.setText("Generated");
		
		setAllFieldsEditable(true);
		setButtons(cancelButton, saveButton);
	}
	
	private void onSave() {
		if(project != null) {
			saveExistingProject();
		} else {
			saveNewProject();
		}
		
		onCancel();
	}
	
	private void saveExistingProject() {
		if(!project.getDescription().equals(descriptionArea.getText())) {
			try {
				dao.updateDescription(project.getId(), descriptionArea.getText());
			} catch (Throwable e) {
				handleSaveException(e, "Error occured updating project description.");
			}
		}
	}
	
	private void saveNewProject() {
		try {
			dao.saveNewProject(nameField.getText(), descriptionArea.getText());
		} catch (Throwable e) {
			handleSaveException(e, "Error occured saving new project.");
		}
	}
	
	private void handleSaveException(Throwable e, String message) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(screen, message);
	}
	
	private void onCancel() {
		setTitle("About Project");
		setAllFieldsEditable(false);
		setButtons();
		
		getParent().remove(this);
		screen.refresh();
	}
}

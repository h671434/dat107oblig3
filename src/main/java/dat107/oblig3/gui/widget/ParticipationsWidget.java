package dat107.oblig3.gui.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
public class ParticipationsWidget extends Widget {
	
	private final Screen screen;
	
	private final ProjectParticipationList participationsList = 
			new ProjectParticipationList();
	
	private final ParticipationEditorWidget editorWidget;
	
	private final JButton deleteButton = createWidgetButton(
			"Delete", e -> onDelete());
	private final JButton editButton = createWidgetButton(
			"Edit", e -> onEdit());
	private final JButton addNewPartcipationButton = createWidgetButton(
			"Add New Participation", e -> onAddParticipation());
	private final JButton saveButton = createWidgetButton(
			"Save", e -> onSave());
	private final JButton cancelButton = createWidgetButton(
			"Cancel", e -> onCancel());
	
	private Object selected;
	
	public ParticipationsWidget(String title, Screen screen) {
		super(title);
		this.screen = screen;
		this.editorWidget = new ParticipationEditorWidget(screen);
		
		editorWidget.setBorder(BorderFactory.createEmptyBorder());
		
		participationsList.addSelectionListener(selected -> {
			removeField(editorWidget);
			updateButtons(selected);
		});
		
		JScrollPane listScrollPane = new JScrollPane(
				participationsList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		addFullWidthField(listScrollPane);
		
		setButtons(deleteButton, editButton, addNewPartcipationButton);
		setButtonsEnabled(false);
		
		screen.validate();
	}
	
	private void onDelete() {
		ProjectParticipation selected = participationsList.getSelected();
		
		if(selected == null) {
			showErrorMessage("No projectparticipation selected");
			return;
		}
		
		Employee employee = selected.getEmployee();
		Project project = selected.getProject();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		try {
			dao.removeEmployeeFromProject(employee.getId(), project.getId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			showErrorMessage("Employee has registered hours in the project.");
		} catch (Throwable e) {
			e.printStackTrace();
			showErrorMessage("Error occured deleting project participation");
		}
		
		screen.refresh();
	}
	
	private void onEdit() {
		if(!editorWidget.isShowing()) {
			addFullWidthField(editorWidget);
		}
		
		editorWidget.edit(participationsList.getSelected());
		
		setButtons(cancelButton, saveButton);
		
		screen.validate();
	}
	
	private void onAddParticipation() {
		if(!editorWidget.isShowing()) {
			addFullWidthField(editorWidget);
		}
		
		if(selected instanceof Employee) {
			editorWidget.newParticipation((Employee) selected);
		} else if(selected instanceof Project) {
			editorWidget.newParticipation((Project) selected);
		} else {
			editorWidget.newParticipation();
		}
		
		setButtons(cancelButton, saveButton);
		
		screen.validate();
	}
	
	private void onSave() {
		if(!editorWidget.isShowing()) {
			return;
		}
		
		editorWidget.save();
		
		onCancel();
	}
	
	private void onCancel() {
		if(!editorWidget.isShowing()) {
			return;
		}
		
		removeField(editorWidget);
		setButtons(deleteButton, editButton, addNewPartcipationButton);
		
		screen.validate();
	}
	
	private void updateButtons(ProjectParticipation selected) {
		setButtonsEnabled(selected != null);
	}
	
	private void setButtonsEnabled(boolean enable) {
		deleteButton.setEnabled(enable);
		editButton.setEnabled(enable);
	}
	
	public void setEmployee(Employee employee) {
		selected = employee;
		
		participationsList.setListType(ProjectParticipationList.ListContent.PROJECT);
		
		if(employee != null) {
			participationsList.updateContent(employee.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
		
		setTitle("Projects");
		resetWidget();
	}
	
	public void setProject(Project project) {
		selected = project;
		
		participationsList.setListType(ProjectParticipationList.ListContent.EMPLOYEE);
		
		if(project != null) {
			participationsList.updateContent(project.getParticipations());
		} else {
			participationsList.updateContent(Collections.emptyList());
		}
		
		setTitle("Participants");
		resetWidget();
	}
	
	public void resetWidget() {
		removeField(editorWidget);
		setButtons(deleteButton, editButton, addNewPartcipationButton);
		
		screen.validate();
	}
	
	private void showErrorMessage(String error) {
		JOptionPane.showMessageDialog(screen, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}

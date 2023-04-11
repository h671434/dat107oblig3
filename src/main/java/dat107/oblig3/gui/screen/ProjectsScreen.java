package dat107.oblig3.gui.screen;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.collection.ProjectList;
import dat107.oblig3.gui.widget.ProjectEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;

@SuppressWarnings("serial")
public class ProjectsScreen extends SearchScreen<Project> {

	private final ProjectEditorWidget editProjectWidget;
	private final ParticipationsWidget participantsWidget;
	private final JButton viewParticipantsButton;
	private final JButton deleteButton;
	private final JButton editButton;
	private final JButton newProjectButton;
	
	private final ProjectDAO dao;
	
	public ProjectsScreen() {
		this.editProjectWidget = new ProjectEditorWidget(this);
		this.participantsWidget = new ParticipationsWidget("Paricipants", this);
		this.viewParticipantsButton =
				createScreenButton("View Participants", e -> onViewParticipants(), true);
		this.deleteButton = 
				createScreenButton("Delete Project", e -> onDeleteProject(), true);
		this.editButton = 
				createScreenButton("Edit Project", e -> onEditProject(), true);
		this.newProjectButton = 
				createScreenButton("Add New Project", e -> onAddNewProject(), false);
		this.dao = new ProjectDAO();
		
		configureScreen();
		addComponents();
	}
	
	private void configureScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		
		addSelectionListener(selected -> setProject(selected));
	}
	
	private void addComponents() {
		addButton(viewParticipantsButton);
		addButton(deleteButton);
		addButton(editButton);
		addButton(newProjectButton);
	}
	
	@Override
	protected EntityCollection<Project> getDatasetComponent() {
		return new ProjectList();
	}
	
	private List<Project> searchById(String search) {
		Optional<Project> result = Optional.empty();
		
		try {
			result = dao.get(Integer.parseInt(search));
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		return result.map(Collections::singletonList).orElse(Collections.emptyList());
	}
	
	public void setProject(Project project) {
		editProjectWidget.setProject(project);
		participantsWidget.setProject(project);
		
		hideWidget(editProjectWidget);
		hideWidget(participantsWidget);
		
		viewParticipantsButton.setText("View Participants");
	}
	
	private void onViewParticipants() {
		if(!participantsWidget.isShowing()) {
			showWidget(participantsWidget, 1);
			viewParticipantsButton.setText("Hide Participants");
		} else {
			hideWidget(participantsWidget);
			viewParticipantsButton.setText("View Participants");
		}
	}
	
	public void onEditProject() {
		if(!editProjectWidget.isShowing()) {
			showWidget(editProjectWidget, 0);
		}
		
		editProjectWidget.editProject();
	}
	
	public void onDeleteProject() {
		try {
			dao.delete(getSelected().getId());
			
			refresh();
			
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error occured deleting project.");
		}
	}
	
	public void onAddNewProject() {
		if(!editProjectWidget.isShowing()) {
			showWidget(editProjectWidget, 0);
		}
		
		editProjectWidget.newProject();
	}
	
	@Override
	public void display() {
		dataset.updateContent(dao.getAll());
		dataset.clearSelection();
		
		searchBar.removeText();
	}

}

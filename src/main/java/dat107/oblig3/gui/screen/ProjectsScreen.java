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

	private ProjectEditorWidget editProjectWidget = 
			new ProjectEditorWidget(this);
	private ParticipationsWidget participantsWidget = 
			new ParticipationsWidget("Paricipants", this);
	
	private JButton viewParticipantsButton;
	
	private ProjectDAO dao = new ProjectDAO();
	
	public ProjectsScreen() {
		addSearchOption("Any", s -> dao.search(s));
		addSearchOption("ID", s -> searchById(s));
		
		addSelectionListener(selected -> {
			setProject(selected);
		});
		
		viewParticipantsButton = addButton("View Participants", e -> onViewParticipants(), true);
		addButton("Delete Project", e -> onDeleteProject(), true);
		addButton("Edit Project", e -> onEditProject(), true);
		addButton("Add New Project", e -> onAddNewProject(), false);
	}
	
	@Override
	protected EntityCollection<Project> getDatasetComponent() {
		return new ProjectList();
	}
	
	private List<Project> searchById(String search) {
		try {
			int id = Integer.parseInt(search);
			
			Optional<Project> result = dao.get(id);
			
			if(result.isPresent()) {
				return Collections.singletonList(result.get());
			} 
			
		} catch (NumberFormatException e) {
			showNoResultsDialog("ID must be a number");
		}	
		
		return Collections.emptyList();
	}
	
	public void setProject(Project project) {
		editProjectWidget.setProject(project);
		participantsWidget.setProject(project);
		
		hideWidget(editProjectWidget);
		
		if(project == null) {
			hideWidget(participantsWidget);
		}
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
		} catch(Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, 
					"Error occured deleting employee.");
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
		
		searchBar.removeText();
	}

}

package dat107.oblig3.gui.screen;

import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.collection.ProjectList;

@SuppressWarnings("serial")
public class ProjectsScreen extends SearchScreen<Project> {

	private ProjectDAO dao = new ProjectDAO();
	
	public ProjectsScreen() {
		addSearchOption("Any", s -> dao.search(s));
		
		addButton("View Participants", e -> onViewParticipants(), true);
		addButton("Add Participant", e -> onAddParticipant(), true);
		addButton("Add New Project", e -> onAddNewProject(), false);
	}
	
	@Override
	protected EntityCollection<Project> getDatasetComponent() {
		return new ProjectList();
	}
	
	private void onViewParticipants() {
		// TODO
	}
	
	private void onAddParticipant() {
		// TODO
	}
	
	private void onAddNewProject() {
		// TODO
	}
	
	@Override
	public void display() {
		if(dataset.isEmpty()) {
			dataset.updateContent(dao.getAll());
		}
	}

}

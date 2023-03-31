package dat107.oblig3.gui.screen;

import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.widget.DataRepresentation;

@SuppressWarnings("serial")
public class ProjectsScreen extends SearchScreen<Project> {

	private ProjectDAO dao = new ProjectDAO();
	
	@Override
	protected DataRepresentation<Project> getDataRepresentation() {
		return null; // TODO
	}
	@Override
	public void display() {
		if(dataview.isEmpty()) {
			dataview.updateContent(dao.getAll());
		}
	}

}

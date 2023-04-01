package dat107.oblig3.gui.screen;

import java.util.List;

import javax.swing.JScrollBar;

import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.widget.entitysets.EntitySet;
import dat107.oblig3.gui.widget.entitysets.ProjectList;

@SuppressWarnings("serial")
public class ProjectsScreen extends SearchScreen<Project> {

	private ProjectDAO dao = new ProjectDAO();
	
	public ProjectsScreen() {
		addSearchOption("Any", s -> searchByAny(s));
		
	}
	
	private List<Project> searchByAny(String s) {
		return dao.search(s);
	}
	
	@Override
	protected EntitySet<Project> getDatasetWidget() {
		return new ProjectList();
	}
	@Override
	public void display() {
		if(dataset.isEmpty()) {
			dataset.updateContent(dao.getAll());
			scrollPane.validate();
			
		}
	}

}

package dat107.oblig3.dao;

import java.util.List;

import dat107.oblig3.entity.Project;

public class ProjectDAO extends DAO<Project>{

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}

	@Override
	public List<Project> search(String search) {
		return search(search, "project_name", "project_description");
	}

}

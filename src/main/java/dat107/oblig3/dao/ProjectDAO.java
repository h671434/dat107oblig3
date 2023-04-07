package dat107.oblig3.dao;

import java.util.List;

import dat107.oblig3.entity.Employee;
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
	
	public void addEmployeeToProject(Employee employee, Project project, 
			int hours) throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employee, project, hours);
	}
	
	public void addEmployeeToProject(Employee employee, Project project) 
			throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employee, project);
	}

}

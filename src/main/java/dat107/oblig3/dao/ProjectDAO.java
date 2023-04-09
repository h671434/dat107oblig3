package dat107.oblig3.dao;

import java.util.List;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProjectDAO extends DAO<Project>{

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}

	@Override
	public List<Project> search(String search) {
		return search(search, "project_name", "project_description");
	}
	
	public void updateDescription(int id, String newDescription) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Project toUpdate = em.find(Project.class, id);
			toUpdate.setDescription(newDescription);
						
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	public Project saveNewProject(String name, String description) 
			throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Project newProject = new Project(name, description);
			em.persist(newProject);
								
			tx.commit();
			
			return newProject;
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	public void addEmployeeToProject(int employeeId, int projectId, 
			int hours) throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId, hours);
	}
	
	public void addEmployeeToProject(int employeeId, int projectId) 
			throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId);
	}

}

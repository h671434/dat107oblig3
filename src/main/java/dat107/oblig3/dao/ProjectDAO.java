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
		return search(search,"project_id", "project_name", "project_description");
	}
	
	/**
	 * Sets description for project with given id.
	 */
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
	
	/**
	 * Saves new project with given parameters in database.
	 */
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
	
	// addEmployeeToProject methods are added in this class for easy availability.
	public void addEmployeeToProject(int employeeId, int projectId,
			String role, int hours) throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId, role, hours);
	}
	
	public void addEmployeeToProject(int employeeId, int projectId, 
			int hours) throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId, hours);
	}
	
	public void addEmployeeToProject(int employeeId, int projectId,
			String role) throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId, role);
	}

	public void addEmployeeToProject(int employeeId, int projectId) 
			throws Throwable {
		new EmployeeDAO().addEmployeeToProject(employeeId, projectId);
	}
	
	public void removeEmployeeFromProject(int employeeId, int projectId) 
			throws Throwable {
		new EmployeeDAO().removeEmployeeFromProject(employeeId, projectId);
	}

	/**
	 * Deletes project with given id from database.
	 * @throws IllegalArgumentException if project has participants.
	 */
	public void delete(int id) throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Project toDelete = em.find(Project.class, id);
			
			if(toDelete.hasParticipants()) {
				throw new IllegalArgumentException(
						"Project has participants and cannot be deleted.");
			}
			
			em.remove(toDelete);
			
			tx.commit();

		} catch (Throwable e) {
			if ((tx != null &&tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
}

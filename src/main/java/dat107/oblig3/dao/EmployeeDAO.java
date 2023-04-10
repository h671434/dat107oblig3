package dat107.oblig3.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.entity.ProjectParticipationPK;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class EmployeeDAO extends DAO<Employee> {

	@Override
	protected Class<Employee> getEntityClass() {
		return Employee.class;
	}

	public Optional<Employee> getById(int id) {
		return get(id);
	}
	
	/**
	 * Returns an optional containing the employee whose username is equal to the paramater.
	 * Empty Optional of no employee is found.
	 */
	public Optional<Employee> getByUsername(String username) 
			throws NonUniqueResultException {
		String arg = "SELECT DISTINCT t FROM Employee t "
				+ "WHERE LOWER(t.username) LIKE LOWER(:username)";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Employee> query = em.createQuery(arg, getEntityClass());
			query.setParameter("username", "%" + username + "%");

			return Optional.of(query.getSingleResult());
			
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Employee> search(String search) {
		return search(search, "employee_id", "username", "first_name", 
				"last_name", "employment_date", "position", "monthly_salary");
	}
	
	/**
	 * Sets the position for the employee with given id.
	 */
	public void updatePosition(int id, String newPosition) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee toUpdate = em.find(Employee.class, id);
			toUpdate.setPosition(newPosition);
						
			tx.commit();
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}

	/**
	 * Sets the monthly_salary for the employee with given id.
	 */
	public void updateSalary(int id, double newSalary) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee toUpdate = em.find(Employee.class, id);
			toUpdate.setMonthlySalary(newSalary);
						
			tx.commit();
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}

	/**
	 * Sets the department for the employee with given id.
	 * @throws IllegalArgumentException if employee is manager in another department.
	 */
	public void updateDepartment(int id, Department newDepartment) 
			throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			newDepartment = em.merge(newDepartment);
			
			Employee toUpdate = em.find(Employee.class, id);
			
			if(toUpdate.isManager()) {
				throw new IllegalArgumentException(
						"Employee is a manager in another Department");
			}
			
			toUpdate.setDepartment(newDepartment);
						
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}

	/**
	 * Saves a new employee with given paramters in the database.
	 */
	public Employee saveNewEmployee(String username, String firstName, 
			String lastName, Date employmentDate, String position, 
			double monthlySalary, Department department) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			department = em.merge(department);
			
			Employee newEmployee = new Employee(username, firstName, 
					lastName, employmentDate, position, 
					monthlySalary, department);
			em.persist(newEmployee);
			
			tx.commit();
			
			return newEmployee;
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	/**
	 * Creates a new ProjectParticipation from given paramters. 
	 */
	public ProjectParticipation addEmployeeToProject(int employeeId, int projectId, 
			String role, int hours) throws EntityExistsException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee employee = em.find(Employee.class, employeeId);
			Project project = em.find(Project.class, projectId);
			
			ProjectParticipation newParticipation = new ProjectParticipation(
					employee, project, role, hours);
			em.persist(newParticipation);
			
			tx.commit();
			
			return newParticipation;
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	/**
	 * Creates a new ProjectParticipation with default role as "Team Member"
	 * and default hours_worked as 0.
	 */
	public ProjectParticipation addEmployeeToProject(int employeeId, 
			int projectId) throws EntityExistsException, Throwable {
		return addEmployeeToProject(employeeId, projectId, "Team Member", 0);
	}
	
	/**
	 * Creates a new ProjectParticipation with default role as "Team Member".
	 */
	public ProjectParticipation addEmployeeToProject(int employeeId, 
			int projectId, int hours) throws EntityExistsException, Throwable {
		return addEmployeeToProject(employeeId, projectId, "Team Member", hours);
	}
	
	/**
	 * Creates a new ProjectParticipation with default hours_worked as 0.
	 */
	public ProjectParticipation addEmployeeToProject(int employeeId, 
			int projectId, String role) throws EntityExistsException, Throwable {
		return addEmployeeToProject(employeeId, projectId, role, 0);
	}
	
	/**
	 * Deletes a project participation.
	 * @throws IllegalArgumentException if employee has registered hours in project.
	 */
	public void removeEmployeeFromProject(int employeeId, int projectId) 
			throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			ProjectParticipationPK pk = 
					new ProjectParticipationPK(employeeId, projectId);
			ProjectParticipation participation =
					em.find(ProjectParticipation.class, pk);
			
			if(participation.getHoursWorked() != 0) {
				throw new IllegalArgumentException(
						"Employee has registered hours in project.");
			}
			
			em.remove(participation);
			
			Employee employee = em.find(Employee.class, employeeId);
			Project project = em.find(Project.class, projectId);
			
			employee.removeProjectParticipation(participation);
			project.removeProjectParticipation(participation);
			
			tx.commit();
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	/**
	 * Updates the houres_worked field for a project participation.
	 * @throws IllegalArgumentException if the ProjectParticipation doesn't exist.
	 */
	public void updateProjectParticipation(int employeeId, int projectId, 
			String role, int hours) throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			ProjectParticipationPK pk = new ProjectParticipationPK(employeeId, projectId);
			ProjectParticipation participation = em.find(ProjectParticipation.class, pk);
			
			participation.setRole(role);
			participation.setHoursWorked(hours);
			
			em.merge(participation);
			
			tx.commit();
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	/**
	 * Deletes employee with given id from database.
	 * @throws IllegalArgumentException if employee is a manager or has registered 
	 * hours in a project.
	 */
	public void delete(Object id) throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee toDelete = em.find(Employee.class, id);
			
			if(toDelete.isManager()) {
				throw new IllegalArgumentException(
						"Employee is a manager and cannot be deleted.");
			}
			
			if(toDelete.hasRegisteredHours()) {
				throw new IllegalArgumentException(
						"Employee has registered hours in a project and cannot be deleted.");
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

package dat107.oblig3.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
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
	
	public Optional<Employee> getByUsername(String username) throws NonUniqueResultException {
		String arg = "SELECT DISTINCT t FROM " + getEntityClass().getSimpleName() + " t "
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
		return search(search, "username", "first_name", "last_name", "position");
	}
	
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

	public void updateDepartment(int id, Department newDepartment) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee toUpdate = em.find(Employee.class, id);
			toUpdate.setDepartment(newDepartment);
						
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}

	public Employee saveNew(String username, String firstName, String lastName, 
			Date employmentDate, String position, double monthlySalary, 
			Department department) throws Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
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

}

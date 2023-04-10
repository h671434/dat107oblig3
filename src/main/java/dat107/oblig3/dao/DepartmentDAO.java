package dat107.oblig3.dao;

import java.util.List;
import java.util.Optional;

import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class DepartmentDAO extends DAO<Department> {

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}
	
	public Optional<Department> getById(int id) {
		return get(id);
	}

	@Override
	public List<Department> search(String search) {
		return search(search, "department_name");
	}

	public List<String> getNamesContaining(String search) {
		String arg = "SELECT t.department_name FROM Department t "
				+ "WHERE LOWER(t.department_name) LIKE :search";
		
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<String> query = em.createQuery(arg, String.class);
			query.setParameter("search", "%" + search.toLowerCase() + "%");
			
			return query.getResultList();
		}
	}

	public void updateManager(int departmentId, Employee newEmployee) 
			throws IllegalArgumentException, Throwable {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			newEmployee = em.merge(newEmployee);
			
			Department toUpdate = em.find(Department.class, departmentId);
			
			if(!toUpdate.isMember(newEmployee)) {
				throw new IllegalArgumentException(
						"Employee is not a member of the department.");
			}
			
			toUpdate.setManager(newEmployee);
						
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
	public Department saveNewDepartment(String name, Employee manager) 
			throws Throwable {
		if(manager.isManager()) {
			throw new IllegalArgumentException(
					"Employee is already a manager in a different department.");
		}
		
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			manager = em.merge(manager);
			
			Department newDepartment = new Department(name, manager);
			em.persist(newDepartment);
			
			manager.setDepartment(newDepartment);
			
			tx.commit();
			
			return newDepartment;
			
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
			
			throw e;
		}
	}
	
}

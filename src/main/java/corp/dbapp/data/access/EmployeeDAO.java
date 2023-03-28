package corp.dbapp.data.access;

import java.util.List;
import java.util.Optional;

import corp.dbapp.data.model.Employee;
import jakarta.persistence.EntityManager;
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

	public Optional<Employee> getByUsername(String username) {
		String arg = "SELECT DISTINCT t FROM " + getEntityClass().getSimpleName() + " t "
				+ "WHERE t.username LIKE :username";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Employee> query = em.createQuery(arg, getEntityClass());
			query.setParameter("username", username);

			return Optional.of(query.getSingleResult());
		} catch (NoResultException | NonUniqueResultException e) {
			return Optional.empty();
		}
	}

	public List<Employee> getByProject(int project_id) {
		return getBy("project_idl", project_id);
	}

	public void updatePosition(Employee toUpdate, String newPosition) {
		toUpdate.setPosition(newPosition);
		save(toUpdate);
	}

	public void updateSalary(Employee toUpdate, Double newSalary) {
		toUpdate.setMonthlySalary(newSalary);
		save(toUpdate);
	}

	public void updateDepartment(Employee toUpdate, Integer newDepartment) {
		toUpdate.setDepartment(newDepartment);
		save(toUpdate);
	}

	public void addNewEmployee(Employee newEmployee) {
		// TODO
	}

	@Override
	public List<Employee> search(String search) {
		String arg = "SELECT t FROM " + getEntityClass().getSimpleName() + " t " + "WHERE t.username LIKE (:search) "
				+ "OR t.first_name LIKE (:search) " + "OR t.last_name LIKE (:search) "
				+ "OR t.position LIKE (:search) ";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Employee> query = em.createQuery(arg, getEntityClass());
			query.setParameter("search", "%" + search + "%");

			return query.getResultList();
		}
	}

	@Override
	public void save(Employee entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Employee entity) {
		// TODO Auto-generated method stub

	}

}

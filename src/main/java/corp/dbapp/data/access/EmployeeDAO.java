package corp.dbapp.data.access;

import java.util.Optional;

import corp.dbapp.data.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class EmployeeDAO extends DataAccess<Employee>{

	@Override
	protected Class<Employee> getEntityClass() {
		return Employee.class;
	}
	 
	public Optional<Employee> getById(int id) {
		return get(id);
	}
	
	public Optional<Employee> getByUsername(String username) {
		String arg = "SELECT DISTINCT t FROM " 
				+ getEntityClass().getSimpleName() + " t "
				+ "WHERE t.username LIKE :username";

        try (EntityManager em = emf.createEntityManager()) {
        	TypedQuery<Employee> query = em.createQuery(arg, getEntityClass());
        	query.setParameter("username", username);

        	return Optional.of(query.getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
        	return Optional.empty();
        }
	}
	
	public void updatePosition(Employee toUpdate, String newPosition) {	
		toUpdate.setPosition(newPosition);
		save(toUpdate);	
	}

}

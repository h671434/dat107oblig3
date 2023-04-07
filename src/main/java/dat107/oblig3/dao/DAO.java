	package dat107.oblig3.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dat107.oblig3.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public abstract class DAO<T> {

	protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("oblig3PU",
			Map.of("jakarta.persistence.jdbc.password", "pass"));

	/**
	 * For methods in this abstract class, where the code would be
	 * otherwise same for each subclass-implementation. Used to avoid 
	 * repetitive code.
	 * @return the entity class of the current DAO
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * Get entity with corresponding primary key.
	 * @param id primary key
	 * @return Optional containing the entity instance or empty if not found
	 */
	public Optional<T> get(Object id) {
		// Using try-with-resources since it auto-closes the EntityManager
		try (EntityManager em = emf.createEntityManager()) {
			T result = em.find(getEntityClass(), id);
			
			return Optional.of(result);
		}
	}

	/**
	 * Get all entity instances.
	 * @return List containing all entity instances
	 */
	public List<T> getAll() {
		String arg = "SELECT t from " + getEntityClass().getSimpleName() + " t";
		
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<T> query = em.createQuery(arg, getEntityClass());

			return query.getResultList();
		}
	}

	/**
	 * Gets all entities where the given field == param.
	 * Used as helper method for getBy-methods in subclasses.
	 * @param field to find value in
	 * @param parameter value to look for
	 * @return List containing results
	 */
	protected List<T> getBy(String field, Object param) {
		String arg = "SELECT t FROM " + getEntityClass().getSimpleName() + " t " 
				+ "WHERE t." + field + " LIKE :param";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<T> query = em.createQuery(arg, getEntityClass());
			query.setParameter("param", param);

			return query.getResultList();
		}
	}

	/**
	 * Searches for all entities where any field matches the search. Does not
	 * include numbers or dates.
	 * @return List containing all entities with fields containing the search
	 */
	public abstract List<T> search(String search);
	
	/**
	 * Searches for all entities where any of the given fields contain the
	 * search string. Used as a helper method for {@link #search(String)}.
	 * @param search string to look for
	 * @param fields to search through
	 * @return List containing all enitites with fields containing the search
	 */
	protected List<T> search(String search, String... fields) {
		if(fields.length == 0) {
			return Collections.emptyList();
		}
		
		StringBuilder arg = new StringBuilder();
		
		arg.append("SELECT t FROM " + getEntityClass().getSimpleName() + " t ");
		arg.append("WHERE LOWER(t." + fields[0] + ") LIKE LOWER(:search) ");
		for(int i = 1; i < fields.length; i++) {
			arg.append("OR LOWER(t." + fields[i] + ") LIKE LOWER(:search) ");
		}
		
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<T> query = em.createQuery(arg.toString(), getEntityClass());
			query.setParameter("search", "%" + search + "%");

			return query.getResultList();
		}
	}

	/**
	 * Saves changes to given entity in the database.
	 * @param entity to save
	 */
	public void update(T entity) {	
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
	
			tx.begin();
			em.merge(entity);
			tx.commit();
			
		} catch (Throwable e) {
			e.printStackTrace();
			if ((tx != null) && (tx.isActive())) {
				tx.rollback();
			}
		}
	}
	
	/**
	 * Deletes the given entity from the database.
	 * @param entity to delete
	 */
	public void delete(Object id) {
		EntityTransaction tx = null;
		try (EntityManager em = emf.createEntityManager()) {
			tx = em.getTransaction();
			
			tx.begin();
			
			Employee toDelete = em.find(Employee.class, id);
			em.remove(toDelete);
			
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if ((tx != null &&tx.isActive())) {
				tx.rollback();
			}
		}
	}
}

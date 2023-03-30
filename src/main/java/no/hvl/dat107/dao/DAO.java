package no.hvl.dat107.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public abstract class DAO<T> {

	protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("oblig3PU",
			Map.of("jakarta.persistence.jdbc.password", "pass"));

	/**
	 * Only used in methods from abstract class DAO<T>. Gets the class of DAO's
	 * entity.
	 * 
	 * @return the entity class
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * Get the entity of class T with given primary key.
	 * 
	 * @param id primary key
	 * @return Optional containing the entity instance or empty if not found
	 */
	public Optional<T> get(int id) {
		// Using try-with-resources since it auto-closes the EntityManager
		try (EntityManager em = emf.createEntityManager()) {
			T result = em.find(getEntityClass(), id);
			
			return Optional.ofNullable(result);
		}
	}

	/**
	 * Get all entity instances of class T.
	 * 
	 * @return a List containing all entity instances
	 */
	public List<T> getAll() {
		String arg = "SELECT t from " + getEntityClass().getSimpleName() + " t";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<T> query = em.createQuery(arg, getEntityClass());

			return query.getResultList();
		}
	}

	/**
	 * Get all entity instances where the field matches the given parameter. Selects
	 * all "t" where t.field = param.
	 * 
	 * @param field such as "monthly_salary" or "position"
	 * @param parameter such as 30000.00 or "Developer"
	 * @return a List containing all matching entity instances
	 */
	public List<T> getBy(String field, Object param) {
		String arg = "SELECT t FROM " + getEntityClass().getSimpleName() + " t " 
				+ "WHERE t." + field + " LIKE :param";

		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<T> query = em.createQuery(arg, getEntityClass());
			query.setParameter("param", param);

			return query.getResultList();
		}
	}
	
	/**
	 * Adds new entity to database.
	 * @param entity to add
	 * @return true if operation was succsesful, false otherwise.
	 */
	public abstract boolean addNew(T toAdd);

	/**
	 * Searches for all entities where any field matches the search. Does not
	 * include numbers or dates.
	 * 
	 * @return a List containing all entities
	 */
	public abstract List<T> search(String search);

	/**
	 * Saves/updates the given entity in the sql database.
	 * 
	 * @param entity to save
	 */
	public abstract void save(T entity);

	/**
	 * Deletes the given entity from the sql database.
	 * 
	 * @param entity to delete
	 */
	public abstract void delete(T entity);
}

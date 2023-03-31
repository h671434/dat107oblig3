package dat107.oblig3.dao;

import java.util.List;
import java.util.Optional;

import dat107.oblig3.entity.Department;
import jakarta.persistence.EntityManager;
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
	
	public List<Department> searchByName(String search) {
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


}

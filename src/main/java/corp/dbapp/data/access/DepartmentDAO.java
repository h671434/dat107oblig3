package corp.dbapp.data.access;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import corp.dbapp.data.model.Department;
import corp.dbapp.data.model.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DepartmentDAO implements DataAccess<Department> {

	private EntityManagerFactory emf;

	public DepartmentDAO() {
		this.emf = Persistence.createEntityManagerFactory("oblig3PU",
				Map.of("jakarta.persistence.jdbc.password", "pass"));
	}

	@Override
	public Optional<Department> get(int id) {
        try (EntityManager em = emf.createEntityManager()){
        	return Optional.ofNullable(em.find(Department.class, id));
        }
	}

	@Override
	public List<Department> getAll() {
		String arg = "SELECT t from Department t";

        try (EntityManager em = emf.createEntityManager()) {
        	TypedQuery<Department> query = em.createQuery(arg, Department.class);

        	return query.getResultList();
        }
	}

	@Override
	public List<Department> getBy(String field, Object param) {
		String arg = "SELECT t FROM Department t "
				+ "WHERE t." + field + " LIKE :param";

        try (EntityManager em = emf.createEntityManager()) {
        	TypedQuery<Department> query = em.createQuery(arg, Department.class);
        	query.setParameter("param", param);

        	return query.getResultList();
        }
	}

}

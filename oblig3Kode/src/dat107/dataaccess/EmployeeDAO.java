package dat107.dataaccess;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dat107.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class EmployeeDAO implements DAO<Employee>{

	private EntityManagerFactory emf;
	
	public EmployeeDAO() {
		this.emf = Persistence.createEntityManagerFactory("oblig3PU",
				Map.of("jakarta.persistence.jdbc.password", "pass"));
	}
			
	@Override
	public Optional<Employee> get(int id) {
        try (EntityManager em = emf.createEntityManager()){
        	return Optional.ofNullable(em.find(Employee.class, id));
        } 
	}

	@Override
	public List<Employee> getAll() {
		String arg = "SELECT t from Employee t";
		
        try (EntityManager em = emf.createEntityManager()) { 
        	TypedQuery<Employee> query = em.createQuery(arg, Employee.class);
        	
        	return query.getResultList();
        } 
	}

	@Override
	public List<Employee> getBy(String field, Object param) {
		String arg = "SELECT t FROM Employee t "
				+ "WHERE t." + field + " LIKE :param";
		
        try (EntityManager em = emf.createEntityManager()) { 
        	TypedQuery<Employee> query = em.createQuery(arg, Employee.class);
        	query.setParameter("param", param);
        	
        	return query.getResultList();
        } 
	}

}

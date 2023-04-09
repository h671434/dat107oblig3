package dat107.oblig3.dao;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dat107.oblig3.dao.DAO;
import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;

public class EmployeeDAOTest {

	private EmployeeDAO dao;
	
	@BeforeEach
	public void init() {
		dao = new EmployeeDAO();
	}
	
	@Test
	@Order(1)
	public void testGet() {
		System.out.println("Test dao.get(1)");
		
		Optional<Employee> result = dao.get(1);
		
		if(result.isPresent()) {
			System.out.println(result.get() + "\n");
		} else {
			System.out.println("No result\n");
			fail("No result found for dao.get(1)");
		}
	}
	
	@Test
	@Order(2)
	public void testGetAll() {
		System.out.println("Test dao.getAll()");
		
		List<Employee> result = dao.getAll();
		
		if(result.size() > 0) {
			result.forEach(e -> System.out.println(e));
			System.out.println();
		} else {
			System.out.println("No result\n");
			fail("No result found for dao.getAll()");
		}
	}
	
	@Test
	@Order(3)
	public void testGetByUsername() {
		System.out.println("Test dao.getByUsername(\"jbo\")");
		
		Optional<Employee> result = dao.getByUsername("jbo");
		
		if(result.isPresent()) {
			System.out.println(result.getClass() + "\n");
		} else {
			System.out.println("No result\n");
			fail("No result found for dao.getByUsername(\"jbo\")");
		}
	}

	@Test
	@Order(4)
	public void testSearch() {
		System.out.println("Test dao.search(\"de\")");
		
		List<Employee> result = dao.search("de");
		 
		if(result.size() < 1) {
			fail("No result found for dao.search(\"de\")");	
		}
		
		result.forEach(e -> System.out.println(e + " " + e.getPosition()));
		System.out.println();
	}

	@Test
	@Order(5)
	public void testUpdate() {
		System.out.println("Test dao.update(employee)");
		
		Employee employee = getById1();
		String oldFirstName = employee.getFirstName();
		System.out.println("Old first_name: " + oldFirstName);
		
		employee.setFirstName("Jonathan");
		try {
			dao.update(employee);
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
		employee = getById1();
		System.out.println("New first_name: " + employee.getFirstName());
		
		employee.setFirstName(oldFirstName);
		try {
			dao.update(employee);
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
		employee = getById1();
		System.out.println("Reverted back to old first_name: " 
				+ employee.getFirstName());
	}
	
	private Employee getById1() {
		Optional<Employee> result = dao.get(1);
		
		if(result.isEmpty()) {
			fail("No result for dao.get(1)");
		} 
		
		return result.get();
	}
	
	@Test
	@Order(6)
	public void testUpdateSalary() {
		System.out.println("Test dao.updateSalary(1, 32)");
		
		Employee employee = getById1();
		double oldSalary = employee.getMonthlySalary();
		System.out.println("Old salary: " + oldSalary);
		
		try {
			dao.updateSalary(employee.getId(), 32);
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
		employee = getById1();
		System.out.println("New salary: " + employee.getMonthlySalary());
		
		try {
			dao.updateSalary(employee.getId(), oldSalary);
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
		employee = getById1();
		System.out.println("Reverted back to old salary: " 
				+ employee.getMonthlySalary());
	}
	
	@Test
	@Order(7)
	public void testDelete() {
		Employee employee = null;
		
		Optional<Employee> result = dao.getByUsername("pml");
		
		if(result.isPresent()) {
			employee = result.get(); 
		} else {
			try {
				employee = dao.saveNewEmployee("pml", "Peter", "Hole", 
							new Date(System.currentTimeMillis()), "Tester", 
							18.0, new DepartmentDAO().get(1).get());
			} catch (Throwable e) {
				fail("Unable to create employee to delete");
			}
		}
		
		if(employee == null) {
			fail("Unable to find employee to delete");
		}
		
		try {
			dao.delete(employee.getId());
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
		result = dao.getByUsername("pml");
		
		if(!result.isEmpty()) {
			fail("dao.delete(id) failed");
		}
	}
	
	@Test
	@Order(8)
	public void testSaveNewEmployee() {
		try {
			dao.saveNewEmployee("pml", "Peter", "Hole", 
					new Date(System.currentTimeMillis()), "Tester", 18.0, 
					new DepartmentDAO().get(1).get());
		} catch (Throwable e) {
			fail(e.getMessage());
		}
	}
	
}

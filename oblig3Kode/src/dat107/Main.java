package dat107;
import dat107.dataaccess.DAO;
import dat107.dataaccess.EmployeeDAO;
import dat107.model.Employee;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        DAO<Employee> employeeDAO = new EmployeeDAO();
        
        employeeDAO.get(1).ifPresent(e -> e.print());
        employeeDAO.getAll().forEach(e -> e.print());
        
        employeeDAO.getBy("username", "pto").forEach(e -> e.print());
    }
    
}
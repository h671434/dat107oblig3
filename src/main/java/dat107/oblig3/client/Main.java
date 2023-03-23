package dat107.oblig3.client;
import dat107.oblig3.data.access.DataAccess;
import dat107.oblig3.data.access.EmployeeDAO;
import dat107.oblig3.data.model.Employee;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        DataAccess<Employee> employeeDAO = new EmployeeDAO();
        
        employeeDAO.get(1).ifPresent(e -> e.print());
        employeeDAO.getAll().forEach(e -> e.print());
        
        employeeDAO.getBy("username", "pto").forEach(e -> e.print());
    }
    
}
package dat107.oblig3.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments", schema = "oblig3")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int department_id;
	private String department_name;
	@OneToOne
	@JoinColumn(name = "department_manager")
	private Employee department_manager;
	@OneToMany(mappedBy = "department")
	private List<Employee> employees = new ArrayList<>();

	public Department() {}

	public Department(String department_name, Employee department_manager) {
		this.department_name = department_name;
		this.department_manager = department_manager;
	}

	@Override
	public String toString() {
		return "#" + department_id + " " + department_name;
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public void printWithEmployees() {
		print();
		employees.forEach(e -> System.out.println(e));
	}

	public int getId() {
		return department_id;
	}

	public String getName() {
		return department_name;
	}

	public void setName(String department_name) {
		this.department_name = department_name;
	}

	public Employee getManager() {
		return department_manager;
	}

	public void setManager(Employee department_manager) {
		this.department_manager = department_manager;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(department_id, department_manager, department_name, 
				employees);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		return (department_id == ((Department) obj).department_id);
	}

}

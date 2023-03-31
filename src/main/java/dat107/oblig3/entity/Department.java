package dat107.oblig3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments", schema = "oblig3")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int department_id;
	
	private String department_name;
	private int department_manager;

	public Department() {}

	public Department(String department_name, Integer department_manager) {
		this.department_name = department_name;
		this.department_manager = department_manager;
	}

	public void print() {
		System.out.println(this.toString());
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

	public int getManager() {
		return department_manager;
	}

	public void setManager(int department_manager) {
		this.department_manager = department_manager;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + department_id 
				+ ", department_name=" + department_name
				+ ", department_manager=" + department_manager + "]";
	}

}

package corp.dbapp.data.model;

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

	public Department() {};
	
	public Department(int department_id, String department_name, int department_manager) {
		this.department_id = department_id;
		this.department_name = department_name;
		this.department_manager = department_manager;
	}

	public int getDepartmentId() {
		return department_id;
	}

	public void setDepartmentId(int department_id) {
		this.department_id = department_id;
	}

	public String getDepartmentName() {
		return department_name;
	}

	public void setDepartmentName(String department_name) {
		this.department_name = department_name;
	}

	public int getDepartmentManager() {
		return department_manager;
	}

	public void setDepartmentManager(int department_manager) {
		this.department_manager = department_manager;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + department_id
				+ ", department_name=" + department_name
				+ ", department_manager=" + department_manager + "]";
	}

	public void print() {
		System.out.println(this.toString());
	}

}

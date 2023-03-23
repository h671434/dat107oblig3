package dat107.oblig3.data.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees", schema = "oblig3")
public class Employee {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employee_id;
	private String username;
	private String first_name;
	private String last_name;
	private Date employment_date;
	private String position;
	
	public Employee() {}

	public Employee(Integer employeeId, String username, String firstName, 
			String lastName, Date employmentDate, String position) {
		this.employee_id = employeeId;
		this.username = username;
		this.first_name = firstName;
		this.last_name = lastName;
		this.employment_date = employmentDate;
		this.position = position;
	}

	public Integer getEmployeeId() {
		return employee_id;
	}

	public void setEmployeeId(Integer employee_id) {
		this.employee_id = employee_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public Date getEmploymentDate() {
		return employment_date;
	}

	public void setEmploymentDate(Date employment_date) {
		this.employment_date = employment_date;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id 
				+ ", username=" + username 
				+ ", first_name=" + first_name 
				+ ", last_name=" + last_name 
				+ ", employment_date=" + employment_date 
				+ ", position=" + position + "]";
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
}

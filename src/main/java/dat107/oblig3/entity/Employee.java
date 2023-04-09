package dat107.oblig3.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	private double monthly_salary;
	
	@ManyToOne
	@JoinColumn(name = "department")
	@OneToOne(mappedBy = "department_manager")
	private Department department;
	
	@OneToMany(mappedBy = "employee")
	private List<ProjectParticipation> project_participations = new ArrayList<>();

	public Employee() {}

	public Employee(String username, String firstName, String lastName, 
			Date employmentDate, String position, double monthlySalary, 
			Department department) {
		this.username = username;
		this.first_name = firstName;
		this.last_name = lastName;
		this.employment_date = employmentDate;
		this.position = position;
		this.monthly_salary = monthlySalary;
		this.department = department;
	}

	public boolean isManager() {
		return equals(department.getManager());
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public void printWithProjects() {
		System.out.println();
		print();
		project_participations.forEach(pp -> pp.print());
	}
	
	@Override
	public String toString() {
		return  "#" + employee_id 
				+ " @" + username
				+ " " + first_name + " " + last_name;
	}

	public Integer getId() {
		return employee_id;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Double getMonthlySalary() {
		return monthly_salary;
	}

	public void setMonthlySalary(double monthly_salary) {
		this.monthly_salary = monthly_salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public List<ProjectParticipation> getParticipations() {
		return project_participations;
	}
	
	public void addProjectParticipation(ProjectParticipation pp) {
		project_participations.add(pp);
	}
	
	public void removeProjectParticipation(ProjectParticipation pp) {
		project_participations.remove(pp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, employee_id, employment_date, 
				first_name, last_name, monthly_salary, position,
				project_participations, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		return (employee_id == ((Employee) obj).employee_id);
	}
	
}

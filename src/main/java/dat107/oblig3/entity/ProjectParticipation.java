package dat107.oblig3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_participations", schema = "oblig3")
@IdClass(ProjectParticipationPK.class)
public class ProjectParticipation {

	@Id
	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;
	
	@Id 
	@ManyToOne
	@JoinColumn(name = "project")
	private Project project;
	
	private String role = "Team Member";
	
	private int hours_worked = 0;
	
	public ProjectParticipation() {}
	
	public ProjectParticipation(Employee employee, Project project) {
		this.employee = employee;
		this.project = project;
		employee.addProjectParticipation(this);
		project.addProjectParticipation(this);
	}
	
	public ProjectParticipation(Employee employee, Project project, int hours_worked) {
		this(employee, project);
		this.hours_worked = hours_worked;
	}
	
	public ProjectParticipation(Employee employee, Project project, String role) {
		this(employee, project);
		this.role = role;
	}
	
	public ProjectParticipation(Employee employee, Project project, String role,
			int hours_worked) {
		this(employee, project, hours_worked);
		this.role = role;
	}
	
	public void print() {
		System.out.println(this.toString());
	}

	public Employee getEmployee() {
		return employee;
	}

	public Project getProject() {
		return project;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public int getHoursWorked() {
		return hours_worked;
	}

	public void setHoursWorked(Integer hours) {
		this.hours_worked = hours;
	}

	@Override
	public String toString() {
		return "Employee: " + employee 
				+ " - Project: " + project
				+ " - Hours worked: " + hours_worked;  
	}
	
}

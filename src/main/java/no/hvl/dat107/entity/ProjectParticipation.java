package no.hvl.dat107.entity;

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
	
	private Integer hours;
	
	public ProjectParticipation() {}
	
	public ProjectParticipation(Employee employee, Project project) {
		this.employee = employee;
		this.project = project;
		employee.addProjectParticipation(this);
		project.addProjectParticipation(this);
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

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "ProjectParticipation [employee=" + employee 
				+ ", project=" + project 
				+ ", hours=" + hours + "]";
	}
	
}

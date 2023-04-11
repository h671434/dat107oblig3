package dat107.oblig3.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects", schema = "oblig3")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int project_id;
	private String project_name;
	private String project_description;
	@OneToMany(mappedBy = "project")
	private List<ProjectParticipation> project_participations;
	
	public Project() {}

	public Project(String project_name, String project_description) {
		this.project_name = project_name;
		this.project_description = project_description;
	}
	
	public boolean hasParticipants() {
		return project_participations.size() > 0;
	}
	
	public void addProjectParticipation(ProjectParticipation pp) {
		project_participations.add(pp);
	}
	
	public void removeProjectParticipation(ProjectParticipation pp) {
		project_participations.remove(pp);
	}
	
	@Override
	public String toString() {
		return "#" + project_id 
				+ " " +  project_name;
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public void printWithProjects() {
		System.out.println();
		print();
		project_participations.forEach(pp -> pp.print());
	}
	
	public int getId() {
		return project_id;
	}

	public String getName() {
		return project_name;
	}

	public void setName(String project_name) {
		this.project_name = project_name;
	}

	public String getDescription() {
		return project_description;
	}

	public void setDescription(String project_description) {
		this.project_description = project_description;
	}

	public List<ProjectParticipation> getParticipations() {
		return this.project_participations;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(project_description, project_id, project_name, project_participations);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Project other = (Project) obj;
		return project_id == other.project_id;
	}
	
}

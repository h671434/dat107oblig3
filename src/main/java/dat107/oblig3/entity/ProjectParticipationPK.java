package dat107.oblig3.entity;

@SuppressWarnings("unused")
public class ProjectParticipationPK {

	private Integer employee;
	private Integer project;
	
	public ProjectParticipationPK() {}
	
	public ProjectParticipationPK(Integer employeeId, Integer projectId) {
		this.employee = employeeId;
		this.project = projectId;
	}
	
}

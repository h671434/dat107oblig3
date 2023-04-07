package dat107.oblig3.gui.widget;

import java.util.Collections;

import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.gui.collection.ProjectParticipationList;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class ParticipationsWidget extends InfoWidget {
	
	private final Screen screen;
	
	private ProjectParticipationList list = new ProjectParticipationList();
	
	public ParticipationsWidget(String title, Screen screen) {
		super(title);
		this.screen = screen;
		
		addFullWidthField(list);
	}
	
	public void setEmployee(Employee employee) {
		setTitle("Projects");
		
		list.setListType(ProjectParticipationList.ListContent.PROJECT);
		
		if(employee != null) {
			list.updateContent(employee.getParticipations());
		} else {
			list.updateContent(Collections.emptyList());
		}
		
		screen.validate();
	}
	
	public void setProject(Project project) {
		setTitle("Project Participants");
		
		list.setListType(ProjectParticipationList.ListContent.EMPLOYEE);
		
		if(project != null) {
			list.updateContent(project.getParticipations());
		} else {
			list.updateContent(Collections.emptyList());
		}
		
		screen.validate();
	}
	
}

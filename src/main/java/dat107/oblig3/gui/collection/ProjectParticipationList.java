package dat107.oblig3.gui.collection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;

@SuppressWarnings("serial")
public class ProjectParticipationList extends EntityList<ProjectParticipation> {

	public enum ListContent{
		EMPLOYEE,
		PROJECT
	}

	private ListContent type;
	
	public ProjectParticipationList() {};
	
	public ProjectParticipationList(ListContent type) {
		this.type = type;
	}
	
	public ListContent getListType() {
		return type;
	}
	
	public void setListType(ListContent type) {
		this.type = type;
	}
	
	@Override
	protected ProjectParticipationList.ProjectParticipationListEntry createEntry(
			ProjectParticipation entity) {
		return new ProjectParticipationListEntry(entity);
	}
	
	@Override
	protected JPanel emptyListMessage() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
		
		JLabel text = new JLabel("No content found");
		text.setForeground(Color.LIGHT_GRAY);
		
		panel.add(text);
		
		return panel;
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(278, getPreferredSize().height);
	}

	public class ProjectParticipationListEntry 
			extends EntityList<ProjectParticipation>.ListEntry {
		
		public ProjectParticipationListEntry(ProjectParticipation entity) {
			super(entity);
			
			setLayout(new GridLayout(0, 1));
			setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);
			
			addIdAndNameLabel();
			addHoursAndRoleField();
		}
		
		private void addIdAndNameLabel() {
			JLabel idAndName = new JLabel();
			
			idAndName.setForeground(UITheme.DEFAULT_TEXT_COLOR);
			
			if (type == ListContent.EMPLOYEE) {
				idAndName.setText(entity.getEmployee().toString());
			}
			if (type == ListContent.PROJECT) {
				idAndName.setText(entity.getProject().toString());
			}
			
			add(idAndName);
		}
		
		private void addHoursAndRoleField() {
			String text = "    " + entity.getRole()
					+ " - " + entity.getHoursWorked() + " hours";
			
			JLabel hoursWorkedLabel = new JLabel(text);
			hoursWorkedLabel.setForeground(UITheme.DEFAULT_TEXT_COLOR);
			
			add(hoursWorkedLabel);
		}
	}

}

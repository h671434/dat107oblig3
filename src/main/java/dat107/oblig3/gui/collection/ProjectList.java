package dat107.oblig3.gui.collection;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dat107.oblig3.entity.Project;

@SuppressWarnings("serial")
public class ProjectList extends EntityList<Project>{

	@Override
	protected ProjectList.ProjectListEntry createEntry(Project entity) {
		return new ProjectListEntry(entity);
	}

	public class ProjectListEntry extends EntityList<Project>.ListEntry {

		public ProjectListEntry(Project entity) {
			super(entity);
			
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			addIdAndNamePanel();
			addDescriptionPanel();
		}
		
		private void addIdAndNamePanel() {
			JLabel id = new JLabel("" + entity.getId());
			id.setPreferredSize(new Dimension(40, 20));
			id.addMouseListener(clickListener);
			
			JLabel name = new JLabel(entity.getName());
			name.setPreferredSize(new Dimension(110, 20));
			name.addMouseListener(clickListener);
			
			JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			outerPanel.add(id);
			outerPanel.add(name);
			outerPanel.addMouseListener(clickListener);
			
			add(outerPanel);
		}
		
		private void addDescriptionPanel() {
			JTextArea description = new JTextArea();
			
			description.setText(entity.getDescription());
			description.setEditable(false);
			description.setFocusable(false);
			description.setBorder(BorderFactory.createEmptyBorder(2, 50, 40, 50));
			description.addMouseListener(clickListener);
			
			add(description);
		}
		
	}

}

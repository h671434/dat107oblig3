package dat107.oblig3.gui.widget.entitysets;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dat107.oblig3.entity.Project;

@SuppressWarnings("serial")
public class ProjectList extends EntityList<Project>{

	@Override
	protected ProjectList.Entry createEntry(Project entity) {
		return new Entry(entity);
	}

	public final class Entry extends EntityList<Project>.Entry {

		public Entry(Project entity) {
			super(entity);
			
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			addIdAndNamePanel();
			addDescriptionPanel();
		}
		
		private void addIdAndNamePanel() {
			Label id = new Label("" + entity.getId());
			id.setSize(40, 20);
			id.setAlignment(Label.LEFT);
			id.addMouseListener(clickListener);
			
			JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			idPanel.add(id);
			idPanel.addMouseListener(clickListener);
			
			Label name = new Label(entity.getName());
			id.setSize(100, 20);
			id.setAlignment(Label.CENTER);
			name.addMouseListener(clickListener);
			
			JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			namePanel.add(name);
			namePanel.addMouseListener(clickListener);
			
			JPanel outerPanel = new JPanel(new GridLayout(1, 3));
			outerPanel.add(idPanel);
			outerPanel.add(namePanel);
			outerPanel.add(new JPanel());
			outerPanel.addMouseListener(clickListener);
			
			add(outerPanel);
		}
		
		private void addDescriptionPanel() {
			JTextArea description = new JTextArea();
			description.setText(entity.getDescription());
			description.setEditable(false);
			description.setFocusable(false);
			description.setBorder(BorderFactory.createEmptyBorder(2, 50, 50, 50));
			description.addMouseListener(clickListener);
			
			add(description);
		}
		
	}

}

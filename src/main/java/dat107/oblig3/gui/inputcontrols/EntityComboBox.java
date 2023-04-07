package dat107.oblig3.gui.inputcontrols;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import dat107.oblig3.dao.DAO;
import dat107.oblig3.dao.DepartmentDAO;
import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.dao.ProjectDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;

@SuppressWarnings("serial")
public class EntityComboBox<T> extends JComboBox<T> {
	
	private EntityComboBox() {
	}
	
	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
	
	public static EntityComboBox<Department> createDepartmentComboBox() {
		DepartmentDAO dao = new DepartmentDAO();
		
		return new EntityComboBox<Department>() {{
			setModel(new EntityComboBoxModel(dao));
		}};
	}
	
	public static EntityComboBox<Project> createProjectComboBox() {
		ProjectDAO dao = new ProjectDAO();
		
		return new EntityComboBox<Project>() {{
			setModel(new EntityComboBoxModel(dao));
		}};
	}
	
	public static EntityComboBox<Employee> createEmployeeComboBox() {
		EmployeeDAO dao = new EmployeeDAO();
		
		return new EntityComboBox<Employee>() {{
			setModel(new EntityComboBoxModel(dao));
		}};
	}
	
	public class EntityComboBoxModel extends AbstractListModel<T> 
		implements ComboBoxModel<T> {

		private final DAO<T> dao;
		
		private List<T> entities;
		private int selected = -1;
		
		public EntityComboBoxModel(DAO<T> dao) {
			this.dao = dao;
			
			refresh();
		}
		
		public void refresh() {
			entities = dao.getAll();
			
			setSelectedItem(null);
		}
		
		@Override
		public void setSelectedItem(Object item) {
			if(item != null) { 
				selected = getIndexOf(item);
			} else {
				selected = -1;
			}
			
			fireContentsChanged(this, -1, -1);
		}
		
		public int getIndexOf(Object item) {
			for(int i = 0; i < entities.size(); i++) {
				if(item.equals(entities.get(i))) {
					return i;
				}
			}
			
			return -1;
		}
		
		@Override
		public T getSelectedItem() {
			if(selected == -1) {
				return null;
			}
			
			return entities.get(selected);
		}
		
		@Override
		public int getSize() {
			return entities.size();
		}
		
		@Override
		public T getElementAt(int index) {
			return entities.get(index);
		}
		
		public List<T> getList() {
			return entities;
		}
	
	}

}


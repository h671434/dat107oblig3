package dat107.oblig3.gui.inputcontrols;

import java.util.List;
import java.util.function.Supplier;

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
	
	private EntityComboBoxModel model;
	
	public EntityComboBox(Supplier<List<T>> contentSupplier) {
		setModel(new EntityComboBoxModel(contentSupplier));
	}
	
	public void setModel(EntityComboBoxModel model) {
		super.setModel(model);
		
		this.model = model;
	}
	
	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
	
	public void refresh() {
		model.refresh();
	}
	
	public static EntityComboBox<Department> createDepartmentComboBox() {
		DepartmentDAO dao = new DepartmentDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public static EntityComboBox<Project> createProjectComboBox() {
		ProjectDAO dao = new ProjectDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public static EntityComboBox<Employee> createEmployeeComboBox() {
		EmployeeDAO dao = new EmployeeDAO();
		
		return new EntityComboBox<>(() -> dao.getAll());
	}
	
	public class EntityComboBoxModel extends AbstractListModel<T> 
		implements ComboBoxModel<T> {
		
		private Supplier<List<T>> contentSupplier; 
		
		private List<T> entities;
		private int selected = -1;
		
		public EntityComboBoxModel(Supplier<List<T>> contentSupplier) {
			this.contentSupplier = contentSupplier;
			
			refresh();
		}
		
		public void refresh() {
			entities = contentSupplier.get();
			
			fireContentsChanged(this, -1, -1);
			
			setSelectedItem(null);
		}
		
		@Override
		public void setSelectedItem(Object item) {
			selected = getIndexOf(item);
			
			fireContentsChanged(this, -1, -1);
		}
		
		public int getIndexOf(Object item) {
			return entities.indexOf(item);
		}
		
		@Override
		public T getSelectedItem() {
			if(selected == -1 || selected >= entities.size()) {
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
			if(index == -1 || index >= entities.size()) {
				return null;
			}
			
			return entities.get(index);
		}
		
		public List<T> getList() {
			return entities;
		}
	
	}

}


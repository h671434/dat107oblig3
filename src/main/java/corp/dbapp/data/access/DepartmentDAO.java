package corp.dbapp.data.access;

import java.util.List;

import corp.dbapp.data.model.Department;

public class DepartmentDAO extends DAO<Department> {

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}

	@Override
	public List<Department> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Department entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Department entity) {
		// TODO Auto-generated method stub

	}

}

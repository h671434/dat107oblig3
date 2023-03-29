package no.hvl.dat107.dao;

import java.util.List;
import java.util.Optional;

import no.hvl.dat107.entity.Department;

public class DepartmentDAO extends DAO<Department> {

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}
	
	public Optional<Department> getById(int id) {
		return get(id);
	}

	@Override
	public List<Department> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addNew(Department toAdd) {
		// TODO
		return false;
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

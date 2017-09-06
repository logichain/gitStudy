package org.king.security.dao;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.king.security.domain.Department;

public class DepartmentDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(Department persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);		
	}

	public List<Department> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<Department> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public Department get(Serializable id) {
		// TODO Auto-generated method stub
		return (Department) baseDAO.getEntity(Department.class,id);
	}

	
	public void save(Department transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(Department transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

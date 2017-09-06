package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class BugTypeDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(BugType persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
	}

	public List<BugType> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<BugType> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public BugType get(Serializable id) {
		// TODO Auto-generated method stub
		return (BugType) baseDAO.getEntity(BugType.class,id);
	}

	
	public void save(BugType transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(BugType transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

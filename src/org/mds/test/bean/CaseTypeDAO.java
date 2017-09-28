package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class CaseTypeDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(CaseType persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
	}

	public List<CaseType> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<CaseType> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public CaseType get(Serializable id) {
		// TODO Auto-generated method stub
		return (CaseType) baseDAO.getEntity(CaseType.class,id);
	}

	
	public void save(CaseType transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(CaseType transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

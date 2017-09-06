package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class ImportantLevelDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(ImportantLevel persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
	}

	public List<ImportantLevel> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<ImportantLevel> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public ImportantLevel get(Serializable id) {
		// TODO Auto-generated method stub
		return (ImportantLevel) baseDAO.getEntity(ImportantLevel.class,id);
	}

	
	public void save(ImportantLevel transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(ImportantLevel transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

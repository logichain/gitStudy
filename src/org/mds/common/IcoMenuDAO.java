package org.mds.common;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class IcoMenuDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(IcoMenu persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);		
	}

	public List<IcoMenu> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<IcoMenu> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public IcoMenu get(Serializable id) {
		// TODO Auto-generated method stub
		return (IcoMenu) baseDAO.getEntity(IcoMenu.class,id);
	}

	
	public void save(IcoMenu transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(IcoMenu transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

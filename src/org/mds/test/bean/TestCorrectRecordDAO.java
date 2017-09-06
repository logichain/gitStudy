package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class TestCorrectRecordDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(TestCorrectRecord persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
	}

	public List<TestCorrectRecord> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<TestCorrectRecord> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public TestCorrectRecord get(Serializable id) {
		// TODO Auto-generated method stub
		return (TestCorrectRecord) baseDAO.getEntity(TestCorrectRecord.class,id);
	}

	
	public void save(TestCorrectRecord transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(TestCorrectRecord transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

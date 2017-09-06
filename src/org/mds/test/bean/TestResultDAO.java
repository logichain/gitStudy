package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class TestResultDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(TestResult persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setTrFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<TestResult> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<TestResult> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public TestResult get(Serializable id) {
		// TODO Auto-generated method stub
		return (TestResult) baseDAO.getEntity(TestResult.class,id);
	}

	
	public void save(TestResult transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(TestResult transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

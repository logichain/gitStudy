package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class TestCaseDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(TestCase persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setTcFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<TestCase> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<TestCase> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public TestCase get(Serializable id) {
		// TODO Auto-generated method stub
		return (TestCase) baseDAO.getEntity(TestCase.class,id);
	}

	
	public void save(TestCase transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(TestCase transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
	
	public int getFindCount(String myQuery)
	{
		int rtn = 0;
		List list = baseDAO.findEntity(myQuery);
		if(list != null && !list.isEmpty())
		{
			if(list.get(0) instanceof Long)
			{
				rtn = ((Long)list.get(0)).intValue();
			}
			else
			{
				rtn = (Integer)list.get(0);
			}			
		}
		
		return rtn;
	}
}

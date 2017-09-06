package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class CaseStatusDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(CaseStatus persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setCsFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<CaseStatus> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<CaseStatus> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public CaseStatus get(Serializable id) {
		// TODO Auto-generated method stub
		return (CaseStatus) baseDAO.getEntity(CaseStatus.class,id);
	}

	
	public void save(CaseStatus transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(CaseStatus transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

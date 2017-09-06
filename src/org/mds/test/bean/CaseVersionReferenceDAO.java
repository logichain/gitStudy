package org.mds.test.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class CaseVersionReferenceDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(CaseVersionReference persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setCvrFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<CaseVersionReference> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<CaseVersionReference> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public CaseVersionReference get(Serializable id) {
		// TODO Auto-generated method stub
		return (CaseVersionReference) baseDAO.getEntity(CaseVersionReference.class,id);
	}

	
	public void save(CaseVersionReference transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(CaseVersionReference transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

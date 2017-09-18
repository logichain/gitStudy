package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class MemberFunctionReferenceDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(MemberFunctionReference persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setMfrFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<MemberFunctionReference> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<MemberFunctionReference> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public MemberFunctionReference get(Serializable id) {
		// TODO Auto-generated method stub
		return (MemberFunctionReference) baseDAO.getEntity(MemberFunctionReference.class,id);
	}

	
	public void save(MemberFunctionReference transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(MemberFunctionReference transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

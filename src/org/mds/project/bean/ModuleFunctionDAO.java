package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class ModuleFunctionDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(ModuleFunction persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setMuFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<ModuleFunction> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<ModuleFunction> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public ModuleFunction get(Serializable id) {
		// TODO Auto-generated method stub
		return (ModuleFunction) baseDAO.getEntity(ModuleFunction.class,id);
	}

	
	public void save(ModuleFunction transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(ModuleFunction transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

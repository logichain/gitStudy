package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class ProjectModuleDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(ProjectModule persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setPmFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<ProjectModule> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<ProjectModule> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public ProjectModule get(Serializable id) {
		// TODO Auto-generated method stub
		return (ProjectModule) baseDAO.getEntity(ProjectModule.class,id);
	}

	
	public void save(ProjectModule transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(ProjectModule transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

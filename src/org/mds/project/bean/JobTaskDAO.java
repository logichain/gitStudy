package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class JobTaskDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(JobTask persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setJtStatus(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<JobTask> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<JobTask> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public JobTask get(Serializable id) {
		// TODO Auto-generated method stub
		return (JobTask) baseDAO.getEntity(JobTask.class,id);
	}

	
	public void save(JobTask transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(JobTask transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

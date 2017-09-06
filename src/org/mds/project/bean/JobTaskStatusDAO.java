package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;

public class JobTaskStatusDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(JobTaskStatus persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		this.update(persistentInstance);
	}

	public List<JobTaskStatus> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<JobTaskStatus> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public JobTaskStatus get(Serializable id) {
		// TODO Auto-generated method stub
		return (JobTaskStatus) baseDAO.getEntity(JobTaskStatus.class,id);
	}

	
	public void save(JobTaskStatus transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(JobTaskStatus transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}	
}

package org.mds.project.bean;

import java.io.Serializable;

import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class TaskAttachmentDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(TaskAttachment persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setTaFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<TaskAttachment> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<TaskAttachment> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}
	

	public TaskAttachment get(Serializable id) {
		// TODO Auto-generated method stub
		return (TaskAttachment) baseDAO.getEntity(TaskAttachment.class,id);
	}

	
	public void save(TaskAttachment transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(TaskAttachment transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.updateEntity(transientInstance);
	}
	
	public int getFindCount(MyQuery myQuery) {
		// TODO Auto-generated method stub
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

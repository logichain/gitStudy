package org.mds.project.bean;

import java.io.Serializable;

import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class ProjectAttachmentDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(ProjectAttachment persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setPaFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<ProjectAttachment> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<ProjectAttachment> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}
	

	public ProjectAttachment get(Serializable id) {
		// TODO Auto-generated method stub
		return (ProjectAttachment) baseDAO.getEntity(ProjectAttachment.class,id);
	}

	
	public void save(ProjectAttachment transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(ProjectAttachment transientInstance) {
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

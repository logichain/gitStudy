package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class ProjectVersionDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(ProjectVersion persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setPvFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<ProjectVersion> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<ProjectVersion> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public ProjectVersion get(Serializable id) {
		// TODO Auto-generated method stub
		return (ProjectVersion) baseDAO.getEntity(ProjectVersion.class,id);
	}

	
	public void save(ProjectVersion transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(ProjectVersion transientInstance) {
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

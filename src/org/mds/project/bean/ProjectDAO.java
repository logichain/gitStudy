package org.mds.project.bean;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class ProjectDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(Project persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setPFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<Project> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<Project> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}

	public Project get(Serializable id) {
		// TODO Auto-generated method stub
		return (Project) baseDAO.getEntity(Project.class,id);
	}

	
	public void save(Project transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(Project transientInstance) {
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

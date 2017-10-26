package org.mds.test.bean;

import java.io.Serializable;

import java.util.List;

import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.mds.common.CommonService;

public class CvrAttachmentDAO implements DAO {
	private BaseDAO baseDAO;
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void delete(CvrAttachment persistentInstance) {
		// TODO Auto-generated method stub
//		this.delete(persistentInstance);
		persistentInstance.setCaFlag(CommonService.DELETE_FLAG);
		this.update(persistentInstance);
	}

	public List<CvrAttachment> find(MyQuery myQuery) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(myQuery);
	}

	public List<CvrAttachment> find(String query) {
		// TODO Auto-generated method stub
		return baseDAO.findEntity(query);
	}
	

	public CvrAttachment get(Serializable id) {
		// TODO Auto-generated method stub
		return (CvrAttachment) baseDAO.getEntity(CvrAttachment.class,id);
	}

	
	public void save(CvrAttachment transientInstance) {
		// TODO Auto-generated method stub
		baseDAO.saveEntity(transientInstance);
	}

	public void update(CvrAttachment transientInstance) {
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

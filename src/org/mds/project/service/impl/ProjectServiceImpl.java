package org.mds.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.king.framework.dao.MyQuery;
import org.king.framework.service.impl.BaseService;
import org.king.security.domain.UsrAccount;
import org.king.util.FileUtil;
import org.mds.common.CommonService;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.ModuleFunctionDAO;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectAttachment;
import org.mds.project.bean.ProjectAttachmentDAO;
import org.mds.project.bean.ProjectDAO;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectModuleDAO;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.bean.ProjectVersionDAO;
import org.mds.project.bean.TeamMember;
import org.mds.project.bean.TeamMemberDAO;
import org.mds.project.service.ProjectService;
import org.mds.test.bean.CaseStatus;

public class ProjectServiceImpl extends BaseService implements ProjectService {
	private ProjectDAO projectDAO;
	private ProjectModuleDAO projectModuleDAO;
	private ModuleFunctionDAO moduleFunctionDAO;
	private TeamMemberDAO teamMemberDAO;
	private ProjectVersionDAO projectVersionDAO;
	private ProjectAttachmentDAO projectAttachmentDAO; 



	public void saveProject(Project p) {
		// TODO Auto-generated method stub
		if(p.getPId() == null)
		{
			projectDAO.save(p);
		}
		else
		{
			projectDAO.update(p);
		}
		
	}

	
	public List<Project> searchProject(Object[] args) {
		Project searchInfo = (Project) args[0];
		String page = (String) args[1];
		
		String hqlStr = "select a from Project a where a.PFlag != " + CaseStatus.DELETE_STATUS;
		
		if(searchInfo.getPName() != null)
		{
			hqlStr = hqlStr + " and a.PName like '%" + searchInfo.getPName() + "%'";
		}
		
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(searchInfo.getPageItemCount());
    	
        if (StringUtils.isNumeric(page)) {
        	myQuery.setPageStartNo(Integer.parseInt(page));
        }else {
        	myQuery.setPageStartNo(0);
        }
        myQuery.setOrderby(" order by a.PId");
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
        
        return projectDAO.find(myQuery);
	}

	
	public Integer searchProjectCount(Object[] args) {
		// TODO Auto-generated method stub
		Project searchInfo = (Project) args[0];
		String page = (String) args[1];
		
		String hqlStr = "select count(a) from Project a where a.PFlag != " + CaseStatus.DELETE_STATUS;
		if(searchInfo.getPName() != null)
		{
			hqlStr = hqlStr + " and a.PName like '%" + searchInfo.getPName() + "%'";
		}
		
        return projectDAO.getFindCount(hqlStr);
	}
	

	public List<Project> getProjectList() {
		// TODO Auto-generated method stub
		String query = "from Project a where a.PFlag != " + CommonService.DELETE_FLAG;
		
		return projectDAO.find(query);
	}

	public Project getProjectById(Integer id) {
		// TODO Auto-generated method stub
		return projectDAO.get(id);
	}
	
	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}
	public void setProjectModuleDAO(ProjectModuleDAO projectModuleDAO) {
		this.projectModuleDAO = projectModuleDAO;
	}
	public ProjectModuleDAO getProjectModuleDAO() {
		return projectModuleDAO;
	}


	
	public void saveProjectModule(ProjectModule pm) {
		// TODO Auto-generated method stub
		if(pm.getPmId() == null)
		{
			projectModuleDAO.save(pm);
		}
		else
		{
			projectModuleDAO.update(pm);
		}
	}

	
	public void saveModuleFunction(ModuleFunction mu) {
		// TODO Auto-generated method stub
		if(mu.getMuId() == null)
		{
			moduleFunctionDAO.save(mu);
		}
		else
		{
			moduleFunctionDAO.update(mu);
		}		
	}

	


	public void setTeamMemberDAO(TeamMemberDAO teamMemberDAO) {
		this.teamMemberDAO = teamMemberDAO;
	}

	public TeamMemberDAO getTeamMemberDAO() {
		return teamMemberDAO;
	}
	
	public void saveTeamMember(TeamMember tm) {
		// TODO Auto-generated method stub
		if(tm.getTmId() == null)
		{
			teamMemberDAO.save(tm);
		}
		else
		{
			teamMemberDAO.update(tm);
		}
	}

		
	public void saveProjectVersion(ProjectVersion vtr,String uploadPath) {
		// TODO Auto-generated method stub
		ArrayList<ProjectAttachment> attachmentList = vtr.getAttachmentList();
		vtr.setAttachmentList(new ArrayList<ProjectAttachment>());
		Date createTime = new Date();
		
		if(vtr.getPvId() == null)
		{
			projectVersionDAO.save(vtr);
		}
		else
		{
			projectVersionDAO.update(vtr);
		}
		
		vtr.setAttachmentList(attachmentList);
		
		for(ProjectAttachment pa:attachmentList)
		{
			if(pa.getPaId() == null)
			{
				String fileName = FileUtil.saveUploadFile(pa.getAttachmentFile(),uploadPath + vtr.getPvProject() + "\\" + vtr.getPvId());
				
				pa.setPaProjectVersion(vtr.getPvId());
				pa.setPaCreateTime(createTime);
				pa.setPaUrl(uploadPath + vtr.getPvProject() + "\\" + vtr.getPvId()  + "\\" + fileName);
								
				projectAttachmentDAO.save(pa);
			}		
		}
	}
	
	public void deleteProjectVersion(ProjectVersion vtr) {
		// TODO Auto-generated method stub
		ArrayList<ProjectAttachment> attachmentList = vtr.getAttachmentList();
		vtr.setAttachmentList(new ArrayList<ProjectAttachment>());
				
		vtr.setPvFlag(CommonService.DELETE_FLAG);	
		projectVersionDAO.update(vtr);
		
		vtr.setAttachmentList(attachmentList);
		
		for(ProjectAttachment pa:attachmentList)
		{
			pa.setPaFlag(CommonService.DELETE_FLAG);			
			projectAttachmentDAO.update(pa);						
		}
	}

	public ProjectAttachmentDAO getProjectAttachmentDAO() {
		return projectAttachmentDAO;
	}


	public void setProjectAttachmentDAO(ProjectAttachmentDAO projectAttachmentDAO) {
		this.projectAttachmentDAO = projectAttachmentDAO;
	}
	
	public ProjectVersionDAO getProjectVersionDAO() {
		return projectVersionDAO;
	}


	public void setProjectVersionDAO(ProjectVersionDAO projectVersionDAO) {
		this.projectVersionDAO = projectVersionDAO;
	}
	
	public ModuleFunctionDAO getModuleFunctionDAO() {
		return moduleFunctionDAO;
	}


	public void setModuleFunctionDAO(ModuleFunctionDAO moduleFunctionDAO) {
		this.moduleFunctionDAO = moduleFunctionDAO;
	}


	@Override
	public ProjectModule getProjectModuleById(Integer id) {
		// TODO Auto-generated method stub
		return projectModuleDAO.get(id);
	}


	public static String getModuleFunctionListForSearch(ProjectModule pm) {
		// TODO Auto-generated method stub
		String rtn = "(";
		
		for(ModuleFunction mf:pm.getAllModuleFunctionList())
		{
			rtn = rtn + mf.getMuId() + ",";
		}
		
		if(rtn.contains(","))
		{
			rtn = rtn.substring(0, rtn.length() -1);
		}
		
		rtn = rtn + ")";
		
		return rtn;
	}
	
	public static String getModuleFunctionListForSearch(ArrayList<ModuleFunction> functionList) {
		// TODO Auto-generated method stub
		String rtn = "(";
		
		for(ModuleFunction mf:functionList)
		{
			rtn = rtn + mf.getMuId() + ",";
		}
		
		if(rtn.contains(","))
		{
			rtn = rtn.substring(0, rtn.length() -1);
		}
		
		rtn = rtn + ")";
		
		return rtn;
	}
	
	public static ModuleFunction findModuleFunctionById(ModuleFunction mf,Integer id)
	{
		ModuleFunction rtn = null;
		
		if(mf.getMuId().equals(id))
		{
			rtn = mf;
		}
		else
		{
			for(ModuleFunction cmf:mf.getChildFunctionList())
			{
				if(cmf.getMuId().equals(id))
				{
					rtn = cmf;
					break;
				}
				else
				{
					rtn = findModuleFunctionById(cmf, id);
				}
			}
		}
		
		return rtn;
	}
	
	public static void addAllFunctionIntoList(ArrayList<ModuleFunction> functionList,ModuleFunction mf)
	{
		functionList.add(mf);
		for(ModuleFunction cmf:mf.getChildFunctionList())
		{
			addAllFunctionIntoList(functionList, cmf);
		}
	}
}

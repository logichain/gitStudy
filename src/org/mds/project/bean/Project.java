package org.mds.project.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.project.service.impl.ProjectServiceImpl;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */

public class Project extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer PId;
	private String PName;

	private Integer PFlag = 0;
	private String PRemark;
	private Integer PCreateUser;
	private Date PCreateTime;
	
	private ProjectVersion initProjectVersion = new ProjectVersion(); 
	private ArrayList<ProjectModule> moduleList = new ArrayList<ProjectModule>();
	private ArrayList<ProjectVersion> projectVersionList = new ArrayList<ProjectVersion>();
	private ArrayList<TeamMember> memberList = new ArrayList<TeamMember>();
		
	private ProjectModule selectedProjectModule = new ProjectModule();
		
	private UsrAccount developLeader;
	private UsrAccount testLeader;
	private Integer pageItemCount = 30;
	
	// Constructors

	
	/** default constructor */
	public Project() {
	}

	/** full constructor */
	public Project(String PName, Integer PFlag) {
		this.PName = PName;
		this.PFlag = PFlag;
	}

	// Property accessors

	public Integer getPId() {
		return this.PId;
	}

	public void setPId(Integer PId) {
		this.PId = PId;
	}

	public String getPName() {
		return this.PName;
	}

	public void setPName(String PName) {
		this.PName = PName;
	}

	public Integer getPFlag() {
		return this.PFlag;
	}

	public void setPFlag(Integer PFlag) {
		this.PFlag = PFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof Project)
		{
			Project cs = (Project)arg0;
			
			return cs.getPId().equals(PId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return PId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return PId + PName;
	}

	public void setModuleSet(Set<ProjectModule> moduleSet) {
		moduleList.clear();
		moduleList.addAll(moduleSet);
	}

	public Set<ProjectModule> getModuleSet() {
		Set<ProjectModule> set = new HashSet<ProjectModule>();
		set.addAll(moduleList);
		
		return set;
	}
	
	public ArrayList<ProjectModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(ArrayList<ProjectModule> moduleList) {
		this.moduleList = moduleList;
	}
			
	public void setMemberSet(Set<TeamMember> memberSet) {
		memberList.clear();
		memberList.addAll(memberSet);
	}

	public Set<TeamMember> getMemberSet() {
		Set<TeamMember> set = new HashSet<TeamMember>();
		set.addAll(memberList);
		
		return set;
	}
	
	public ArrayList<TeamMember> getMemberList() {
		return memberList;
	}

	public void setMemberList(ArrayList<TeamMember> memberList) {
		this.memberList = memberList;
	}	

	public void setDevelopLeader(UsrAccount developLeader) {
		this.developLeader = developLeader;
	}

	public UsrAccount getDevelopLeader() {
		return developLeader;
	}

	public void setTestLeader(UsrAccount testLeader) {
		this.testLeader = testLeader;
	}

	public UsrAccount getTestLeader() {
		return testLeader;
	}

	public void setPageItemCount(Integer pageItemCount) {
		this.pageItemCount = pageItemCount;
	}

	public Integer getPageItemCount() {
		return pageItemCount;
	}

	public void setPRemark(String pRemark) {
		PRemark = pRemark;
	}

	public String getPRemark() {
		return PRemark;
	}

	public void setPCreateTime(Date pCreateTime) {
		PCreateTime = pCreateTime;
	}

	public Date getPCreateTime() {
		return PCreateTime;
	}

	public void setPCreateUser(Integer pCreateUser) {
		PCreateUser = pCreateUser;
	}

	public Integer getPCreateUser() {
		return PCreateUser;
	}

	public void setProjectVersionList(ArrayList<ProjectVersion> projectVersionList) {
		this.projectVersionList = projectVersionList;
	}

	public ArrayList<ProjectVersion> getProjectVersionList() {
		return projectVersionList;
	}
	
	public void setProjectVersionSet(Set<ProjectVersion> projectVersionSet) {
		projectVersionList.clear();
		projectVersionList.addAll(projectVersionSet);
		for(ProjectVersion vtr:projectVersionList)
		{
			if(vtr.getPvInit().equals(1))
			{
				this.initProjectVersion = vtr;
				break;
			}
		}
	}

	public Set<ProjectVersion> getProjectVersionSet() {
		Set<ProjectVersion> set = new HashSet<ProjectVersion>();
		set.addAll(projectVersionList);
		
		return set;
	}

	public ProjectVersion getInitProjectVersion() {
		return initProjectVersion;
	}

	public void setInitProjectVersion(ProjectVersion initProjectVersion) {
		this.initProjectVersion = initProjectVersion;
	}

	public ProjectModule getSelectedProjectModule() {
		return selectedProjectModule;
	}

	public void setSelectedProjectModule(ProjectModule selectedProjectModule) {
		this.selectedProjectModule = selectedProjectModule;
	}
	
	public ArrayList<ModuleFunction> getAllModuleFunctionList()
	{
		 ArrayList<ModuleFunction> rtn = new  ArrayList<ModuleFunction>();
		 
		 for(ProjectModule pm:this.moduleList)
		 {
			 for(ModuleFunction mf:pm.getModuleFunctionList())
			 {
				 ProjectServiceImpl.addAllFunctionIntoList(rtn, mf);
			 }
		 }
		 
		 return rtn;
	}
	
	
	public boolean isTeamMember(UsrAccount ua)
	{
		boolean rtn = false;
		
		for(TeamMember tm:this.memberList)
		{
			if(tm.getTmFlag() != CommonService.DELETE_FLAG && tm.getTmAccount() != null && ua.getId().equals(tm.getTmAccount()))
			{
				rtn = true;
				break;
			}
		}
		
		return rtn;
	}

}
package org.mds.project.bean;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.test.bean.TestCase;


public class ProjectVersion extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pvId;
	private Integer pvProject;
	private String pvVersion;
	private Integer pvTestLeader;

	private String pvTestBegin;
	private String pvTestEnd;
	private Integer pvFlag = CommonService.NORMAL_FLAG;
	private Integer pvInit = 0;
	private String pvRemark;
	private Integer pvCreateUser;
	private Date pvCreateTime;
	private boolean selected;
	
	private UsrAccount testLeader = new UsrAccount();
	
	private ArrayList<TestCase> applyCaseList = new ArrayList<TestCase>();
	private ArrayList<ProjectAttachment> attachmentList = new ArrayList<ProjectAttachment>();
	private ProjectAttachment currentAttachment;
	// Constructors

	/** default constructor */
	public ProjectVersion() {
	}
	
	// Property accessors

	public Integer getPvId() {
		return this.pvId;
	}

	public void setPvId(Integer pvId) {
		this.pvId = pvId;
	}

	public Integer getPvProject() {
		return this.pvProject;
	}

	public void setPvProject(Integer pvProject) {
		this.pvProject = pvProject;
	}

	public String getPvVersion() {
		return this.pvVersion;
	}

	public void setPvVersion(String pvVersion) {
		this.pvVersion = pvVersion;
	}

	public Integer getPvTestLeader() {
		return this.pvTestLeader;
	}

	public void setPvTestLeader(Integer pvTestLeader) {
		this.pvTestLeader = pvTestLeader;
	}


	public String getPvTestBegin() {
		if("".equals(pvTestBegin))
		{
			return null;
		}
		return this.pvTestBegin;
	}

	public void setPvTestBegin(String pvTestBegin) {
		
		this.pvTestBegin = pvTestBegin;
	}

	public String getPvTestEnd() {
		if("".equals(pvTestEnd))
		{
			return null;
		}
		return this.pvTestEnd;
	}

	public void setPvTestEnd(String pvTestEnd) {
		
		this.pvTestEnd = pvTestEnd;
	}

	public Integer getPvFlag() {
		return this.pvFlag;
	}

	public void setPvFlag(Integer pvFlag) {
		this.pvFlag = pvFlag;
	}

	public String getPvRemark() {
		return this.pvRemark;
	}

	public void setPvRemark(String pvRemark) {
		this.pvRemark = pvRemark;
	}

	public Integer getPvCreateUser() {
		return this.pvCreateUser;
	}

	public void setPvCreateUser(Integer pvCreateUser) {
		this.pvCreateUser = pvCreateUser;
	}

	public Date getPvCreateTime() {
		return this.pvCreateTime;
	}

	public void setPvCreateTime(Date pvCreateTime) {
		this.pvCreateTime = pvCreateTime;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof ProjectVersion)
		{
			ProjectVersion cs = (ProjectVersion)arg0;
			
			return cs.getPvId().equals(pvId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub	
		return pvId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pvId + pvVersion;
	}

	public void setTestLeader(UsrAccount testLeader) {
		this.testLeader = testLeader;
	}

	public UsrAccount getTestLeader() {
		return testLeader;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setApplyCaseList(ArrayList<TestCase> applyCaseList) {
		this.applyCaseList = applyCaseList;
	}

	public ArrayList<TestCase> getApplyCaseList() {
		return applyCaseList;
	}
	
	public void setApplyCaseSet(Set applyCaseSet) {
		applyCaseList.clear();
		applyCaseList.addAll(applyCaseSet);
	}

	public Set getApplyCaseSet() {
		Set rtn = new HashSet();
		rtn.addAll(applyCaseList);
		
		return rtn;
	}
	
	public void setAttachmentList(ArrayList<ProjectAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public ArrayList<ProjectAttachment> getAttachmentList() {
		return attachmentList;
	}
	
	public Set<ProjectAttachment> getAttachmentSet() {
		Set<ProjectAttachment> detailSet = new HashSet<ProjectAttachment>();
		detailSet.addAll(attachmentList);
		
		return detailSet;
	}
	
	public void setAttachmentSet(Set<ProjectAttachment> attachmentSet) {
		attachmentList.clear();
		attachmentList.addAll(attachmentSet);
	}

	public ProjectAttachment getCurrentAttachment() {
		return currentAttachment;
	}

	public void setCurrentAttachment(ProjectAttachment currentAttachment) {
		this.currentAttachment = currentAttachment;
	}

	public Integer getPvInit() {
		return pvInit;
	}

	public void setPvInit(Integer pvInit) {
		this.pvInit = pvInit;
	}

}
package org.mds.project.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.king.security.domain.UsrAccount;

/**
 * AbstractJobTask entity provides the base persistence definition of the
 * JobTask entity. @author MyEclipse Persistence Tools
 */

public  class JobTask extends	org.king.framework.domain.BaseObject implements java.io.Serializable {

	// Fields
	
	public static int JOBTASK_TYPE_CE = 1;
	public static int JOBTASK_TYPE_CFDA = 2;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer jtId;
	private String jtName;
	private Integer jtProject;
	private Integer jtType;
	private String jtBegin;
	private String jtEnd;
	private Integer jtLeader;
	private Integer jtStatus;
	private Integer jtCreateUser;
	private Date jtCreateTime;
	private String jtDescription;
	
	private ArrayList<TaskAttachment> taskAttachmentList = new ArrayList<TaskAttachment>();
	private UsrAccount leader;
	private Project project;
	private JobTaskStatus status;
	
	private TaskAttachment currentAttachment;

	// Constructors

	/** default constructor */
	public JobTask() {
	}

	/** full constructor */
	public JobTask(Integer jtId, String jtName, Integer jtType,
			String jtBegin, String jtEnd, Integer jtLeader,
			Integer jtStatus, Integer jtCreateUser, Date jtCreateTime) {
		this.jtId = jtId;
		this.jtName = jtName;
		this.jtType = jtType;
		this.jtBegin = jtBegin;
		this.jtEnd = jtEnd;
		this.jtLeader = jtLeader;
		this.jtStatus = jtStatus;
		this.jtCreateUser = jtCreateUser;
		this.jtCreateTime = jtCreateTime;
	}

	// Property accessors

	public Integer getJtId() {
		return this.jtId;
	}

	public void setJtId(Integer jtId) {
		this.jtId = jtId;
	}

	public String getJtName() {
		return this.jtName;
	}

	public void setJtName(String jtName) {
		this.jtName = jtName;
	}

	public Integer getJtType() {
		return this.jtType;
	}

	public void setJtType(Integer jtType) {
		this.jtType = jtType;
	}

	public String getJtBegin() {
		return this.jtBegin;
	}

	public void setJtBegin(String jtBegin) {
		this.jtBegin = jtBegin;
	}

	public String getJtEnd() {
		return this.jtEnd;
	}

	public void setJtEnd(String jtEnd) {
		this.jtEnd = jtEnd;
	}

	public Integer getJtLeader() {
		return this.jtLeader;
	}

	public void setJtLeader(Integer jtLeader) {
		this.jtLeader = jtLeader;
	}

	public Integer getJtStatus() {
		return this.jtStatus;
	}

	public void setJtStatus(Integer jtStatus) {
		this.jtStatus = jtStatus;
	}

	public Integer getJtCreateUser() {
		return this.jtCreateUser;
	}

	public void setJtCreateUser(Integer jtCreateUser) {
		this.jtCreateUser = jtCreateUser;
	}

	public Date getJtCreateTime() {
		return this.jtCreateTime;
	}

	public void setJtCreateTime(Date jtCreateTime) {
		this.jtCreateTime = jtCreateTime;
	}
	
	public void setTaskAttachmentSet(Set<TaskAttachment> taskAttachmentSet) {
		taskAttachmentList.clear();
		taskAttachmentList.addAll(taskAttachmentSet);
	}

	public Set<TaskAttachment> getTaskAttachmentSet() {
		Set<TaskAttachment> set = new HashSet<TaskAttachment>();
		set.addAll(taskAttachmentList);
		
		return set;
	}
	
	public ArrayList<TaskAttachment> getTaskAttachmentList() {
		return taskAttachmentList;
	}

	public void setTaskAttachmentList(ArrayList<TaskAttachment> taskAttachmentList) {
		this.taskAttachmentList = taskAttachmentList;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		
		if(arg0 != null && arg0 instanceof JobTask)
		{
			JobTask jt = (JobTask)arg0;
			if(this.jtId.equals(jt.jtId))
			{
				return true;
			}
		}		
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.jtId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.jtId + "," + this.jtName;
	}

	public String getJtDescription() {
		return jtDescription;
	}

	public void setJtDescription(String jtDescription) {
		this.jtDescription = jtDescription;
	}

	public Integer getJtProject() {
		return jtProject;
	}

	public void setJtProject(Integer jtProject) {
		this.jtProject = jtProject;
	}

	public UsrAccount getLeader() {
		return leader;
	}

	public void setLeader(UsrAccount leader) {
		this.leader = leader;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public JobTaskStatus getStatus() {
		return status;
	}

	public void setStatus(JobTaskStatus status) {
		this.status = status;
	}

	public TaskAttachment getCurrentAttachment() {
		return currentAttachment;
	}

	public void setCurrentAttachment(TaskAttachment currentAttachment) {
		this.currentAttachment = currentAttachment;
	}

}
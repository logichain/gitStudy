package org.mds.project.bean;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * AbstractTaskAttachment entity provides the base persistence definition of the
 * TaskAttachment entity. @author MyEclipse Persistence Tools
 */

public  class TaskAttachment extends 	org.king.framework.domain.BaseObject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer taId;
	private Integer taJobTask;
	private String taCode;
	private String taName;
	private String taUrl;
	private String taLocalUrl;
	private Integer taFlag;
	private String taDescription;
	private Integer taCreateUser;
	private Date taCreateTime;
	
	private FormFile attachmentFile;
	private JobTask jobTask;
		
	// Constructors

	/** default constructor */
	public TaskAttachment() {
	}

	/** minimal constructor */
	public TaskAttachment(Integer taId) {
		this.taId = taId;
	}

	/** full constructor */
	public TaskAttachment(Integer taId, Integer taJobTask,
			String taCode, String taName, String taUrl, String taLocalUrl,
			Integer taFlag, String taDescription, Integer taCreateUser,
			Timestamp taCreateTime) {
		this.taId = taId;
		this.taJobTask = taJobTask;
		this.taCode = taCode;
		this.taName = taName;
		this.taUrl = taUrl;
		this.taLocalUrl = taLocalUrl;
		this.taFlag = taFlag;
		this.taDescription = taDescription;
		this.taCreateUser = taCreateUser;
		this.taCreateTime = taCreateTime;
	}

	// Property accessors

	public Integer getTaId() {
		return this.taId;
	}

	public void setTaId(Integer taId) {
		this.taId = taId;
	}

	public Integer getTaJobTask() {
		return this.taJobTask;
	}

	public void setTaJobTask(Integer taJobTask) {
		this.taJobTask = taJobTask;
	}

	public String getTaCode() {
		return this.taCode;
	}

	public void setTaCode(String taCode) {
		this.taCode = taCode;
	}

	public String getTaName() {
		return this.taName;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getTaUrl() {
		return this.taUrl;
	}

	public void setTaUrl(String taUrl) {
		this.taUrl = taUrl;
	}

	public String getTaLocalUrl() {
		return this.taLocalUrl;
	}

	public void setTaLocalUrl(String taLocalUrl) {
		this.taLocalUrl = taLocalUrl;
	}

	public Integer getTaFlag() {
		return this.taFlag;
	}

	public void setTaFlag(Integer taFlag) {
		this.taFlag = taFlag;
	}

	public String getTaDescription() {
		return this.taDescription;
	}

	public void setTaDescription(String taDescription) {
		this.taDescription = taDescription;
	}

	public Integer getTaCreateUser() {
		return this.taCreateUser;
	}

	public void setTaCreateUser(Integer taCreateUser) {
		this.taCreateUser = taCreateUser;
	}

	public Date getTaCreateTime() {
		return this.taCreateTime;
	}

	public void setTaCreateTime(Date taCreateTime) {
		this.taCreateTime = taCreateTime;
	}
	
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof TaskAttachment)
		{
			TaskAttachment ta = (TaskAttachment)arg0;
			if(this.taId.equals(ta.getTaId()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.taId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.taId + "," + this.taName;
	}

	public FormFile getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(FormFile attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	public JobTask getJobTask() {
		return jobTask;
	}

	public void setJobTask(JobTask jobTask) {
		this.jobTask = jobTask;
	}

}
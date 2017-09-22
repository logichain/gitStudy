package org.mds.project.bean;

import java.util.Date;


import org.apache.struts.upload.FormFile;
import org.mds.common.CommonService;

/**
 * ProjectAttachment entity. @author MyEclipse Persistence Tools
 */

public class ProjectAttachment extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer paId;
	private Integer paProjectVersion;
	private String paCode;
	private String paName;
	private String paUrl;
	private Integer paFlag = CommonService.NORMAL_FLAG;
	private Integer paCreateUser;
	private Date paCreateTime;
	private String paLocalUrl;
	private String paDescription;
	
	private FormFile attachmentFile;

	// Constructors


	/** default constructor */
	public ProjectAttachment() {
	}


	/** full constructor */
	public ProjectAttachment(Integer paProjectVersion, String paCode, String paName,
			String paUrl, Integer paFlag, Integer paCreateUser, Date paCreateTime) {
		this.paProjectVersion = paProjectVersion;
		this.paCode = paCode;
		this.paName = paName;
		this.paUrl = paUrl;
		this.paFlag = paFlag;
		this.paCreateUser = paCreateUser;
		this.paCreateTime = paCreateTime;
	}

	// Property accessors

	public Integer getPaId() {
		return this.paId;
	}

	public void setPaId(Integer paId) {
		this.paId = paId;
	}

	public String getPaCode() {
		return this.paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getPaName() {
		return this.paName;
	}

	public void setPaName(String paName) {
		this.paName = paName;
	}

	public String getPaUrl() {
		return this.paUrl;
	}

	public void setPaUrl(String paUrl) {
		this.paUrl = paUrl;
	}

	public Integer getPaFlag() {
		return this.paFlag;
	}

	public void setPaFlag(Integer paFlag) {
		this.paFlag = paFlag;
	}

	public Integer getPaCreateUser() {
		return this.paCreateUser;
	}

	public void setPaCreateUser(Integer paCreateUser) {
		this.paCreateUser = paCreateUser;
	}

	public Date getPaCreateTime() {
		return this.paCreateTime;
	}

	public void setPaCreateTime(Date paCreateTime) {
		this.paCreateTime = paCreateTime;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 != null && arg0 instanceof ProjectAttachment) {
			if(arg0 == this){
				return true;
			}
			ProjectAttachment pa = (ProjectAttachment) arg0;
			if (pa.getPaId().equals(paId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (paId + paName).hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.paId + this.paName;
	}


	public FormFile getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(FormFile attachmentFile) {
		this.attachmentFile = attachmentFile;
	}



	public void setPaLocalUrl(String paLocalUrl) {
		this.paLocalUrl = paLocalUrl;
	}

	public String getPaLocalUrl() {
		return paLocalUrl;
	}


	public String getPaDescription() {
		return paDescription;
	}


	public void setPaDescription(String paDescription) {
		this.paDescription = paDescription;
	}


	public Integer getPaProjectVersion() {
		return paProjectVersion;
	}


	public void setPaProjectVersion(Integer paProjectVersion) {
		this.paProjectVersion = paProjectVersion;
	}

}
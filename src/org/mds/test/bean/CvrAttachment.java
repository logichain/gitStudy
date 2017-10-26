package org.mds.test.bean;

import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.mds.common.CommonService;

/**
 * ProjectAttachment entity. @author MyEclipse Persistence Tools
 */

public class CvrAttachment extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer caId;
	private Integer caCaseVersionReference;
	private String caCode;
	private String caName;
	private String caUrl;
	private Integer caFlag = CommonService.NORMAL_FLAG;
	private Integer caCreateUser;
	private Date caCreateTime;
	private String caLocalUrl;
	private String caDescription;
	
	private FormFile attachmentFile;

	// Constructors


	/** default constructor */
	public CvrAttachment() {
	}


	/** full constructor */
	public CvrAttachment(Integer caCaseVersionReference, String caCode, String caName,
			String caUrl, Integer caFlag, Integer caCreateUser, Date caCreateTime) {
		this.caCaseVersionReference = caCaseVersionReference;
		this.caCode = caCode;
		this.caName = caName;
		this.caUrl = caUrl;
		this.caFlag = caFlag;
		this.caCreateUser = caCreateUser;
		this.caCreateTime = caCreateTime;
	}

	// Property accessors

	public Integer getCaId() {
		return this.caId;
	}

	public void setCaId(Integer caId) {
		this.caId = caId;
	}

	public String getCaCode() {
		return this.caCode;
	}

	public void setCaCode(String caCode) {
		this.caCode = caCode;
	}

	public String getCaName() {
		return this.caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public String getCaUrl() {
		return this.caUrl;
	}

	public void setCaUrl(String caUrl) {
		this.caUrl = caUrl;
	}

	public Integer getCaFlag() {
		return this.caFlag;
	}

	public void setCaFlag(Integer caFlag) {
		this.caFlag = caFlag;
	}

	public Integer getCaCreateUser() {
		return this.caCreateUser;
	}

	public void setCaCreateUser(Integer caCreateUser) {
		this.caCreateUser = caCreateUser;
	}

	public Date getCaCreateTime() {
		return this.caCreateTime;
	}

	public void setCaCreateTime(Date caCreateTime) {
		this.caCreateTime = caCreateTime;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 != null && arg0 instanceof CvrAttachment) {
			if(arg0 == this){
				return true;
			}
			CvrAttachment ca = (CvrAttachment) arg0;
			if (ca.getCaId().equals(caId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (caId + caName).hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.caId + this.caName;
	}


	public FormFile getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(FormFile attachmentFile) {
		this.attachmentFile = attachmentFile;
	}



	public void setCaLocalUrl(String caLocalUrl) {
		this.caLocalUrl = caLocalUrl;
	}

	public String getCaLocalUrl() {
		return caLocalUrl;
	}


	public String getCaDescription() {
		return caDescription;
	}


	public void setCaDescription(String caDescription) {
		this.caDescription = caDescription;
	}


	public Integer getCaCaseVersionReference() {
		return caCaseVersionReference;
	}


	public void setCaCaseVersionReference(Integer caCaseVersionReference) {
		this.caCaseVersionReference = caCaseVersionReference;
	}




}
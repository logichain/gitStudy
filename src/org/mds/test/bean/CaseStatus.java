package org.mds.test.bean;

/**
 * CaseStatus entity. @author MyEclipse Persistence Tools
 */

public class CaseStatus extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields
	
	public static Integer DELETE_STATUS = -1;//É¾³ý
	public static Integer WAIT_TEST_STATUS = 1;//´ý²â
	public static Integer CLOSE_STATUS = 2;//¹Ø±Õ
	public static Integer TESTED_STATUS = 3;//²âÊÔ
	public static Integer CORRECT_STATUS = 4;//ÐÞÕý

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer csId;
	private String csName;
	private Integer csFlag;

	// Constructors

	/** default constructor */
	public CaseStatus() {
	}

	/** full constructor */
	public CaseStatus(String csName, Integer csFlag) {
		this.csName = csName;
		this.csFlag = csFlag;
	}

	// Property accessors

	public Integer getCsId() {
		return this.csId;
	}

	public void setCsId(Integer csId) {
		this.csId = csId;
	}

	public String getCsName() {
		return this.csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public Integer getCsFlag() {
		return this.csFlag;
	}

	public void setCsFlag(Integer csFlag) {
		this.csFlag = csFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof CaseStatus)
		{
			CaseStatus cs = (CaseStatus)arg0;
			
			return cs.getCsId().equals(csId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return csId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return csId + csName;
	}

}
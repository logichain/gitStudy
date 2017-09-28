package org.mds.test.bean;

/**
 * BugType entity. @author MyEclipse Persistence Tools
 */

public class CaseType extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields
	public static final Integer CASE_TYPE_NORMAL = 1;
	public static final Integer CASE_TYPE_EXTEND = 2;
	public static final Integer CASE_TYPE_NECESSARY = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ctId;
	private String ctName;
	private Integer ctFlag;

	// Constructors

	/** default constructor */
	public CaseType() {
	}

	public Integer getCtId() {
		return ctId;
	}



	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}



	public String getCtName() {
		return ctName;
	}



	public void setCtName(String ctName) {
		this.ctName = ctName;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof CaseType)
		{
			CaseType bt = (CaseType)arg0;
			
			return bt.getCtId().equals(ctId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return ctId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  ctId + ctName;
	}



	public Integer getCtFlag() {
		return ctFlag;
	}



	public void setCtFlag(Integer ctFlag) {
		this.ctFlag = ctFlag;
	}

}
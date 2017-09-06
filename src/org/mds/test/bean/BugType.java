package org.mds.test.bean;

/**
 * BugType entity. @author MyEclipse Persistence Tools
 */

public class BugType extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer btId;
	private String btName;
	private Integer btFlag;

	// Constructors

	/** default constructor */
	public BugType() {
	}

	/** full constructor */
	public BugType(String btName, Integer btFlag) {
		this.btName = btName;
		this.btFlag = btFlag;
	}

	// Property accessors

	public Integer getBtId() {
		return this.btId;
	}

	public void setBtId(Integer btId) {
		this.btId = btId;
	}

	public String getBtName() {
		return this.btName;
	}

	public void setBtName(String btName) {
		this.btName = btName;
	}

	public Integer getBtFlag() {
		return this.btFlag;
	}

	public void setBtFlag(Integer btFlag) {
		this.btFlag = btFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof BugType)
		{
			BugType bt = (BugType)arg0;
			
			return bt.getBtId().equals(btId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return btId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return btId + btName;
	}

}
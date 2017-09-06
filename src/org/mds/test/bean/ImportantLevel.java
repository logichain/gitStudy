package org.mds.test.bean;

/**
 * ImportantLevel entity. @author MyEclipse Persistence Tools
 */

public class ImportantLevel extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ilId;
	private String ilName;
	private Integer ilFlag;

	// Constructors

	/** default constructor */
	public ImportantLevel() {
	}

	/** full constructor */
	public ImportantLevel(String ilName, Integer ilFlag) {
		this.ilName = ilName;
		this.ilFlag = ilFlag;
	}

	// Property accessors

	public Integer getIlId() {
		return this.ilId;
	}

	public void setIlId(Integer ilId) {
		this.ilId = ilId;
	}

	public String getIlName() {
		return this.ilName;
	}

	public void setIlName(String ilName) {
		this.ilName = ilName;
	}

	public Integer getIlFlag() {
		return this.ilFlag;
	}

	public void setIlFlag(Integer ilFlag) {
		this.ilFlag = ilFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof ImportantLevel)
		{
			ImportantLevel cs = (ImportantLevel)arg0;
			
			return cs.getIlId().equals(ilId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return ilId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ilId + ilName;
	}

}
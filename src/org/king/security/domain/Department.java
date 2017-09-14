package org.king.security.domain;

/**
 * Department entity. @author MyEclipse Persistence Tools
 */

public class Department extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	public static Integer DEPART_DEVELOP = 1;
	public static Integer DEPART_QUALITY = 2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer DId;
	private String DName;
	private Integer DFlag;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** full constructor */
	public Department(String DName, Integer DFlag) {
		this.DName = DName;
		this.DFlag = DFlag;
	}

	// Property accessors

	public Integer getDId() {
		return this.DId;
	}

	public void setDId(Integer DId) {
		this.DId = DId;
	}

	public String getDName() {
		return this.DName;
	}

	public void setDName(String DName) {
		this.DName = DName;
	}

	public Integer getDFlag() {
		return this.DFlag;
	}

	public void setDFlag(Integer DFlag) {
		this.DFlag = DFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof Department)
		{
			Department d = (Department)arg0;
			return d.getDId().equals(DId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return DId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DId + DName;
	}

}
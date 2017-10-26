package org.mds.test.bean;

/**
 * TestResault entity. @author MyEclipse Persistence Tools
 */

public class TestResult extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	public static Integer TestResult_PASSED = 1;//通过
	public static Integer TestResult_FAILED = 2;//未通过
	public static Integer TestResult_NA = 3;//NA
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer trId;
	private String trName;
	private Integer trFlag;

	// Constructors

	/** default constructor */
	public TestResult() {
	}

	/** full constructor */
	public TestResult(String trName, Integer trFlag) {
		this.trName = trName;
		this.trFlag = trFlag;
	}

	// Property accessors

	public Integer getTrId() {
		return this.trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	public String getTrName() {
		return this.trName;
	}

	public void setTrName(String trName) {
		this.trName = trName;
	}

	public Integer getTrFlag() {
		return this.trFlag;
	}

	public void setTrFlag(Integer trFlag) {
		this.trFlag = trFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof TestResult)
		{
			TestResult cs = (TestResult)arg0;
			
			return cs.getTrId().equals(trId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return trId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return trId + trName;
	}

}
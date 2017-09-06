package org.mds.project.bean;

/**
 * JobTaskStatus entity. @author MyEclipse Persistence Tools
 */

public class JobTaskStatus extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int JOBTASK_STATUS_INIT = 0;//未开始
	public static int JOBTASK_STATUS_DOING = 1;//进行中
	public static int JOBTASK_STATUS_FINISH = 2;//完成
	// Fields

	private Integer jtsId;
	private String jtsName;
	private Integer jtsFlag;

	// Constructors

	/** default constructor */
	public JobTaskStatus() {
	}

	/** minimal constructor */
	public JobTaskStatus(Integer jtsId) {
		this.jtsId = jtsId;
	}

	/** full constructor */
	public JobTaskStatus(Integer jtsId, String jtsName, Integer jtsFlag) {
		this.jtsId = jtsId;
		this.jtsName = jtsName;
		this.jtsFlag = jtsFlag;
	}

	// Property accessors

	public Integer getJtsId() {
		return this.jtsId;
	}

	public void setJtsId(Integer jtsId) {
		this.jtsId = jtsId;
	}

	public String getJtsName() {
		return this.jtsName;
	}

	public void setJtsName(String jtsName) {
		this.jtsName = jtsName;
	}

	public Integer getJtsFlag() {
		return this.jtsFlag;
	}

	public void setJtsFlag(Integer jtsFlag) {
		this.jtsFlag = jtsFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof JobTaskStatus)
		{
			JobTaskStatus jts = (JobTaskStatus)arg0;
			if(jtsId.equals(jts.getJtsId()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.jtsId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.jtsId + "," + this.jtsName;
	}

}
package org.mds.test.bean;

import java.util.Date;

import org.king.security.domain.UsrAccount;
import org.mds.project.bean.ProjectVersion;

/**
 * TestCorrectRecord entity. @author MyEclipse Persistence Tools
 */

public class TestCorrectRecord extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tcrId;
	private Integer tcrTestCase;
	private Integer tcrOperUser;
	private Date tcrOperTime;
	private Integer tcrCaseStatus;
	private String tcrRemark;
	private Integer tcrTestResult;
	private Integer tcrTestVersion;
	private ProjectVersion testVersion;
	
	private UsrAccount operUser;
	private CaseStatus status;
	private TestResult testResult;
	
	private boolean currentOperRecord = false;

	// Constructors

	/** default constructor */
	public TestCorrectRecord() {
	}

	/** minimal constructor */
	public TestCorrectRecord(Integer tcrId) {
		this.tcrId = tcrId;
	}

	/** full constructor */
	public TestCorrectRecord(Integer tcrId, Integer tcrTestCase,
			Integer tcrOperUser, Date tcrOperTime, Integer tcrCaseStatus,
			String tcrRemark) {
		this.tcrId = tcrId;
		this.tcrTestCase = tcrTestCase;
		this.tcrOperUser = tcrOperUser;
		this.tcrOperTime = tcrOperTime;
		this.tcrCaseStatus = tcrCaseStatus;
		this.tcrRemark = tcrRemark;
	}

	// Property accessors

	public Integer getTcrId() {
		return this.tcrId;
	}

	public void setTcrId(Integer tcrId) {
		this.tcrId = tcrId;
	}

	public Integer getTcrTestCase() {
		return this.tcrTestCase;
	}

	public void setTcrTestCase(Integer tcrTestCase) {
		this.tcrTestCase = tcrTestCase;
	}

	public Integer getTcrOperUser() {
		return this.tcrOperUser;
	}

	public void setTcrOperUser(Integer tcrOperUser) {
		this.tcrOperUser = tcrOperUser;
	}

	public Date getTcrOperTime() {
		return this.tcrOperTime;
	}

	public void setTcrOperTime(Date tcrOperTime) {
		this.tcrOperTime = tcrOperTime;
	}

	public Integer getTcrCaseStatus() {
		return this.tcrCaseStatus;
	}

	public void setTcrCaseStatus(Integer tcrCaseStatus) {
		this.tcrCaseStatus = tcrCaseStatus;
	}

	public String getTcrRemark() {
		return this.tcrRemark;
	}

	public void setTcrRemark(String tcrRemark) {
		this.tcrRemark = tcrRemark;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof TestCorrectRecord)
		{
			TestCorrectRecord cs = (TestCorrectRecord)arg0;
			
			return cs.getTcrId().equals(tcrId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return tcrId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tcrId + "";
	}

	public void setOperUser(UsrAccount operUser) {
		this.operUser = operUser;
	}

	public UsrAccount getOperUser() {
		return operUser;
	}

	public void setStatus(CaseStatus status) {
		this.status = status;
	}

	public CaseStatus getStatus() {
		return status;
	}

	public Integer getTcrTestResult() {
		return tcrTestResult;
	}

	public void setTcrTestResult(Integer tcrTestResult) {
		this.tcrTestResult = tcrTestResult;
	}

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public boolean isCurrentOperRecord() {
		return currentOperRecord;
	}

	public void setCurrentOperRecord(boolean currentOperRecord) {
		this.currentOperRecord = currentOperRecord;
	}

	public ProjectVersion getTestVersion() {
		return testVersion;
	}

	public void setTestVersion(ProjectVersion testVersion) {
		this.testVersion = testVersion;
	}

	public Integer getTcrTestVersion() {
		return tcrTestVersion;
	}

	public void setTcrTestVersion(Integer tcrTestVersion) {
		this.tcrTestVersion = tcrTestVersion;
	}

}
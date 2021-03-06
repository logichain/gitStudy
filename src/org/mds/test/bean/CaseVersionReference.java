package org.mds.test.bean;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.project.bean.ProjectVersion;


/**
 * CaseVersionReference entity. @author MyEclipse Persistence Tools
 */

public class CaseVersionReference extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cvrId;
	private Integer cvrTestCase;
	private Integer cvrProjectVersion;
	private Integer cvrFlag = CommonService.NORMAL_FLAG;
	
	private String cvrCaseOutput="";
	private Integer cvrCaseResult;
	private Integer cvrCaseStatus;
	private Integer cvrTestUser;
	private Date cvrTestTime;
	private Integer cvrCorrectUser;
	private Date cvrCorrectTime;
	private Integer cvrImportantLevel;
	private Integer cvrBugType;
	private Integer cvrCloseUser;
	private Date cvrCloseTime;
	
	private String cvrTestTimeStr;
	private String cvrCorrectTimeStr;	
	private String cvrTestUserStr;
	private String cvrCorrectUserStr;
	
	private UsrAccount testUser;
	private UsrAccount correctUser;
	private CaseStatus status;
	private TestResult testResult;
	private ImportantLevel importantLevel;
	private BugType bugType;
	
	private ProjectVersion projectVersion;
	
	private Integer referVersion;
	private ArrayList<CvrAttachment> attachmentList = new ArrayList<CvrAttachment>();
	private CvrAttachment currentAttachment;
	
	// Constructors

	/** default constructor */
	public CaseVersionReference() {
	}

	/** full constructor */
	public CaseVersionReference(Integer cvrTestCase, Integer cvrProjectVersion,
			Integer cvrFlag) {
		this.setCvrTestCase(cvrTestCase);
		this.setCvrProjectVersion(cvrProjectVersion);
		this.cvrFlag = cvrFlag;
	}

	// Property accessors

	public Integer getCvrId() {
		return this.cvrId;
	}

	public void setCvrId(Integer cvrId) {
		this.cvrId = cvrId;
	}

	public Integer getCvrFlag() {
		return this.cvrFlag;
	}

	public void setCvrFlag(Integer cvrFlag) {
		this.cvrFlag = cvrFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof CaseVersionReference)
		{
			CaseVersionReference cs = (CaseVersionReference)arg0;
			
			return cs.getCvrId().equals(cvrId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return cvrId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return cvrTestCase + "," + cvrProjectVersion;
	}


	public Integer getCvrTestCase() {
		return cvrTestCase;
	}

	public void setCvrTestCase(Integer cvrTestCase) {
		this.cvrTestCase = cvrTestCase;
	}

	public Integer getCvrProjectVersion() {
		return cvrProjectVersion;
	}

	public void setCvrProjectVersion(Integer cvrProjectVersion) {
		this.cvrProjectVersion = cvrProjectVersion;
	}
	
	
	public Integer getCvrTestUser() {
		return this.cvrTestUser;
	}

	public void setCvrTestUser(Integer cvrTestUser) {
		this.cvrTestUser = cvrTestUser;
	}

	public Date getCvrTestTime() {
		return this.cvrTestTime;
	}

	public void setCvrTestTime(Date cvrTestTime) {
		this.cvrTestTime = cvrTestTime;
	}

	public Integer getCvrCorrectUser() {
		return this.cvrCorrectUser;
	}

	public void setCvrCorrectUser(Integer cvrCorrectUser) {
		this.cvrCorrectUser = cvrCorrectUser;
	}

	public Date getCvrCorrectTime() {
		return this.cvrCorrectTime;
	}

	public void setCvrCorrectTime(Date cvrCorrectTime) {
		this.cvrCorrectTime = cvrCorrectTime;
	}
	
	public void setCvrImportantLevel(Integer cvrImportantLevel) {
		this.cvrImportantLevel = cvrImportantLevel;
	}

	public Integer getCvrImportantLevel() {
		if(cvrImportantLevel != null && cvrImportantLevel.equals(0))
		{
			return null;
		}
		return cvrImportantLevel;
	}
	public void setCvrTestTimeStr(String cvrTestTimeStr) {
		this.cvrTestTimeStr = cvrTestTimeStr;
	}

	public String getCvrTestTimeStr() {
		if(cvrTestTime != null)
		{
			return DateFormat.getDateTimeInstance().format(cvrTestTime);
		}
		return cvrTestTimeStr;
	}

	public void setCvrCorrectTimeStr(String cvrCorrectTimeStr) {
		this.cvrCorrectTimeStr = cvrCorrectTimeStr;
	}

	public String getCvrCorrectTimeStr() {
		if(cvrCorrectTime != null)
		{
			return DateFormat.getDateTimeInstance().format(cvrCorrectTime);
		}
		return cvrCorrectTimeStr;
	}

	public void setTestUser(UsrAccount testUser) {
		this.testUser = testUser;
	}

	public UsrAccount getTestUser() {
		return testUser;
	}
	
	public void setCvrTestUserStr(String cvrTestUserStr) {
		this.cvrTestUserStr = cvrTestUserStr;
	}

	public String getCvrTestUserStr() {
		return cvrTestUserStr;
	}

	public void setCvrCorrectUserStr(String cvrCorrectUserStr) {
		this.cvrCorrectUserStr = cvrCorrectUserStr;
	}

	public String getCvrCorrectUserStr() {
		return cvrCorrectUserStr;
	}

	public void setStatus(CaseStatus status) {
		this.status = status;
	}

	public CaseStatus getStatus() {
		return status;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public TestResult getTestResult() {
		return testResult;
	}
	
	public void setCorrectUser(UsrAccount correctUser) {
		this.correctUser = correctUser;
	}

	public UsrAccount getCorrectUser() {
		return correctUser;
	}
	public void setCvrCloseUser(Integer cvrCloseUser) {
		this.cvrCloseUser = cvrCloseUser;
	}

	public Integer getCvrCloseUser() {
		return cvrCloseUser;
	}

	public void setCvrCloseTime(Date cvrCloseTime) {
		this.cvrCloseTime = cvrCloseTime;
	}

	public Date getCvrCloseTime() {
		return cvrCloseTime;
	}

	//�б���ʾ��
	public boolean isEditable() {
		return !CaseStatus.CLOSE_STATUS.equals(cvrCaseStatus);
	}
	public boolean isTestable() {
		return CaseStatus.WAIT_TEST_STATUS.equals(cvrCaseStatus) || CaseStatus.CORRECT_STATUS.equals(cvrCaseStatus);
	}
	public boolean isCorrectable() {
		return CaseStatus.TESTED_STATUS.equals(cvrCaseStatus) && TestResult.TestResult_FAILED.equals(cvrCaseResult);
	}
	public boolean isCloseable() {
		return CaseStatus.TESTED_STATUS.equals(cvrCaseStatus) && TestResult.TestResult_FAILED.equals(cvrCaseResult);
	}
	public boolean isDeleteable() {
		return CaseStatus.WAIT_TEST_STATUS.equals(cvrCaseStatus);
	}
	public boolean isOpenable() {
		return CaseStatus.CLOSE_STATUS.equals(cvrCaseStatus);
	}

	public void setImportantLevel(ImportantLevel importantLevel) {
		this.importantLevel = importantLevel;
	}

	public ImportantLevel getImportantLevel() {
		return importantLevel;
	}

	public void setCvrBugType(Integer cvrBugType) {
		this.cvrBugType = cvrBugType;
	}

	public Integer getCvrBugType() {
		if(cvrBugType != null && cvrBugType.equals(0))
		{
			return null;
		}
		return cvrBugType;
	}

	public void setBugType(BugType bugType) {
		this.bugType = bugType;
	}

	public BugType getBugType() {
		return bugType;
	}

	public Integer getCvrCaseStatus() {
		if(cvrCaseStatus != null && cvrCaseStatus.equals(0))
		{
			return null;
		}
		return cvrCaseStatus;
	}

	public void setCvrCaseStatus(Integer cvrCaseStatus) {
		this.cvrCaseStatus = cvrCaseStatus;
	}

	public Integer getCvrCaseResult() {
		if(cvrCaseResult != null && cvrCaseResult.equals(0))
		{
			return null;
		}
		
		return cvrCaseResult;
	}

	public void setCvrCaseResult(Integer cvrCaseResult) {
		this.cvrCaseResult = cvrCaseResult;
	}

	public String getCvrCaseOutput() {
		return cvrCaseOutput;
	}

	public void setCvrCaseOutput(String cvrCaseOutput) {
		this.cvrCaseOutput = cvrCaseOutput;
	}

	public ProjectVersion getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(ProjectVersion projectVersion) {
		this.projectVersion = projectVersion;
	}

	public boolean isSearchInfoEmpty()
	{
		boolean rtn = true;
		
		if(cvrCaseOutput != null && !cvrCaseOutput.isEmpty())
		{
			rtn = false;
		}		
		else if(cvrCaseResult != null && cvrCaseResult != 0)
		{
			rtn = false;
		}
		else if(cvrCaseStatus != null && cvrCaseStatus != 0)
		{
			rtn = false;
		}
		else if(cvrTestTimeStr != null && !cvrTestTimeStr.isEmpty())
		{
			rtn = false;
		}		
		else if(cvrTestUserStr != null && !cvrTestUserStr.isEmpty())
		{
			rtn = false;
		}
		else if(cvrCorrectTimeStr  != null && !cvrCorrectTimeStr.isEmpty())
		{
			rtn = false;
		}		
		else if(cvrCorrectUserStr  != null && !cvrCorrectUserStr.isEmpty())
		{
			rtn = false;
		}		
		else if(cvrImportantLevel != null && cvrImportantLevel != 0)
		{
			rtn = false;
		}
		else if(cvrBugType != null && cvrBugType != 0)
		{
			rtn = false;
		}
		else if(cvrProjectVersion != null && cvrProjectVersion != 0)
		{
			rtn = false;
		}
	
		
		return rtn;
	}

	public Integer getReferVersion() {
		return referVersion;
	}

	public void setReferVersion(Integer referVersion) {
		this.referVersion = referVersion;
	}

	
	public void setAttachmentList(ArrayList<CvrAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public ArrayList<CvrAttachment> getAttachmentList() {
		return attachmentList;
	}
	
	public Set<CvrAttachment> getAttachmentSet() {
		Set<CvrAttachment> detailSet = new HashSet<CvrAttachment>();
		detailSet.addAll(attachmentList);
		
		return detailSet;
	}
	
	public void setAttachmentSet(Set<CvrAttachment> attachmentSet) {
		attachmentList.clear();
		attachmentList.addAll(attachmentSet);
	}

	public CvrAttachment getCurrentAttachment() {
		return currentAttachment;
	}

	public void setCurrentAttachment(CvrAttachment currentAttachment) {
		this.currentAttachment = currentAttachment;
	}
	
	
}
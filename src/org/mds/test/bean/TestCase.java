package org.mds.test.bean;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts.upload.FormFile;
import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;


/**
 * TestCase entity. @author MyEclipse Persistence Tools
 */

public class TestCase extends org.king.framework.domain.BaseObject implements java.io.Serializable {

	// Fields

	public static final String CODE_MODULEFUNCTION_DIVIDE = ".";
	public static final String CODE_SERIALNUM_DIVIDE = "-";
	public static final String MODULEFUNCTION_NAME_DIVIDE = "��";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tcId;
	private Integer tcModuleFunction;
	private String tcCode;
	private String tcTestObjective;
	private String tcTestContent;
	private String tcTestStep = "";
	private String tcIntendOutput="";
	private Integer tcFlag = CommonService.NORMAL_FLAG;

	private Integer tcType;
	
	private Integer tcCreateUser;
	private Date tcCreateTime;
		
	private ModuleFunction moduleFunction;
	private ProjectModule projectModule;
	
	private String tcCreateUserStr;
	private String tcCreateTimeStr;
	
	private CaseType caseType;
	private UsrAccount createUser;
	
	private Integer pageItemCount = 30;
	private int offset = 0;
		
	private ArrayList<TestCorrectRecord> testCorrectRecordList = new ArrayList<TestCorrectRecord>();
	private ArrayList<CaseVersionReference> caseVersionReferenceList = new ArrayList<CaseVersionReference>();
	private CaseVersionReference currentCaseVersionReference;
	private ArrayList<CaseAttachment> attachmentList = new ArrayList<CaseAttachment>();
	private CaseAttachment currentAttachment;
	
	private FormFile importFile;
	// Constructors

	/** default constructor */
	public TestCase() {
	}

	/** minimal constructor */
	public TestCase(Integer tcId) {
		this.tcId = tcId;
	}


	// Property accessors

	public Integer getTcId() {
		return this.tcId;
	}

	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}

	public Integer getTcModuleFunction() {
		if(tcModuleFunction != null && tcModuleFunction.equals(0))
		{
			return null;
		}
		return this.tcModuleFunction;
	}

	public void setTcModuleFunction(Integer tcModuleFunction) {
		this.tcModuleFunction = tcModuleFunction;
	}

	public String getTcCode() {
		return this.tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

	public String getTcTestObjective() {
		return this.tcTestObjective;
	}

	public void setTcTestObjective(String tcTestObjective) {
		this.tcTestObjective = tcTestObjective;
	}

	public String getTcTestContent() {
		return this.tcTestContent;
	}

	public void setTcTestContent(String tcTestContent) {
		this.tcTestContent = tcTestContent;
	}

	public String getTcTestStep() {
		return this.tcTestStep;
	}

	public void setTcTestStep(String tcTestStep) {
		this.tcTestStep = tcTestStep;
	}

	public String getTcIntendOutput() {
		return this.tcIntendOutput;
	}

	public void setTcIntendOutput(String tcIntendOutput) {
		this.tcIntendOutput = tcIntendOutput;
	}

	public Integer getTcCreateUser() {
		return this.tcCreateUser;
	}

	public void setTcCreateUser(Integer tcCreateUser) {
		this.tcCreateUser = tcCreateUser;
	}

	public Date getTcCreateTime() {
		return this.tcCreateTime;
	}

	public void setTcCreateTime(Date tcCreateTime) {
		this.tcCreateTime = tcCreateTime;
	}

	

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof TestCase)
		{
			TestCase cs = (TestCase)arg0;
			
			return cs.getTcId().equals(tcId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return tcId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tcId + "," + tcCode;
	}

	

	public void setTcCreateTimeStr(String tcCreateTimeStr) {
		this.tcCreateTimeStr = tcCreateTimeStr;
	}

	public String getTcCreateTimeStr() {
		if(tcCreateTime != null)
		{
			return DateFormat.getDateTimeInstance().format(tcCreateTime);
		}
		return tcCreateTimeStr;
	}

	

	public void setCreateUser(UsrAccount createUser) {
		this.createUser = createUser;
	}

	public UsrAccount getCreateUser() {
		return createUser;
	}


	public void setTcCreateUserStr(String tcCreateUserStr) {
		this.tcCreateUserStr = tcCreateUserStr;
	}

	public String getTcCreateUserStr() {
		return tcCreateUserStr;
	}

	

	public void setPageItemCount(Integer pageItemCount) {
		this.pageItemCount = pageItemCount;
	}

	public Integer getPageItemCount() {
		return pageItemCount;
	}

	

	public void setTestCorrectRecordList(ArrayList<TestCorrectRecord> testCorrectRecordList) {
		this.testCorrectRecordList = testCorrectRecordList;
	}

	public ArrayList<TestCorrectRecord> getTestCorrectRecordList() {
		return testCorrectRecordList;
	}
	
	public void setTestCorrectRecordSet(Set testCorrectRecordSet) {
		testCorrectRecordList.clear();
		testCorrectRecordList.addAll(testCorrectRecordSet);	
	}

	public Set getTestCorrectRecordSet() {
		Set rtn = new HashSet();
		rtn.addAll(testCorrectRecordList);
		
		return rtn;
	}

	public void setCaseVersionReferenceList(ArrayList<CaseVersionReference> caseVersionReferenceList) {
		this.caseVersionReferenceList = caseVersionReferenceList;
	}

	public ArrayList<CaseVersionReference> getCaseVersionReferenceList() {
		
		Collections.sort(caseVersionReferenceList,new Comparator<CaseVersionReference>(){
			@Override
			public int compare(CaseVersionReference arg0, CaseVersionReference arg1) {
				// TODO Auto-generated method stub
				return arg0.getCvrProjectVersion().compareTo(arg1.getCvrProjectVersion());
			}});
		
		return caseVersionReferenceList;
	}
	
	public void setCaseVersionReferenceSet(Set caseVersionReferenceSet) {
		caseVersionReferenceList.clear();
		caseVersionReferenceList.addAll(caseVersionReferenceSet);		
	}

	public Set getCaseVersionReferenceSet() {
		HashSet<CaseVersionReference> rtn = new HashSet<CaseVersionReference>();
		rtn.addAll(caseVersionReferenceList);
		
		return rtn;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public CaseVersionReference isApplyProjectVersion(ProjectVersion version)
	{
		CaseVersionReference rtn = null;
		for(CaseVersionReference cvr:caseVersionReferenceList)
		{
			if(cvr.getCvrProjectVersion().equals(version.getPvId()))
			{
				rtn = cvr;
				break;
			}
		}		
		
		return rtn;
	}
	
	public TestCase copy()
	{
		TestCase rtn = new TestCase();
				
		rtn.setTcModuleFunction(this.tcModuleFunction);
		rtn.setTcTestObjective(tcTestObjective);
		rtn.setTcTestContent(tcTestContent);
		rtn.setTcTestStep(tcTestStep);

		rtn.setTcIntendOutput(tcIntendOutput);
		rtn.setTcCreateUser(tcCreateUser);
		rtn.setTcCreateTime(tcCreateTime);
		
		return rtn;		
	}

	public Integer getTcFlag() {
		return tcFlag;
	}

	public void setTcFlag(Integer tcFlag) {
		this.tcFlag = tcFlag;
	}

	public void setModuleFunction(ModuleFunction moduleFunction) {
		this.moduleFunction = moduleFunction;
	}

	public ModuleFunction getModuleFunction() {
		return moduleFunction;
	}

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	
	public void setAttachmentList(ArrayList<CaseAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public ArrayList<CaseAttachment> getAttachmentList() {
		return attachmentList;
	}
	
	public Set<CaseAttachment> getAttachmentSet() {
		Set<CaseAttachment> detailSet = new HashSet<CaseAttachment>();
		detailSet.addAll(attachmentList);
		
		return detailSet;
	}
	
	public void setAttachmentSet(Set<CaseAttachment> attachmentSet) {
		attachmentList.clear();
		attachmentList.addAll(attachmentSet);
	}

	public CaseAttachment getCurrentAttachment() {
		return currentAttachment;
	}

	public void setCurrentAttachment(CaseAttachment currentAttachment) {
		this.currentAttachment = currentAttachment;
	}

	public Integer getTcType() {
		return tcType;
	}

	public void setTcType(Integer tcType) {
		this.tcType = tcType;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public ProjectModule getProjectModule() {
		return projectModule;
	}

	public void setProjectModule(ProjectModule projectModule) {
		this.projectModule = projectModule;
	}

	public CaseVersionReference getCurrentCaseVersionReference() {
		return currentCaseVersionReference;
	}

	public void setCurrentCaseVersionReference(CaseVersionReference currentCaseVersionReference) {
		this.currentCaseVersionReference = currentCaseVersionReference;
	}

}
package org.mds.test.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.king.framework.dao.MyQuery;
import org.king.framework.service.impl.BaseService;
import org.mds.common.CommonService;
import org.mds.project.bean.Project;
import org.mds.test.bean.BugType;
import org.mds.test.bean.BugTypeDAO;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseStatusDAO;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.CaseVersionReferenceDAO;
import org.mds.test.bean.ImportantLevel;
import org.mds.test.bean.ImportantLevelDAO;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCaseDAO;
import org.mds.test.bean.TestCorrectRecord;
import org.mds.test.bean.TestCorrectRecordDAO;
import org.mds.test.bean.TestResult;
import org.mds.test.bean.TestResultDAO;
import org.mds.test.service.TestCaseService;

public class TestCaseServiceImpl extends BaseService implements TestCaseService {
	private TestCaseDAO testCaseDAO;
	
	private ImportantLevelDAO importantLevelDAO;
	private TestResultDAO testResultDAO; 
	private CaseStatusDAO caseStatusDAO; 
	
	private TestCorrectRecordDAO testCorrectRecordDAO;
	private BugTypeDAO bugTypeDAO;
	private CaseVersionReferenceDAO caseVersionReferenceDAO; 
	
	
	public void saveCaseVersionReference(CaseVersionReference cvr)
	{
		if(cvr.getCvrId() == null)
		{
			caseVersionReferenceDAO.save(cvr);
		}
		else
		{
			caseVersionReferenceDAO.update(cvr);
		}
	}
	

	public void saveTestCase(TestCase testCase) {		
		// TODO Auto-generated method stub
		ArrayList<TestCorrectRecord> recordList = testCase.getTestCorrectRecordList();
		ArrayList<CaseVersionReference> cvrList = testCase.getCaseVersionReferenceList();
		
		testCase.setTestCorrectRecordList(new ArrayList<TestCorrectRecord>());
		testCase.setCaseVersionReferenceList(new ArrayList<CaseVersionReference>());
		
		if(testCase.getTcId() == null)
		{
			testCaseDAO.save(testCase);
		}
		else
		{
			testCaseDAO.update(testCase);
		}
		
		testCase.setTestCorrectRecordList(recordList);
		testCase.setCaseVersionReferenceList(cvrList);
		
		for(TestCorrectRecord record:recordList)
		{
			if(!record.isCurrentOperRecord())
			{
				continue;
			}
				
			if(record.getTcrId() == null)
			{
				record.setTcrTestCase(testCase.getTcId());
				testCorrectRecordDAO.save(record);
			}
			else
			{
				testCorrectRecordDAO.update(record);
			}
		}
		
		for(CaseVersionReference cvr:cvrList)
		{					
			if(cvr.getCvrId() == null)
			{
				cvr.setCvrTestCase(testCase.getTcId());
				caseVersionReferenceDAO.save(cvr);
			}
			else
			{
				caseVersionReferenceDAO.update(cvr);
			}			
		}
	}

	
	public List<TestCase> searchTestCase(Object[] args) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[2];
		String page = (String) args[1];
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[3];
		String functionList = (String) args[4];
		
		String hqlStr = null;
		
		if(cvrSearchInfo.isSearchInfoEmpty())
		{			
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + " and a.tcModuleFunction in " + functionList;			
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
						        
        return this.retrieveTestCaseList(searchInfo,cvrSearchInfo, hqlStr,Integer.parseInt(page));		 
	}
	
	public static String processQuerySql(CaseVersionReference cvr,String hqlStr)
	{
		
		if(cvr.getCvrCaseOutput() != null && !cvr.getCvrCaseOutput().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrCaseOutput like '%" + cvr.getCvrCaseOutput() + "%'";
		}		
		if(cvr.getCvrCaseResult() != null && cvr.getCvrCaseResult() != 0)
		{
			hqlStr = hqlStr + " and e.cvrCaseResult = " + cvr.getCvrCaseResult();
		}
		if(cvr.getCvrCaseStatus() != null && cvr.getCvrCaseStatus() != 0)
		{
			hqlStr = hqlStr + " and e.cvrCaseStatus = " + cvr.getCvrCaseStatus();
		}
		if(cvr.getCvrTestTimeStr() != null && !cvr.getCvrTestTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrTestTime like '%" + cvr.getCvrTestTimeStr() + "%'";
		}		
		if(cvr.getCvrTestUserStr() != null && !cvr.getCvrTestUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.testUser.personName like '%" + cvr.getCvrTestUserStr() + "%'";
		}
		if(cvr.getCvrTestUser() != null)
		{
			hqlStr = hqlStr + " and e.cvrTestUser = " + cvr.getCvrTestUser();
		}
		if(cvr.getCvrCorrectTimeStr()  != null && !cvr.getCvrCorrectTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrCorrectTime like '%" + cvr.getCvrCorrectTimeStr() + "%'";
		}		
		if(cvr.getCvrCorrectUserStr()  != null && !cvr.getCvrCorrectUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.correctUser.personName like '%" + cvr.getCvrCorrectUserStr() + "%'";
		}		
		if(cvr.getCvrImportantLevel() != null && cvr.getCvrImportantLevel() != 0)
		{
			hqlStr = hqlStr + " and e.cvrImportantLevel = " + cvr.getCvrImportantLevel();
		}
		if(cvr.getCvrBugType() != null && cvr.getCvrBugType() != 0)
		{
			hqlStr = hqlStr + " and e.cvrBugType = " + cvr.getCvrBugType();
		}
		if(cvr.getCvrProjectVersion() != null && cvr.getCvrProjectVersion() != 0)
		{
			hqlStr = hqlStr + " and e.cvrProjectVersion = " + cvr.getCvrProjectVersion();
		}		
		
		return hqlStr;
	}
	
	public static String processQuerySql(TestCase searchInfo,String hqlStr)
	{		
		if(searchInfo.getTcModuleFunction()!= null)
		{
			hqlStr = hqlStr + " and a.tcModuleFunction = " + searchInfo.getTcModuleFunction();
		}
		
		if(searchInfo.getTcCode() != null && !searchInfo.getTcCode().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcCode like '%" + searchInfo.getTcCode() + "%'";
		}
		if(searchInfo.getTcTestObjective() != null && !searchInfo.getTcTestObjective().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcTestObjective like '%" + searchInfo.getTcTestObjective() + "%'";
		}
		if(searchInfo.getTcTestContent() != null && !searchInfo.getTcTestContent().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcTestContent like '%" + searchInfo.getTcTestContent() + "%'";
		}
		if(searchInfo.getTcIntendOutput() != null && !searchInfo.getTcIntendOutput().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcIntendOutput like '%" + searchInfo.getTcIntendOutput() + "%'";
		}

		if(searchInfo.getTcRemark() != null && !searchInfo.getTcRemark().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcRemark like '%" + searchInfo.getTcRemark() + "%'";
		}		

		if(searchInfo.getTcCreateTimeStr() != null && !searchInfo.getTcCreateTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcCreateTime like '%" + searchInfo.getTcCreateTimeStr() + "%'";
		}		
		if(searchInfo.getTcCreateUserStr() != null && !searchInfo.getTcCreateUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and a.createUser.personName like '%" + searchInfo.getTcCreateUserStr() + "%'";
		}
		
		if(searchInfo.getTcCreateUser() != null)
		{
			hqlStr = hqlStr + " and a.tcCreateUser = " + searchInfo.getTcCreateUser();
		}

		
		return hqlStr;		
	}

	
	public Integer searchTestCaseCount(Object[] args) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[2];
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[3];
		String functionList = (String) args[4];
		
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())			
		{			
			hqlStr = "select count(distinct a) from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + " and a.tcModuleFunction in " + functionList;			
		}
		else
		{
			hqlStr = "select  count(a) from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}    	
		    	
    	hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
    	hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
    	
        return testCaseDAO.getFindCount(hqlStr);		
	}

	public void setTestCaseDAO(TestCaseDAO testCaseDAO) {
		this.testCaseDAO = testCaseDAO;
	}

	public TestCaseDAO getTestCaseDAO() {
		return testCaseDAO;
	}

	public void setImportantLevelDAO(ImportantLevelDAO importantLevelDAO) {
		this.importantLevelDAO = importantLevelDAO;
	}

	public ImportantLevelDAO getImportantLevelDAO() {
		return importantLevelDAO;
	}

	public void setTestResultDAO(TestResultDAO testResultDAO) {
		this.testResultDAO = testResultDAO;
	}

	public TestResultDAO getTestResultDAO() {
		return testResultDAO;
	}
	
	public List<ImportantLevel> getImportantLevelList() {
		// TODO Auto-generated method stub
		String sql = "from ImportantLevel a where a.ilFlag != " + CommonService.DELETE_FLAG;
		
		return importantLevelDAO.find(sql);
	}

	
	public List<TestResult> getTestResultList() {
		// TODO Auto-generated method stub
		String sql = "from TestResult a where a.trFlag != " + CommonService.DELETE_FLAG;
		
		return testResultDAO.find(sql);
	}

	
	public List<CaseStatus> getCaseStatusList() {
		// TODO Auto-generated method stub
		String sql = "from CaseStatus a where a.csFlag != " + CommonService.DELETE_FLAG + " and a.csId != " + CommonService.DELETE_FLAG;
		
		return caseStatusDAO.find(sql);
	}

	public void setCaseStatusDAO(CaseStatusDAO caseStatusDAO) {
		this.caseStatusDAO = caseStatusDAO;
	}

	public CaseStatusDAO getCaseStatusDAO() {
		return caseStatusDAO;
	}
	
	public TestCase getTestCaseById(Integer id) {
		// TODO Auto-generated method stub
		return testCaseDAO.get(id);
	}
	
	public TestCase getTestCaseNextDisplay(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
				
		return this.getTestCaseDisplay(projectInfo, searchInfo, id, true,cvrSearchInfo,(String)args[3]);
	}
		
	private TestCase getTestCaseDisplay(Project projectInfo,TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{		
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS ;
			if(searchInfo.getModuleId() != null)
			{
				hqlStr = hqlStr + " and a.tcModuleFunction in " + functionList;
			}
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
			
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		return this.retrieveTestCase(searchInfo, hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousDisplay(Object[] args,Integer id) {
		// TODO Auto-generated method stub		
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		
		return this.getTestCaseDisplay(projectInfo, searchInfo, id, false,cvrSearchInfo,(String)args[3]);
	}
	
	private TestCase retrieveTestCase(TestCase searchInfo,String hqlStr,boolean isNext,Integer id)
	{	    	
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(1);    	
        myQuery.setPageStartNo(0);
                
        if(isNext)
        {
        	hqlStr =  hqlStr + " and a.tcId > " + id;
        	myQuery.setOrderby(" order by a.tcId");
        }
        else
        {
        	hqlStr =  hqlStr + " and a.tcId < " + id;
        	myQuery.setOrderby(" order by a.tcId desc");
        }
        
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
       
        List<TestCase>  list = testCaseDAO.find(myQuery);
        
        return list.size()==0?null:list.get(0);
	}
	
	private List<TestCase> retrieveTestCaseList(TestCase searchInfo,CaseVersionReference cvrSearchInfo,String hqlStr,int pageNum)
	{		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(searchInfo.getPageItemCount());    	
        myQuery.setPageStartNo(pageNum);
        myQuery.setOrderby(" order by a.tcId");
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
       
        return testCaseDAO.find(myQuery);        
	}

	public TestCase getTestCaseNextEdit(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
				 
		return this.getTestCaseEdit(projectInfo, searchInfo, id, true,cvrSearchInfo,(String)args[3]);
	}
	
	private TestCase getTestCaseEdit(Project projectInfo,TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS ;
			if(searchInfo.getModuleId() != null)
			{
				hqlStr = hqlStr + " and a.tcModuleFunction in " + functionList;
			}
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		 return this.retrieveTestCase(searchInfo, hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousEdit(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
				 
		 return this.getTestCaseEdit(projectInfo, searchInfo, id, false,cvrSearchInfo,(String)args[3]);
	}
	
	public TestCase getTestCaseNextTest(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
						 
		 return this.getTestCaseTest(projectInfo, searchInfo, id, true,cvrSearchInfo);
	}
	
	private TestCase getTestCaseTest(Project projectInfo,TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo)
	{		
		String hqlStr = "select a from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrProjectVersion = " + cvrSearchInfo.getCvrProjectVersion() + " and e.cvrFlag != " + CommonService.DELETE_FLAG +
					" and ( e.cvrCaseStatus = " + CaseStatus.WAIT_TEST_STATUS + " or e.cvrCaseStatus = " + CaseStatus.CORRECT_STATUS + ")";
				
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		 return this.retrieveTestCase(searchInfo, hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousTest(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
						 
		 return this.getTestCaseTest(projectInfo, searchInfo, id, false,cvrSearchInfo);
	}

	public TestCase getTestCaseNextCorrect(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		
		 return this.getTestCaseCorrect(projectInfo, searchInfo, id, true,cvrSearchInfo); 
	}
	
	private TestCase getTestCaseCorrect(Project projectInfo,TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo)
	{
		String hqlStr = "select a from TestCase a ,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS +
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG +
					" and e.cvrCaseStatus = " + CaseStatus.TESTED_STATUS + " and e.cvrCaseResult = " + TestResult.TestResult_FAILED;
				
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
			
		 return this.retrieveTestCase(searchInfo, hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousCorrect(Object[] args,Integer id) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase searchInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		
		 return this.getTestCaseCorrect(projectInfo, searchInfo, id, false,cvrSearchInfo); 
	}
	
	public List<TestCase> getAllTestCase(Object[] args) {
		// TODO Auto-generated method stub		
		TestCase searchInfo = (TestCase) args[0];
		Project projectInfo = (Project) args[1];		
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
				
		String hqlStr = null;
		
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS ;
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e ,ModuleFunction b,ProjectModule c" +
					" where a.tcModuleFunction = b.muId and b.muModule = c.pmId and c.pmProject = " + projectInfo.getPId() + " and a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcId = e.cvrTestCase and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);  
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		hqlStr = hqlStr + " order by a.tcId";
		
        return testCaseDAO.find(hqlStr);
	}


	
	public TestCorrectRecord createTestCorrectRecord(CaseVersionReference cvr) {
		// TODO Auto-generated method stub
		TestCorrectRecord rtn = new TestCorrectRecord();
		rtn.setTcrTestCase(cvr.getCvrTestCase());
		rtn.setTcrCaseStatus(cvr.getCvrCaseStatus());
		rtn.setStatus(cvr.getStatus());
		rtn.setTcrTestVersion(cvr.getCvrProjectVersion());
		rtn.setTestVersion(cvr.getProjectVersion());
				
		rtn.setCurrentOperRecord(true);
		
		return rtn;
	}

	public void setTestCorrectRecordDAO(TestCorrectRecordDAO testCorrectRecordDAO) {
		this.testCorrectRecordDAO = testCorrectRecordDAO;
	}

	public TestCorrectRecordDAO getTestCorrectRecordDAO() {
		return testCorrectRecordDAO;
	}

	
	public CaseStatus getCaseStatusById(int id) {
		// TODO Auto-generated method stub
		return caseStatusDAO.get(id);
	}

	public void setBugTypeDAO(BugTypeDAO bugTypeDAO) {
		this.bugTypeDAO = bugTypeDAO;
	}

	public BugTypeDAO getBugTypeDAO() {
		return bugTypeDAO;
	}

	
	public List<BugType> getBugTypeList() {
		// TODO Auto-generated method stub
		String sql = "from BugType a where a.btFlag != " + CommonService.DELETE_FLAG;
		
		return bugTypeDAO.find(sql);
	}

	@Override
	public TestResult getTestResultById(Integer id) {
		// TODO Auto-generated method stub
		return testResultDAO.get(id);
	}

	public CaseVersionReferenceDAO getCaseVersionReferenceDAO() {
		return caseVersionReferenceDAO;
	}

	public void setCaseVersionReferenceDAO(CaseVersionReferenceDAO caseVersionReferenceDAO) {
		this.caseVersionReferenceDAO = caseVersionReferenceDAO;
	}

	@Override
	public CaseVersionReference getCaseVersionReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return caseVersionReferenceDAO.get(id);
	}
}

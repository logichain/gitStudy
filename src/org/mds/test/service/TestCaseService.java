package org.mds.test.service;

import java.util.List;

import org.king.framework.service.Service;
import org.mds.project.bean.ProjectVersion;
import org.mds.test.bean.BugType;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.ImportantLevel;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCorrectRecord;
import org.mds.test.bean.TestResult;

public interface TestCaseService extends Service {	
	public TestCase getTestCaseById(Integer id);
	public TestCase getTestCaseNextDisplay(Object[] args,Integer id);
	public TestCase getTestCasePreviousDisplay(Object[] args,Integer id);
	public TestCase getTestCaseNextEdit(Object[] args,Integer id);
	public TestCase getTestCasePreviousEdit(Object[] args,Integer id);
	public TestCase getTestCaseNextTest(Object[] args,Integer id);
	public TestCase getTestCasePreviousTest(Object[] args,Integer id);
	public TestCase getTestCaseNextCorrect(Object[] args,Integer id);
	public TestCase getTestCasePreviousCorrect(Object[] args,Integer id);
	public TestCorrectRecord createTestCorrectRecord(CaseVersionReference cvr);
	
	public List<TestCase> searchTestCase(Object[] args);
	public Integer searchTestCaseCount(Object[] args);
	public List<TestCase> getAllTestCase(Object[] args);
	
	public void saveTestCase(TestCase testCase);
	public void saveCaseVersionReference(CaseVersionReference cvr);
		
	public List<ImportantLevel> getImportantLevelList();
	public List<TestResult> getTestResultList();
	public List<CaseStatus> getCaseStatusList();
	public List<BugType> getBugTypeList();
	
	public CaseStatus getCaseStatusById(int id);
	public TestResult getTestResultById(Integer id);
	public CaseVersionReference getCaseVersionReferenceById(Integer id);
	
	public List<TestCase> searchTestCaseByVersion(ProjectVersion projectVersion);
	public void writeTestCaseToXslFile(String filePath,List<TestCase> customerList,Integer versionId);
}

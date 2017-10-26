package org.mds.statistics.service.impl;

import org.king.framework.service.impl.BaseService;
import org.mds.common.CommonService;
import org.mds.project.bean.Project;
import org.mds.statistics.service.TestStatisticsService;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCaseDAO;
import org.mds.test.service.impl.TestCaseServiceImpl;

public class TestStatisticsServiceImpl extends BaseService implements TestStatisticsService 
{
	private TestCaseDAO testCaseDAO;
			
	public Integer searchTestCaseCount(Object[] args) {
		// TODO Auto-generated method stub
		Project projectInfo = (Project) args[0];		
		TestCase caseInfo = (TestCase) args[1];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		   		
		String functionList = (String) args[3];//有可能是“（）”
	
		
		String hqlStr = null;
		
		if(cvrSearchInfo.isSearchInfoEmpty())
		{			
			hqlStr = "select count(distinct a) from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + " and a.tcModuleFunction in " + functionList;			
		}
		else
		{
			hqlStr = "select count(distinct a) from TestCase a,CaseVersionReference e where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList +
				" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;			
		}

    	hqlStr = TestCaseServiceImpl.processQuerySql(caseInfo, hqlStr);
    	hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
    	
        return testCaseDAO.getFindCount(hqlStr);		
	}
	

	public TestCaseDAO getTestCaseDAO() {
		return testCaseDAO;
	}

	public void setTestCaseDAO(TestCaseDAO testCaseDAO) {
		this.testCaseDAO = testCaseDAO;
	}

}

package org.mds.statistics.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.king.framework.web.action.BaseAction;
import org.king.security.domain.Department;
import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.bean.TeamMember;
import org.mds.project.service.ProjectService;
import org.mds.project.service.impl.ProjectServiceImpl;
import org.mds.statistics.bean.StatisticsData;
import org.mds.statistics.service.TestStatisticsService;
import org.mds.statistics.svg.CakySvg;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestResult;
import org.mds.test.service.TestCaseService;

public class TestStatisticsAction extends BaseAction {
	private TestStatisticsService testStatisticsService;
	private TestCaseService testCaseService;
	private ProjectService projectService;

	
	synchronized public ActionForward resetDataStatistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		String pid = request.getParameter("pid");
		
		if(pid != null && !"".equals(pid))
		{
			projectInfo = projectService.getProjectById(Integer.valueOf(pid));
			dform.set("projectInfo", projectInfo);
		}
	
		TestCase caseInfo = new TestCase();
		caseInfo.setProjectModule(new ProjectModule());
		dform.set("caseInfo",caseInfo);
		dform.set("cvrSearchInfo",new CaseVersionReference());
						
		return this.dataStatistics(mapping, dform, request, response);
	}
	
	public ActionForward projectDataStatistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		List<Project> pList = projectService.getProjectList();
		TestCase caseInfo = new TestCase();
		CaseVersionReference cvrSearchInfo = new CaseVersionReference();
				
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allDesignCount = 0;
		Integer allTestCount = 0;
		Integer allUnpassCount = 0;
		Integer allNACount = 0;
		Integer allCloseCount = 0;
		Integer allWaitTestCount = 0;
		
		for(Project p:pList)
		{				
			String functionList = ProjectServiceImpl.getApplicaleFunctionList(p, caseInfo);		
								
			if(functionList.length() > 2)
			{
				Integer designCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(designCaseCount >= 0)
				{
					countList.add(designCaseCount);
					dataInfoList.add(p.getPName() +":" + designCaseCount);				
					allDesignCount = allDesignCount+ designCaseCount;
				}
				
				cvrSearchInfo.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
				Integer waitTestCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(waitTestCaseCount >= 0)
				{							
					allWaitTestCount = allWaitTestCount+ waitTestCaseCount;
				}
				cvrSearchInfo.setCvrCaseStatus(null);
				
				cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
				Integer testCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(testCaseCount >= 0)
				{							
					allTestCount = allTestCount+ testCaseCount;
				}
				cvrSearchInfo.setCvrCaseStatus(null);
				
				
				cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
				cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_FAILED);
				Integer unpassCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(unpassCaseCount >= 0)
				{							
					allUnpassCount = allUnpassCount+ unpassCaseCount;
				}			
				cvrSearchInfo.setCvrCaseStatus(null);
				cvrSearchInfo.setCvrCaseResult(null);
				
				
				cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
				cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_NA);			
				Integer NACaseCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(NACaseCount >= 0)
				{							
					allNACount = allNACount+ NACaseCount;
				}
				cvrSearchInfo.setCvrCaseStatus(null);
				cvrSearchInfo.setCvrCaseResult(null);
				
				cvrSearchInfo.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
				Integer closeCount = testStatisticsService.searchTestCaseCount(new Object[]{p,caseInfo,cvrSearchInfo,functionList});
				if(closeCount >= 0)
				{							
					allCloseCount = allCloseCount+ closeCount;
				}
				cvrSearchInfo.setCvrCaseStatus(null);
				
				statisticsDataList.add(new StatisticsData(p.getPName(),designCaseCount,testCaseCount,unpassCaseCount,NACaseCount,closeCount,waitTestCaseCount));
			}
			else
			{
				statisticsDataList.add(new StatisticsData(p.getPName(),0,0,0,0,0,0));
			}			
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("projectSvgStr", svgStr);		
		
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allNACount,allCloseCount,allWaitTestCount));
		request.setAttribute("projectDataList", statisticsDataList);
		
		return mapping.findForward("projectStatistics");
	}
		
	synchronized public ActionForward dataStatistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, caseInfo);	
				
		if(projectInfo.isTeamMember(ua) || ua.getId().equals(1))
		{
			if(functionList.length() > 2)
			{
				this.versionStatistics(dform, request);
				this.moduleStatistics(dform, request);
				this.userStatistics(dform, request);
			}
			else
			{
				
			}						
		}		
				
		return mapping.findForward("statistics");
	}
	
	
	private void versionStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		String pid = request.getParameter("pid");
		
		if(pid != null && !"".equals(pid))
		{
			projectInfo = projectService.getProjectById(Integer.valueOf(pid));
			dform.set("projectInfo", projectInfo);
		}
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		Integer oldProjectVersion = cvrSearchInfo.getCvrProjectVersion();
		
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allDesignCount = 0;		
		Integer allTestCount = 0;
		Integer allUnpassCount = 0;
		Integer allNACount = 0;
		Integer allCloseCount = 0;
		Integer allWaitTestCount = 0;
		
		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, caseInfo);		
		
		for(ProjectVersion pv:projectInfo.getProjectVersionList())
		{
			cvrSearchInfo.setCvrProjectVersion(pv.getPvId());			
			Integer designCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(designCaseCount >= 0)
			{
				countList.add(designCaseCount);
				dataInfoList.add(pv.getPvVersion() +":" + designCaseCount);				
				allDesignCount = allDesignCount+ designCaseCount;
			}
						
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
			Integer waitTestCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(waitTestCaseCount >= 0)
			{							
				allWaitTestCount = allWaitTestCount+ waitTestCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			Integer testCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(testCaseCount >= 0)
			{							
				allTestCount = allTestCount+ testCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_FAILED);
			Integer unpassCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(unpassCaseCount >= 0)
			{							
				allUnpassCount = allUnpassCount+ unpassCaseCount;
			}			
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_NA);			
			Integer NACaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(NACaseCount >= 0)
			{							
				allNACount = allNACount+ NACaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
			Integer closeCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(closeCount >= 0)
			{							
				allCloseCount = allCloseCount+ closeCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			statisticsDataList.add(new StatisticsData(pv.getPvVersion(),designCaseCount,testCaseCount,unpassCaseCount,NACaseCount,closeCount,waitTestCaseCount));
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("versionSvgStr", svgStr);		
		cvrSearchInfo.setCvrProjectVersion(oldProjectVersion);
		
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allNACount,allCloseCount,allWaitTestCount));
		request.setAttribute("versionDataList", statisticsDataList);
	}
	
	private void moduleStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
	
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		ProjectModule oldModule = caseInfo.getProjectModule();
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allDesignCount = 0;
		Integer allTestCount = 0;
		Integer allUnpassCount = 0;
		Integer allNACount = 0;
		Integer allCloseCount = 0;
		Integer allWaitTestCount = 0;
				
		for(ProjectModule pm:projectInfo.getModuleList())
		{
			String functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
						
			Integer designCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(designCaseCount > 0)
			{
				countList.add(designCaseCount);
				dataInfoList.add(pm.getPmName() +":" + designCaseCount);
				allDesignCount = allDesignCount+ designCaseCount;
			}
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
			Integer waitTestCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(waitTestCaseCount >= 0)
			{							
				allWaitTestCount = allWaitTestCount+ waitTestCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			Integer testCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(testCaseCount >= 0)
			{							
				allTestCount = allTestCount+ testCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
									
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_FAILED);
			Integer unpassCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(unpassCaseCount >= 0)
			{							
				allUnpassCount = allUnpassCount+ unpassCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_NA);		
			Integer NACaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(NACaseCount >= 0)
			{							
				allNACount = allNACount+ NACaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
			Integer closeCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(closeCount >= 0)
			{							
				allCloseCount = allCloseCount+ closeCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			statisticsDataList.add(new StatisticsData(pm.getPmName(),designCaseCount,testCaseCount,unpassCaseCount,NACaseCount,closeCount,waitTestCaseCount));
			
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("moduleSvgStr", svgStr);		
		caseInfo.setProjectModule(oldModule);
		
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allNACount,allCloseCount,allWaitTestCount));
		
		request.setAttribute("moduleDataList", statisticsDataList);
				
	}
	
	
	private void userStatistics( ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
	
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allDesignCount = 0;
		Integer allTestCount = 0;
		Integer allUnpassCount = 0;
		Integer allNACount = 0;
		Integer allCloseCount = 0;
		Integer allWaitTestCount = 0;
		
		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, caseInfo);	
				
		ArrayList<TeamMember> tmList = projectInfo.getMemberList();		
		for(TeamMember tm:tmList)
		{
			if(tm.getTmFlag().equals(CommonService.DELETE_FLAG) || tm.getAccount().getDept().equals(Department.DEPART_DEVELOP))
			{
				continue;
			}
			
			caseInfo.setTcCreateUser(tm.getTmAccount());
			Integer designCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(designCaseCount > 0)
			{
				countList.add(designCaseCount);
				dataInfoList.add(tm.getAccount().getPersonName() +":" + designCaseCount);				
				allDesignCount = allDesignCount+ designCaseCount;
			}			
			caseInfo.setTcCreateUser(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
			Integer waitTestCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(waitTestCaseCount >= 0)
			{							
				allWaitTestCount = allWaitTestCount+ waitTestCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrTestUser(tm.getTmAccount());
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			Integer testCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(testCaseCount > 0)
			{							
				allTestCount = allTestCount+ testCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
						
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_FAILED);
			Integer unpassCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(unpassCaseCount >= 0)
			{							
				allUnpassCount = allUnpassCount+ unpassCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_NA);			
			Integer NACaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(NACaseCount >= 0)
			{							
				allNACount = allNACount+ NACaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
			Integer closeCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(closeCount >= 0)
			{							
				allCloseCount = allCloseCount+ closeCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrTestUser(null);
			
			statisticsDataList.add(new StatisticsData(tm.getAccount().getPersonName(),designCaseCount,testCaseCount,unpassCaseCount,NACaseCount,closeCount,waitTestCaseCount));
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("userSvgStr", svgStr);
				
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allNACount,allCloseCount,allWaitTestCount));
		
		request.setAttribute("userDataList", statisticsDataList);
				
	}
		
	public void setTestStatisticsService(TestStatisticsService testStatisticsService) {
		this.testStatisticsService = testStatisticsService;
	}
	public TestStatisticsService getTestStatisticsService() {
		return testStatisticsService;
	}
	public void setTestCaseService(TestCaseService testCaseService) {
		this.testCaseService = testCaseService;
	}
	public TestCaseService getTestCaseService() {
		return testCaseService;
	}
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}

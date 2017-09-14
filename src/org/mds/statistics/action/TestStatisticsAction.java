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
import org.mds.project.bean.ModuleFunction;
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
	
		dform.set("caseInfo",new TestCase());
		dform.set("cvrSearchInfo",new CaseVersionReference());
						
		return this.dataStatistics(mapping, dform, request, response);
	}
		
	synchronized public ActionForward dataStatistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
				
		if(projectInfo.isTeamMember(ua) || ua.getId().equals(1))
		{
			this.versionStatistics(dform, request);
			this.moduleStatistics(dform, request);
			this.functionStatistics(dform, request);
			this.userStatistics(dform, request);			
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
		Integer allCorrectCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}		
		
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
			
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);			
			Integer correctCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(correctCaseCount >= 0)
			{							
				allCorrectCount = allCorrectCount+ correctCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			statisticsDataList.add(new StatisticsData(pv.getPvVersion(),designCaseCount,testCaseCount,unpassCaseCount,correctCaseCount));
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
		
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allCorrectCount));
		request.setAttribute("versionDataList", statisticsDataList);
	}
	
	private void moduleStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
	
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		Integer oldModule = caseInfo.getModuleId();
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allDesignCount = 0;
		Integer allTestCount = 0;
		Integer allUnpassCount = 0;
		Integer allCorrectCount = 0;
				
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
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);			
			Integer correctCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(correctCaseCount >= 0)
			{							
				allCorrectCount = allCorrectCount+ correctCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);	
			
			statisticsDataList.add(new StatisticsData(pm.getPmName(),designCaseCount,testCaseCount,unpassCaseCount,correctCaseCount));
			
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("moduleSvgStr", svgStr);		
		caseInfo.setModuleId(oldModule);
		
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allCorrectCount));
		
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
		Integer allCorrectCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
				
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
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);			
			Integer correctCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(correctCaseCount >= 0)
			{							
				allCorrectCount = allCorrectCount+ correctCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrTestUser(null);
			
			statisticsDataList.add(new StatisticsData(tm.getAccount().getPersonName(),designCaseCount,testCaseCount,unpassCaseCount,correctCaseCount));
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
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allCorrectCount));
		
		request.setAttribute("userDataList", statisticsDataList);
				
	}
	
	private void functionStatistics(ActionForm form,HttpServletRequest request)
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
		Integer allCorrectCount = 0;
		
		ArrayList<ModuleFunction> mfList = null;
		if(caseInfo.getModuleId() == null)
		{
			mfList = projectInfo.getAllModuleFunctionList();
		}
		else
		{
			mfList = projectService.getProjectModuleById(caseInfo.getModuleId()).getAllModuleFunctionList();
		}
								
		for(ModuleFunction mf:mfList)
		{					
			caseInfo.setTcModuleFunction(mf.getMuId());
						
			Integer designCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo});
			if(designCaseCount > 0)
			{
				countList.add(designCaseCount);
				dataInfoList.add(mf.getEntireName() +":" + designCaseCount);
				allDesignCount = allDesignCount+ designCaseCount;
			}
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			Integer testCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo});
			if(testCaseCount >= 0)
			{							
				allTestCount = allTestCount+ testCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
			cvrSearchInfo.setCvrCaseResult(TestResult.TestResult_FAILED);
			Integer unpassCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo});
			if(unpassCaseCount >= 0)
			{							
				allUnpassCount = allUnpassCount+ unpassCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);
			cvrSearchInfo.setCvrCaseResult(null);
			
			cvrSearchInfo.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);			
			Integer correctCaseCount = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo});
			if(correctCaseCount >= 0)
			{							
				allCorrectCount = allCorrectCount+ correctCaseCount;
			}
			cvrSearchInfo.setCvrCaseStatus(null);	
			
			statisticsDataList.add(new StatisticsData(mf.getEntireName(),designCaseCount,testCaseCount,unpassCaseCount,correctCaseCount));
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("functionSvgStr", svgStr);
		caseInfo.setTcModuleFunction(null);
		
		if(allDesignCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getDesignCaseCount()*1.0/allDesignCount);
			}			
		}
		statisticsDataList.add(new StatisticsData("All",allDesignCount,allTestCount,allUnpassCount,allCorrectCount));
		
		request.setAttribute("functionDataList", statisticsDataList);				
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

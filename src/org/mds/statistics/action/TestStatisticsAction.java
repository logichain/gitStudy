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
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.service.ProjectService;
import org.mds.project.service.impl.ProjectServiceImpl;
import org.mds.statistics.bean.StatisticsData;
import org.mds.statistics.service.TestStatisticsService;
import org.mds.statistics.svg.CakySvg;
import org.mds.test.bean.BugType;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.ImportantLevel;
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
		dform.set("caseInfo",caseInfo);
						
		return this.dataStatistics(mapping, dform, request, response);
	}
		
	synchronized public ActionForward dataStatistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
				
		this.versionStatistics(dform, request);
		this.bugTypeStatistics(dform, request);
		this.functionStatistics(dform, request);
		this.importLevelStatistics(dform, request);
		this.moduleStatistics(dform, request);
		this.resultStatistics(dform, request);
		this.statusStatistics(dform, request);
				
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
		Integer allCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}		
		
		for(ProjectVersion pv:projectInfo.getProjectVersionList())
		{
			cvrSearchInfo.setCvrProjectVersion(pv.getPvId());			
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(pv.getPvVersion() +":" + count);
			statisticsDataList.add(new StatisticsData(pv.getPvVersion(),count));
			allCount = allCount+ count;
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("versionSvgStr", svgStr);		
		cvrSearchInfo.setCvrProjectVersion(oldProjectVersion);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
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
		Integer allCount = 0;
				
		for(ProjectModule pm:projectInfo.getModuleList())
		{
			String functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);			
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(pm.getPmName() +":" + count);
			statisticsDataList.add(new StatisticsData(pm.getPmName(),count));
			allCount = allCount+ count;
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("moduleSvgStr", svgStr);		
		caseInfo.setModuleId(oldModule);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		request.setAttribute("moduleDataList", statisticsDataList);
				
	}
	
	private void statusStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");

		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
				
		List<CaseStatus> statusList = testCaseService.getCaseStatusList();
		for(CaseStatus s:statusList)
		{
			cvrSearchInfo.setCvrCaseStatus(s.getCsId());
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(s.getCsName() +":" + count);
			statisticsDataList.add(new StatisticsData(s.getCsName(),count));
			allCount = allCount+ count;
		}
		
		double[]  dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("statusSvgStr", svgStr);		
		cvrSearchInfo.setCvrCaseStatus(null);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		request.setAttribute("statusDataList", statisticsDataList);
				
	}
	
	private void resultStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");

		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
				
		List<TestResult> resultList = testCaseService.getTestResultList();		
		for(TestResult s:resultList)
		{
			cvrSearchInfo.setCvrCaseResult(s.getTrId());
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(s.getTrName() +":" + count);
			statisticsDataList.add(new StatisticsData(s.getTrName(),count));
			allCount = allCount+ count;
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("resultSvgStr", svgStr);		
		cvrSearchInfo.setCvrCaseResult(null);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		request.setAttribute("resultDataList", statisticsDataList);
				
	}
	
	private void importLevelStatistics(ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");

		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
				
		List<ImportantLevel> levelList = testCaseService.getImportantLevelList();		
		for(ImportantLevel s:levelList)
		{
			cvrSearchInfo.setCvrImportantLevel(s.getIlId());
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(s.getIlName() +":" + count);
			statisticsDataList.add(new StatisticsData(s.getIlName(),count));
			allCount = allCount+ count;
		}

		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("importLevelSvgStr", svgStr);		
		cvrSearchInfo.setCvrImportantLevel(null);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		request.setAttribute("importLevelDataList", statisticsDataList);
				
	}
	
	private void bugTypeStatistics( ActionForm form,HttpServletRequest request)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
	
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		List<Integer> countList = new ArrayList<Integer>();
		List<String> dataInfoList = new ArrayList<String>();
		List<StatisticsData> statisticsDataList = new ArrayList<StatisticsData>();
		Integer allCount = 0;
		
		String functionList = "";
		if(caseInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(caseInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
				
		List<BugType> typeList = testCaseService.getBugTypeList();		
		for(BugType s:typeList)
		{
			cvrSearchInfo.setCvrBugType(s.getBtId());
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo,functionList});
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(s.getBtName() +":" + count);
			statisticsDataList.add(new StatisticsData(s.getBtName(),count));
			allCount = allCount+ count;
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("bugTypeSvgStr", svgStr);
		cvrSearchInfo.setCvrBugType(null);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		
		request.setAttribute("bugTypeDataList", statisticsDataList);
				
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
		Integer allCount = 0;
		
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
			Integer count = testStatisticsService.searchTestCaseCount(new Object[]{projectInfo,caseInfo,cvrSearchInfo});
			
			if(count <= 0)
			{
				continue;
			}
			countList.add(count);
			dataInfoList.add(mf.getEntireName() +":" + count);
			statisticsDataList.add(new StatisticsData(mf.getEntireName(),count));
			allCount = allCount+ count;
		}
		
		double[] dataPercent = CakySvg.getPercent(countList);
		String svgStr = CakySvg.initialize(dataPercent,dataInfoList);
		request.setAttribute("functionSvgStr", svgStr);
		caseInfo.setTcModuleFunction(null);
		
		if(allCount != 0)
		{
			for(StatisticsData d:statisticsDataList)
			{
				d.setPercent(d.getCount()*1.0/allCount);
			}
			statisticsDataList.add(new StatisticsData("All",allCount,1));
		}
		
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

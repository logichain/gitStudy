package org.mds.test.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;
import org.king.framework.web.action.BaseAction;
import org.king.security.domain.UsrAccount;
import org.mds.common.CommonService;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.service.ProjectService;
import org.mds.project.service.impl.ProjectServiceImpl;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCorrectRecord;
import org.mds.test.service.TestCaseService;

public class TestCaseManage extends BaseAction {
	private TestCaseService testCaseService;
	private ProjectService projectService;
	
	
	public ActionForward exportTestCaseByVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		Integer pvId = Integer.parseInt(request.getParameter("pvId"));
		
		ProjectVersion projectVersion = projectService.getProjectVersionById(pvId);
				
		String uploadPath = request.getSession().getServletContext().getRealPath("");
		if (!uploadPath.endsWith("\\")) {
			uploadPath = uploadPath + "\\uploadImportFile\\";
		}
		
		//测试临时
		uploadPath = "d:\\";
		
		List<TestCase> customerList = testCaseService.searchTestCaseByVersion(projectVersion);	
		
		String fileName = uploadPath + "exportCase.xls";
		testCaseService.writeTestCaseToXslFile(fileName,customerList,pvId);
				
		try {
			response.setContentType(org.king.util.FileUtil.getContentType(fileName));
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName.substring(fileName.lastIndexOf("\\")+1));
			InputStream file = new FileInputStream(fileName);
			byte[] bit = new byte[1024];
			int len = file.read(bit);
			OutputStream out = response.getOutputStream();

			while (len != -1) {
				out.write(bit, 0, len);
				len = file.read(bit);
			}
			out.close();
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.download"));
			saveErrors(request, errors);	
			
			return mapping.findForward("fail");
		}    
	
		return null;
	}
	
	public ActionForward createTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		TestCase testCase = new TestCase();
		Date createTime = new Date();
		testCase.setTcFlag(CommonService.NORMAL_FLAG);
				
		testCase.setCreateUser(ua);
		testCase.setTcCreateUser(ua.getId());
		testCase.setTcCreateTime(createTime);	
		//新建用例记录
		TestCorrectRecord tcr = new TestCorrectRecord();
		tcr.setTcrTestCase(testCase.getTcId());
		tcr.setTcrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
		tcr.setCurrentOperRecord(true);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(createTime);		
		tcr.setOperUser(ua);
		testCase.getTestCorrectRecordList().add(tcr);
		
		dform.set("caseInfo", testCase);
		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseInput");
	}
	
	public ActionForward editTestCase(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		
		//返回列表后，仍保留原页码
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		this.savePagerOffsetFromRequest(request, searchInfo);		
		
		String id = request.getParameter("id");
		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		
		this.editOperProcess(request, testCase, projectInfo);
		
		dform.set("caseInfo", testCase);	
		
		return mapping.findForward("caseEdit");
	}
	
	public ActionForward editTestCasePrevious(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
				
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};		
		
		TestCase testCase = testCaseService.getTestCasePreviousEdit(args,caseInfo.getTcId());
		if(testCase != null)
		{
			this.editOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo",testCase);
		}	
		else
		{
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}
				
		return mapping.findForward("caseEdit");
	}
	
	public ActionForward editTestCaseNext(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
						
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCaseNextEdit(args,caseInfo.getTcId());
		if(testCase != null)
		{
			this.editOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo",testCase);
		}
		else
		{
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}
				
		return mapping.findForward("caseEdit");
	}
	
	private String getApplicaleFunctionList(Project projectInfo,TestCase searchInfo)
	{
		String functionList = "";
		if(searchInfo.getModuleId() != null)
		{
			ProjectModule pm = projectService.getProjectModuleById(searchInfo.getModuleId());
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(pm);
		}
		else
		{
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(projectInfo.getAllModuleFunctionList());
		}
				
		return functionList;
	}
	
	/*
	 * 当前适用版本
	 */
	private void initApplyProjectVersionInfo(TestCase testCase,Project projectInfo)
	{
		for(ProjectVersion vtr:projectInfo.getProjectVersionList())
		{
			if(testCase.isApplyProjectVersion(vtr) != null)
			{
				vtr.setSelected(true);
			}
			else
			{
				vtr.setSelected(false);
			}
		}
	}
	
	private void editOperProcess(HttpServletRequest request,TestCase testCase,Project projectInfo)
	{
		testCase.setModuleId(testCase.getModuleFunction().getMuModule());
		
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
//		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
//		
//		if(cvr.getCvrCaseStatus() .equals(CaseStatus.CORRECT_STATUS) || cvr.getCvrCaseStatus().equals(CaseStatus.TESTED_STATUS) )
//		{
//			UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
//			Date createTime = new Date();
//			
//			TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
//			tcr.setTcrOperUser(ua.getId());
//			tcr.setTcrOperTime(createTime);		
//			tcr.setOperUser(ua);
//			
//			testCase.getTestCorrectRecordList().add(tcr);
//		}
		
		this.prepareMetaData(request);
	}
	
	private void savePagerOffsetFromRequest(HttpServletRequest request,TestCase searchInfo )
	{		
		String offset = request.getParameter("pager.offset");
		if(offset != null && !"".equals(offset))
		{
			searchInfo.setOffset(Integer.parseInt(offset));
		}		
	}
	
	public ActionForward displayTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		
		String id = request.getParameter("id");
		
		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
		request.setAttribute("caseInfo", testCase);
				
		return mapping.findForward("caseDisplay");
	}
	
	public ActionForward displayTestCaseNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		String id = request.getParameter("id");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCaseNextDisplay(args,Integer.valueOf(id));
		if(testCase == null)
		{
			testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		}
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
		request.setAttribute("caseInfo", testCase);
				
		return mapping.findForward("caseDisplay");
	}
	
	public ActionForward displayTestCasePrevious(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		String id = request.getParameter("id");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCasePreviousDisplay(args,Integer.valueOf(id));
		if(testCase == null)
		{
			testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		}
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
		request.setAttribute("caseInfo", testCase);
				
		return mapping.findForward("caseDisplay");
	}
	
	public ActionForward testTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		
		this.savePagerOffsetFromRequest(request, searchInfo);
		
		String id = request.getParameter("id");
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());
		
		this.testOperProcess(request, testCase,projectInfo);
		
		dform.set("caseInfo", testCase);
				
		return mapping.findForward("caseTest");
	}
	
	public ActionForward testTestCaseNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(caseInfo);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCaseNextTest(args,caseInfo.getTcId());
		if(testCase != null)		
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}		
				
		return mapping.findForward("caseTest");
	}
	
	public ActionForward testTestCasePrevious(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(caseInfo);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCasePreviousTest(args,caseInfo.getTcId());
		if(testCase != null)		
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}		
				
		return mapping.findForward("caseTest");
	}
	
	private void testOperProcess(HttpServletRequest request,TestCase testCase,Project projectInfo)
	{
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
		Date testTime = new Date();
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
				
		cvr.setCvrTestUser(ua.getId());
		cvr.setCvrTestTime(testTime);
		cvr.setTestUser(ua);
		cvr.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.TESTED_STATUS));
		
		//测试用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setOperUser(ua);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(testTime);		
		testCase.getTestCorrectRecordList().add(tcr);
		
		this.prepareMetaData(request);
	}
	
	public ActionForward correctTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		
		this.savePagerOffsetFromRequest(request, searchInfo);
				
		String id = request.getParameter("id");
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());
		
		this.correctOperProcess(request, testCase,projectInfo);
		
		dform.set("caseInfo", testCase);	
		
		return mapping.findForward("caseCorrect");
	}
	
	public ActionForward correctTestCasePrevious(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(caseInfo);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCasePreviousCorrect(args,caseInfo.getTcId());
		if(testCase != null)
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}
				
		return mapping.findForward("caseCorrect");
	}
	
	public ActionForward correctTestCaseNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(caseInfo);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		Object[] args = {projectInfo,searchInfo,cvrSearchInfo,functionList};
		
		TestCase testCase = testCaseService.getTestCaseNextCorrect(args,caseInfo.getTcId());
		if(testCase != null)
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}
								
		return mapping.findForward("caseCorrect");
	}
	
	private void correctOperProcess(HttpServletRequest request,TestCase testCase,Project projectInfo)
	{
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		Date correctTime = new Date();
		
		this.initApplyProjectVersionInfo(testCase, projectInfo);
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
		
		cvr.setCvrCorrectUser(ua.getId());
		cvr.setCvrCorrectTime(correctTime);
		cvr.setCorrectUser(ua);
		cvr.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.CORRECT_STATUS));
		
		//修正用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setOperUser(ua);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(correctTime);		
		testCase.getTestCorrectRecordList().add(tcr);
		
		this.prepareMetaData(request);
	}
		
	public ActionForward closeTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
				
		Date closeTime = new Date();
		
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase,cvr.getCvrId());
		
		cvr.setCvrCloseUser(ua.getId());
		cvr.setCvrCloseTime(closeTime);
		cvr.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.CLOSE_STATUS));
		
		//关闭用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(closeTime);		
		testCase.getTestCorrectRecordList().add(tcr);
		
		testCaseService.saveTestCase(testCase);
		
		return this.searchTestCase(mapping, dform, request, response);
	}
	
	
	public ActionForward openTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
				
		Date closeTime = new Date();
		
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase,cvr.getCvrId());
		
		cvr.setCvrCloseUser(ua.getId());
		cvr.setCvrCloseTime(closeTime);
		cvr.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.TESTED_STATUS));
		
		//关闭用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(closeTime);				
		testCase.getTestCorrectRecordList().add(tcr);
		
		testCaseService.saveTestCase(testCase);
		
		return this.searchTestCase(mapping, dform, request, response);
	}
	
	public ActionForward deleteTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		testCase.setTcFlag(CaseStatus.DELETE_STATUS);
		
		//删除用例记录
		TestCorrectRecord tcr = new TestCorrectRecord();
		tcr.setTcrTestCase(testCase.getTcId());
		tcr.setTcrCaseStatus(CaseStatus.DELETE_STATUS);
				
		tcr.setCurrentOperRecord(true);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(new Date());		
		testCase.getTestCorrectRecordList().add(tcr);
		
		testCaseService.saveTestCase(testCase);
		
		return this.searchTestCase(mapping, dform, request, response);
	}
	
	private void prepareMetaData(HttpServletRequest request)
	{
		request.setAttribute("importantLevelList", testCaseService.getImportantLevelList());
		request.setAttribute("testResultList", testCaseService.getTestResultList());	
		request.setAttribute("caseStatusList", testCaseService.getCaseStatusList());
		request.setAttribute("bugTypeList", testCaseService.getBugTypeList());		
	}
	
	public ActionForward setCaseModuleForInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
				
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		Project projectInfo = (Project) dform.get("projectInfo");
		
		for(ProjectModule pm:projectInfo.getModuleList())
		{
			if(pm.getPmId().equals(caseInfo.getModuleId()))
			{
				projectInfo.setSelectedProjectModule(pm);
				break;
			}
		}		
		
		this.prepareMetaData(request);
						
		return mapping.findForward("caseInput");
	}
		
	public ActionForward saveTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase testCase = (TestCase) dform.get("caseInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
				
		
		for(ProjectVersion version:projectInfo.getProjectVersionList())
		{
			CaseVersionReference cvr = testCase.isApplyProjectVersion(version);
			if(version.isSelected())
			{
				if(cvr == null)
				{
					CaseVersionReference ccvr = new CaseVersionReference();
					ccvr.setCvrFlag(CommonService.NORMAL_FLAG);
					ccvr.setCvrTestCase(testCase.getTcId());
					ccvr.setCvrProjectVersion(version.getPvId());
					ccvr.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
					
					testCase.getCaseVersionReferenceList().add(ccvr);
				}				
			}
			else
			{
				if(cvr  != null)
				{
					cvr.setCvrFlag(CommonService.DELETE_FLAG);				
				}
			}			
		}
		
		testCaseService.saveTestCase(testCase);
						
		for(ModuleFunction mu:projectInfo.getAllModuleFunctionList())
		{
			if(mu.getMuId().equals(testCase.getTcModuleFunction()))
			{
				testCase.setModuleFunction(mu);
				break;
			}
		}
		
		
		return this.redirectToTestCaseListPage(request, response, searchInfo);		
	}
	
	private ActionForward redirectToTestCaseListPage(HttpServletRequest request, HttpServletResponse response,TestCase searchInfo)
	{
		try {
			String url = request.getRequestURL().toString();
			url = url + "?method=searchTestCase&pager.offset=" + searchInfo.getOffset();
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;		
	}
	
	public ActionForward saveTestCaseNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
				
		testCaseService.saveTestCase(testCase);
				
		return this.createTestCase(mapping, dform, request, response);
	}
	
	public ActionForward saveTestCaseCopy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
				
		testCaseService.saveTestCase(testCase);
				
		dform.set("caseInfo", testCase.copy());
		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseInput");
	}
	
	public ActionForward saveTestCaseEditNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		
		Object[] args = {searchInfo,null,projectInfo};
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
		testCaseService.saveTestCase(testCase);
						
		testCase = testCaseService.getTestCaseNextEdit(args,testCase.getTcId());
		if(testCase == null)
		{
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}
				
		testCase.setModuleId(testCase.getModuleFunction().getMuModule());
		
		dform.set("caseInfo", testCase);
		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseEdit");
	}
	
	private CaseVersionReference getCurrentCaseVersionReference(TestCase testCase)
	{
		CaseVersionReference cvr = null;
		for(CaseVersionReference c:testCase.getCaseVersionReferenceList())
		{
			if(c.isCurrentReference())
			{
				cvr = c;
				break;
			}
		}
		
		return cvr;
	}
	
	private void setCurrentCaseVersionReferenceByCvrId(TestCase testCase,Integer id)
	{
		for(CaseVersionReference c:testCase.getCaseVersionReferenceList())
		{
			if(c.getCvrId().equals(id))
			{
				c.setCurrentReference(true);
				break;
			}
		}
	}
	
	private void setCurrentCaseVersionReferenceByVersionId(TestCase testCase,Integer id)
	{
		for(CaseVersionReference c:testCase.getCaseVersionReferenceList())
		{
			if(c.getCvrProjectVersion().equals(id))
			{
				c.setCurrentReference(true);
				break;
			}
		}
	}
	
	public ActionForward saveTestCaseTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
				
		TestCase testCase = (TestCase) dform.get("caseInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
						
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
				
		for(TestCorrectRecord tcr:testCase.getTestCorrectRecordList())
		{
			if(tcr.isCurrentOperRecord())
			{
				tcr.setTcrTestResult(cvr.getCvrCaseResult());
			}
		}
		
		testCaseService.saveTestCase(testCase);
						
		return this.redirectToTestCaseListPage(request, response, searchInfo);	
	}
	
	public ActionForward saveTestCaseTestNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		Object[] args = {searchInfo,null,projectInfo};
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
		testCaseService.saveTestCase(testCase);
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
				
		testCase = testCaseService.getTestCaseNextTest(args,testCase.getTcId());
		if(testCase != null)
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase,projectInfo);
			
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}
						
		return mapping.findForward("caseTest");
	}
		
	
	public ActionForward saveTestCaseCorrect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
				
		TestCase testCase = (TestCase) dform.get("caseInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		
		testCaseService.saveTestCase(testCase);
				
		return this.redirectToTestCaseListPage(request, response, searchInfo);
	}
	
	public ActionForward saveTestCaseCorrectNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
				
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase testCase = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		CaseVersionReference cvr = this.getCurrentCaseVersionReference(testCase);
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());
		
		Object[] args = {searchInfo,null,projectInfo};		
		
		testCaseService.saveTestCase(testCase);				
		testCase = testCaseService.getTestCaseNextCorrect(args,testCase.getTcId());
		
		if(testCase != null)
		{
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase,projectInfo);
			dform.set("caseInfo", testCase);
		}
		else
		{
			cvrSearchInfo.setCvrProjectVersion(null);
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}
						
		return mapping.findForward("caseCorrect");
	}
	
	public ActionForward resetSearchTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		String pid = request.getParameter("pid");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		if(projectInfo.getPId() == null && pid == null)
		{	
			return mapping.findForward("first");			
		}
		
		if(pid != null && !"".equals(pid))
		{
			projectInfo = projectService.getProjectById(Integer.valueOf(pid));
			dform.set("projectInfo", projectInfo);
		}
		
		TestCase searchInfo = new TestCase();										
		String page = request.getParameter("pager.offset");
		if(page == null)
		{
			page = "0";
		}
				
		dform.set("searchInfo", searchInfo);
		CaseVersionReference cvrSearchInfo = new CaseVersionReference();
		dform.set("cvrSearchInfo", cvrSearchInfo);
		
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		if((projectInfo.isTeamMember(ua) || ua.getId().equals(1)) && functionList.length() > 2)
		{
			Object[] args = {searchInfo,page,projectInfo,cvrSearchInfo,functionList};
			
			List<TestCase> caseList = testCaseService.searchTestCase(args);
			Integer caseCount = testCaseService.searchTestCaseCount(args);
			
			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		}
		else
		{
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}
				
		this.prepareMetaData(request);
		
		return this.getForwardByUser(mapping, dform, request, response);
	}
	
	private ActionForward getForwardByUser(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		if(ua.getDepartment().getDFlag().equals(1))
		{
			return mapping.findForward("caseListForTest");
		}
		
		return mapping.findForward("caseListForCorrect");
	}
	
	public ActionForward searchTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		String page = request.getParameter("pager.offset");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)dform.get("cvrSearchInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		
		if(functionList.length() > 2)
		{
			Object[] args = {searchInfo,page,projectInfo,cvrSearchInfo,functionList};			
			
			List<TestCase> caseList = testCaseService.searchTestCase(args);
			Integer caseCount = testCaseService.searchTestCaseCount(args);
			
			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		}	
		else
		{
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}
		
		this.prepareMetaData(request);
		
		return this.getForwardByUser(mapping, dform, request, response);
	}
	
	public ActionForward exportTestCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{			
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)dform.get("cvrSearchInfo");
		
		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);
		Object[] args = {searchInfo,projectInfo,cvrSearchInfo,functionList};
		
		List<TestCase> caseList = testCaseService.getAllTestCase(args);
		
		request.setAttribute("caseList", caseList);
		request.setAttribute("caseCount", caseList.size());
		
		return mapping.findForward("caseExport");
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

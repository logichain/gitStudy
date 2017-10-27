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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import org.king.framework.web.action.BaseAction;
import org.king.security.domain.UsrAccount;
import org.king.util.FileUtil;
import org.mds.common.CommonService;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.service.ProjectService;
import org.mds.project.service.impl.ProjectServiceImpl;
import org.mds.test.bean.CaseAttachment;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.CvrAttachment;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCorrectRecord;
import org.mds.test.service.TestCaseService;

public class TestCaseManage extends BaseAction {
	private TestCaseService testCaseService;
	private ProjectService projectService;
	
	public ActionForward confirmModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;		
		
		String opType = request.getParameter("opType");
		String id = request.getParameter("id");
		
		ModuleFunction mf = projectService.getModuleFunctionById(Integer.parseInt(id));
		
		ActionForward rtn = null;
		
		if("caseList".equals(opType))
		{
			TestCase searchInfo = (TestCase) dform.get("searchInfo");
			searchInfo.setModuleFunction(mf);
			
			rtn = mapping.findForward("refreshCaseList");
		}
		else if("caseInput".equals(opType))
		{
			TestCase caseInfo = (TestCase) dform.get("caseInfo");
			caseInfo.setModuleFunction(mf);
			caseInfo.setTcModuleFunction(mf.getMuId());
			
			rtn = mapping.findForward("refreshCaseInput");
		}
		else if("caseEdit".equals(opType))
		{
			TestCase caseInfo = (TestCase) dform.get("caseInfo");
			caseInfo.setModuleFunction(mf);
			caseInfo.setTcModuleFunction(mf.getMuId());
			
			rtn = mapping.findForward("refreshCaseEdit");
		}		
		
		return rtn;
	}
	
	public ActionForward selectModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		String opType = request.getParameter("opType");
		request.setAttribute("opType", opType);
		
		return  mapping.findForward("moduleFunctionSelect");
	}
	public ActionForward confirmProjectModule(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;			
		String opType = request.getParameter("opType");
		String id = request.getParameter("id");
				
		ProjectModule pm = projectService.getProjectModuleById(Integer.parseInt(id));
		
		ActionForward rtn = null;
		
		if("caseList".equals(opType))
		{
			TestCase searchInfo = (TestCase) dform.get("searchInfo");			
			searchInfo.setProjectModule(pm);
			searchInfo.setModuleFunction(new ModuleFunction());
			
			rtn = mapping.findForward("refreshCaseList");
		}
		else if("caseInput".equals(opType))
		{
			TestCase caseInfo = (TestCase) dform.get("caseInfo");
			caseInfo.setProjectModule(pm);
			caseInfo.setModuleFunction(new ModuleFunction());
			
			rtn = mapping.findForward("refreshCaseInput");
		}
		else if("caseEdit".equals(opType))
		{
			TestCase caseInfo = (TestCase) dform.get("caseInfo");
			caseInfo.setProjectModule(pm);
			caseInfo.setModuleFunction(new ModuleFunction());
			
			rtn = mapping.findForward("refreshCaseEdit");
		}	
		
		return rtn;
	}
	
	public ActionForward selectProjectModule(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		String opType = request.getParameter("opType");
		request.setAttribute("opType", opType);
		
		return  mapping.findForward("projectModuleSelect");
	}
	
	public ActionForward addAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		
		CaseAttachment pa = new CaseAttachment();
		pa.setCaFlag(CommonService.NORMAL_FLAG);
		pa.setCaCreateTime(new Date());
		pa.setCaCreateUser(ua.getId());
				
		caseInfo.setCurrentAttachment(pa);
						
		return  mapping.findForward("attachmentInput");
	}
	
	public ActionForward addCvrAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		
		CvrAttachment pa = new CvrAttachment();
		pa.setCaFlag(CommonService.NORMAL_FLAG);
		pa.setCaCreateTime(new Date());
		pa.setCaCreateUser(ua.getId());
				
		caseInfo.getCurrentCaseVersionReference().setCurrentAttachment(pa);
						
		return  mapping.findForward("cvrAttachmentInput");
	}
	
	public ActionForward confirmAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseAttachment pa = caseInfo.getCurrentAttachment();
		caseInfo.getAttachmentList().add(pa);
				
		ActionForward rtn = null;
		if("caseInput".equals(opType))
		{
			rtn = mapping.findForward("refreshCaseInput");
		}
		else
		{
			rtn = mapping.findForward("refreshCaseEdit");
		}
				
		return rtn;		
	}
	
	public ActionForward confirmCvrAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CvrAttachment pa = caseInfo.getCurrentCaseVersionReference().getCurrentAttachment();
		caseInfo.getCurrentCaseVersionReference().getAttachmentList().add(pa);
								
		return mapping.findForward("refreshCaseTest");		
	}
			
	public ActionForward deleteAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		String index = request.getParameter("index");
				
		CaseAttachment ca = caseInfo.getAttachmentList().get(Integer.parseInt(index));
		ca.setCaFlag(CommonService.DELETE_FLAG);
		
		this.prepareMetaData(request);
						
		ActionForward rtn = null;
		if("caseInput".equals(opType))
		{
			rtn = mapping.findForward("caseInput");
		}
		else
		{
			rtn = mapping.findForward("caseEdit");
		}
				
		return rtn;	
	}
	
	public ActionForward downloadAttachment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{	
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		String id = request.getParameter("id");
		CaseAttachment pa = null;
				
		for(CaseAttachment p:caseInfo.getAttachmentList())
		{
			if(p.getCaId().equals(Integer.valueOf(id)))
			{
				pa = p;
				break;
			}
		}			
	
						
		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
						
		String fileName = uploadPath + pa.getCaUrl();			
		
		try {
			response.setContentType(FileUtil.getContentType(fileName));
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
	
	public ActionForward refreshCaseInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseInput");
	}
	public ActionForward refreshCaseEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseEdit");
	}
	
	public ActionForward refreshCaseTest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		this.prepareMetaData(request);
		
		return mapping.findForward("caseTest");
	}
	
	public ActionForward importTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("caseImport");
	}

	public ActionForward uploadTestCaseInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount user = (UsrAccount) request.getSession().getAttribute("accountPerson");

		String uploadPath = request.getSession().getServletContext().getRealPath("");
		if (!uploadPath.endsWith("\\")) {
			uploadPath = uploadPath + "\\uploadImportFile\\";
		}
		

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		FormFile formFIle = caseInfo.getImportFile();
		
		Integer caseCount = testCaseService.saveImportTestCaseInfo(formFIle, uploadPath, user, projectInfo);
		
		ActionMessages messenges = new ActionMessages();

		messenges.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.importCaseCount",caseCount));
		saveErrors(request.getSession(), messenges);
		
		return mapping.findForward("refreshCaseList");
	}
	

	public ActionForward exportTestCaseXLSByVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		Integer pvId = Integer.parseInt(request.getParameter("pvId"));
		ProjectVersion projectVersion = projectService.getProjectVersionById(pvId);

		String uploadPath = request.getSession().getServletContext().getRealPath("");
		if (!uploadPath.endsWith("\\")) {
			uploadPath = uploadPath + "\\uploadImportFile\\";
		}
		
		List<TestCase> caseList = testCaseService.searchTestCaseByVersion(projectVersion);
		
		String fileName = uploadPath + "exportCase.xls";
		testCaseService.writeTestCaseToXslFile(fileName, caseList,projectVersion,"design".equals(type));

		try {
			response.setContentType(org.king.util.FileUtil.getContentType(fileName));
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName.substring(fileName.lastIndexOf("\\") + 1));
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
	
	public ActionForward exportTestCaseDOCByVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		Integer pvId = Integer.parseInt(request.getParameter("pvId"));
		ProjectVersion projectVersion = projectService.getProjectVersionById(pvId);
		Project projectInfo = projectService.getProjectById(projectVersion.getPvProject());

		List<TestCase> caseList = testCaseService.searchTestCaseByVersion(projectVersion);

		request.setAttribute("caseList", caseList);
		request.setAttribute("caseCount", caseList.size());
		
		request.setAttribute("type",type);
		request.setAttribute("version",projectVersion.getPvId());
		request.setAttribute("projectInfo", projectInfo);
		request.setAttribute("versionInfo", projectVersion);
		
		return mapping.findForward("caseExportByVersion");
	}

	public ActionForward createTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		TestCase testCase = new TestCase();
		Date createTime = new Date();
		testCase.setTcFlag(CommonService.NORMAL_FLAG);
		testCase.setProjectModule(new ProjectModule());
		testCase.setModuleFunction(new ModuleFunction());
		
		testCase.setCreateUser(ua);
		testCase.setTcCreateUser(ua.getId());
		testCase.setTcCreateTime(createTime);
		// 新建用例记录
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

	public ActionForward editTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");

		// 返回列表后，仍保留原页码
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		this.savePagerOffsetFromRequest(request, searchInfo);

		String id = request.getParameter("id");
		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));

		this.editOperProcess(request, testCase, projectInfo);

		dform.set("caseInfo", testCase);

		return mapping.findForward("caseEdit");
	}

	public ActionForward editTestCasePrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");

		searchInfo.setTcCreateUser(ua.getId());
		searchInfo.setTcCreateUserStr(ua.getPersonName());
		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCasePreviousEdit(args, caseInfo.getTcId());
		if (testCase != null) {
			this.editOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseEdit");
	}

	public ActionForward editTestCaseNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");

		searchInfo.setTcCreateUser(ua.getId());
		searchInfo.setTcCreateUserStr(ua.getPersonName());
		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCaseNextEdit(args, caseInfo.getTcId());
		if (testCase != null) {
			this.editOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseEdit");
	}



	/*
	 * 当前适用版本
	 */
	private void initApplyProjectVersionInfo(TestCase testCase, Project projectInfo) {
		for (ProjectVersion vtr : projectInfo.getProjectVersionList()) {
			if (testCase.isApplyProjectVersion(vtr) != null) {
				vtr.setSelected(true);
			} else {
				vtr.setSelected(false);
			}
		}
	}

	private void editOperProcess(HttpServletRequest request, TestCase testCase, Project projectInfo) {
		testCase.setProjectModule(testCase.getModuleFunction().getParentProjectModule());

		this.initApplyProjectVersionInfo(testCase, projectInfo);

		this.prepareMetaData(request);
	}

	private void savePagerOffsetFromRequest(HttpServletRequest request, TestCase searchInfo) {
		String offset = request.getParameter("pager.offset");
		if (offset != null && !"".equals(offset)) {
			searchInfo.setOffset(Integer.parseInt(offset));
		}
	}

	public ActionForward displayTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");

		String id = request.getParameter("id");

		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		this.initApplyProjectVersionInfo(testCase, projectInfo);

		request.setAttribute("caseInfo", testCase);

		return mapping.findForward("caseDisplay");
	}

	public ActionForward displayTestCaseNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("id");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCaseNextDisplay(args, Integer.valueOf(id));
		if (testCase == null) {
			testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		}
		this.initApplyProjectVersionInfo(testCase, projectInfo);

		request.setAttribute("caseInfo", testCase);

		return mapping.findForward("caseDisplay");
	}

	public ActionForward displayTestCasePrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("id");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCasePreviousDisplay(args, Integer.valueOf(id));
		if (testCase == null) {
			testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		}
		this.initApplyProjectVersionInfo(testCase, projectInfo);

		request.setAttribute("caseInfo", testCase);

		return mapping.findForward("caseDisplay");
	}

	public ActionForward testTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");

		this.savePagerOffsetFromRequest(request, searchInfo);

		String id = request.getParameter("id");
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());

		this.testOperProcess(request, testCase, projectInfo);

		dform.set("caseInfo", testCase);

		return mapping.findForward("caseTest");
	}

	public ActionForward testTestCaseNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		CaseVersionReference cvr = caseInfo.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCaseNextTest(args, caseInfo.getTcId());
		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseTest");
	}

	public ActionForward testTestCasePrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		CaseVersionReference cvr = caseInfo.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCasePreviousTest(args, caseInfo.getTcId());
		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseTest");
	}

	private void testOperProcess(HttpServletRequest request, TestCase testCase, Project projectInfo) {
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		this.initApplyProjectVersionInfo(testCase, projectInfo);

		Date testTime = new Date();

		CaseVersionReference cvr = testCase.getCurrentCaseVersionReference();

		cvr.setCvrTestUser(ua.getId());
		cvr.setCvrTestTime(testTime);
		cvr.setTestUser(ua);
		cvr.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.TESTED_STATUS));

		// 测试用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setOperUser(ua);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(testTime);
		testCase.getTestCorrectRecordList().add(tcr);

		this.prepareMetaData(request);
	}

	public ActionForward correctTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");

		this.savePagerOffsetFromRequest(request, searchInfo);

		String id = request.getParameter("id");
		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());

		this.correctOperProcess(request, testCase, projectInfo);

		dform.set("caseInfo", testCase);

		return mapping.findForward("caseCorrect");
	}

	public ActionForward correctTestCasePrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		CaseVersionReference cvr = caseInfo.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCasePreviousCorrect(args, caseInfo.getTcId());
		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseCorrect");
	}

	public ActionForward correctTestCaseNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase caseInfo = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		CaseVersionReference cvr = caseInfo.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = testCaseService.getTestCaseNextCorrect(args, caseInfo.getTcId());
		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseCorrect");
	}

	private void correctOperProcess(HttpServletRequest request, TestCase testCase, Project projectInfo) {
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		Date correctTime = new Date();

		this.initApplyProjectVersionInfo(testCase, projectInfo);

		CaseVersionReference cvr = testCase.getCurrentCaseVersionReference();

		cvr.setCvrCorrectUser(ua.getId());
		cvr.setCvrCorrectTime(correctTime);
		cvr.setCorrectUser(ua);
		cvr.setCvrCaseStatus(CaseStatus.CORRECT_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.CORRECT_STATUS));

		// 修正用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setOperUser(ua);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(correctTime);
		testCase.getTestCorrectRecordList().add(tcr);

		this.prepareMetaData(request);
	}

	public ActionForward closeTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}

		Date closeTime = new Date();

		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());

		cvr.setCvrCloseUser(ua.getId());
		cvr.setCvrCloseTime(closeTime);
		cvr.setCvrCaseStatus(CaseStatus.CLOSE_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.CLOSE_STATUS));

		// 关闭用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(closeTime);
		testCase.getTestCorrectRecordList().add(tcr);

		testCaseService.saveTestCase(testCase,uploadPath);

		return this.searchTestCase(mapping, dform, request, response);
	}

	public ActionForward openTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		Date closeTime = new Date();

		CaseVersionReference cvr = testCaseService.getCaseVersionReferenceById(Integer.valueOf(id));
		TestCase testCase = testCaseService.getTestCaseById(cvr.getCvrTestCase());
		this.setCurrentCaseVersionReferenceByCvrId(testCase, cvr.getCvrId());

		cvr.setCvrCloseUser(ua.getId());
		cvr.setCvrCloseTime(closeTime);
		cvr.setCvrCaseStatus(CaseStatus.TESTED_STATUS);
		cvr.setStatus(testCaseService.getCaseStatusById(CaseStatus.TESTED_STATUS));

		// 关闭用例记录
		TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(closeTime);
		testCase.getTestCorrectRecordList().add(tcr);

		testCaseService.saveTestCase(testCase,uploadPath);

		return this.searchTestCase(mapping, dform, request, response);
	}

	public ActionForward deleteTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String id = request.getParameter("id");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		TestCase testCase = testCaseService.getTestCaseById(Integer.valueOf(id));
		testCase.setTcFlag(CaseStatus.DELETE_STATUS);

		// 删除用例记录
		TestCorrectRecord tcr = new TestCorrectRecord();
		tcr.setTcrTestCase(testCase.getTcId());
		tcr.setTcrCaseStatus(CaseStatus.DELETE_STATUS);

		tcr.setCurrentOperRecord(true);
		tcr.setTcrOperUser(ua.getId());
		tcr.setTcrOperTime(new Date());
		testCase.getTestCorrectRecordList().add(tcr);

		testCaseService.saveTestCase(testCase,uploadPath);

		return this.searchTestCase(mapping, dform, request, response);
	}

	private void prepareMetaData(HttpServletRequest request) {
		request.setAttribute("importantLevelList", testCaseService.getImportantLevelList());
		request.setAttribute("testResultList", testCaseService.getTestResultList());
		request.setAttribute("caseStatusList", testCaseService.getCaseStatusList());
		request.setAttribute("bugTypeList", testCaseService.getBugTypeList());
		request.setAttribute("caseTypeList", testCaseService.getCaseTypeList());
	}

	public ActionForward saveTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		
		this.saveTestCase(dform, request);
				
		return this.redirectToTestCaseListPage(request, response, searchInfo);
	}
	
	private void saveTestCase(DynaValidatorForm dform, HttpServletRequest request) 
	{
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase testCase = (TestCase) dform.get("caseInfo");
		
		Date operTime = new Date();

		for (ProjectVersion version : projectInfo.getProjectVersionList()) {
			CaseVersionReference cvr = testCase.isApplyProjectVersion(version);
			if (version.isSelected()) {
				if (cvr == null) {
					CaseVersionReference ccvr = new CaseVersionReference();
					ccvr.setCvrFlag(CommonService.NORMAL_FLAG);
					ccvr.setCvrTestCase(testCase.getTcId());
					ccvr.setCvrProjectVersion(version.getPvId());
					ccvr.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);

					testCase.getCaseVersionReferenceList().add(ccvr);
					
					//关联用例记录
					TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(ccvr);
					
					tcr.setTcrOperUser(ua.getId());
					tcr.setTcrOperTime(operTime);
					testCase.getTestCorrectRecordList().add(tcr);
				}
			} else {
				if (cvr != null) {
					cvr.setCvrFlag(CommonService.DELETE_FLAG);
					cvr.setCvrCaseStatus(CaseStatus.DELETE_STATUS);
					
					//解除关联用例记录
					TestCorrectRecord tcr = testCaseService.createTestCorrectRecord(cvr);
					
					tcr.setTcrOperUser(ua.getId());
					tcr.setTcrOperTime(operTime);
					testCase.getTestCorrectRecordList().add(tcr);
				}
			}
		}
		
						
		testCaseService.saveTestCase(testCase,uploadPath);
	}

	private ActionForward redirectToTestCaseListPage(HttpServletRequest request, HttpServletResponse response, TestCase searchInfo) {
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

	public ActionForward saveTestCaseNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		this.saveTestCase(dform, request);		

		TestCase oldTestCase = (TestCase) dform.get("caseInfo");
		
		TestCase testCase = new TestCase();
		Date createTime = new Date();
		testCase.setTcFlag(CommonService.NORMAL_FLAG);
		testCase.setProjectModule(oldTestCase.getProjectModule());
		testCase.setTcModuleFunction(oldTestCase.getModuleFunction().getMuId());
		testCase.setModuleFunction(oldTestCase.getModuleFunction());
		
		testCase.setCreateUser(ua);
		testCase.setTcCreateUser(ua.getId());
		testCase.setTcCreateTime(createTime);
		// 新建用例记录
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

	public ActionForward saveTestCaseCopy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
				
		this.saveTestCase(dform, request);	
		
		dform.set("caseInfo", testCase.copy());

		this.prepareMetaData(request);

		return mapping.findForward("caseInput");
	}

	public ActionForward saveTestCaseEditNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		TestCase testCase = (TestCase) dform.get("caseInfo");		
		this.saveTestCase(dform, request);	
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);
		Object[] args = { searchInfo, cvrSearchInfo, functionList };		

		testCase = testCaseService.getTestCaseNextEdit(args, testCase.getTcId());
		if (testCase == null) {
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		
		dform.set("caseInfo", testCase);

		this.prepareMetaData(request);

		return mapping.findForward("caseEdit");
	}
	

	private void setCurrentCaseVersionReferenceByCvrId(TestCase testCase, Integer id) {
		for (CaseVersionReference c : testCase.getCaseVersionReferenceList()) {
			if (c.getCvrId().equals(id)) {
				testCase.setCurrentCaseVersionReference(c);
				break;
			}
		}
	}

	private void setCurrentCaseVersionReferenceByVersionId(TestCase testCase, Integer id) {
		for (CaseVersionReference c : testCase.getCaseVersionReferenceList()) {
			if (c.getCvrProjectVersion().equals(id)) {
				testCase.setCurrentCaseVersionReference(c);
				break;
			}
		}
	}

	public ActionForward saveTestCaseTest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");

		CaseVersionReference cvr = testCase.getCurrentCaseVersionReference();

		for (TestCorrectRecord tcr : testCase.getTestCorrectRecordList()) {
			if (tcr.isCurrentOperRecord()) {
				tcr.setTcrTestResult(cvr.getCvrCaseResult());
			}
		}

		testCaseService.saveTestCase(testCase,uploadPath);

		return this.redirectToTestCaseListPage(request, response, searchInfo);
	}

	public ActionForward saveTestCaseTestNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);
		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		TestCase testCase = (TestCase) dform.get("caseInfo");
		testCaseService.saveTestCase(testCase,uploadPath);

		CaseVersionReference cvr = testCase.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		testCase = testCaseService.getTestCaseNextTest(args, testCase.getTcId());
		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.testOperProcess(request, testCase, projectInfo);

			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseTest");
	}

	public ActionForward saveTestCaseCorrect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		TestCase testCase = (TestCase) dform.get("caseInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");

		testCaseService.saveTestCase(testCase,uploadPath);

		return this.redirectToTestCaseListPage(request, response, searchInfo);
	}

	public ActionForward saveTestCaseCorrectNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		TestCase testCase = (TestCase) dform.get("caseInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		CaseVersionReference cvr = testCase.getCurrentCaseVersionReference();
		cvrSearchInfo.setCvrProjectVersion(cvr.getCvrProjectVersion());

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		Object[] args = { searchInfo, cvrSearchInfo, functionList };

		testCaseService.saveTestCase(testCase,uploadPath);
		testCase = testCaseService.getTestCaseNextCorrect(args, testCase.getTcId());

		if (testCase != null) {
			this.setCurrentCaseVersionReferenceByVersionId(testCase, cvr.getCvrProjectVersion());
			this.correctOperProcess(request, testCase, projectInfo);
			dform.set("caseInfo", testCase);
		} else {
			cvrSearchInfo.setCvrProjectVersion(null);
			return this.redirectToTestCaseListPage(request, response, searchInfo);
		}

		return mapping.findForward("caseCorrect");
	}

	public ActionForward resetSearchTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		String pid = request.getParameter("pid");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		if (projectInfo.getPId() == null && pid == null) {
			return mapping.findForward("first");
		}

		if (pid != null && !"".equals(pid)) {
			projectInfo = projectService.getProjectById(Integer.valueOf(pid));
			dform.set("projectInfo", projectInfo);
		}

		TestCase searchInfo = new TestCase();	
		searchInfo.setProjectModule(new ProjectModule());
		searchInfo.setModuleFunction(new ModuleFunction());
		String page = request.getParameter("pager.offset");
		if (page == null) {
			page = "0";
		}

		dform.set("searchInfo", searchInfo);
		CaseVersionReference cvrSearchInfo = new CaseVersionReference();
		dform.set("cvrSearchInfo", cvrSearchInfo);

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		if ((projectInfo.isTeamMember(ua) || ua.getId().equals(1)) && functionList.length() > 2) {
			Object[] args = { searchInfo, page, cvrSearchInfo, functionList };

			List<TestCase> caseList = testCaseService.searchTestCase(args);
			Integer caseCount = testCaseService.searchTestCaseCount(args);

			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		} else {
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}

		this.prepareMetaData(request);

		return this.getForwardByUser(mapping, dform, request, response);
	}

	private ActionForward getForwardByUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");

		if (ua.getDepartment().getDFlag().equals(1)) {
			return mapping.findForward("caseListForTest");
		}

		return mapping.findForward("caseListForCorrect");
	}

	public ActionForward searchTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String page = request.getParameter("pager.offset");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);

		if (functionList.length() > 2) {
			Object[] args = { searchInfo, page, cvrSearchInfo, functionList };

			List<TestCase> caseList = testCaseService.searchTestCase(args);
			Integer caseCount = testCaseService.searchTestCaseCount(args);

			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		} else {
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}

		this.prepareMetaData(request);

		return this.getForwardByUser(mapping, dform, request, response);
	}

	public ActionForward exportTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		Integer id = searchInfo.getTcModuleFunction();
		if( id != null && !id.equals(0))
		{
			searchInfo.setModuleFunction(testCaseService.getModuleFunctionById(id));
		}
		
		id = searchInfo.getTcType();
		if( id != null && !id.equals(0))
		{
			searchInfo.setCaseType(testCaseService.getCaseTypeById(id));
		}
		id = cvrSearchInfo.getCvrCaseResult();
		if( id != null && !id.equals(0))
		{
			cvrSearchInfo.setTestResult(testCaseService.getTestResultById(id));
		}
		id = cvrSearchInfo.getCvrCaseStatus();
		if( id != null && !id.equals(0))
		{
			cvrSearchInfo.setStatus(testCaseService.getCaseStatusById(id));
		}
		
		id = cvrSearchInfo.getCvrImportantLevel();
		if( id != null && !id.equals(0))
		{
			cvrSearchInfo.setImportantLevel(testCaseService.getImportantLevelById(id));
		}
		id = cvrSearchInfo.getCvrBugType();
		if( id != null && !id.equals(0))
		{
			cvrSearchInfo.setBugType(testCaseService.getBugTypeById(id));
		}
		id = cvrSearchInfo.getCvrProjectVersion();
		if( id != null && !id.equals(0))
		{
			cvrSearchInfo.setProjectVersion(projectService.getProjectVersionById(id));
		}

		String functionList = ProjectServiceImpl.getApplicaleFunctionList(projectInfo, searchInfo);
		Object[] args = { searchInfo, projectInfo, cvrSearchInfo, functionList };

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

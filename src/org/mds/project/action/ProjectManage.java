package org.mds.project.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.king.framework.web.action.BaseAction;
import org.king.security.domain.UsrAccount;
import org.king.security.service.AccountService;
import org.king.util.FileUtil;
import org.mds.common.CommonService;
import org.mds.project.bean.MemberFunctionReference;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectAttachment;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.bean.TeamMember;
import org.mds.project.service.ProjectService;
import org.mds.project.service.impl.ProjectServiceImpl;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.TestCase;
import org.mds.test.service.TestCaseService;

public class ProjectManage extends BaseAction {
	private AccountService accountService;
	private ProjectService projectService;
	private TestCaseService testCaseService;

	
	public ActionForward caseVersionRefer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("pid");
		Project project = projectService.getProjectById(Integer.parseInt(id));
				
		dform.set("projectInfo", project);
		
		return mapping.findForward("caseVersionRefer");
	}
	
	public ActionForward testDesignOutput(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("pid");
		Project project = projectService.getProjectById(Integer.parseInt(id));
				
		dform.set("projectInfo", project);
		
		return mapping.findForward("testDesignOutput");
	}
	
	public ActionForward testResultOutput(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("pid");
		Project project = projectService.getProjectById(Integer.parseInt(id));
				
		dform.set("projectInfo", project);
		
		return mapping.findForward("testResultOutput");
	}
	
	
	public ActionForward confirmTestCaseforReference(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		String[] selectedCaseList = request.getParameterValues("selectedCaseList");
		CaseVersionReference cvr = (CaseVersionReference) dform.get("cvrSearchInfo");
		
		Project project = (Project) dform.get("projectInfo");
		
		if(selectedCaseList != null)
		{
			testCaseService.saveCaseVersionReference(selectedCaseList, cvr.getReferVersion(),ua.getId());
		}
		
		request.setAttribute("pid", project.getPId());
				
		return mapping.findForward("refreshCaseVersionRefer");
	}

	public ActionForward resetSearchTestCaseforRenference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project projectInfo = (Project) dform.get("projectInfo");
		String pvid = request.getParameter("pvId");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		CaseVersionReference cvrSearchInfo = new CaseVersionReference();
		
		if (pvid != null && !"".equals(pvid)) {
			ProjectVersion versionInfo = projectService.getProjectVersionById(Integer.valueOf(pvid));
			projectInfo = projectService.getProjectById(versionInfo.getPvProject());
			dform.set("projectInfo", projectInfo);
			
			cvrSearchInfo.setReferVersion(versionInfo.getPvId());			
		}
		else
		{
			CaseVersionReference cvr = (CaseVersionReference) dform.get("cvrSearchInfo");
			cvrSearchInfo.setReferVersion(cvr.getReferVersion());
		}

		TestCase searchInfo = new TestCase();		
		String page = request.getParameter("pager.offset");
		if (page == null) {
			page = "0";
		}

		dform.set("searchInfo", searchInfo);
		dform.set("cvrSearchInfo", cvrSearchInfo);

		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);

		if ((projectInfo.isTeamMember(ua) || ua.getId().equals(1)) && functionList.length() > 2) {
			Object[] args = { searchInfo, page, cvrSearchInfo, functionList };

			List<TestCase> caseList = testCaseService.searchTestCaseForReference(args);
			Integer caseCount = testCaseService.searchTestCaseCountForReference(args);

			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		} else {
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}

		this.prepareMetaData(request);

		return mapping.findForward("caseListForReference");
	}
	
	public ActionForward searchTestCaseforReference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String page = request.getParameter("pager.offset");
		Project projectInfo = (Project) dform.get("projectInfo");
		TestCase searchInfo = (TestCase) dform.get("searchInfo");
		CaseVersionReference cvrSearchInfo = (CaseVersionReference) dform.get("cvrSearchInfo");

		String functionList = this.getApplicaleFunctionList(projectInfo, searchInfo);

		if (functionList.length() > 2) {
			Object[] args = { searchInfo, page, cvrSearchInfo, functionList };

			List<TestCase> caseList = testCaseService.searchTestCaseForReference(args);
			Integer caseCount = testCaseService.searchTestCaseCountForReference(args);

			request.setAttribute("caseList", caseList);
			request.setAttribute("caseCount", caseCount);
		} else {
			request.setAttribute("caseList", new ArrayList<TestCase>());
			request.setAttribute("caseCount", 0);
		}

		this.prepareMetaData(request);

		return mapping.findForward("caseListForReference");
	}
				
	private String getApplicaleFunctionList(Project projectInfo, TestCase searchInfo) {
		String functionList = "";
		if(searchInfo.getModuleFunction().getMuId() != null){
			if(searchInfo.getModuleFunction().getChildFunctionList().size() == 0)
			{
				functionList = "(" + searchInfo.getModuleFunction().getMuId() + ")";
			}
			else
			{
				functionList = ProjectServiceImpl.getModuleFunctionListForSearch(searchInfo.getModuleFunction());
			}			
		} else if (searchInfo.getProjectModule().getPmId() != null) {
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(searchInfo.getProjectModule());
		} else {
			functionList = ProjectServiceImpl.getModuleFunctionListForSearch(projectInfo.getAllModuleFunctionList());
		}

		return functionList;
	}
	
	public ActionForward addAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");		
		Project project = (Project) dform.get("projectInfo");
		
		ProjectAttachment pa = new ProjectAttachment();
		pa.setPaFlag(CommonService.NORMAL_FLAG);
		pa.setPaCreateTime(new Date());
		pa.setPaCreateUser(ua.getId());
		
		ProjectVersion vtr = null;
		if("projectInput".equals(opType))
		{
			vtr = project.getInitProjectVersion();
		}
		else if("versionInput".equals(opType))
		{
			vtr = (ProjectVersion) dform.get("versionInfo");
		}
		vtr.setCurrentAttachment(pa);
		dform.set("versionInfo", vtr);
								
		return  mapping.findForward("attachmentInput");
	}
	
	public ActionForward confirmAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		ProjectVersion version = (ProjectVersion) dform.get("versionInfo");
		ProjectAttachment pa = version.getCurrentAttachment();
		version.getAttachmentList().add(pa);
		
		if("projectInput".equals(opType))
		{
			return mapping.findForward("refreshProjectInput");
		}
		else if("versionInput".equals(opType))
		{
			return mapping.findForward("refreshVersionInput");
		}
				
		return null;
	}
			
	public ActionForward deleteAttachment(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
		
		Project project = (Project) dform.get("projectInfo");
		String index = request.getParameter("index");
				
		ProjectAttachment pa = project.getInitProjectVersion().getAttachmentList().get(Integer.parseInt(index));
		pa.setPaFlag(CommonService.DELETE_FLAG);
						
		if("projectInput".equals(opType))
		{
			return mapping.findForward("projectInput");
		}
		else if("versionInput".equals(opType))
		{
			return mapping.findForward("versionInput");
		}
		
		return null;
	}
	
	public ActionForward downloadAttachment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{	
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project tp = (Project) dform.get("projectInfo");
		String id = request.getParameter("id");
		ProjectAttachment pa = null;
		
		for(ProjectVersion vtr:tp.getProjectVersionList())
		{
			for(ProjectAttachment p:vtr.getAttachmentList())
			{
				if(p.getPaId().equals(Integer.valueOf(id)))
				{
					pa = p;
					break;
				}
			}			
		}
						
		String fileName = pa.getPaUrl();				
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
	
	public ActionForward searchAccount(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->listAccount' method");
		}

		// 处理查询条件
		// 用户名
		String para = request.getParameter("para");
		// 姓名
		String paraPersonName = request.getParameter("paraPersonName");
		// 起始页
		String page = request.getParameter("pager.offset");
		
		String department = request.getParameter("department");

		String[] args = { para, paraPersonName, page,department };

		Integer accountCount = new Integer(0);
		List<UsrAccount> accounts = accountService.findAccount(args);
		accountCount = accountService.getAccountCount(args);

		request.setAttribute("accounts", accounts);
		request.setAttribute("accountCount", accountCount);
		
		this.prepareMetaData(request);
		
		return mapping.findForward("searchAccount");

	}
	
	private void prepareMetaData(HttpServletRequest request)
	{
		request.setAttribute("departmentList", accountService.getDepartmentList());
		
		request.setAttribute("importantLevelList", testCaseService.getImportantLevelList());
		request.setAttribute("testResultList", testCaseService.getTestResultList());
		request.setAttribute("caseStatusList", testCaseService.getCaseStatusList());
		request.setAttribute("bugTypeList", testCaseService.getBugTypeList());
		request.setAttribute("caseTypeList", testCaseService.getCaseTypeList());
	}
	
	public ActionForward resetSearchAccount(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->listAccount' method");
		}
		DynaValidatorForm dform = (DynaValidatorForm) form;
		dform.set("paraPersonName", null);
		dform.set("department", null);
		
		String[] args = { null, null, null,null };

		Integer accountCount = new Integer(0);
		List<UsrAccount> accounts = accountService.findAccount(args);
		accountCount = accountService.getAccountCount(args);

		request.setAttribute("accounts", accounts);
		request.setAttribute("accountCount", accountCount);
		
		this.prepareMetaData(request);
		
		return mapping.findForward("searchAccount");

	}
	public ActionForward confirmAccount(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String opType = request.getParameter("opType");
						
		String id = request.getParameter("id");
		
		Project project = (Project)dform.get("projectInfo");
		UsrAccount ua = accountService.findAccountById(Integer.parseInt(id));
		if("dl".equals(opType))
		{
			project.getInitProjectVersion().setDevelopLeader(ua);
			project.getInitProjectVersion().setPvDevelopLeader(ua.getId());
		}
		else if("tl".equals(opType))
		{
			project.getInitProjectVersion().setTestLeader(ua);
			project.getInitProjectVersion().setPvTestLeader(ua.getId());
		}
		else if("tm".equals(opType))
		{
			for(TeamMember tm:project.getMemberList())
			{
				if(tm.getTmAccount().equals(Integer.parseInt(id)))
				{
					if(tm.getTmFlag().equals(CommonService.DELETE_FLAG))
					{
						tm.setTmFlag(CommonService.NORMAL_FLAG);
						projectService.saveTeamMember(tm);
					}
					
					request.setAttribute("tabpageId","memberInfo");					
					return mapping.findForward("refreshProjectInfo");
				}				
			}
			TeamMember tm = projectService.addTeamMember(ua, project.getPId());
			project.getMemberList().add(tm);
			
			request.setAttribute("tabpageId","memberInfo");
						
			return mapping.findForward("refreshProjectInfo");
		}
		else if("vdl".equals(opType))
		{
			ProjectVersion vtr = (ProjectVersion)dform.get("versionInfo");			
			vtr.setDevelopLeader(ua);
			vtr.setPvDevelopLeader(ua.getId());
			
			return mapping.findForward("refreshVersionInput");
		}
		else if("vtl".equals(opType))
		{
			ProjectVersion vtr = (ProjectVersion)dform.get("versionInfo");			
			vtr.setTestLeader(ua);
			vtr.setPvTestLeader(ua.getId());
			
			return mapping.findForward("refreshVersionInput");
		}
		
		
		return mapping.findForward("refreshProjectInput");
	}
	
	
	
	public ActionForward refreshVersionInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		
		return mapping.findForward("versionInput");
	}
	
	
	public ActionForward refreshProjectInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		
		return mapping.findForward("projectInput");
	}
	
	public ActionForward editTeamMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project) dform.get("projectInfo");
		
		String id = request.getParameter("id");
		TeamMember ctm = null;
		for(TeamMember tm:project.getMemberList())
		{
			if(tm.getTmId().equals(Integer.valueOf(id)))
			{
				ctm = tm;
				break;
			}
		}
		
		List<LabelValueBean> selectedFunction = new ArrayList<LabelValueBean>();
		List<LabelValueBean> availableFunction = new ArrayList<LabelValueBean>();
		List<ModuleFunction> selectedFunctionList = new ArrayList<ModuleFunction>();
		
		for (MemberFunctionReference mfr:ctm.getMemberFunctionReferenceList()) 
		{
			LabelValueBean lv = new LabelValueBean();			
			lv.setLabel(mfr.getModuleFunction().getProjectModule().getPmName() + "-" + mfr.getModuleFunction().getMuName());
			lv.setValue(mfr.getMfrModuleFunction().toString());
			selectedFunction.add(lv);
			selectedFunctionList.add(mfr.getModuleFunction());
		}
		
		ArrayList<ModuleFunction> functionList = project.getAllModuleFunctionList();

		for (ModuleFunction mf:functionList) {			
			if (!selectedFunctionList.contains(mf) && mf.getProjectModule() != null) {
				LabelValueBean lv = new LabelValueBean();
				lv.setLabel(mf.getProjectModule().getPmName() + "-" + mf.getMuName());
				lv.setValue(mf.getMuId().toString());
				availableFunction.add(lv);
			}
		}

		request.setAttribute("selectedFunction", selectedFunction);
		request.setAttribute("availableFunction", availableFunction);
		
		request.setAttribute("currentTeamMember",ctm);
								
		return mapping.findForward("editTeamMember");
	}
	
	public ActionForward deleteTeamMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project) dform.get("projectInfo");
		
		String id = request.getParameter("id");
	
		for(TeamMember tm:project.getMemberList())
		{
			if(tm.getTmId().equals(Integer.valueOf(id)))
			{
				tm.setTmFlag(CommonService.DELETE_FLAG);
				projectService.saveTeamMember(tm);				
				break;
			}
		}
								
		request.setAttribute("tabpageId","memberInfo");
		
		return mapping.findForward("editProjectInfo");
	}
	public ActionForward refreshProjectInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{		
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project project = (Project) dform.get("projectInfo");
		project = projectService.getProjectById(project.getPId());		
		dform.set("projectInfo", project);
		request.setAttribute("tabpageId",request.getParameter("tabpageId"));
		
		return mapping.findForward("editProjectInfo");
	}
	
	public ActionForward createProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;		
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		Date createTime = new Date();
		Project project = new Project();
		project.setPCreateUser(ua.getId());
		project.setPCreateTime(createTime);
		
		ProjectVersion vtr = new ProjectVersion();
		vtr.setPvCreateUser(ua.getId());
		vtr.setPvCreateTime(createTime);
		vtr.setPvInit(1);
		project.setInitProjectVersion(vtr);
				
		dform.set("projectInfo", project);
		
		return mapping.findForward("projectInput");
	}
	public ActionForward editProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;

		String id = request.getParameter("id");
		Project project = projectService.getProjectById(Integer.parseInt(id));
				
		dform.set("projectInfo", project);
				
		return mapping.findForward("editProjectInfo");
	}
		
	
	public ActionForward deleteProject(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		String id = request.getParameter("id");
		Project project = projectService.getProjectById(Integer.parseInt(id));
		
		project.setPFlag(CommonService.DELETE_FLAG);
		projectService.saveProject(project);
		
		return this.searchProject(mapping, dform, request, response);
	}
	
	public ActionForward enableProject(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		String id = request.getParameter("id");
		Project project = projectService.getProjectById(Integer.parseInt(id));
		
		if(project.getPStatus().equals(Project.PROJECT_STATUS_CLOSE))
		{
			project.setPStatus(Project.PROJECT_STATUS_NORMAL);
		}
		else
		{
			project.setPStatus(Project.PROJECT_STATUS_CLOSE);
		}
		
		projectService.saveProject(project);
		
		return this.searchProject(mapping, dform, request, response);
	}
	
	public ActionForward editProjectVersion(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project) dform.get("projectInfo");
		
		String id = request.getParameter("id");
		
		for(ProjectVersion vtr:project.getProjectVersionList())
		{
			if(vtr.getPvId().equals(Integer.valueOf(id)))
			{
				dform.set("versionInfo",vtr);
				break;
			}
		}
						
		return mapping.findForward("versionInput");
	}
	
	public ActionForward deleteProjectVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project) dform.get("projectInfo");
		
		String id = request.getParameter("id");
		
		ProjectVersion cvtr = null;		
		for(ProjectVersion vtr:project.getProjectVersionList())
		{
			if(vtr.getPvId().equals(Integer.valueOf(id)))
			{							
				projectService.deleteProjectVersion(vtr);
				cvtr = vtr;
				
				break;
			}
		}
		
		project.getProjectVersionList().remove(cvtr);
				
		return mapping.findForward("editVersionInfo");
	}
	public ActionForward saveProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project project = (Project)dform.get("projectInfo");

		ArrayList<ProjectVersion> vtrList = project.getProjectVersionList(); 
		project.setProjectVersionList(new ArrayList<ProjectVersion>());
		
		projectService.saveProject(project);
		
		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
			
		//初始版本保存
		ProjectVersion vtr = project.getInitProjectVersion();
		if(vtr.getPvId() == null)
		{			
			vtr.setPvProject(project.getPId());			
		}	
		
		projectService.saveProjectVersion(vtr,uploadPath);
				
		project.setProjectVersionList(vtrList);
		
		//当开发、测试leader不是项目成员时，自动添加为项目成员
		if(project.getInitProjectVersion().getPvDevelopLeader() != null)
		{
			projectService.addTeamMember(project, project.getInitProjectVersion().getDevelopLeader());
		}			
		projectService.addTeamMember(project, project.getInitProjectVersion().getTestLeader());
		
		return this.resetSearchProject(mapping, dform, request, response);
	}
	
	public ActionForward saveProjectVersion(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		ProjectVersion vtr = (ProjectVersion)dform.get("versionInfo");
		
		String uploadPath = request.getSession().getServletContext().getInitParameter("uploadFilePath");
		if(!uploadPath.endsWith("\\"))
		{
			uploadPath = uploadPath + "\\";
		}
		
		if(vtr.getPvId() == null)
		{
			project.getProjectVersionList().add(vtr);
		}
		
		projectService.saveProjectVersion(vtr,uploadPath);
		
		//当开发、测试leader不是项目成员时，自动添加为项目成员
		projectService.addTeamMember(project, vtr.getDevelopLeader());	
		projectService.addTeamMember(project, vtr.getTestLeader());
		
		request.setAttribute("tabpageId","versionInfo");
						
		return mapping.findForward("refreshProjectInfo");
	}
	
	public ActionForward saveTeamMember(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String tmId = request.getParameter("id");

		TeamMember tm = projectService.getTeamMemberById(Integer.parseInt(tmId));

		String[] selectedFunction = request.getParameterValues("selectedFunction");
		if (selectedFunction != null) {
			List<String> selectedFunctionList = Arrays.asList(selectedFunction);

			ArrayList<MemberFunctionReference> mfrList = tm.getMemberFunctionReferenceList();

			for (MemberFunctionReference mfr : mfrList) {
				if (selectedFunctionList.contains(mfr.getMfrModuleFunction())) {
					if (mfr.getMfrFlag().equals(CommonService.DELETE_FLAG)) {
						mfr.setMfrFlag(CommonService.NORMAL_FLAG);
					}

					selectedFunctionList.remove(mfr.getMfrModuleFunction());
				} else {
					mfr.setMfrFlag(CommonService.DELETE_FLAG);
				}
			}

			for (String sf : selectedFunctionList) {
				MemberFunctionReference mfr = new MemberFunctionReference();
				mfr.setMfrModuleFunction(Integer.parseInt(sf));
				mfr.setMfrTeamMember(tm.getTmId());

				mfrList.add(mfr);
			}

			projectService.saveTeamMember(tm);
		}

		request.setAttribute("tabpageId", "memberInfo");

		return mapping.findForward("refreshProjectInfo");
	}
	
	
	
	public ActionForward searchProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project project = (Project)dform.get("projectInfo");
					
		String page = request.getParameter("pager.offset");
		Object[] args = {project,page};
		
		List<Project> projectList = projectService.searchProject(args);
		Integer projectCount = projectService.searchProjectCount(args);
		
		request.setAttribute("projectList", projectList);
		request.setAttribute("projectCount", projectCount);
		
		return mapping.findForward("projectList");
	}
	
	public ActionForward resetSearchProject(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		Project project = new Project();
		dform.set("projectInfo",project);
			
		String page = request.getParameter("pager.offset");
		Object[] args = {project,page};
		
		List<Project> projectList = projectService.searchProject(args);
		Integer projectCount = projectService.searchProjectCount(args);
		
		request.setAttribute("projectList", projectList);
		request.setAttribute("projectCount", projectCount);
		
		return mapping.findForward("projectList");
	}
	
	
	
	public ActionForward addProjectModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String pid = request.getParameter("pid");
		
		Project project = projectService.getProjectById(Integer.parseInt(pid));
		ProjectModule pm = new ProjectModule();
		pm.setPmProject(project.getPId());
		pm.setProject(project);
		
		dform.set("moduleInfo", pm);
		
		return mapping.findForward("moduleInput");
	}
	

	
	public ActionForward addProjectVersion(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		String pid = request.getParameter("pid");
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		
		Project project = projectService.getProjectById(Integer.parseInt(pid));
		
		ProjectVersion vtr = new ProjectVersion();
		vtr.setDevelopLeader(new UsrAccount());
		vtr.setTestLeader(new UsrAccount());
		vtr.setPvCreateUser(ua.getId());
		vtr.setPvCreateTime(new Date());
		vtr.setPvProject(project.getPId());
						
		dform.set("versionInfo", vtr);
		
		return mapping.findForward("versionInput");
	}
	
	public ActionForward saveProjectModule(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		
		ProjectModule pm = (ProjectModule)dform.get("moduleInfo");
		projectService.saveProjectModule(pm);
		
		request.setAttribute("tabpageId","moduleInfo");
		
		return mapping.findForward("refreshProjectInfo");
	}
		
	public ActionForward editProjectModule(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
		
		for(ProjectModule pm:project.getModuleList())
		{
			if(pm.getPmId().equals(Integer.valueOf(id)))
			{
				dform.set("moduleInfo", pm);
				break;
			}
		}
				
		return mapping.findForward("moduleInput");
	}
	
	public ActionForward deleteProjectModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
		
		ProjectModule cpm = null;
		for(ProjectModule pm:project.getModuleList())
		{
			if(pm.getPmId().equals(Integer.valueOf(id)))
			{
				pm.setPmFlag(CommonService.DELETE_FLAG);				
				projectService.saveProjectModule(pm);
				cpm = pm;
				break;
			}
		}		

		project.getModuleList().remove(cpm);		
		
		request.setAttribute("tabpageId","moduleInfo");
		
		return mapping.findForward("editProjectInfo");
	}
	
	public ActionForward addModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
				
		for(ProjectModule pm:project.getModuleList())
		{
			if(pm.getPmId().equals(Integer.valueOf(id)))
			{
				ModuleFunction mu = new ModuleFunction();
				mu.setProjectModule(pm);
				mu.setMuModule(pm.getPmId());
				
				dform.set("moduleFunctionInfo", mu);
				break;
			}
		}			
		
		return mapping.findForward("moduleFunctionInput");
	}
	
	public ActionForward addChildModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
				
		for(ProjectModule pm:project.getModuleList())
		{
			for(ModuleFunction mf:pm.getModuleFunctionList())
			{
				ModuleFunction pmf = ProjectServiceImpl.findModuleFunctionById(mf, Integer.valueOf(id));
				if(pmf != null)
				{
					ModuleFunction mu = new ModuleFunction();
					mu.setParentFunction(pmf);
					mu.setMuParent(pmf.getMuId());
					
					dform.set("moduleFunctionInfo", mu);
					break;
				}
			}			
		}			
		
		return mapping.findForward("childFunctionInput");
	}	
	
	public ActionForward saveModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		
		ModuleFunction mu = (ModuleFunction)dform.get("moduleFunctionInfo");
		if(mu.getMuId() == null)
		{			
			for(ProjectModule pm:project.getModuleList())
			{
				if(mu.getMuModule() != null)
				{
					if(pm.getPmId().equals(mu.getMuModule()))
					{
						pm.getModuleFunctionList().add(mu);
						break;
					}
				}
				else if(mu.getMuParent() != null)
				{
					for(ModuleFunction mf:pm.getModuleFunctionList())
					{
						ModuleFunction pmf = ProjectServiceImpl.findModuleFunctionById(mf, mu.getMuParent());
						if(pmf != null)
						{
							pmf.getChildFunctionList().add(mu);
							break;
						}
					}
				}											
			}
		}
		
		projectService.saveModuleFunction(mu);		
		
		request.setAttribute("tabpageId","moduleInfo");
		
		return mapping.findForward("refreshProjectInfo");
	}
	
	public ActionForward editModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
		
		for(ProjectModule pm:project.getModuleList())
		{
			for(ModuleFunction mu:pm.getModuleFunctionList())
			{
				if(mu.getMuId().equals(Integer.valueOf(id)))
				{
					dform.set("moduleFunctionInfo",mu);
					break;
				}
			}			
		}
		
		return mapping.findForward("moduleFunctionInput");
	}
	
	public ActionForward editChildModuleFunction(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		String id = request.getParameter("id");
		
		for(ProjectModule pm:project.getModuleList())
		{
			for(ModuleFunction mu:pm.getAllModuleFunctionList())
			{
				if(mu.getMuId().equals(Integer.valueOf(id)))
				{
					dform.set("moduleFunctionInfo",mu);
					break;
				}
			}			
		}
		
		return mapping.findForward("childFunctionInput");
	}
	
	public ActionForward deleteModuleFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaValidatorForm dform = (DynaValidatorForm) form;
		Project project = (Project)dform.get("projectInfo");
		
		String id = request.getParameter("id");
		
		ModuleFunction cmu = null;
		for(ProjectModule pm:project.getModuleList())
		{			
			for(ModuleFunction mu:pm.getAllModuleFunctionList())
			{
				if(mu.getMuId().equals(Integer.valueOf(id)))
				{
					mu.setMuFlag(CommonService.DELETE_FLAG);					
					projectService.saveModuleFunction(mu);		
					
					cmu = mu;
					break;
				}
			}			
		}
		
		if(cmu.getParentFunction() != null)
		{
			cmu.getParentFunction().getChildFunctionList().remove(cmu);
		}
		else if(cmu.getProjectModule() != null)
		{
			cmu.getProjectModule().getModuleFunctionList().remove(cmu);
		}
		
		request.setAttribute("tabpageId","moduleInfo");
							
		return mapping.findForward("editProjectInfo");
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	
	public TestCaseService getTestCaseService() {
		return testCaseService;
	}

	public void setTestCaseService(TestCaseService testCaseService) {
		this.testCaseService = testCaseService;
	}
}

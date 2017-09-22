/* ============================================================
 * 版权：    king 版权所有 (c) 2006

 * 文件：    org.king.security.web.action.SecurityAction.java
 * 创建日期： 2006-4-20 11:18:02
 * 功能：    {具体要实现的功能}
 * 所含类:   {包含的类}
 * 修改记录：
 * 日期                    作者         内容
 * =============================================================
 * 2006-4-20 11:18:02      ljf        创建文件，实现基本功能
 * ============================================================
 */

/**
 * 
 */
package org.king.security.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;
import org.king.framework.util.MyUtils;
import org.king.framework.web.action.BaseAction;
import org.king.security.domain.UsrAccount;
import org.king.security.service.AccountService;
import org.king.security.service.SecurityService;
import org.mds.common.CommonService;
import org.mds.project.service.ProjectService;

/**
 * <p>
 * SecurityAction.java
 * </p>
 * <p>
 * {功能说明}
 * </p>
 * 
 * <p>
 * <a href="SecurityAction.java.html"><i>查看源代码</i></a>
 * </p>
 * 
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-20
 * 
 * 
 */

public class SecurityAction extends BaseAction {

	private SecurityService securityService;

	private AccountService accountService;
	
	private CommonService commonService;
	
	private ProjectService projectService;
	

	
	/**
	 * 登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		commonService.printRequestMessage(request);
		
		HttpSession session = request.getSession(false);

		if (session == null) {
			session = request.getSession(true);
		}		
		
		ActionMessages errors = new ActionMessages();
		DynaActionForm loginForm = (DynaActionForm) form;
		String userName = (String) loginForm.get("name");
		String password = (String) loginForm.get("password");

		// 检查验证码
		if (session.getAttribute("chkCode") == null || !session.getAttribute("chkCode").equals(request.getParameter("chkCode"))) 
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.chkCodeNoMatch"));
			saveErrors(request, errors);

			return mapping.findForward("faile");
		}

		UsrAccount account = null;

		// 数据库验证
		try {
			account = securityService.login(userName, password);
		} catch (Exception e) {
			 errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.UnKnowError"));
		}
		
		
		if (account == null) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.LoginError"));
		}
		else if ("0".equals(account.getEnabled())) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.noright"));
		}

		// Report any errors we have discovered back to the original form
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("faile");
		}

		// Remove the obsolete form bean
		removeAttribute(mapping, request);

		// Set account Object to session
		session.setAttribute("accountPerson", account);
		//用例编辑权限匹配
		session.setAttribute("accountPersonId", account.getId());
		
		commonService.createSystemTreeMenu(request, projectService);
				
		String destinatedUrl = request.getParameter("DEST_URL");
				
		// If there is a destinated url,forward to this url
		if (destinatedUrl != null && !"null".equals(destinatedUrl)) 
		{
//			request.getRequestDispatcher(destinatedUrl).forward(request,response);
			response.sendRedirect(request.getContextPath()+ destinatedUrl);
			return null;
		} 
											
		return mapping.findForward("success");
	}
	
	
	public ActionForward logout(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

		String userName = (String) request.getSession()
				.getAttribute("userName");

		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}

		// Remove the obsolete form bean
		removeAttribute(mapping, request);
				
		log.info(userName + "---------------------logout");

		return mapping.findForward("success");
	}
	
	/*--------------------------用户管理action方法-----------*/
	/**
	 * 
	 * 查询帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
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
		
		return mapping.findForward("success");

	}
	
	public ActionForward resetSearchAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->listAccount' method");
		}
		DynaValidatorForm dform = (DynaValidatorForm) form;
		dform.set("para", null);
		dform.set("paraPersonName", null);
		dform.set("department", null);
		
		String[] args = { null, null, null,null };

		Integer accountCount = new Integer(0);
		List<UsrAccount> accounts = accountService.findAccount(args);
		accountCount = accountService.getAccountCount(args);

		request.setAttribute("accounts", accounts);
		request.setAttribute("accountCount", accountCount);
		
		this.prepareMetaData(request);
		
		return mapping.findForward("success");

	}

	/**
	 * 装载为了新增帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadAccount4Add(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->loadAccount4Add' method");
		}

		UsrAccount account = new UsrAccount();

		account.setSex("1");
		DynaValidatorForm aform = (DynaValidatorForm) form;
		aform.set("account", account);

		saveToken(request);
		this.prepareMetaData(request);
		
		return mapping.findForward("input");
	}

	/**
	 * 装载帐户为了更新帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadAccount4Edit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->loadAccount4Edit' method");
		}
		ActionMessages errors = new ActionMessages();
		String accountId = (String) request.getParameter("id");

		if (MyUtils.isBlank(accountId)) {
			errors.add("account id is not empty", new ActionMessage(
					"errors.AccountIdNotEmpty"));
		}

		String offset = request.getParameter("offset");
		if (offset == null) {
			offset = "0";
		}

		UsrAccount account = accountService.findAccountById(Integer.valueOf(accountId));

		if (account == null) {
			errors.add("update account",
					new ActionMessage("errors.UnKnowError"));
		} else {
			DynaValidatorForm aform = (DynaValidatorForm) form;
			account.setRepassword(account.getPassword());
			aform.set("account", account);
    	}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			saveToken(request);

			return mapping.getInputForward();
		}
		
		this.prepareMetaData(request);

		return mapping.findForward("edit");
	}
	

	/**
	 * 增加帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->addAccount' method");
		}
		
		if(this.isCancelled(request))
		{
			return mapping.findForward("search");
		}
		
		UsrAccount account = (UsrAccount) ((DynaActionForm) form).get("account");
		account.setEnabled("1");

		if (StringUtils.equals(request.getParameter("encryptPass"), "true")) {
			account.setPassword(MyUtils.toMD5(account.getPassword()));			
		}
		
		accountService.saveAccount(account);
		account.setDepartment(accountService.getDepartmentById(account.getDept()));

		return this.searchAccount(mapping, form, request, response);
	}

	/**
	 * 更新帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->loadPerson4Edit' method");
		}
		
		if(this.isCancelled(request))
		{
			return mapping.findForward("search");
		}
						
		UsrAccount account = (UsrAccount) ((DynaActionForm) form).get("account");

		if (StringUtils.equals(request.getParameter("encryptPass"), "true")) {
			account.setPassword(MyUtils.toMD5(account.getPassword()));
			
		}
				
		accountService.updateAccount(account);

		return mapping.findForward("search");
	}
	
	public ActionForward enableAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		UsrAccount account = accountService.findAccountById(Integer.valueOf(id));

		if(account.getEnabled().equals("1"))
		{
			account.setEnabled("0");
		}
		else
		{
			account.setEnabled("1");
		}
		
		accountService.updateAccount(account);

		return this.searchAccount(mapping, form, request, response);
	}

	/**
	 * 删除帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->loadPerson4Edit' method");
		}
		ActionMessages messages = new ActionMessages();

		String accountId = request.getParameter("id");

		accountService.deleteAccount(Integer.valueOf(accountId));

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"account.deleted"));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("search");
	}

	/**
	 * 装载为了改变密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward load4ChangePWD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->load4ChangePWD' method");
		}

		return mapping.findForward("changePWD");
	}

	/**
	 * 修改帐户密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeAccountPWD(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'securityAction->changeAccountPWD' method");
		}
		ActionMessages errors = new ActionMessages();
		// 原密码
		String oldPWD = (String) request.getParameter("oldPWD");
		// 新密码
		String newPWD = (String) request.getParameter("newPWD");

		UsrAccount account = (UsrAccount) request.getSession().getAttribute("accountPerson");
					
		oldPWD = MyUtils.toMD5(oldPWD);
		
        if(!oldPWD.equals(account.getPassword())){
        	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.oldpassword.mismatch"));
        }
        
        if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return (mapping.findForward("changePWD"));
        }
		
        newPWD = MyUtils.toMD5(newPWD);
        
        account.setPassword(newPWD);
        
        accountService.updateAccount(account);
	
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("success.changepassword"));
	         
        
        if (!errors.isEmpty()) {
			saveErrors(request, errors);
        }
        
		return mapping.findForward("msg");
	}
	
	
	private void prepareMetaData(HttpServletRequest request)
	{
		request.setAttribute("departmentList", accountService.getDepartmentList());
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

		
}

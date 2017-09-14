package org.mds.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.king.framework.service.Service;
import org.mds.project.service.ProjectService;

public interface CommonService extends Service{
	public static Integer DELETE_FLAG = -1;
	public static Integer NORMAL_FLAG = 0;
	
	String[] IMPORT_COLUMN_NAME = {"功能模块","功能点","用例编号","测试目的","测试内容","测试步骤","测试说明","预期输出","测试输出","测试结果","创建人","测试人"};
	
	public void printRequestMessage(HttpServletRequest request);
	
	public List<IcoMenu> getSystemTreeMenu();
	
	public void createSystemTreeMenu(HttpServletRequest request,ProjectService projectService);

}

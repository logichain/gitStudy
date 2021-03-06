package org.mds.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.king.framework.service.Service;
import org.mds.project.service.ProjectService;

public interface CommonService extends Service{
	public static Integer DELETE_FLAG = -1;
	public static Integer NORMAL_FLAG = 0;
	
	String[] IMPORT_COLUMN_NAME = {"所属模块","功能点","用例编号","测试目的","测试内容","测试条件/方法","输出期望","实际输出","测试结果","创建人","测试人","用例类型"};
	
	public void printRequestMessage(HttpServletRequest request);
	
	public List<IcoMenu> getSystemTreeMenu();
	
	public void createSystemTreeMenu(HttpServletRequest request,ProjectService projectService);

}

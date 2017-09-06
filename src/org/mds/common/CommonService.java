package org.mds.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.king.framework.service.Service;
import org.mds.project.service.ProjectService;

public interface CommonService extends Service{
	public static Integer DELETE_FLAG = -1;
	public static Integer NORMAL_FLAG = 0;
	
	public void printRequestMessage(HttpServletRequest request);
	
	public List<IcoMenu> getSystemTreeMenu();
	
	public void createSystemTreeMenu(HttpServletRequest request,ProjectService projectService);

}

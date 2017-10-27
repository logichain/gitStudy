package org.mds.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.king.framework.service.impl.BaseService;
import org.king.security.domain.UsrAccount;
import org.mds.project.bean.Project;
import org.mds.project.service.ProjectService;

public class CommonServiceImpl extends BaseService implements CommonService {
	private IcoMenuDAO icoMenuDAO;
	

	public List<IcoMenu> getSystemTreeMenu() {
		// TODO Auto-generated method stub
		String hqlStr = "from IcoMenu a order by a.sortFlag";
        
        return icoMenuDAO.find(hqlStr);
	}

	public void setIcoMenuDAO(IcoMenuDAO icoMenuDAO) {
		this.icoMenuDAO = icoMenuDAO;
	}

	public IcoMenuDAO getIcoMenuDAO() {
		return icoMenuDAO;
	}
	
	public void printRequestMessage(HttpServletRequest request)
	{		
			System.out.println("-------request attribute-------");
	         System.out.println("RequestURL: "+request.getRequestURL());
	         System.out.println("RequestURI: "+request.getRequestURI());
	         System.out.println("QueryString: "+request.getQueryString());
	         System.out.println("RemoteAddr: "+request.getRemoteAddr());
	         System.out.println("RemoteHost: "+request.getRemoteHost());
	         System.out.println("RemotePort: "+request.getRemotePort());
	         System.out.println("RemoteUser: "+request.getRemoteUser());
	         System.out.println("LocalAddr: "+request.getLocalAddr());
	         System.out.println("LocalName: "+request.getLocalName());
	         System.out.println("LocalPort: "+request.getLocalPort());
	         System.out.println("Method: "+request.getMethod());
	         System.out.println("-------request.getParamterMap()-------");
	         //得到请求的参数Map，注意map的value是String数组类型
	         Map map = request.getParameterMap();
	         Set<String> keySet = map.keySet();
	         for (String key : keySet) {
				String[] values = (String[]) map.get(key);
				for (String value : values) {
					System.out.println(key+"="+value);
				}
			 }
	         System.out.println("--------request.getHeader()--------");
	         //得到请求头的name集合
	         Enumeration<String> em = request.getHeaderNames();
	         while (em.hasMoreElements()) {
				String name = (String) em.nextElement();
				String value = request.getHeader(name);
				System.out.println(name+"="+value);
			}	         		
	}

	@Override
	public void createSystemTreeMenu(HttpServletRequest request,ProjectService projectService) {
		// TODO Auto-generated method stub
		UsrAccount ua = (UsrAccount) request.getSession().getAttribute("accountPerson");
		ArrayList<IcoMenu> menuList = new ArrayList<IcoMenu>();
		
		List<IcoMenu> list = this.getSystemTreeMenu();
		
		if(ua.getAccountName().equals("admin"))
		{
			menuList.addAll(list);
		}
		else
		{
			for(IcoMenu im:list)
			{
				if("1".equals(im.getTitle()))
				{
					continue;
				}
				menuList.add(im);
			}
		}
		
		
		List<Project> pList = projectService.getProjectList();
		for(Project p:pList)
		{
			if(p.getPStatus().equals(Project.PROJECT_STATUS_CLOSE))
			{
				continue;
			}
			if(!ua.getAccountName().equals("admin") && !p.isTeamMember(ua))
			{
				continue;
			}
			IcoMenu m = new IcoMenu();
			m.setIcon("img/project.gif");
			m.setIconopen("img/project.gif");
			m.setId(String.valueOf(100+p.getPId()));
			m.setPid("1");
			m.setMenuName(p.getPName());
			m.setUrl("casemanage.do?method=resetSearchTestCase&pid=" + p.getPId());
						
			menuList.add(m);
					
			m = new IcoMenu();
			m.setIcon("img/project.gif");
			m.setIconopen("img/project.gif");
			m.setId(String.valueOf(100+p.getPId()));
			m.setPid("3");
			m.setMenuName(p.getPName());
			m.setUrl("teststatistics.do?method=resetDataStatistics&pid=" + p.getPId());
						
			menuList.add(m);
			
			m = new IcoMenu();
			m.setIcon("img/project.gif");
			m.setIconopen("img/project.gif");
			m.setId(String.valueOf(100+p.getPId()));
			m.setPid("6");
			m.setMenuName(p.getPName());
			m.setUrl("projectmanage.do?method=testDesignOutput&pid=" + p.getPId());
						
			menuList.add(m);
			
			m = new IcoMenu();
			m.setIcon("img/project.gif");
			m.setIconopen("img/project.gif");
			m.setId(String.valueOf(100+p.getPId()));
			m.setPid("7");
			m.setMenuName(p.getPName());
			m.setUrl("projectmanage.do?method=testResultOutput&pid=" + p.getPId());
						
			menuList.add(m);
			
			m = new IcoMenu();
			m.setIcon("img/project.gif");
			m.setIconopen("img/project.gif");
			m.setId(String.valueOf(100+p.getPId()));
			m.setPid("8");
			m.setMenuName(p.getPName());
			m.setUrl("projectmanage.do?method=caseVersionRefer&pid=" + p.getPId());
						
			menuList.add(m);
		}
			
			request.getSession().setAttribute("menuTreeList", menuList);
	}
	
}

/* ============================================================
 * 版权：    king 版权所有 (c) 2006
 * 文件：    org.king.security.web.filter.AclFilter.java
 * 创建日期： 2006-4-20 13:12:18
 * 功能：    {具体要实现的功能}
 * 所含类:   {包含的类}
 * 修改记录：
 * 日期                    作者         内容
 * =============================================================
 * 2006-4-20 13:12:18      ljf        创建文件，实现基本功能
 * ============================================================
 */

/**
 * 
 */
package org.king.security.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <p>
 * AclFilter.java
 * </p>
 * <p>
 * {功能说明}
 * </p>
 * 
 * <p>
 * <a href="AclFilter.java.html"><i>查看源代码</i></a>
 * </p>
 * 
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-20
 * 
 * 
 */
public class AclFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AclFilter.class);

	/** Acl DAO, responsible for reading acl configuration from file */


	/**
	 * The filter configuration object we are associated with. If this value is
	 * null, this filter instance is not currently configured.
	 */
	private FilterConfig config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	       config = filterConfig;

	        ServletContext context = filterConfig.getServletContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		String[] urls = StringUtils.split(hreq.getRequestURI(), "/");
		String url = urls[(urls.length - 1)];
	
		// check login
		if (!isLogin(hreq) && !url.contains("login")) {
			//为自行跳转到目的画面准备路径
			Enumeration<String> it = hreq.getParameterNames();			
			while(it.hasMoreElements())
			{
				String pn = it.nextElement();
				if(url.contains("?"))
				{
					url = url + "&" + pn + "=" + hreq.getParameter(pn);
				}
				else
				{
					url = url + "?" + pn + "=" + hreq.getParameter(pn);
				}				
			}			
			request.setAttribute("DEST_URL", "/" + url);
			//----------------------
			config.getServletContext().getRequestDispatcher("/login.do").forward(request, response);

			return;
		}

		//写入日志
		logger.info(url);
		
		// Pass control on to the next filter
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		config = null;
	}

	private boolean isLogin(HttpServletRequest hreq) {
		boolean isLogin = false;
		HttpSession session = hreq.getSession();

		isLogin = (session != null) && !session.isNew()	&& (session.getAttribute("accountPerson") != null);

		return isLogin;
	}
}

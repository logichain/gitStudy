/* ============================================================
 * 版权：    king 版权所有 (c) 2006
 * 文件：    org.king.security.service.SecurityService.java
 * 创建日期： 2006-4-19 17:14:45
 * 功能：    {具体要实现的功能}
 * 所含类:   {包含的类}
 * 修改记录：
 * 日期                    作者         内容
 * =============================================================
 * 2006-4-19 17:14:45      ljf        创建文件，实现基本功能
 * ============================================================
 */

/**
 * 
 */
package org.king.security.service;

import org.king.framework.exception.BusinessException;
import org.king.framework.service.Service;
import org.king.security.domain.UsrAccount;

/**
 * <p>
 * SecurityService.java
 * </p>
 * <p>
 * {功能说明}
 * </p>
 * 
 * <p>
 * <a href="SecurityService.java.html"><i>查看源代码</i></a>
 * </p>
 * 
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-19
 * 
 * 
 */
public interface SecurityService extends Service {

	/**
	 * 初始化安全
	 * 
	 * @throws BusinessException
	 */
	public void initSecurity() throws BusinessException;

	/**
	 * 登录
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public UsrAccount login(String name, String password) throws BusinessException;

		
}

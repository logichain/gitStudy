
package org.king.security.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.king.framework.exception.BusinessException;
import org.king.framework.service.impl.BaseService;
import org.king.framework.util.MyUtils;
import org.king.security.dao.AccountDAO;
import org.king.security.domain.UsrAccount;
import org.king.security.service.SecurityService;

/**
 * <p>
 * SecurityServiceImpl.java
 * </p>
 * <p>
 * {功能说明}
 * </p>
 * 
 * <p>
 * <a href="SecurityServiceImpl.java.html"><i>查看源代码</i></a>
 * </p>
 * 
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-19
 * 
 * 
 */
public class SecurityServiceImpl extends BaseService implements SecurityService {

	private static Log log = LogFactory.getLog(SecurityServiceImpl.class);

	private AccountDAO accountDAO;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.king.security.service.SecurityService#initSecurity()
	 */
	public void initSecurity() throws BusinessException {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.king.security.service.SecurityService#login(java.lang.String,
	 * java.lang.String)
	 */
	public UsrAccount login(String name, String password) throws BusinessException {
		UsrAccount account = findAccountByName(name);

		if ((account == null) || !account.getPassword().equals(MyUtils.toMD5(password))) {
			throw new BusinessException("password not match");
		}

		return account;
	}

	public UsrAccount findAccountByName(String name) throws BusinessException {
		if (MyUtils.isBlank(name)) {
			return null;
		}

		UsrAccount account = accountDAO.findAccountByName(name);
		return account;
	}
	
}

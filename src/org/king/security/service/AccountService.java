
package org.king.security.service;

import java.io.Serializable;
import java.util.List;

import org.king.framework.exception.BusinessException;
import org.king.framework.service.Service;
import org.king.security.domain.Department;
import org.king.security.domain.UsrAccount;

/**
 * <p> AccountService.java </p>
 * <p> {功能说明} </p>
 *
 * <p><a href="AccountService.java.html"><i>查看源代码</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-18
 * 
 *
 */
public interface AccountService extends Service{

	/**
	 * 通过id查找帐户
	 * @param id
	 * @return
	 */
	public UsrAccount findAccountById(Serializable id);
	
	/**
	 * 通过帐户名得到帐户
	 * @param name
	 * @return
	 * @throws BusinessException
	 */
	public UsrAccount findAccountByName(String name) throws BusinessException;
	
	/**
	 * 得到所有帐户
	 * @return
	 * @throws BusinessException
	 */
	public List<UsrAccount> findAllAccount() throws BusinessException;
	
	/**
	 * 不同查询条件查询帐户
	 * @param args
	 * @return
	 * @throws BusinessException
	 */
	public List<UsrAccount> findAccount(String[] args) throws BusinessException;
	
	/**
	 * 保存帐户
	 * @param account
	 * @throws BusinessException
	 * @throws AccountAlreadyExistException
	 */
	public void saveAccount(UsrAccount account) throws BusinessException;
	
	/**
	 * 更新帐户
	 * @param account
	 * @throws BusinessException
	 */
	public void updateAccount(UsrAccount account) throws BusinessException;

	/**
	 * 删除帐户
	 * @param id
	 * @throws BusinessException
	 */
	public void deleteAccount(Serializable id) throws BusinessException;
		
    /**
     * 得到帐户数
     * @param args
     * @return
     */
	public Integer getAccountCount(final String[] args);
	
	public List<Department> getDepartmentList();
	public Department getDepartmentById(Integer id);
}

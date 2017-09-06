
package org.king.security.service;

import java.io.Serializable;
import java.util.List;

import org.king.framework.exception.BusinessException;
import org.king.framework.service.Service;
import org.king.security.domain.Department;
import org.king.security.domain.UsrAccount;

/**
 * <p> AccountService.java </p>
 * <p> {����˵��} </p>
 *
 * <p><a href="AccountService.java.html"><i>�鿴Դ����</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-18
 * 
 *
 */
public interface AccountService extends Service{

	/**
	 * ͨ��id�����ʻ�
	 * @param id
	 * @return
	 */
	public UsrAccount findAccountById(Serializable id);
	
	/**
	 * ͨ���ʻ����õ��ʻ�
	 * @param name
	 * @return
	 * @throws BusinessException
	 */
	public UsrAccount findAccountByName(String name) throws BusinessException;
	
	/**
	 * �õ������ʻ�
	 * @return
	 * @throws BusinessException
	 */
	public List<UsrAccount> findAllAccount() throws BusinessException;
	
	/**
	 * ��ͬ��ѯ������ѯ�ʻ�
	 * @param args
	 * @return
	 * @throws BusinessException
	 */
	public List<UsrAccount> findAccount(String[] args) throws BusinessException;
	
	/**
	 * �����ʻ�
	 * @param account
	 * @throws BusinessException
	 * @throws AccountAlreadyExistException
	 */
	public void saveAccount(UsrAccount account) throws BusinessException;
	
	/**
	 * �����ʻ�
	 * @param account
	 * @throws BusinessException
	 */
	public void updateAccount(UsrAccount account) throws BusinessException;

	/**
	 * ɾ���ʻ�
	 * @param id
	 * @throws BusinessException
	 */
	public void deleteAccount(Serializable id) throws BusinessException;
		
    /**
     * �õ��ʻ���
     * @param args
     * @return
     */
	public Integer getAccountCount(final String[] args);
	
	public List<Department> getDepartmentList();
	public Department getDepartmentById(Integer id);
}

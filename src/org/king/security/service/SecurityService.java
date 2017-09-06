/* ============================================================
 * ��Ȩ��    king ��Ȩ���� (c) 2006
 * �ļ���    org.king.security.service.SecurityService.java
 * �������ڣ� 2006-4-19 17:14:45
 * ���ܣ�    {����Ҫʵ�ֵĹ���}
 * ������:   {��������}
 * �޸ļ�¼��
 * ����                    ����         ����
 * =============================================================
 * 2006-4-19 17:14:45      ljf        �����ļ���ʵ�ֻ�������
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
 * {����˵��}
 * </p>
 * 
 * <p>
 * <a href="SecurityService.java.html"><i>�鿴Դ����</i></a>
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
	 * ��ʼ����ȫ
	 * 
	 * @throws BusinessException
	 */
	public void initSecurity() throws BusinessException;

	/**
	 * ��¼
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public UsrAccount login(String name, String password) throws BusinessException;

		
}

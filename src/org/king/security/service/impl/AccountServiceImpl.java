package org.king.security.service.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.king.framework.dao.MyQuery;
import org.king.framework.exception.BusinessException;
import org.king.framework.service.impl.BaseService;
import org.king.security.dao.AccountDAO;
import org.king.security.dao.DepartmentDAO;
import org.king.security.domain.Department;
import org.king.security.domain.UsrAccount;
import org.king.security.service.AccountService;
import org.mds.test.bean.CaseStatus;

/**
 * <p> AccountServiceImpl.java </p>
 * <p> {功能说明} </p>
 *
 * <p><a href="AccountServiceImpl.java.html"><i>查看源代码</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-4-20
 * 
 *
 */
public class AccountServiceImpl extends BaseService implements AccountService {

	private AccountDAO accountDAO;

	private DepartmentDAO departmentDAO;
	
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#findAccountById(java.io.Serializable)
	 */
	public UsrAccount findAccountById(Serializable id) {
		return accountDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#findAccountByName(java.lang.String)
	 */
	public UsrAccount findAccountByName(String name) throws BusinessException {
		return accountDAO.findAccountByName(name);
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#findAllAccount()
	 */
	public List findAllAccount() throws BusinessException {
		return accountDAO.getAll();
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#findAccount(java.lang.String[])
	 */
	public List findAccount(String[] args) throws BusinessException {
    	String number = args[0];
    	String personName = args[1];
    	String page = args[2];
    	
    	String department = args[3];
    	
    	List entitys = null;
        
    	String hqlStr = "from UsrAccount a where 1=1 ";
    	hqlStr += (StringUtils.isNotEmpty(number)?" and a.accountName like ?" : "");
    	
    	hqlStr += (StringUtils.isNotEmpty(personName)?" and a.personName like ?" : "");
    	
    	hqlStr += (StringUtils.isNotEmpty(department)?" and a.dept = ?" : "");
    	
    	MyQuery myQuery = new MyQuery();
//    	myQuery.setPageSize(SystemParameter.PAGE_ITEM_COUNT);
    	
        if (StringUtils.isNotEmpty(number)) {
        	myQuery.addPara("%" + number + "%", Types.VARCHAR);
        }

        if (StringUtils.isNotEmpty(personName)) {
        	myQuery.addPara("%" + personName + "%", Types.VARCHAR);
        }
        
        if (StringUtils.isNotEmpty(department)) {
        	myQuery.addPara(Integer.valueOf(department), Types.INTEGER);
        }
        
        if (StringUtils.isNumeric(page)) {
        	myQuery.setPageStartNo(Integer.parseInt(page));
        }else {
        	myQuery.setPageStartNo(0);
        }
        myQuery.setOrderby(" order by a.accountName");
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
        entitys = accountDAO.find(myQuery);
        return entitys;
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#saveAccount(org.king.security.domain.UsrAccount)
	 */
	public void saveAccount(UsrAccount account) throws BusinessException {
		if(account.getId() != null && !account.getId().equals(0))
		{
			accountDAO.update(account);
		}
		else
		{
			accountDAO.save(account);
		}
	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#updateAccount(org.king.security.domain.UsrAccount)
	 */
	public void updateAccount(UsrAccount account) throws BusinessException {
		
		accountDAO.update(account);

	}

	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#deleteAccount(java.io.Serializable)
	 */
	public void deleteAccount(Serializable id) throws BusinessException {
		if (id == null) {
            throw new BusinessException("account can't be null");
        }
		accountDAO.delete(findAccountById(id));

	}
	
	/* (non-Javadoc)
	 * @see org.king.security.service.AccountService#getAccountCount(java.lang.String[])
	 */
	public Integer getAccountCount(String[] args) {
	   	String number = args[0];
    	String personName = args[1];
    	
    	String department = args[3];
    	
    	String hqlStr = "select count(*) from UsrAccount a where 1=1 ";
    	hqlStr += (StringUtils.isNotEmpty(number)?" and a.accountName like ?" : "");
    	
    	hqlStr += (StringUtils.isNotEmpty(personName)?" and a.personName like ?" : "");
    	hqlStr += (StringUtils.isNotEmpty(department)?" and a.dept = ?" : "");
    	
    	MyQuery myQuery = new MyQuery();

        if (StringUtils.isNotEmpty(number)) {
        	myQuery.addPara("%" + number + "%", Types.VARCHAR);
        }

        if (StringUtils.isNotEmpty(personName)) {
        	myQuery.addPara("%" + personName + "%", Types.VARCHAR);
        }
        
        if (StringUtils.isNotEmpty(department)) {
        	myQuery.addPara(Integer.valueOf(department), Types.INTEGER);
        }
        
        myQuery.setQueryString(hqlStr);
                
        return accountDAO.getFindCount(myQuery);
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	
	public List<Department> getDepartmentList() {
		// TODO Auto-generated method stub
		String hqlStr = "from Department a where a.DFlag != " + CaseStatus.DELETE_STATUS;
		return departmentDAO.find(hqlStr);
	}

	
	public Department getDepartmentById(Integer id) {
		// TODO Auto-generated method stub
		return departmentDAO.get(id);
	}

}

package org.king.security.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.king.framework.dao.BaseDAO;
import org.king.framework.dao.MyQuery;
import org.king.framework.util.MyUtils;
import org.king.security.dao.AccountDAO;
import org.king.security.domain.UsrAccount;

/**
 * Data access object (DAO) for domain model class Account.
 * 
 * @see org.king.security.dao.hibernate.Account
 * @author MyEclipse - Hibernate Tools
 */
public class AccountDAOHibernate implements AccountDAO {

	private static final Log log = LogFactory.getLog(AccountDAOHibernate.class);

	protected void initDao() {
		// do nothing
	}

	private BaseDAO baseDAO;

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public List<UsrAccount> find(MyQuery myQuery) {
		log.debug("finding UsrAccount instance by myQuery");
		return baseDAO.findEntity(myQuery);
	}

	public List<UsrAccount> find(String query) {
		log.debug("finding UsrAccount instance by query");
		return baseDAO.findEntity(query);
	}

	public UsrAccount get(Serializable id) {
		log.debug("getting UsrAccount instance by id");
		return (UsrAccount) baseDAO.getEntity(UsrAccount.class, id);
	}

	public List<UsrAccount> getAll() {
		String allHql = "from UsrAccount";
		return baseDAO.findEntity(allHql);
	}

	public void save(UsrAccount transientInstance) {
		log.debug("saving Account instance");
		baseDAO.saveEntity(transientInstance);
	}

	public void update(UsrAccount transientInstance) {
		log.debug("updating Account instance");
		baseDAO.updateEntity(transientInstance);
	}

	public void delete(UsrAccount persistentInstance) {
		log.debug("deleting Account instance");

		baseDAO.removeEntity(persistentInstance);
	}

	public UsrAccount findAccountByName(String name) {
		if (MyUtils.isBlank(name)) {
			return null;
		}

		List accounts = find("from UsrAccount a where a.accountName='" + name	+ "' order by a.accountName");

		if (MyUtils.isBlank(accounts)) {
			return null;
		}

		return (UsrAccount) accounts.get(0);
	}
	
	public int getFindCount(MyQuery myQuery) {
		// TODO Auto-generated method stub
		int rtn = 0;
		List list = baseDAO.findEntity(myQuery);
		if(list != null && !list.isEmpty())
		{
			if(list.get(0) instanceof Long)
			{
				rtn = ((Long)list.get(0)).intValue();
			}
			else
			{
				rtn = (Integer)list.get(0);
			}			
		}
		
		return rtn;
	}
		
}
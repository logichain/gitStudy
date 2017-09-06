package org.king.security.dao;

import java.io.Serializable;
import java.util.List;

import org.king.framework.dao.DAO;
import org.king.framework.dao.MyQuery;
import org.king.security.domain.UsrAccount;

/**
 * <p> UsrAccountDAO.java </p>
 * <p> {功能说明} </p>
 *
 * <p><a href="UsrAccountDAO.java.html"><i>查看源代码</i></a></p>  
 *
 * @author 
 * @version 0.1
 * @since 2006-4-20
 * 
 *
 */
public interface AccountDAO extends DAO {

	public List<UsrAccount> find(MyQuery myQuery);
	
	public List<UsrAccount> find(String query);
	 
	public UsrAccount get(Serializable id);
	
	public List<UsrAccount> getAll();
	
	public void save(UsrAccount transientInstance);
	
    public void update(UsrAccount transientInstance);
    
    public void delete(UsrAccount persistentInstance);
    
    //自定义方法
    
    public UsrAccount findAccountByName(String name);
    
    public int getFindCount(MyQuery myQuery);
    
}

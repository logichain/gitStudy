package org.king.security.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * UsrAccount entity. @author MyEclipse Persistence Tools
 */

public class UsrAccount extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String accountName;
	private String password;
	private String enabled;
	private String personCode;
	private String personName;
	private String sex;
	private String email;
	private String phone;
	private Integer dept;
	private Integer status;
	private Date entryDate;

	private String birthdayStr;
	private String entryDateStr;

    private String repassword;
    private Department department;
    
	// Constructors

	/** default constructor */
	public UsrAccount() {
	}

	/** minimal constructor */
	public UsrAccount(String accountName, String personCode, String personName) {
		this.accountName = accountName;
		this.personCode = personCode;
		this.personName = personName;
	}

	/** full constructor */
	public UsrAccount(String accountName, String password, String enabled,
			String personCode, String personName, String sex, String email,
			String phone, Integer dept,
			Integer status, Date entryDate) {
		this.accountName = accountName;
		this.password = password;
		this.enabled = enabled;
		this.personCode = personCode;
		this.personName = personName;
		this.sex = sex;
		this.email = email;
		this.phone = phone;
		this.dept = dept;
		this.status = status;
		this.entryDate = entryDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getPersonCode() {
		return this.personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	

	public Integer getDept() {
		if(dept != null && dept.equals(0))
		{
			return null;
		}
		return this.dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof UsrAccount)
		{
			UsrAccount ua = (UsrAccount)arg0;
			
			return ua.getId().equals(id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return accountName + personName;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setEntryDateStr(String entryDateStr) {
		if(!"".equals(entryDateStr))
		{
			try {
				entryDate = DateFormat.getDateInstance().parse(entryDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.entryDateStr = entryDateStr;
	}

	public String getEntryDateStr() {
		if(entryDate != null)
		{
			return DateFormat.getDateInstance().format(entryDate);
		}
		return entryDateStr;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}
}
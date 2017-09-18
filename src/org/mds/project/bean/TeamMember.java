package org.mds.project.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.king.security.domain.UsrAccount;

/**
 * TeamMember entity. @author MyEclipse Persistence Tools
 */

public class TeamMember extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tmId;
	private Integer tmProject;
	private Integer tmModuleFunction;
	private Integer tmAccount;
	private Integer tmFlag = 0;
	
	private UsrAccount account = new UsrAccount();
	private ArrayList<MemberFunctionReference> memberFunctionReferenceList = new ArrayList<MemberFunctionReference>();

	// Constructors

	/** default constructor */
	public TeamMember() {
	}

	/** full constructor */
	public TeamMember(Integer tmProject, Integer tmAccount, Integer tmFlag) {
		this.tmProject = tmProject;
		this.tmAccount = tmAccount;
		this.tmFlag = tmFlag;
	}

	// Property accessors

	public Integer getTmId() {
		return this.tmId;
	}

	public void setTmId(Integer tmId) {
		this.tmId = tmId;
	}

	public Integer getTmProject() {
		return this.tmProject;
	}

	public void setTmProject(Integer tmProject) {
		this.tmProject = tmProject;
	}

	public Integer getTmAccount() {
		return this.tmAccount;
	}

	public void setTmAccount(Integer tmAccount) {
		this.tmAccount = tmAccount;
	}

	public Integer getTmFlag() {
		return this.tmFlag;
	}

	public void setTmFlag(Integer tmFlag) {
		this.tmFlag = tmFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof TeamMember)
		{
			TeamMember tm = (TeamMember) arg0;
			
			return tm.getTmId().equals(tmId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return tmId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tmProject + "," + tmAccount;
	}

	public void setAccount(UsrAccount account) {
		this.account = account;
	}

	public UsrAccount getAccount() {
		return account;
	}

	public Integer getTmModuleFunction() {
		return tmModuleFunction;
	}

	public void setTmModuleFunction(Integer tmModuleFunction) {
		this.tmModuleFunction = tmModuleFunction;
	}
		
	public void setMemberFunctionReferenceSet(Set<MemberFunctionReference> mfrSet) {
		memberFunctionReferenceList.clear();
		memberFunctionReferenceList.addAll(mfrSet);		
	}

	public Set<MemberFunctionReference> getMemberFunctionReferenceSet() {
		Set<MemberFunctionReference> set = new HashSet<MemberFunctionReference>();
		set.addAll(memberFunctionReferenceList);
		
		return set;
	}

	public ArrayList<MemberFunctionReference> getMemberFunctionReferenceList() {
		return memberFunctionReferenceList;
	}

	public void setMemberFunctionReferenceList(ArrayList<MemberFunctionReference> memberFunctionReferenceList) {
		this.memberFunctionReferenceList = memberFunctionReferenceList;
	}

}
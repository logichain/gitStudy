package org.mds.project.bean;

import org.mds.common.CommonService;

/**
 * ModuleFunction entity. @author MyEclipse Persistence Tools
 */

public class MemberFunctionReference extends org.king.framework.domain.BaseObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer mfrId;
	private Integer mfrModuleFunction;
	private Integer mfrTeamMember;
	private Integer mfrFlag = CommonService.NORMAL_FLAG;
	
	private ModuleFunction moduleFunction;

	// Constructors

	/** default constructor */
	public MemberFunctionReference() {
	}

	
	// Property accessors

	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof MemberFunctionReference)
		{
			MemberFunctionReference cs = (MemberFunctionReference)arg0;
			
			return cs.getMfrId().equals(mfrId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return mfrId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mfrId + " ";
	}


	public Integer getMfrId() {
		return mfrId;
	}


	public void setMfrId(Integer mfrId) {
		this.mfrId = mfrId;
	}


	public Integer getMfrModuleFunction() {
		return mfrModuleFunction;
	}


	public void setMfrModuleFunction(Integer mfrModuleFunction) {
		this.mfrModuleFunction = mfrModuleFunction;
	}


	public Integer getMfrTeamMember() {
		return mfrTeamMember;
	}


	public void setMfrTeamMember(Integer mfrTeamMember) {
		this.mfrTeamMember = mfrTeamMember;
	}


	public Integer getMfrFlag() {
		return mfrFlag;
	}


	public void setMfrFlag(Integer mfrFlag) {
		this.mfrFlag = mfrFlag;
	}


	public ModuleFunction getModuleFunction() {
		return moduleFunction;
	}


	public void setModuleFunction(ModuleFunction moduleFunction) {
		this.moduleFunction = moduleFunction;
	}

	
}
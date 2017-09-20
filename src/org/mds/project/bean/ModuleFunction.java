package org.mds.project.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * ModuleFunction entity. @author MyEclipse Persistence Tools
 */

public class ModuleFunction extends org.king.framework.domain.BaseObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer muId;
	private Integer muModule;
	private String muName;
	private Integer muFlag = 0;
	private String muRemark;
	private Integer muParent;
	
	private ProjectModule projectModule;
	private ModuleFunction parentFunction;
	private ArrayList<ModuleFunction> childFunctionList = new ArrayList<ModuleFunction>();

	// Constructors

	/** default constructor */
	public ModuleFunction() {
	}

	/** full constructor */
	public ModuleFunction(Integer muModule, String muName, Integer muFlag) {
		this.muModule = muModule;
		this.muName = muName;
		this.muFlag = muFlag;
	}

	// Property accessors

	public Integer getMuId() {
		return this.muId;
	}

	public void setMuId(Integer muId) {
		this.muId = muId;
	}

	public Integer getMuModule() {
		return this.muModule;
	}

	public void setMuModule(Integer muModule) {
		this.muModule = muModule;
	}

	public String getMuName() {
		return this.muName;
	}
	
	public String getEntireName() {
		String rtn = "";
		
		if(this.muId != null)
		{
			rtn = this.getParentFunctionEntireName(this, muName,true);				
		}		
				
		return rtn;		
	}
	
	public String getEntireModuleFunctionName() {
		String rtn = "";
		
		if(this.muId != null)
		{
			rtn = this.getParentFunctionEntireName(this, muName,false);				
		}		
				
		return rtn;		
	}
	
	public String getProjectModuleName() {
				
		ModuleFunction mf = this;
		while(mf.parentFunction != null)
		{
			mf = mf.parentFunction;
		}
				
		return mf.getProjectModule().getPmName();		
	}
	
	private String getParentFunctionEntireName(ModuleFunction mf,String name,boolean hasPmName)
	{
		String rtn = name;
		
		if(mf.getParentFunction() != null)
		{
			rtn = this.getParentFunctionEntireName(mf.getParentFunction(), mf.getParentFunction().getMuName() + "¡ú" + name,hasPmName);
		}
		else if(mf.getProjectModule() != null && hasPmName)
		{
			rtn = mf.getProjectModule().getPmName() + "¡ú" + name;
		}
		
		return rtn;
	}

	public void setMuName(String muName) {
		this.muName = muName;
	}

	public Integer getMuFlag() {
		return this.muFlag;
	}

	public void setMuFlag(Integer muFlag) {
		this.muFlag = muFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof ModuleFunction)
		{
			ModuleFunction cs = (ModuleFunction)arg0;
			
			return cs.getMuId().equals(muId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return muId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return muId + muName;
	}

	public void setProjectModule(ProjectModule projectModule) {
		this.projectModule = projectModule;
	}

	public ProjectModule getProjectModule() {
		return projectModule;
	}

	public void setMuRemark(String muRemark) {
		this.muRemark = muRemark;
	}

	public String getMuRemark() {
		return muRemark;
	}
	
	public Set<ModuleFunction> getChildFunctionSet() {
		Set<ModuleFunction> set = new HashSet<ModuleFunction>();
		set.addAll(childFunctionList);
		
		return set;
	}

	public void setChildFunctionSet(Set<ModuleFunction> moduleFunctionSet) {
		childFunctionList.clear();
		childFunctionList.addAll(moduleFunctionSet);
	}

	public void setChildFunctionList(ArrayList<ModuleFunction> childFunctionList) {
		this.childFunctionList = childFunctionList;
	}

	public ArrayList<ModuleFunction> getChildFunctionList() {
		return childFunctionList;
	}

	public void setParentFunction(ModuleFunction parentFunction) {
		this.parentFunction = parentFunction;
	}

	public ModuleFunction getParentFunction() {
		return parentFunction;
	}

	public void setMuParent(Integer muParent) {
		this.muParent = muParent;
	}

	public Integer getMuParent() {
		return muParent;
	}

}
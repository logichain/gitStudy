package org.mds.project.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.mds.project.service.impl.ProjectServiceImpl;

/**
 * ProjectModule entity. @author MyEclipse Persistence Tools
 */

public class ProjectModule extends org.king.framework.domain.BaseObject
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pmId;
	private Integer pmProject;
	private String pmName;
	private Integer pmFlag = 0;
	private String pmRemark;
	
	private Project project;
	private ArrayList<ModuleFunction> moduleFunctionList = new ArrayList<ModuleFunction>();

	// Constructors

	
	/** default constructor */
	public ProjectModule() {
	}

	/** full constructor */
	public ProjectModule(Integer pmProject, String pmName, Integer pmFlag) {
		this.pmProject = pmProject;
		this.pmName = pmName;
		this.pmFlag = pmFlag;
	}

	// Property accessors

	public Integer getPmId() {
		return this.pmId;
	}

	public void setPmId(Integer pmId) {
		this.pmId = pmId;
	}

	public Integer getPmProject() {
		return this.pmProject;
	}

	public void setPmProject(Integer pmProject) {
		this.pmProject = pmProject;
	}

	public String getPmName() {
		return this.pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public Integer getPmFlag() {
		return this.pmFlag;
	}

	public void setPmFlag(Integer pmFlag) {
		this.pmFlag = pmFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof ProjectModule)
		{
			ProjectModule cs = (ProjectModule)arg0;
			
			return cs.getPmId().equals(pmId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return pmId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pmId + pmName;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}
	
	public Set<ModuleFunction> getModuleFunctionSet() {
		Set<ModuleFunction> set = new HashSet<ModuleFunction>();
		set.addAll(moduleFunctionList);
		
		return set;
	}

	public void setModuleFunctionSet(Set<ModuleFunction> moduleFunctionSet) {
		moduleFunctionList.clear();
		moduleFunctionList.addAll(moduleFunctionSet);
	}

	public void setModuleFunctionList(ArrayList<ModuleFunction> moduleFunctionList) {
		this.moduleFunctionList = moduleFunctionList;
	}

	public ArrayList<ModuleFunction> getModuleFunctionList() {
		return moduleFunctionList;
	}

	public void setPmRemark(String pmRemark) {
		this.pmRemark = pmRemark;
	}

	public String getPmRemark() {
		return pmRemark;
	}

	public ArrayList<ModuleFunction> getAllModuleFunctionList()
	{
		 ArrayList<ModuleFunction> rtn = new  ArrayList<ModuleFunction>();
		 		
		 for(ModuleFunction mf:this.getModuleFunctionList())
		 {
			 ProjectServiceImpl.addAllFunctionIntoList(rtn, mf);
		 }
		 
		 
		 return rtn;
	}


}
package org.mds.project.service;

import java.util.List;

import org.king.framework.service.Service;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.bean.TeamMember;

public interface ProjectService extends Service {
	public List<Project> searchProject(Object[] args);
	public Integer searchProjectCount(Object[] args);
	
	public void saveProject(Project p);
	public void saveProjectVersion(ProjectVersion vtr,String uploadPath);
	public void saveProjectModule(ProjectModule pm);

	public void saveModuleFunction(ModuleFunction mu);
	public void saveTeamMember(TeamMember tm);
	public void deleteProjectVersion(ProjectVersion vtr);
	public ProjectVersion getProjectVersionById(Integer pvId);
	
	public List<Project> getProjectList();
	
	public Project getProjectById(Integer id);
	public ProjectModule getProjectModuleById(Integer id);	
	
}

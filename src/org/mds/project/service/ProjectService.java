package org.mds.project.service;

import java.util.List;

import org.king.framework.service.Service;
import org.king.security.domain.UsrAccount;
import org.mds.project.bean.JobTask;
import org.mds.project.bean.JobTaskStatus;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectModule;
import org.mds.project.bean.TaskAttachment;
import org.mds.project.bean.TeamMember;
import org.mds.project.bean.ProjectVersion;

public interface ProjectService extends Service {
	public List<Project> searchProject(Object[] args);
	public Integer searchProjectCount(Object[] args);
	
	public void saveProject(Project p);
	public void saveProjectVersion(ProjectVersion vtr,String uploadPath);
	public void saveProjectModule(ProjectModule pm);
	public void saveJobTask(JobTask jobTask);
	public void saveModuleFunction(ModuleFunction mu);
	public void saveTeamMember(TeamMember tm);
	public void saveTaskAttachment(TaskAttachment ta,String uploadPath);
	public void saveTaskAttachment(TaskAttachment ta);
	
	public void deleteProjectVersion(ProjectVersion vtr);
	
	public List<Project> getProjectList();
	public List<JobTaskStatus> getJobTaskStatusList();
	public boolean isTeamMember(Integer projectId,Integer accountId);
	public boolean isTeamMember(Project p,UsrAccount a);
	
	public Project getProjectById(Integer id);
	public ProjectModule getProjectModuleById(Integer id);	
	
}

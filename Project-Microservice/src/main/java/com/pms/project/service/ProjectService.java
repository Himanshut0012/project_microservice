package com.pms.project.service;

import java.util.List;

import com.pms.project.dto.ProjectCreationDTO;
import com.pms.project.dto.ProjectDTO;
import com.pms.project.dto.ProjectUpdatedDTO;
import com.pms.project.model.Project;

public interface ProjectService {

	void addProject(ProjectCreationDTO projectCreationDTO);
	
	ProjectDTO getProjectById(int projectId);
	
	void updateProject(ProjectUpdatedDTO projectUpdatedDTO, int projectId);
	
	void deleteProject(int projectId);
	
	List<ProjectDTO> getProjectOfManager(int managerId);
	
	List<ProjectDTO> getAllProject();

}

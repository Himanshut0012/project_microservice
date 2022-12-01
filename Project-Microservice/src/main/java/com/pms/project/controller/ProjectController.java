package com.pms.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.project.dto.ProjectCreationDTO;
import com.pms.project.dto.ProjectDTO;
import com.pms.project.dto.ProjectUpdatedDTO;
import com.pms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProjectController {
	
	private final ProjectService projectService;
	
	@GetMapping
	public List<ProjectDTO> getAllProject() {
		return this.projectService.getAllProject();
	}
	
	@GetMapping("/{projectId}")
	public ProjectDTO getProjectById(@PathVariable int projectId) {
		return this.projectService.getProjectById(projectId);
	}
	
	
	@PostMapping
	public void addProject(@RequestBody ProjectCreationDTO projectCreationDTO) {
		this.projectService.addProject(projectCreationDTO);
	}
	
	@PutMapping("/{projectId}")
	public void updateProject(@RequestBody ProjectUpdatedDTO projectUpdatedDTO, @PathVariable int projectId) {
		this.projectService.updateProject(projectUpdatedDTO, projectId);
	}
	
	@DeleteMapping("/{projectId}")
	public void deleteProject(@PathVariable int projectId) {
		this.projectService.deleteProject(projectId);
	}
	
	@GetMapping("/manager/project/{managerId}")
	public List<ProjectDTO> getProjectOfManager(@PathVariable int managerId){
		return this.projectService.getProjectOfManager(managerId);
	}

}

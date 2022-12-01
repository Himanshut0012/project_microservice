package com.pms.project.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pms.project.dao.ProjectRepository;
import com.pms.project.dto.ProjectContributorCreateDTO;
import com.pms.project.dto.ProjectContributorDTO;
import com.pms.project.dto.ProjectCreationDTO;
import com.pms.project.dto.ProjectDTO;
import com.pms.project.dto.ProjectUpdatedDTO;
import com.pms.project.model.Project;
import com.pms.project.model.ProjectContributors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ManagerDetail managerDetail;
	private final BillingDetails billingDetails;
	private final StatusDetails statusDetails;
	private final ProjectContributorDetails projectContributorDetails;
	private final ModelMapper modelMapper;
	private final RestTemplate restTemplate;

	@Override
	public void addProject(ProjectCreationDTO projectCreationDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Project project = modelMapper.map(projectCreationDTO, Project.class);
		this.projectRepository.save(project);
	}

	@Override
	public ProjectDTO getProjectById(int projectId) {
		Project project = this.projectRepository.findByProjectId(projectId);
		ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
		projectDTO.setContributors(projectContributorDetails.getProjectContributors(projectId));
		projectDTO.setBillingType(billingDetails.knowBillingType(projectDTO.getBillingId()));
		projectDTO.setManagerName(managerDetail.KnowManagerName(projectDTO.getManagerId()));
		projectDTO.setProjectStatus(statusDetails.knowProjectStatus(projectDTO.getStatusId()));
		return projectDTO;
	}

	@Override
	public void updateProject(ProjectUpdatedDTO projectUpdatedDTO, int projectId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<ProjectContributorDTO> updatedProjectContributorsDTO = projectUpdatedDTO.getContributors();
		List<ProjectContributors> previousProjectContributors = projectContributorDetails.getProjectContributorForUpdateAPI(projectId);

		if (updatedProjectContributorsDTO.size() != 0 && previousProjectContributors.size() != 0) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			List<ProjectContributors> updatedProjectContributors = updatedProjectContributorsDTO.stream()
					.map(dto -> modelMapper.map(dto, ProjectContributors.class)).toList();
			List<ProjectContributors> contributorsToBeDeleted = previousProjectContributors.stream()
					.filter(id -> !updatedProjectContributors.contains(id)).toList();
			List<ProjectContributors> contributorsToBeAdded = updatedProjectContributors.stream()
					.filter(id -> !previousProjectContributors.contains(id)).toList();
			contributorsToBeAdded.forEach(projectContributor -> projectContributor.setProjectId(projectId));
			contributorsToBeAdded.forEach(recordStatus -> recordStatus.setRecordStatus("A"));
			projectContributorDetails.contributorsToBeAdded(contributorsToBeAdded);
			contributorsToBeDeleted.forEach(contributor -> projectContributorDetails.singleDelete(contributor.getProjectContributorId()));
		} else if (updatedProjectContributorsDTO.size() != 0) {
			List<ProjectContributors> updatedProjectContributors = updatedProjectContributorsDTO.stream()
					.map(dto -> modelMapper.map(dto, ProjectContributors.class)).toList();
			updatedProjectContributors.forEach(projectContributor -> projectContributor.setProjectId(projectId));
			updatedProjectContributors.forEach(recordStatus -> recordStatus.setRecordStatus("A"));
			projectContributorDetails.contributorsToBeAdded(updatedProjectContributors);
		} else if (previousProjectContributors.size() != 0) {
			previousProjectContributors.forEach(contributor -> projectContributorDetails.singleDelete(contributor.getProjectContributorId()));
		}

		Project project = this.projectRepository.findByProjectId(projectId);

		project.setBillingId(projectUpdatedDTO.getBillingId());
		project.setCompanyName(projectUpdatedDTO.getCompanyName());
		project.setManagerId(projectUpdatedDTO.getManagerId());
		project.setEndDate(projectUpdatedDTO.getEndDate());
		project.setProjectCode(projectUpdatedDTO.getProjectCode());
		project.setProjectName(projectUpdatedDTO.getProjectName());
		project.setRecordStatus(projectUpdatedDTO.getRecordStatus());
		project.setRepoUrl(projectUpdatedDTO.getRepoUrl());
		project.setTechnologies(projectUpdatedDTO.getTechnologies());
		project.setProjectId(projectId);
		project.setRecordStatus("A");

		this.projectRepository.save(project);
	}

	@Override
	public void deleteProject(int projectId) {
		Project project = this.projectRepository.findByProjectId(projectId);
		this.projectRepository.delete(project);
	}

	@Override
	public List<ProjectDTO> getProjectOfManager(int managerId) {
		List<Project> projects = this.projectRepository.findByManagerId(managerId);
		List<ProjectDTO> projectDTOs = projects.stream().map(project -> modelMapper.map(project, ProjectDTO.class))
				.toList();
		projectDTOs.forEach(billingType -> billingType.setBillingType(billingDetails.knowBillingType(billingType.getBillingId())));
		projectDTOs.forEach(managerName -> managerName.setManagerName(managerDetail.KnowManagerName(managerName.getManagerId())));
		return projectDTOs;
	}

	@Override
	public List<ProjectDTO> getAllProject() {
		List<Project> projects = this.projectRepository.findAll();
		List<ProjectDTO> projectDTOs = projects.stream().map(project -> modelMapper.map(project, ProjectDTO.class))
				.toList();
		projectDTOs.forEach(manager -> manager.setManagerName(managerDetail.KnowManagerName(manager.getManagerId())));
		projectDTOs.forEach(billingType -> billingType.setBillingType(billingDetails.knowBillingType(billingType.getBillingId())));
		projectDTOs.forEach(projectStatus -> projectStatus.setProjectStatus(statusDetails.knowProjectStatus(projectStatus.getStatusId())));
		return projectDTOs;
	}





}

package com.pms.project.service;

import com.pms.project.dto.ProjectContributorCreateDTO;
import com.pms.project.dto.ProjectContributorDTO;
import com.pms.project.model.ProjectContributors;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectContributorDetails {

    private static final String PROJECT_CONTRIBUTOR="projectContributor";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;




    @CircuitBreaker(name = "PROJECT_CONTRIBUTOR",fallbackMethod = "fallBackGetProjectContributors")
    public List<ProjectContributorDTO> getProjectContributors(int projectId) {
        ResponseEntity<ProjectContributorDTO[]> response = this.restTemplate
                .getForEntity("http://project-contributor/api/v1/contributor/" + projectId, ProjectContributorDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ProjectContributorDTO> fallBackGetProjectContributors(int projectId, Exception e){
        List<ProjectContributorDTO> projectContributorDTOS=new ArrayList<>();
        return projectContributorDTOS;
    }
    @CircuitBreaker(name = "PROJECT_CONTRIBUTOR",fallbackMethod = "fallBackContributorsToBeAdded")
    public void contributorsToBeAdded(List<ProjectContributors> contributorsToBeAdded) {
        List<ProjectContributorCreateDTO> projectContributorCreateDTOs = contributorsToBeAdded.stream()
                .map(contributor -> modelMapper.map(contributor, ProjectContributorCreateDTO.class)).toList();
        this.restTemplate.postForObject("http://project-contributor/api/v1/contributor/addAll", projectContributorCreateDTOs, Void.class);
    }

    public void fallBackContributorsToBeAdded(List<ProjectContributors> contributorsToBeAdded, Exception e){

    }

    @CircuitBreaker(name = "PROJECT_CONTRIBUTOR",fallbackMethod = "fallBackSingleDelete")
    public void singleDelete(int projectContributorId) {
        this.restTemplate.delete("http://project-contributor/api/v1/contributor/delete/" + projectContributorId);
    }

    public void fallBackSingleDelete(int projectContributorId){

    }


    @CircuitBreaker(name = "PROJECT_CONTRIBUTOR",fallbackMethod = "fallBackGetProjectContributorForUpdateAPI")
    public List<ProjectContributors> getProjectContributorForUpdateAPI(int projectId) {
        ResponseEntity<ProjectContributors[]> response = this.restTemplate.getForEntity(
                "http://project-contributor/api/v1/contributor/getProjectContributors/" + projectId, ProjectContributors[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ProjectContributors> fallBackGetProjectContributorForUpdateAPI(int projectId, Exception e){
        List<ProjectContributors> projectContributors=new ArrayList<>();
        return projectContributors;
    }
}

package com.pms.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.project.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	Project findByProjectId(int projectId);
	
	List<Project> findByManagerId(int managerId);
	
	void deleteByProjectId(int projectId);

}

package com.pms.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectContributorCreateDTO {
	
	private Integer projectId;
	private Integer contributorId;
	 private String recordStatus;
}

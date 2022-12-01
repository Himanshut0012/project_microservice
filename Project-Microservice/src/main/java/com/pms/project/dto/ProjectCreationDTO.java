package com.pms.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreationDTO {

	private String projectName;
	private String projectCode;
	private String companyName;
	private Integer managerId;
	private Integer billingId;
	private Integer statusId;
	@JsonIgnore
	private String recordStatus = "1";

}

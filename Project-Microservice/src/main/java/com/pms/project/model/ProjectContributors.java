package com.pms.project.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectContributors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectContributorId;
	private Integer projectId;
	private Integer contributorId;
    private String createdBy;
    private Date createdDate;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String recordStatus;

}

package com.pms.project.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId ;
    private String projectCode ;
    private String projectName ;
    private String companyName;
    private String repoUrl;
    private Integer statusId;
    private String technologies;
    private Integer billingId ;
    private Integer managerId ;
    private Date startDate;
    private Date endDate;
    private String createdBy;
    private Date createdDate;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String recordStatus;

}

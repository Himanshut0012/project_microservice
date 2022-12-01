package com.pms.project.dto;

import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdatedDTO {

    private String projectCode ;
    private String projectName ;
    private String companyName;
    private String repoUrl;
    private String technologies;
    private Date startDate;
    private Date endDate;
    private Integer managerId ;
    private Integer billingId;
    private Integer statusId;
    private String recordStatus;
    private List<ProjectContributorDTO> contributors;
}

package org.example.service;

import org.example.models.Job;

import java.util.List;

public interface JobService {
    boolean createJobTable();
    boolean addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    String deleteDescriptionColumn();
}

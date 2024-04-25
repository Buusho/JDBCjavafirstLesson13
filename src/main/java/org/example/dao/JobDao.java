package org.example.dao;

import org.example.models.Job;

import java.util.List;

public interface JobDao {
    boolean createJobTable();
    boolean addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    String deleteDescriptionColumn();
}

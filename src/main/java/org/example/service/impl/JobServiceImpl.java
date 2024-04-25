package org.example.service.impl;

import org.example.dao.JobDao;
import org.example.dao.impl.JobDaoImpl;
import org.example.models.Job;
import org.example.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    private  final JobDao jobDao =new JobDaoImpl();
    @Override
    public boolean createJobTable() {

        return jobDao.createJobTable();
    }

    @Override
    public boolean addJob(Job job) {
        return jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);

    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public String deleteDescriptionColumn() {
jobDao.deleteDescriptionColumn();
        return null;
    }
}

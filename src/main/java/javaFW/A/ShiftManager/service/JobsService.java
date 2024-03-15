package javaFW.A.ShiftManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javaFW.A.ShiftManager.model.Jobs;
import javaFW.A.ShiftManager.repository.JobsRepository;

@Service
public class JobsService {

    @Autowired
    private JobsRepository jobsRepository;

    public void saveJob(Jobs job) {
        jobsRepository.save(job);
    }
    
    public void deleteJob(@NonNull Long index) {
		this.jobsRepository.deleteById(index);
	}
    
    public List<Jobs> getJobsList() {
    	List<Jobs> entityList = this.jobsRepository.findAll();
	    return entityList;
    }
    
    public List<Jobs> getJobs() {
    	return this.jobsRepository.findAll(); 
    }

}

package javaFW.A.ShiftManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_name")
    private String jobName;
    
    @Column(name = "hourly_wage")
    private Integer hourlyWage;
    
    @Column(name = "night_hourly_wage")
    private Integer nightHourlyWage;

    @Column(name = "authority", columnDefinition = "TEXT")
    private String authority;

    // Getter and setter methods

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public Integer getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Integer hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
    
    public Integer getNightHourlyWage() {
    	return nightHourlyWage;
    }
    
    public void setNightHourlyWage(Integer nightHourlyWage) {
    	this.nightHourlyWage = nightHourlyWage;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

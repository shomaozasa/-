package javaFW.A.ShiftManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javaFW.A.ShiftManager.model.Jobs;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
    
}

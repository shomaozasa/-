package javaFW.A.ShiftManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javaFW.A.ShiftManager.model.Shifts;

public interface ShiftsRepository extends JpaRepository<Shifts, Long> {
    
}
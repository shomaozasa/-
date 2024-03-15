package javaFW.A.ShiftManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javaFW.A.ShiftManager.model.Shifts;
import javaFW.A.ShiftManager.repository.ShiftsRepository;

@Service
public class ShiftsService {

    @Autowired
    private ShiftsRepository shiftsRepository;

    public void saveShift(Shifts shift) {
        shiftsRepository.save(shift);
    }
    
    public void deleteShift(@NonNull Long index) {
		this.shiftsRepository.deleteById(index);
	}
    
    public List<Shifts> getshiftsList() {
    	List<Shifts> entityList = this.shiftsRepository.findAll();
	    return entityList;
    }
    
    public List<Shifts> getshifts() {
    	return this.shiftsRepository.findAll(); 
    }
    
}

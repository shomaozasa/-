package javaFW.A.ShiftManager.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javaFW.A.ShiftManager.model.Jobs;
import javaFW.A.ShiftManager.model.Users;
import javaFW.A.ShiftManager.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public boolean authenticate(String userName, String password) {
        Optional<Users> userOptional = usersRepository.findByUserNameAndPassword(userName, password);
        return userOptional.isPresent();
    }
    
    public void signUp(String userName, String password, Long jobId, Integer age, String phone, String email) {
        Users user = new Users();
        user.setUserName(userName);
        user.setPassword(password);
        user.setJobId(jobId);
        user.setAge(age);
        user.setPhone(phone);
        user.setEmail(email);
        if (usersRepository.existsByEmailOrPhone(email, phone)) {
            throw new RuntimeException("このメールアドレス又は電話番号は既に使用されています");
        }
        else {
        	usersRepository.save(user);
        }
    }
    
    public List<Users> getUsersList() {
    	List<Users> entityList = this.usersRepository.findAll();
	    return entityList;
    }
    
    public void deleteUser(@NonNull Long index) {
		this.usersRepository.deleteById(index);
	}
    
    public String getUserAuthority(String userName) {
        Optional<Users> userOptional = usersRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            Jobs job = user.getJob();
            String authority = job.getAuthority();
            return authority;
        } else {
            return null; // ユーザーが存在しない場合
        }
    }
    
    public Long getUserIdByUserName(String username) {
    	Optional<Users> optionalUser = usersRepository.findByUserName(username);
    	Users user = optionalUser.orElse(null); // 存在しない場合はnullが返される

        if (user != null) {
            return user.getUserId();
        }
        return null;
    }
    
}


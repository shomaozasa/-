package javaFW.A.ShiftManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaFW.A.ShiftManager.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserNameAndPassword(String userName, String password);
    Optional<Users> findByUserName(String userName);
    boolean existsByEmailOrPhone(String email, String phone);
}

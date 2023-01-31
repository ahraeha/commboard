package com.jjez.commboard.user.repository;

import com.jjez.commboard.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

}

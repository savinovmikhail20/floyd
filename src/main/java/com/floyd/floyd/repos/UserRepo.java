package com.floyd.floyd.repos;

import com.floyd.floyd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByGetNews(boolean b);
    User findByActivationCode(String code);
}
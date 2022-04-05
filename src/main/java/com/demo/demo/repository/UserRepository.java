package com.demo.demo.repository;

import com.demo.demo.entity.User;
import com.demo.demo.entity.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findById(Integer id);

    List<User> findAllByState(EntityStatus active);
}

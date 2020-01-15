package com.example.demo.dao;

import com.example.demo.domain.AgUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AgUser,Integer> {
    AgUser findByBotUserIdAndStatus(String ii, Integer status);
}
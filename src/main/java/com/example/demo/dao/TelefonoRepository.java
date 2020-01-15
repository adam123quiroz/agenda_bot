package com.example.demo.dao;

import com.example.demo.domain.AgPhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepository extends JpaRepository<AgPhone, Integer> {


}

package com.example.demo.dao;

import com.example.demo.domain.AgPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<AgPerson, Integer> {
    List<AgPerson> findAllByStatus(int status);
}

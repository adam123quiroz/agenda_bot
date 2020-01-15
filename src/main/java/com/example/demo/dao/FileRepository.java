package com.example.demo.dao;

import com.example.demo.domain.AgFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AgFile, Integer> {
}

package com.example.demo.dao;

import com.example.demo.domain.AgContactFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFileRepository extends JpaRepository<AgContactFile, Integer> {
}

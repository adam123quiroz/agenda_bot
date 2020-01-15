package com.example.demo.dao;

import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<AgContact, Integer > {
    List<AgContact> findAllByIdUserAndStatus(AgUser user, Integer status);

    AgContact findByIdContactAndStatus(Integer id, int status);

    @Query(value = "SELECT * FROM ag_contact a, ag_person b where a.id_person = b.id_person AND (b.first_name  LIKE %?1% OR b.last_name LIKE %?1%) ", nativeQuery = true)
    List<AgContact> findAllContactByParecido(String parecido);

    @Query(value = "SELECT * FROM ag_phone a, ag_contact b where a.id_contact = b.id_contact AND a.phone LIKE %?1% ", nativeQuery = true)
    List<AgContact> findAllTelefonoByParecido(String parecido);
}

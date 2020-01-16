package com.example.demo.dao;

import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<AgContact, Integer > {
    List<AgContact> findAllByIdUserAndStatus(AgUser user, Integer status);

    AgContact findByIdContactAndStatus(Integer id, int status);

    @Query(value = "" +
            "SELECT * " +
            "FROM ag_contact a, ag_person b " +
            "WHERE a.id_person = b.id_person " +
            "AND a.id_user = ?2 " +
            "AND a.status = ?3 " +
            "AND (b.first_name  LIKE %?1% OR b.last_name LIKE %?1%) ", nativeQuery = true)
    List<AgContact> findAllContactByParecidoAndIdUserAndStatus(String parecido, Integer idUser, Integer status);

    @Query(value = "" +
            "SELECT * " +
            "FROM ag_phone a, ag_contact b " +
            "WHERE a.id_contact = b.id_contact " +
            "AND b.id_user = ?2 " +
            "AND b.status = ?3 " +
            "AND a.phone LIKE %?1% ", nativeQuery = true)
    List<AgContact> findAllTelefonoByParecidoAndIdUserAndStatus(String parecido, Integer idUser, Integer status);
}

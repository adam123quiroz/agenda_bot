/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author artud
 */
@Entity
@Table(name = "ag_person", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgPerson.findAll", query = "SELECT a FROM AgPerson a"),
    @NamedQuery(name = "AgPerson.findByIdPerson", query = "SELECT a FROM AgPerson a WHERE a.idPerson = :idPerson"),
    @NamedQuery(name = "AgPerson.findByFirstName", query = "SELECT a FROM AgPerson a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "AgPerson.findByLastName", query = "SELECT a FROM AgPerson a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "AgPerson.findByStatus", query = "SELECT a FROM AgPerson a WHERE a.status = :status"),
    @NamedQuery(name = "AgPerson.findByTxUser", query = "SELECT a FROM AgPerson a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgPerson.findByTxHost", query = "SELECT a FROM AgPerson a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgPerson.findByTxDate", query = "SELECT a FROM AgPerson a WHERE a.txDate = :txDate")})
public class AgPerson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_person", nullable = false)
    private Integer idPerson;
    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tx_user", nullable = false, length = 50)
    private String txUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tx_host", nullable = false, length = 100)
    private String txHost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tx_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date txDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson", fetch = FetchType.LAZY)
    private List<AgContact> agContactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson", fetch = FetchType.LAZY)
    private List<AgUser> agUserList;

    public AgPerson() {
    }

    public AgPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public AgPerson(Integer idPerson, int status, String txUser, String txHost, Date txDate) {
        this.idPerson = idPerson;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTxUser() {
        return txUser;
    }

    public void setTxUser(String txUser) {
        this.txUser = txUser;
    }

    public String getTxHost() {
        return txHost;
    }

    public void setTxHost(String txHost) {
        this.txHost = txHost;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    @XmlTransient
    public List<AgContact> getAgContactList() {
        return agContactList;
    }

    public void setAgContactList(List<AgContact> agContactList) {
        this.agContactList = agContactList;
    }

    @XmlTransient
    public List<AgUser> getAgUserList() {
        return agUserList;
    }

    public void setAgUserList(List<AgUser> agUserList) {
        this.agUserList = agUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerson != null ? idPerson.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgPerson)) {
            return false;
        }
        AgPerson other = (AgPerson) object;
        if ((this.idPerson == null && other.idPerson != null) || (this.idPerson != null && !this.idPerson.equals(other.idPerson))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgPerson[ idPerson=" + idPerson + " ]";
    }
    
}

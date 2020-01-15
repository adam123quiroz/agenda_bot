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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ag_user", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgUser.findAll", query = "SELECT a FROM AgUser a"),
    @NamedQuery(name = "AgUser.findByIdUser", query = "SELECT a FROM AgUser a WHERE a.idUser = :idUser"),
    @NamedQuery(name = "AgUser.findByBotUserId", query = "SELECT a FROM AgUser a WHERE a.botUserId = :botUserId"),
    @NamedQuery(name = "AgUser.findByStatus", query = "SELECT a FROM AgUser a WHERE a.status = :status"),
    @NamedQuery(name = "AgUser.findByTxUser", query = "SELECT a FROM AgUser a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgUser.findByTxHost", query = "SELECT a FROM AgUser a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgUser.findByTxDate", query = "SELECT a FROM AgUser a WHERE a.txDate = :txDate")})
public class AgUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user", nullable = false)
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "bot_user_id", nullable = false, length = 100)
    private String botUserId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser", fetch = FetchType.LAZY)
    private List<AgContact> agContactList;
    @JoinColumn(name = "id_person", referencedColumnName = "id_person", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgPerson idPerson;

    public AgUser() {
    }

    public AgUser(Integer idUser) {
        this.idUser = idUser;
    }

    public AgUser(Integer idUser, String botUserId, int status, String txUser, String txHost, Date txDate) {
        this.idUser = idUser;
        this.botUserId = botUserId;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getBotUserId() {
        return botUserId;
    }

    public void setBotUserId(String botUserId) {
        this.botUserId = botUserId;
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

    public AgPerson getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(AgPerson idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgUser)) {
            return false;
        }
        AgUser other = (AgUser) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgUser[ idUser=" + idUser + " ]";
    }
    
}

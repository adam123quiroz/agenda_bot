/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author artud
 */
@Entity
@Table(name = "ag_phone", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgPhone.findAll", query = "SELECT a FROM AgPhone a"),
    @NamedQuery(name = "AgPhone.findByIdPhone", query = "SELECT a FROM AgPhone a WHERE a.idPhone = :idPhone"),
    @NamedQuery(name = "AgPhone.findByPhone", query = "SELECT a FROM AgPhone a WHERE a.phone = :phone"),
    @NamedQuery(name = "AgPhone.findByStatus", query = "SELECT a FROM AgPhone a WHERE a.status = :status"),
    @NamedQuery(name = "AgPhone.findByTxUser", query = "SELECT a FROM AgPhone a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgPhone.findByTxHost", query = "SELECT a FROM AgPhone a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgPhone.findByTxDate", query = "SELECT a FROM AgPhone a WHERE a.txDate = :txDate")})
public class AgPhone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_phone", nullable = false)
    private Integer idPhone;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int phone;
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
    @JoinColumn(name = "id_contact", referencedColumnName = "id_contact", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgContact idContact;

    public AgPhone() {
    }

    public AgPhone(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public AgPhone(Integer idPhone, int phone, int status, String txUser, String txHost, Date txDate) {
        this.idPhone = idPhone;
        this.phone = phone;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
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

    public AgContact getIdContact() {
        return idContact;
    }

    public void setIdContact(AgContact idContact) {
        this.idContact = idContact;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPhone != null ? idPhone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgPhone)) {
            return false;
        }
        AgPhone other = (AgPhone) object;
        if ((this.idPhone == null && other.idPhone != null) || (this.idPhone != null && !this.idPhone.equals(other.idPhone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgPhone[ idPhone=" + idPhone + " ]";
    }
    
}

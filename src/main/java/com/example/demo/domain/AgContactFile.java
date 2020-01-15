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
@Table(name = "ag_contact_file", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgContactFile.findAll", query = "SELECT a FROM AgContactFile a"),
    @NamedQuery(name = "AgContactFile.findByIdContactFile", query = "SELECT a FROM AgContactFile a WHERE a.idContactFile = :idContactFile"),
    @NamedQuery(name = "AgContactFile.findByStatus", query = "SELECT a FROM AgContactFile a WHERE a.status = :status"),
    @NamedQuery(name = "AgContactFile.findByTxUser", query = "SELECT a FROM AgContactFile a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgContactFile.findByTxHost", query = "SELECT a FROM AgContactFile a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgContactFile.findByTxDate", query = "SELECT a FROM AgContactFile a WHERE a.txDate = :txDate")})
public class AgContactFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contact_file", nullable = false)
    private Integer idContactFile;
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
    @JoinColumn(name = "id_file", referencedColumnName = "id_file", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgFile idFile;

    public AgContactFile() {
    }

    public AgContactFile(Integer idContactFile) {
        this.idContactFile = idContactFile;
    }

    public AgContactFile(Integer idContactFile, int status, String txUser, String txHost, Date txDate) {
        this.idContactFile = idContactFile;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdContactFile() {
        return idContactFile;
    }

    public void setIdContactFile(Integer idContactFile) {
        this.idContactFile = idContactFile;
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

    public AgFile getIdFile() {
        return idFile;
    }

    public void setIdFile(AgFile idFile) {
        this.idFile = idFile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContactFile != null ? idContactFile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgContactFile)) {
            return false;
        }
        AgContactFile other = (AgContactFile) object;
        if ((this.idContactFile == null && other.idContactFile != null) || (this.idContactFile != null && !this.idContactFile.equals(other.idContactFile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgContactFile[ idContactFile=" + idContactFile + " ]";
    }
    
}

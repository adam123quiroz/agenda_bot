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
@Table(name = "ag_contact", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgContact.findAll", query = "SELECT a FROM AgContact a"),
    @NamedQuery(name = "AgContact.findByIdContact", query = "SELECT a FROM AgContact a WHERE a.idContact = :idContact"),
    @NamedQuery(name = "AgContact.findByCorreo", query = "SELECT a FROM AgContact a WHERE a.correo = :correo"),
    @NamedQuery(name = "AgContact.findByFechaNac", query = "SELECT a FROM AgContact a WHERE a.fechaNac = :fechaNac"),
    @NamedQuery(name = "AgContact.findByStatus", query = "SELECT a FROM AgContact a WHERE a.status = :status"),
    @NamedQuery(name = "AgContact.findByTxUser", query = "SELECT a FROM AgContact a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgContact.findByTxHost", query = "SELECT a FROM AgContact a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgContact.findByTxDate", query = "SELECT a FROM AgContact a WHERE a.txDate = :txDate")})
public class AgContact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contact", nullable = false)
    private Integer idContact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nac", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
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
    @JoinColumn(name = "id_person", referencedColumnName = "id_person", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgPerson idPerson;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AgUser idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContact", fetch = FetchType.LAZY)
    private List<AgContactFile> agContactFileList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContact", fetch = FetchType.LAZY)
    private List<AgPhone> agPhoneList;

    public AgContact() {
    }

    public AgContact(Integer idContact) {
        this.idContact = idContact;
    }

    public AgContact(Integer idContact, String correo, Date fechaNac, int status, String txUser, String txHost, Date txDate) {
        this.idContact = idContact;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
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

    public AgPerson getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(AgPerson idPerson) {
        this.idPerson = idPerson;
    }

    public AgUser getIdUser() {
        return idUser;
    }

    public void setIdUser(AgUser idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<AgContactFile> getAgContactFileList() {
        return agContactFileList;
    }

    public void setAgContactFileList(List<AgContactFile> agContactFileList) {
        this.agContactFileList = agContactFileList;
    }

    @XmlTransient
    public List<AgPhone> getAgPhoneList() {
        return agPhoneList;
    }

    public void setAgPhoneList(List<AgPhone> agPhoneList) {
        this.agPhoneList = agPhoneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContact != null ? idContact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgContact)) {
            return false;
        }
        AgContact other = (AgContact) object;
        if ((this.idContact == null && other.idContact != null) || (this.idContact != null && !this.idContact.equals(other.idContact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgContact[ idContact=" + idContact + " ]";
    }
    
}

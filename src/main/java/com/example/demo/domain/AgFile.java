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
@Table(name = "ag_file", catalog = "agenda_final", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgFile.findAll", query = "SELECT a FROM AgFile a"),
    @NamedQuery(name = "AgFile.findByIdFile", query = "SELECT a FROM AgFile a WHERE a.idFile = :idFile"),
    @NamedQuery(name = "AgFile.findByFileName", query = "SELECT a FROM AgFile a WHERE a.fileName = :fileName"),
    @NamedQuery(name = "AgFile.findByMimeType", query = "SELECT a FROM AgFile a WHERE a.mimeType = :mimeType"),
    @NamedQuery(name = "AgFile.findByPath", query = "SELECT a FROM AgFile a WHERE a.path = :path"),
    @NamedQuery(name = "AgFile.findByScoreType", query = "SELECT a FROM AgFile a WHERE a.scoreType = :scoreType"),
    @NamedQuery(name = "AgFile.findByStatus", query = "SELECT a FROM AgFile a WHERE a.status = :status"),
    @NamedQuery(name = "AgFile.findByTxUser", query = "SELECT a FROM AgFile a WHERE a.txUser = :txUser"),
    @NamedQuery(name = "AgFile.findByTxHost", query = "SELECT a FROM AgFile a WHERE a.txHost = :txHost"),
    @NamedQuery(name = "AgFile.findByTxDate", query = "SELECT a FROM AgFile a WHERE a.txDate = :txDate")})
public class AgFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_file", nullable = false)
    private Integer idFile;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String path;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "score_type", nullable = false, length = 100)
    private String scoreType;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFile", fetch = FetchType.LAZY)
    private List<AgContactFile> agContactFileList;

    public AgFile() {
    }

    public AgFile(Integer idFile) {
        this.idFile = idFile;
    }

    public AgFile(Integer idFile, String fileName, String mimeType, String path, String scoreType, int status, String txUser, String txHost, Date txDate) {
        this.idFile = idFile;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.path = path;
        this.scoreType = scoreType;
        this.status = status;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
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
    public List<AgContactFile> getAgContactFileList() {
        return agContactFileList;
    }

    public void setAgContactFileList(List<AgContactFile> agContactFileList) {
        this.agContactFileList = agContactFileList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFile != null ? idFile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgFile)) {
            return false;
        }
        AgFile other = (AgFile) object;
        if ((this.idFile == null && other.idFile != null) || (this.idFile != null && !this.idFile.equals(other.idFile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.domain.AgFile[ idFile=" + idFile + " ]";
    }
    
}

package com.example.demo.dto;

import com.example.demo.domain.AgContact;

import java.util.Date;
import java.util.List;

public class ContactDto {
    private int idContact;
    private String nombres;
    private String apellidos;
    private String correo;
    private Date fechaNac;

    private List<PhoneDto> telefonoList;
    private List<FileDto> fileDtos;

    public ContactDto(AgContact contact) {
        this.idContact = contact.getIdContact();
        this.nombres = contact.getIdPerson().getFirstName();
        this.apellidos = contact.getIdPerson().getLastName();
        this.correo = contact.getCorreo();
        this.fechaNac = contact.getFechaNac();
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public List<PhoneDto> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<PhoneDto> telefonoList) {
        this.telefonoList = telefonoList;
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

    public List<FileDto> getFileDtos() {
        return fileDtos;
    }

    public void setFileDtos(List<FileDto> fileDtos) {
        this.fileDtos = fileDtos;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "idContact=" + idContact +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaNac=" + fechaNac +
                ", telefonoList=" + telefonoList +
                ", fileDtos=" + fileDtos +
                '}';
    }
}

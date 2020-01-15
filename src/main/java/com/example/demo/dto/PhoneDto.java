package com.example.demo.dto;


import com.example.demo.domain.AgPhone;

public class PhoneDto {
    private int idTelefono;
    private Integer numero;

    public PhoneDto(AgPhone telefono) {
        this.idTelefono = telefono.getIdPhone();
        this.numero = telefono.getPhone();
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "TelefonoDto{" +
                "idTelefono=" + idTelefono +
                ", numero=" + numero +
                '}';
    }
}

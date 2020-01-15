package com.example.demo.auxs;

import com.example.demo.dao.*;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgPerson;
import com.example.demo.domain.AgPhone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManager {
    private TelefonoRepository telefonoRepository;
    private PersonRepository personRepository;

    private AgContact contact;

    public ContactManager(TelefonoRepository telefonoRepository, PersonRepository personRepository) {
        this.telefonoRepository = telefonoRepository;
        this.personRepository = personRepository;
        contact = new AgContact();
        AgPerson agPerson = new AgPerson();
        contact.setIdPerson(agPerson);
    }

    public AgContact getContact() {
        return contact;
    }
    public void setContact(AgContact contact) {
        this.contact = contact;
    }

    public boolean setName(String data) {
        boolean flag = false;
        if (onlyLetters(data)) {
            contact.getIdPerson().setFirstName(data);
            flag = true;
        }
        return flag;
    }

    public boolean setLastName(String data) {
        boolean flag = false;
        if (onlyLetters(data)){
            contact.getIdPerson().setLastName(data);
            flag = true;
        }
        contact.getIdPerson().setStatus(1);
        contact.getIdPerson().setTxUser("admin");
        contact.getIdPerson().setTxDate(new Date());
        contact.getIdPerson().setTxHost("localhost");
        personRepository.save(contact.getIdPerson());
        return flag;
    }

    public boolean setEmail(String data) {
        boolean flag = false;
        if (validateEmail(data)){
            contact.setCorreo(data);
            flag = true;
        }
        return flag;
    }

    public boolean setBirthday(String data) {
        boolean flag;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date;
            date = dateFormat.parse(data);
            contact.setFechaNac(new Date(date.getTime()));
            flag = true;
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }

    public boolean setPhone(String data, AgContact contact){
        boolean flag = false;
        if (validatePhone(data)){
            AgPhone aPhone = new AgPhone();
            aPhone.setPhone(Integer.parseInt(data));
            aPhone.setIdContact(contact);
            aPhone.setTxDate(new Date(new java.util.Date().getTime()));
            aPhone.setTxHost("localhost");
            aPhone.setTxUser(contact.getIdPerson().getFirstName());
            telefonoRepository.save(aPhone);
            flag = true;
        }
        return flag;
    }

    private boolean onlyLetters(String text) {
        List<String> addressPart = Arrays.asList(text.split(" "));
        for (String data :
                addressPart) {
            for (int i = 0; i < data.length(); i++) {
                char caracter = data.toUpperCase().charAt(i);
                int valorASCII = (int)caracter;
                if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90))
                    return false;
            }
        }

        return true;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePhone(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}

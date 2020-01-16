package com.example.demo.bl;

import com.example.demo.auxs.Validation;
import com.example.demo.dao.PersonRepository;
import com.example.demo.dao.TelefonoRepository;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgPerson;
import com.example.demo.domain.AgPhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Service
public class ContactUpdateManagerBl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactUpdateManagerBl.class);

    private TelefonoRepository telefonoRepository;
    private PersonRepository personRepository;

    private AgContact contact;

    @Autowired
    public ContactUpdateManagerBl(TelefonoRepository telefonoRepository, PersonRepository personRepository) {
        this.telefonoRepository = telefonoRepository;
        this.personRepository = personRepository;
    }

    public AgContact getContact() {
        return contact;
    }
    public void setContact(AgContact contact) {
        this.contact = contact;
    }

    public boolean setName(String data) {
        boolean flag = false;
        if (Validation.onlyLetters(data)) {
            contact.getIdPerson().setFirstName(data);
            personRepository.save(contact.getIdPerson());
            flag = true;
        }
        return flag;
    }

    public boolean setLastName(String data) {
        boolean flag = false;
        if (Validation.onlyLetters(data)){
            contact.getIdPerson().setLastName(data);
            personRepository.save(contact.getIdPerson());
            flag = true;
        }
        return flag;
    }

    public boolean setEmail(String data) {
        boolean flag = false;
        if (Validation.validateEmail(data)){
            contact.setCorreo(data);
            flag = true;
        }
        return flag;
    }

    public boolean setBirthday(String data) {
        boolean flag;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
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
        if (Validation.validatePhone(data)){
            AgPhone aPhone = new AgPhone();
            aPhone.setPhone(Integer.parseInt(data));
            aPhone.setIdContact(contact);
            aPhone.setTxDate(new Date(new Date().getTime()));
            aPhone.setTxHost("localhost");
            aPhone.setStatus(1);
            aPhone.setTxUser(contact.getIdPerson().getFirstName());
            telefonoRepository.save(aPhone);
            flag = true;
        }
        return flag;
    }


}

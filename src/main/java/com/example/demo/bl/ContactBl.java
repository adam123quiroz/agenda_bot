package com.example.demo.bl;

import com.example.demo.dao.ContactFileRepository;
import com.example.demo.dao.ContactRepository;
import com.example.demo.dao.TelefonoRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.ContactDto;
import com.example.demo.dto.FileDto;
import com.example.demo.dto.PhoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class ContactBl {
    ContactRepository contactRepository;
    TelefonoRepository telefonoRepository;
    ContactFileRepository contactFileRepository;

    @Autowired
    public ContactBl(ContactRepository contactRepository, TelefonoRepository telefonoRepository, ContactFileRepository contactFileRepository) {
        this.contactRepository = contactRepository;
        this.telefonoRepository = telefonoRepository;
        this.contactFileRepository = contactFileRepository;
    }

    public ContactDto getContactWithAdress(String text) {
        List<String> name = Arrays.asList(text.split(" "));
        AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
        ContactDto contactDto = new ContactDto(contact);
        List<PhoneDto> phoneDtos = new ArrayList<>();
        List<AgPhone> telefonoList = contact.getAgPhoneList();
        for(AgPhone cpa : telefonoList) {
            phoneDtos.add(new PhoneDto(cpa));
        }
        contactDto.setTelefonoList(phoneDtos);
        return contactDto;
    }

    public ContactDto getContactWithFile(String text) {
        List<String> name = Arrays.asList(text.split(" "));
        AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
        ContactDto contactDto = new ContactDto(contact);
        List<FileDto> fileDtos = new ArrayList<>();
        List<AgContactFile> agContactFileList = contact.getAgContactFileList();
        for(AgContactFile contactFile : agContactFileList) {
            AgFile file = contactFile.getIdFile();
            fileDtos.add(new FileDto(file));
        }
        contactDto.setFileDtos(fileDtos);
        return contactDto;
    }

    public List<ContactDto> getContactWithPhone(AgUser user) {
        List<ContactDto> personDtoList = new ArrayList<>();
        for (AgContact contact:contactRepository.findAllByIdUserAndStatus(user ,1)) {
            ContactDto contactDto = new ContactDto(contact);
            List<PhoneDto> phoneDtos = new ArrayList<>();
            List<AgPhone> cpAddressList = contact.getAgPhoneList();
            for(AgPhone cpa : cpAddressList) {
                phoneDtos.add(new PhoneDto(cpa));
            }
            contactDto.setTelefonoList(phoneDtos);
            personDtoList.add(contactDto);
        }
        return personDtoList;
    }
}

package com.example.demo.auxs;

import com.example.demo.domain.AgContact;
import com.example.demo.dto.ContactDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConcatListContact {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceUpdateContact.class);

    private List<AgContact> contacts;

    public ConcatListContact(List<AgContact> contacts1) {
        this.contacts = contacts1;
    }

    public List<String> getStringListContact() {
        StringBuilder stringBuilder;
        List<String> listContactString = new ArrayList<>();
        for (AgContact contact :
                contacts) {
            stringBuilder = new StringBuilder();
            ContactDto contactDto = new ContactDto(contact);
            LOGGER.info("Apellido {}", contactDto.getApellidos());
            stringBuilder.append(String.format("%d %s %s",
                    contactDto.getIdContact(),
                    contactDto.getNombres(),
                    contactDto.getApellidos()
                    )
            );
            listContactString.add(stringBuilder.toString());
        }
        return listContactString;
    }
}
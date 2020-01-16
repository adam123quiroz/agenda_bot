package com.example.demo.auxs;

import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgPhone;
import com.example.demo.dto.ContactDto;
import com.example.demo.dto.PhoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConcatListPhone {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceUpdateContact.class);

    private List<AgPhone> phones;

    public ConcatListPhone(List<AgPhone> phones) {
        this.phones = phones;
    }

    public List<String> getStringListContact() {
        StringBuilder stringBuilder;
        List<String> listContactString = new ArrayList<>();
        for (AgPhone phone :
                phones) {
            stringBuilder = new StringBuilder();
            PhoneDto phoneDto = new PhoneDto(phone);
            stringBuilder.append(String.format("%d",
                    phoneDto.getNumero()
                    )
            );
            listContactString.add(stringBuilder.toString());
        }
        return listContactString;
    }
}
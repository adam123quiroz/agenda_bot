package com.example.demo.auxs;

import com.example.demo.bot.ComandManager;
import com.example.demo.bot.MainBot;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceDeleteContact extends Sequence {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceDeleteContact.class);
    private ContactRepository contactRepository;

    private ContactManager contactManager;
    private AgUser user;
    private MainBot mainBot;
    private AgContact contact;
    private String value;

    private List<String> chatResponce;

    public SequenceDeleteContact(UserRepository userRepository,
                                 PersonRepository personRepository,
                                 FileRepository fileRepository,
                                 ContactFileRepository contactFileRepository,
                                 TelefonoRepository telefonoRepository,
                                 ContactRepository contactRepository,
                                 AgUser user,
                                 MainBot mainBot) {
        this.contactRepository = contactRepository;
        this.user = user;
        this.mainBot =  mainBot;
        this.contactManager = new ContactManager(telefonoRepository, personRepository);

    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException, TelegramApiException {
        String text = update.getMessage().getText();
        this.chatResponce = chatResponce;
        switch (getStepActually()) {
            case 0:
                List<String> name = Arrays.asList(text.split(" "));
                AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
                this.contact = contact;
                List<String> listOptions = new ArrayList<>();
                listOptions.add("SI");
                listOptions.add("NO");
                ComandManager comandManager = new ComandManager(listOptions);
                mainBot.execute(comandManager.showMenu("Esta Seguro de Eliminar el Contacto?", update));
                break;
            case 1:
                if (text.equalsIgnoreCase("SI")) {
                    this.contact.setStatus(0);
                    contactRepository.save(this.contact);
                    chatResponce.add("Contacto Eliminado");
                }
                if (text.equalsIgnoreCase("NO")) {
                    chatResponce.add("Secuencia Detenida");
                    List<AgContact> contactList1 = contactRepository.findAllByIdUserAndStatus(user, 1);
                    ConcatListContact concatListContact1 = new ConcatListContact(contactList1);
                    comandManager = new ComandManager(concatListContact1.getStringListContact());
                    mainBot.execute(comandManager.showMenu("Elija una Opcion", update));
                }
                break;
        }

        setStepActually(getStepActually() + 1);
        if (getStepActually() == 2) {
            setRunning(false);
        }
    }
}

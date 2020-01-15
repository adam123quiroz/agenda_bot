package com.example.demo.bl;

import com.example.demo.auxs.*;
import com.example.demo.bot.ComandManager;
import com.example.demo.bot.MainBot;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgPerson;
import com.example.demo.domain.AgPhone;
import com.example.demo.domain.AgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class BotBl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotBl.class);

    private UserRepository userRepository;
    private PersonRepository personRepository;
    private FileRepository fileRepository;
    private ContactFileRepository contactFileRepository;
    private TelefonoRepository telefonoRepository;
    private ContactRepository contactRepository;

    private ContactBl contactBl;

    private AdminChat adminChat;

    public static Update update;
    public static MainBot mainBot;

    @Autowired
    public BotBl(UserRepository userRepository,
                 PersonRepository personRepository,
                 FileRepository fileRepository,
                 ContactFileRepository contactFileRepository,
                 TelefonoRepository telefonoRepository,
                 ContactRepository contactRepository,
                 ContactBl contactBl) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
        this.telefonoRepository = telefonoRepository;
        this.contactRepository = contactRepository;
        this.contactBl = contactBl;
    }

    public List<String> processUpdate(Update update, MainBot mainBot) throws TelegramApiException, IOException {
        this.update = update;
        this.mainBot = mainBot;
        List<String> chatResponse = new ArrayList<>();
        AgUser user = initUser(update.getMessage().getFrom());
        continueChatWithUser(user, chatResponse);
        return chatResponse;
    }



    private void continueChatWithUser(AgUser user, List<String> chatResponse) throws TelegramApiException, IOException {

        Sequence sequence = MainBot.getSequenceByChatId(update.getMessage().getChat().getId().toString());
        if (sequence != null) {
            if (sequence.isRunning()) {
                sequence.runSequence(update, chatResponse);
            } else {
                continueChat(user, chatResponse, update);
            }
        } else {
            continueChat(user, chatResponse, update);
        }
    }

    private AgUser initUser(User user) {
        AgUser aUser = userRepository.findByBotUserIdAndStatus(user.getId().toString(), 1);
        if (aUser == null) {
            AgPerson agPerson = new AgPerson();
            agPerson.setFirstName(user.getFirstName());
            agPerson.setLastName(user.getLastName());
            agPerson.setStatus(1);
            agPerson.setTxHost("localhost");
            agPerson.setTxUser("admin");
            agPerson.setTxDate(new Date());
            personRepository.save(agPerson);
            aUser = new AgUser();
            aUser.setBotUserId(user.getId().toString());
            aUser.setIdPerson(agPerson);
            aUser.setStatus(1);
            aUser.setTxHost("localhost");
            aUser.setTxUser("admin");
            aUser.setTxDate(new Date());
            userRepository.save(aUser);
        }
        return aUser;
    }

    private void continueChat(AgUser user, List<String> chatResponse, Update update) throws TelegramApiException {
        switch (update.getMessage().getText()) {
            case "/start":
            case "Hola":
            case "hola":
                List<String> listOptions = new ArrayList<>();
                listOptions.add("Agregar");
                listOptions.add("Modificar");
                listOptions.add("Eliminar");
                listOptions.add("Buscar Contacto");
                listOptions.add("Listar");
                ComandManager comandManager = new ComandManager(listOptions);
                mainBot.execute(comandManager.showMenu("Elija una Opcion", update));
                break;

            case "Agregar":
                SequenceAddContact sequenceAddContact = new SequenceAddContact(
                        userRepository,
                        personRepository,
                        fileRepository,
                        contactFileRepository,
                        telefonoRepository,
                        contactRepository,
                        user
                );
                sequenceAddContact.setStepActually(0);
                sequenceAddContact.setNumberStep(6);
                sequenceAddContact.setRunning(true);
                chatResponse.add("Ingrese nombres del contacto");
                MainBot.addSequenceToList(update.getMessage().getChat().getId().toString(), sequenceAddContact);
                //sequence = sequenceAddContact;
                break;

            case "Modificar":
                SequenceUpdateContact sequenceUpdateContact = new SequenceUpdateContact(
                        userRepository,
                        personRepository,
                        fileRepository,
                        contactFileRepository,
                        telefonoRepository,
                        contactRepository,
                        user,
                        mainBot
                );
                sequenceUpdateContact.setStepActually(0);
                sequenceUpdateContact.setNumberStep(3);
                sequenceUpdateContact.setRunning(true);
                MainBot.addSequenceToList(update.getMessage().getChat().getId().toString(), sequenceUpdateContact);
                //sequence = sequenceUpdateContact;
                List<AgContact> contactList = contactRepository.findAllByIdUserAndStatus(user, 1);
                ConcatListContact concatListContact = new ConcatListContact(contactList);
                comandManager = new ComandManager(concatListContact.getStringListContact());
                mainBot.execute(comandManager.showMenu("Elija una Opcion", update));

                break;
            case "Eliminar":
                SequenceDeleteContact sequenceDeleteContact = new SequenceDeleteContact(
                        userRepository,
                        personRepository,
                        fileRepository,
                        contactFileRepository,
                        telefonoRepository,
                        contactRepository,
                        user,
                        mainBot
                );
                sequenceDeleteContact.setStepActually(0);
                sequenceDeleteContact.setNumberStep(2);
                sequenceDeleteContact.setRunning(true);
                MainBot.addSequenceToList(update.getMessage().getChat().getId().toString(), sequenceDeleteContact);
                List<AgContact> contactList1 = contactRepository.findAllByIdUserAndStatus(user, 1);
                ConcatListContact concatListContact1 = new ConcatListContact(contactList1);
                comandManager = new ComandManager(concatListContact1.getStringListContact());
                mainBot.execute(comandManager.showMenu("Elija una Opcion", update));
                break;
            case "Buscar Contacto":
                SequenceFindContact sequenceFindContact = new SequenceFindContact(contactRepository, user, mainBot, contactBl);
                sequenceFindContact.setStepActually(0);
                sequenceFindContact.setNumberStep(2);
                sequenceFindContact.setRunning(true);
                MainBot.addSequenceToList(update.getMessage().getChat().getId().toString(), sequenceFindContact);
                chatResponse.add("Puedes ingresar el apellido o nombre o numero");
                break;
        }
    }
}

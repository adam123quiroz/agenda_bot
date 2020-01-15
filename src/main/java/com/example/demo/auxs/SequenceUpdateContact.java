package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.bot.ComandManager;
import com.example.demo.bot.MainBot;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceUpdateContact extends Sequence {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceUpdateContact.class);

    private UserRepository userRepository;
    private PersonRepository personRepository;
    private FileRepository fileRepository;
    private ContactFileRepository contactFileRepository;
    private TelefonoRepository telefonoRepository;
    private ContactRepository contactRepository;

    private ContactManager contactManager;
    private AgUser user;
    private MainBot mainBot;
    private AgContact contact;
    private String value;

    private List<String> chatResponce;

    public SequenceUpdateContact(UserRepository userRepository,
                                 PersonRepository personRepository,
                                 FileRepository fileRepository,
                                 ContactFileRepository contactFileRepository,
                                 TelefonoRepository telefonoRepository,
                                 ContactRepository contactRepository,
                                 AgUser user,
                                 MainBot mainBot) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
        this.telefonoRepository = telefonoRepository;
        this.contactRepository = contactRepository;
        this.user = user;
        this.mainBot =  mainBot;
        this.contactManager = new ContactManager(telefonoRepository, personRepository);
    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException, TelegramApiException {
        LOGGER.info("paso {}", getStepActually());
        String text = update.getMessage().getText();
        this.chatResponce = chatResponce;
        switch (getStepActually()) {
            case 0:
                List<String> name = Arrays.asList(text.split(" "));
                AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
                this.contact = contact;
                contactManager.setContact(contact);
                List<String> listOptions = new ArrayList<>();
                listOptions.add("Nombres");
                listOptions.add("Apellidos");
                listOptions.add("Correo Electronico");
                listOptions.add("Fecha de Nacimiento");
                listOptions.add("Numero de Telefono");
                listOptions.add("Foto de Contacto");
                ComandManager comandManager = new ComandManager(listOptions);
                mainBot.execute(comandManager.showMenu("Elija Atributo que quiere cambiar", update));
                break;
            case 1:
                LOGGER.info("VALUEEEE 1");
                value = text;
                chatResponce.add("Cual es nuevo valor?");
                break;
            case 2:
                LOGGER.info("VALUEEEE 2");
                chooseAttribute(text);
                break;
        }

        setStepActually(getStepActually() + 1);
        if (getStepActually() == 3){
            setRunning(false);
        }
    }

    private void chooseAttribute(String text) throws IOException {
        LOGGER.info("VALUEEEE {}", value);
        switch (value) {
            case "Nombres":
                if (!contactManager.setName(text)){
                    chatResponce.add("Debe ingresar un nombre valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Apellidos":
                if (!contactManager.setLastName(text)){
                    chatResponce.add("Debe ingresar  apellidos correctamente, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Correo Electronico":
                if (!contactManager.setEmail(text)){
                    chatResponce.add("Debe ingresar correo valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Fecha de Nacimiento":
                if (!contactManager.setBirthday(text)){
                    chatResponce.add("Debe ingresar formato de fecha valida, ej: 2019-06-13, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Numero de Telefono":
                if (!contactManager.setPhone(text, contactManager.getContact())){
                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                    setStepActually(1);
                }
                break;
            case "Foto de Contacto":
                LOGGER.info("nameFile {}");
                if( ! (text instanceof String) ) {
                    BufferedImage bImage = null;
                    String nameFile = String.valueOf(contactManager.getContact().getIdContact());
                    LOGGER.info("nameFile {}", nameFile);

                    String storeType = "C";
                    String path = "://img/";
                    String mimeType = "jpg";
                    String pathName = storeType + path + nameFile + "." + mimeType;
                    SavePhoto savePhoto = new SavePhoto(BotBl.update, BotBl.mainBot);
                    bImage = ImageIO.read(savePhoto.downloadPhotoByFilePath(savePhoto.getFilePath(savePhoto.getPhoto())));
                    ImageIO.write(bImage, mimeType, new File(pathName));

                }
                break;
        }
        contactRepository.save(contact);
    }
}

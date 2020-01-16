package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.bl.ContactManagerBl;
import com.example.demo.bl.ContactUpdateManagerBl;
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

    private ContactRepository contactRepository;

    private ContactUpdateManagerBl contactManagerBl;
    private AgUser user;
    private MainBot mainBot;
    private String value;

    private List<String> chatResponce;

    public SequenceUpdateContact(ContactRepository contactRepository,
                                 AgUser user,
                                 MainBot mainBot,
                                 ContactUpdateManagerBl contactManagerBl) {
        this.contactRepository = contactRepository;
        this.user = user;
        this.mainBot =  mainBot;
        this.contactManagerBl = contactManagerBl;
    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException, TelegramApiException {
        String text = update.getMessage().getText();
        this.chatResponce = chatResponce;
        switch (getStepActually()) {
            case 0:
                List<String> name = Arrays.asList(text.split(" "));
                AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
                LOGGER.info("paso {}", contact.getIdPerson());
                contactManagerBl.setContact(contact);
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
                value = text;
                chatResponce.add("Cual es nuevo valor?");
                break;
            case 2:
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
                if (!contactManagerBl.setName(text)){
                    chatResponce.add("Debe ingresar un nombre valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Apellidos":
                if (!contactManagerBl.setLastName(text)){
                    chatResponce.add("Debe ingresar  apellidos correctamente, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Correo Electronico":
                if (!contactManagerBl.setEmail(text)){
                    chatResponce.add("Debe ingresar correo valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Fecha de Nacimiento":
                if (!contactManagerBl.setBirthday(text)){
                    chatResponce.add("Debe ingresar formato de fecha valida, ej: 2019-06-13, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Numero de Telefono":
                if (!contactManagerBl.setPhone(text, contactManagerBl.getContact())){
                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                    setStepActually(1);
                }
                break;
            case "Foto de Contacto":
                LOGGER.info("nameFile {}");
                if( ! (text instanceof String) ) {
                    BufferedImage bImage = null;
                    String nameFile = String.valueOf(contactManagerBl.getContact().getIdContact());
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
        contactRepository.save(contactManagerBl.getContact());
    }
}

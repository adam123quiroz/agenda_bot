package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.bl.ContactUpdateManagerBl;
import com.example.demo.bot.ComandManager;
import com.example.demo.bot.MainBot;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContact;
import com.example.demo.domain.AgPhone;
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
    private TelefonoRepository telefonoRepository;

    private ContactUpdateManagerBl contactUpdateManagerBl;
    private MainBot mainBot;
    private String value;
    private int countPhoneSequence = 0;
    private int countPhone = 0;
    private boolean flagPhone = false;
    private boolean flagPhoneUpdate = false;
    private String option;

    private AgPhone agPhone = null;

    private List<String> chatResponce;

    public SequenceUpdateContact(ContactRepository contactRepository,
                                 TelefonoRepository telefonoRepository,
                                 MainBot mainBot,
                                 ContactUpdateManagerBl contactUpdateManagerBl) {
        this.contactRepository = contactRepository;
        this.mainBot =  mainBot;
        this.contactUpdateManagerBl = contactUpdateManagerBl;
        this.telefonoRepository = telefonoRepository;
    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException, TelegramApiException {
        String text = update.getMessage().getText();
        this.chatResponce = chatResponce;
        switch (getStepActually()) {
            case 0:
                List<String> name = Arrays.asList(text.split(" "));
                AgContact contact = contactRepository.findByIdContactAndStatus(Integer.parseInt(name.get(0)), 1);
                LOGGER.info("{}", contact.getIdPerson());
                LOGGER.info("gg {}",contact.getAgPhoneList());
                contactUpdateManagerBl.setContact(contact);
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
                LOGGER.info("Estoy AQUI PRINCIPAL");
                value = text;
                if (value.equalsIgnoreCase("Numero de Telefono")) {
                    flagPhone = true;
                }
                if (flagPhone) {
                    switch (countPhoneSequence) {
                        case 0:
                            List<String> listPhoneOption = new ArrayList<>();
                            listPhoneOption.add("Editar Numeros Existentes");
                            listPhoneOption.add("Añadir mas Numeros de Telefono al Contacto");
                            ComandManager comandManagerPhone = new ComandManager(listPhoneOption);
                            mainBot.execute(comandManagerPhone.showMenu("Elija una Opcion", update));
                            break;
                        case 1:
                            option = text;
                            if (option.equalsIgnoreCase("Editar Numeros Existentes")) {

                                List<AgPhone> phoneList = contactUpdateManagerBl.getContact().getAgPhoneList();
                                LOGGER.info("gg {}", contactUpdateManagerBl.getContact().getAgPhoneList());
                                ConcatListPhone concatListPhone = new ConcatListPhone(phoneList);
                                ComandManager manager = new ComandManager(concatListPhone.getStringListContact());

                                mainBot.execute(manager.showMenu("Elija una Telefono para Editarlo", update));
                            } else if (option.
                                    equalsIgnoreCase("Añadir mas Numeros de Telefono al Contacto")) {
                               chatResponce.add("ingrese el nuevo numero para añadir al Contacto");
                            } else {
                                chatResponce.add("Opcion Invalida");
                                setRunning(false);
                            }
                            break;
                        case 2:
                            if (option.equalsIgnoreCase("Añadir mas Numeros de Telefono al Contacto")) {
                                if (!contactUpdateManagerBl.setPhone(text, contactUpdateManagerBl.getContact())) {
                                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                                    countPhoneSequence = 1;
                                }
                                setRunning(false);
                                chatResponce.add("Telefono Agregado con Exito");
                            }
                            if (option.
                                    equalsIgnoreCase("Editar Numeros Existentes")) {
                                countPhoneSequence = 1;
                                LOGGER.info("Estoy AQUI 1");

                                flagPhoneUpdate = true;
                                if (flagPhoneUpdate) {
                                    switch (countPhone) {
                                        case 0:
                                            agPhone = telefonoRepository.findByPhoneAndStatus(Integer.parseInt(value), 1);
                                            LOGGER.info("GG Phone {}", agPhone.getIdContact());
                                            LOGGER.info("Estoy AQUI 2");
                                            chatResponce.add("Ingrese el nuevo telefono");
                                            break;
                                        case 1:
                                            LOGGER.info("Estoy AQUI 3");
                                            if (! contactUpdateManagerBl.setPhoneUpdate(value, agPhone.getIdContact(), agPhone)) {
                                                chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                                                countPhone = 0;
                                            } else {
                                                setRunning(false);
                                                chatResponce.add("Actualizacion Exitosa");
                                            }
                                            break;
                                    }
                                    countPhone += 1;
                                }


                            }
                            break;
                    }
                    countPhoneSequence += 1;
                    setStepActually(0);
                } else {
                    chatResponce.add("Cual es nuevo valor?");
                }
                break;
            case 2:
                chooseAttribute(text);
                break;
        }

        setStepActually(getStepActually() + 1);
        if (getStepActually() == 3){
            setRunning(false);
            chatResponce.add("Actualizacion correcta");
        }
    }

    private void chooseAttribute(String text) throws IOException {
        switch (value) {
            case "Nombres":
                if (!contactUpdateManagerBl.setName(text)) {
                    chatResponce.add("Debe ingresar un nombre valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Apellidos":
                if (!contactUpdateManagerBl.setLastName(text)) {
                    chatResponce.add("Debe ingresar  apellidos correctamente, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Correo Electronico":
                if (!contactUpdateManagerBl.setEmail(text)) {
                    chatResponce.add("Debe ingresar correo valido, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Fecha de Nacimiento":
                if (!contactUpdateManagerBl.setBirthday(text)) {
                    chatResponce.add("Debe ingresar formato de fecha valida, ej: 2019-06-13, vuelve a ingresar");
                    setStepActually(1);
                }
                break;
            case "Numero de Telefono":
                if (!contactUpdateManagerBl.setPhone(text, contactUpdateManagerBl.getContact())) {
                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                    setStepActually(1);
                }
                break;
            case "Foto de Contacto":
                LOGGER.info("nameFile {}");
                if( ! (text instanceof String) ) {
                    BufferedImage bImage;
                    String nameFile = contactUpdateManagerBl.getContact().getIdUser().getBotUserId()+"-"+ contactUpdateManagerBl.getContact().getIdContact();
                    String storeType = "C";
                    String path = "://img/";
                    String mimeType = "jpg";
                    String pathName = storeType + path + nameFile + "." + mimeType;
                    SavePhoto savePhoto = new SavePhoto(BotBl.update, BotBl.mainBot);
                    bImage = ImageIO.read(savePhoto.downloadPhotoByFilePath(savePhoto.getFilePath(savePhoto.getPhoto())));
                    ImageIO.write(bImage, mimeType, new File(pathName));
                    chatResponce.add("Actualizacion correcta");

                } else {
                    chatResponce.add("enviame una foto");
                }
                break;
        }
        contactRepository.save(contactUpdateManagerBl.getContact());
    }
}

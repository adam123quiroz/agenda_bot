package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContactFile;
import com.example.demo.domain.AgFile;
import com.example.demo.domain.AgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SequenceAddContact extends Sequence {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceAddContact.class);

    private FileRepository fileRepository;
    private ContactFileRepository contactFileRepository;
    private ContactRepository contactRepository;

    private ContactManager contactManager;
    private AgUser user;

    public SequenceAddContact(UserRepository userRepository,
                              PersonRepository personRepository,
                              FileRepository fileRepository,
                              ContactFileRepository contactFileRepository,
                              TelefonoRepository telefonoRepository,
                              ContactRepository contactRepository,
                              AgUser user) {
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
        this.contactRepository = contactRepository;
        this.user = user;
        this.contactManager = new ContactManager(telefonoRepository, personRepository);

    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException {
        String text = update.getMessage().getText();
        switch (getStepActually()) {
            case 0:
                if (!contactManager.setName(text)){
                    chatResponce.add("Debe ingresar un nombre valido, vuelve a ingresar");
                    setStepActually(-1);
                } else {
                    chatResponce.add("Ingresa Apellidos");
                }
                break;
            case 1:
                if (!contactManager.setLastName(text)){
                    chatResponce.add("Debe ingresar  apellidos correctamente, vuelve a ingresar");
                    setStepActually(0);
                } else {
                    chatResponce.add("Ingrese correo electronico");
                }
                break;
            case 2:
                if (!contactManager.setEmail(text)){
                    chatResponce.add("Debe ingresar correo valido, vuelve a ingresar");
                    setStepActually(1);
                } else {
                    chatResponce.add("Ingresa  fecha de Nacimiento");
                }
                break;
            case 3:
                if (!contactManager.setBirthday(text)){
                    chatResponce.add("Debe ingresar formato de fecha valida, ej: 2019-06-13, vuelve a ingresar");
                    setStepActually(2);
                } else {
                    contactManager.getContact().setIdUser(user);
                    contactManager.getContact().setStatus(1);
                    contactManager.getContact().setTxHost("localhost");
                    contactManager.getContact().setTxDate(new Date());
                    contactManager.getContact().setTxUser(user.getIdPerson().getFirstName());
                    LOGGER.info("contact {}", contactManager.getContact().toString());

                    contactRepository.save(contactManager.getContact());
                    chatResponce.add("Ingresa el numero de telefono");
                }
                break;
            case 4:
                if (!contactManager.setPhone(text, contactManager.getContact())){
                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                    setStepActually(3);
                } else {
                    chatResponce.add("Ingrese tu Foto de Contacto");
                }
                break;
            case 5:
                if( ! (text instanceof String) ) {
                    BufferedImage bImage;
                    String nameFile = String.valueOf(contactManager.getContact().getIdContact());

                    String storeType = "C";
                    String path = "://img/";
                    String mimeType = "jpg";
                    String pathName = storeType+path+nameFile+"."+mimeType;
                    SavePhoto savePhoto = new SavePhoto(BotBl.update, BotBl.mainBot);
                    bImage = ImageIO.read(savePhoto.downloadPhotoByFilePath(savePhoto.getFilePath(savePhoto.getPhoto())));
                    ImageIO.write(bImage, mimeType, new File(pathName));

                    AgFile file = new AgFile();
                    file.setFileName(nameFile);
                    file.setMimeType(mimeType);
                    file.setPath(path);
                    file.setScoreType(storeType);
                    file.setStatus(1);
                    file.setTxUser(user.getIdPerson().getFirstName());
                    file.setTxDate(new Date());
                    file.setTxHost("localhost");
                    fileRepository.save(file);

                    AgContactFile userFile = new AgContactFile();
                    userFile.setIdFile(file);
                    userFile.setIdContact(contactManager.getContact());
                    userFile.setStatus(1);
                    userFile.setTxUser(user.getIdPerson().getFirstName());
                    userFile.setTxDate(new Date());
                    userFile.setTxHost("localhost");
                    contactFileRepository.save(userFile);
                } else {
                    chatResponce.add("Enviame una foto");
                }
                break;
        }
        setStepActually(getStepActually() + 1);
        if (getStepActually() == 6){
            setRunning(false);
        }
    }
}

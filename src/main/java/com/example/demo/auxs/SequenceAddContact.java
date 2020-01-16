package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.bl.ContactManagerBl;
import com.example.demo.dao.*;
import com.example.demo.domain.AgContactFile;
import com.example.demo.domain.AgFile;
import com.example.demo.domain.AgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Transactional
public class SequenceAddContact extends Sequence {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceAddContact.class);

    private FileRepository fileRepository;
    private ContactFileRepository contactFileRepository;
    private ContactRepository contactRepository;

    private ContactManagerBl contactManagerBl;
    private AgUser user;

    public SequenceAddContact(PersonRepository personRepository,
                              FileRepository fileRepository,
                              ContactFileRepository contactFileRepository,
                              TelefonoRepository telefonoRepository,
                              ContactRepository contactRepository,
                              AgUser user,
                              ContactManagerBl contactManagerBl) {
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
        this.contactRepository = contactRepository;
        this.user = user;
        this.contactManagerBl = contactManagerBl;

    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws IOException {
        String text = update.getMessage().getText();
        switch (getStepActually()) {
            case 0:
                if (!contactManagerBl.setName(text)){
                    chatResponce.add("Debe ingresar un nombre valido, vuelve a ingresar");
                    setStepActually(-1);
                } else {
                    chatResponce.add("Ingresa Apellidos");
                }
                break;
            case 1:
                if (!contactManagerBl.setLastName(text)){
                    chatResponce.add("Debe ingresar  apellidos correctamente, vuelve a ingresar");
                    setStepActually(0);
                } else {
                    chatResponce.add("Ingrese correo electronico");
                }
                break;
            case 2:
                if (!contactManagerBl.setEmail(text)){
                    chatResponce.add("Debe ingresar correo valido, vuelve a ingresar");
                    setStepActually(1);
                } else {
                    chatResponce.add("Ingresa  fecha de Nacimiento");
                }
                break;
            case 3:
                if (!contactManagerBl.setBirthday(text)){
                    chatResponce.add("Debe ingresar formato de fecha valida, ej: 2019-06-13, vuelve a ingresar");
                    setStepActually(2);
                } else {
                    contactManagerBl.getContact().setIdUser(user);
                    contactManagerBl.getContact().setStatus(1);
                    contactManagerBl.getContact().setTxHost("localhost");
                    contactManagerBl.getContact().setTxDate(new Date());
                    contactManagerBl.getContact().setTxUser(user.getIdPerson().getFirstName());
                    LOGGER.info("contact {}", contactManagerBl.getContact().toString());

                    contactRepository.save(contactManagerBl.getContact());
                    chatResponce.add("Ingresa el numero de telefono");
                }
                break;
            case 4:
                if (!contactManagerBl.setPhone(text, contactManagerBl.getContact())){
                    chatResponce.add("Debe Ingresar un telefono valido, ingrese de nuevo");
                    setStepActually(3);
                } else {
                    chatResponce.add("Ingrese tu Foto de Contacto");
                }
                break;
            case 5:
                if( ! (text instanceof String) ) {
                    BufferedImage bImage;
                    String nameFile = String.valueOf(contactManagerBl.getContact().getIdContact());

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
                    userFile.setIdContact(contactManagerBl.getContact());
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

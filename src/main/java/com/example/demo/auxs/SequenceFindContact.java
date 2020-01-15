package com.example.demo.auxs;

import com.example.demo.bl.ContactBl;
import com.example.demo.bot.ComandManager;
import com.example.demo.bot.MainBot;
import com.example.demo.dao.ContactRepository;
import com.example.demo.domain.AgContact;
import com.example.demo.dto.ContactDto;
import com.example.demo.dto.FileDto;
import com.example.demo.dto.PhoneDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class SequenceFindContact extends Sequence {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceFindContact.class);

    private ContactRepository contactRepository;
    private ContactBl contactBl;

    private MainBot mainBot;

    public SequenceFindContact(ContactRepository contactRepository,
                               MainBot mainBot,
                               ContactBl contactBl) {
        this.contactRepository = contactRepository;
        this.mainBot =  mainBot;
        this.contactBl =  contactBl;

    }

    @Override
    public void runSequence(Update update, List<String> chatResponce) throws TelegramApiException {
        String text = update.getMessage().getText();
        switch (getStepActually()) {
            case 0:
                ConcatListContact concatListContact = null;
                List<AgContact> contactList = null;
                if (validateNumber(text)) {
                    contactList = contactRepository.findAllTelefonoByParecido(text);

                    concatListContact = new ConcatListContact(contactList);
                }

                if (onlyLetters(text)) {
                    contactList = contactRepository.findAllContactByParecido(text);
                    concatListContact = new ConcatListContact(contactList);

                }
                if (contactList.isEmpty()) {
                    chatResponce.add("no existe contactos");
                    setRunning(false);
                } else {
                    ComandManager comandManager = new ComandManager(concatListContact.getStringListContact());
                    mainBot.execute(comandManager.showMenu("Contactos Encontrados: ", update));
                }
                break;

            case 1:
                ContactDto contactDto = contactBl.getContactWithAdress(text);
                SendMessage message;

                message = new SendMessage()
                        .enableMarkdown(true)
                        .setChatId(update.getMessage().getChatId())
                        .setText("*DATOS DEL CONTACTO*");
                mainBot.execute(message);

                message.setText("*Nombres y Apellidos*:\n"+contactDto.getNombres()+" "+contactDto.getApellidos());
                mainBot.execute(message);

                message.setText("*Correo Electronico:*\n"+contactDto.getCorreo());
                mainBot.execute(message);

                message.setText("*Fecha de Nacimiento:*\n"+contactDto.getFechaNac());
                mainBot.execute(message);

                String telefonos = "";
                for (PhoneDto tel :
                        contactDto.getTelefonoList()) {
                    telefonos = "" + tel.getNumero().toString()+"\n";
                }
                message.setText("*Telefonos:*\n"+telefonos);
                mainBot.execute(message);

                SendPhotoContact sendPhotoContact = new SendPhotoContact(mainBot);
                ContactDto contactWithFile = contactBl.getContactWithFile(text);
                FileDto fileDto = contactWithFile.getFileDtos().get(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fileDto.getScoreType());
                stringBuilder.append(fileDto.getPath());
                stringBuilder.append(fileDto.getFileName());
                stringBuilder.append(".");
                stringBuilder.append(fileDto.getMimeType());
                sendPhotoContact.sendImageUploadingAFile(stringBuilder.toString(), update.getMessage().getChat().getId().toString());
                break;
        }

        setStepActually(getStepActually() + 1);
        if (getStepActually() == 2){
            setRunning(false);
        }
    }

    private boolean validateNumber(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private boolean onlyLetters(String text) {
        List<String> addressPart = Arrays.asList(text.split(" "));
        for (String data :
                addressPart) {
            for (int i = 0; i < data.length(); i++) {
                char caracter = data.toUpperCase().charAt(i);
                int valorASCII = (int)caracter;
                if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90))
                    return false;
            }
        }

        return true;
    }


}
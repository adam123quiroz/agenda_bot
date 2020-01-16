package com.example.demo.auxs;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean onlyLetters(String text) {
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

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePhone(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}

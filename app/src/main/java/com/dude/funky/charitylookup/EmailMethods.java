package com.dude.funky.charitylookup;

public class EmailMethods {

    static String encodeEmail(String email) {
        return email.replace(".", ",");
    }

    static String decodeEmail(String email) {
        return email.replace(",", ".");
    }
}

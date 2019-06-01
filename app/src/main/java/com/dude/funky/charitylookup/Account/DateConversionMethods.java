package com.dude.funky.charitylookup.Account;

public class DateConversionMethods {
    public static String encodeDate(String date) {
        return date.replace("/", ".");
    }

    public static String decodeDate(String date) {
        return date.replace("/", ".");
    }
}

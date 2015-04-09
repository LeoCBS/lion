package com.lion.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {

    private MessageUtils() {

    }

    public static String getMessage(final Locale locale, final String messageKey) {
        final ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
        return rb.getString(messageKey);
    }

    public static String getMessage(final Locale locale, final String messageKey, final Object[] params) {
        final ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
        return MessageFormat.format(rb.getString(messageKey), params);
    }

}

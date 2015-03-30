package com.lion.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Leonardo Cesar Borges
 * 
 */
public class AWUtils {

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * Criptograma uma String para MD5
     * 
     * @param valor
     * @return
     * @throws Exception
     */
    public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(text == null){
        	throw new IllegalArgumentException("Param text can't be null");
        }
    	MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    /**
     * Convert first letter to upper from which word
     * @param text
     * @return
     */
    public static String capitalizeString(String text) {
    	if(text == null){
        	throw new IllegalArgumentException("Param text can't be null");
        }
        String retorno = "";
        String[] split = text.toLowerCase().split(" ");
        for (String string : split) {
            retorno = retorno + " " + string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
        }
        return retorno.trim();
    }

    public static String getSHA(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	 if(text == null){
         	throw new IllegalArgumentException("Param text can't be null");
         }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(text.getBytes("UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    public static String getLoginUserLogged() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.getUserPrincipal().getName();
    }

    public static Object getAttributeFromSession(String idAttribute) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return session.getAttribute(idAttribute);
    }

    public static void setAttributeToSession(String idAttribute, Object attribute) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(idAttribute, attribute);
    }
}

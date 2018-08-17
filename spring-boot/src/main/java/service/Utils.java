package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String getMd5(String txt) {
        try {
            StringBuffer sbuf = new StringBuffer();

            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(txt.getBytes());

            byte[] msgStr = mDigest.digest();

            for (int i = 0; i < msgStr.length; i++) {
                String tmpEncTxt = Integer.toHexString((int) msgStr[i] & 0x00ff);
                sbuf.append(tmpEncTxt);
            }
            return sbuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return txt;
        }
    }
}

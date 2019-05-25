package com.sc;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by songsf on 2019/4/12.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.CHINA);
        SimpleDateFormat df2= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        System.out.println(df.format(new Date()));
        System.out.println(df2.format(new Date()));
    }
    public static void codeTest() throws IOException {
        String str = "Y2xpZW50OjEyMzQ1Njc4OTA=";
        String pwd = "client:1234567890";
        BASE64Decoder base64Decoder = new BASE64Decoder();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.println(base64Encoder.encode(pwd.getBytes()));
        System.out.println(new String(base64Decoder.decodeBuffer(str),"UTF-8"));
    }
}

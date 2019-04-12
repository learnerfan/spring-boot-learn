package com.sc;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by songsf on 2019/4/12.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String str = "Y2xpZW50OjEyMzQ1Njc4OTA=";
        String pwd = "client:1234567890";
        BASE64Decoder base64Decoder = new BASE64Decoder();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.println(base64Encoder.encode(pwd.getBytes()));
        System.out.println(new String(base64Decoder.decodeBuffer(str),"UTF-8"));
    }
}

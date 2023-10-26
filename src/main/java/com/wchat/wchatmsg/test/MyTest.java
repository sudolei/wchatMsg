package com.wchat.wchatmsg.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MyTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp 4RPcs8TgAE7OaBO FZXvnaqQ==";
        String enStr = URLEncoder.encode(str,"UTF-8");
        enStr = enStr.replace("+","%2B");
        System.out.println(URLDecoder.decode(enStr,"UTF-8"));
    }
}

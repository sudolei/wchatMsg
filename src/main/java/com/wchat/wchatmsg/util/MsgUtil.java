package com.wchat.wchatmsg.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wchat.wchatmsg.config.AppInfo;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Component
public class MsgUtil {

    /**
     * 处理echostr
     *
     * @param verifyEchoStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String handleEchoStr(String verifyEchoStr) throws UnsupportedEncodingException {
        String enStr = URLEncoder.encode(verifyEchoStr, "UTF-8");
        String reStr = enStr.replace("+", "%2B");
        String echostr = URLDecoder.decode(reStr, "UTF-8");
        return echostr;
    }

    /**
     *
     * @param appInfo
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws AesException
     */
    public static String getEchoStr(AppInfo appInfo, String msg_signature, String timestamp, String nonce, String echostr) throws AesException {
        String sToken = appInfo.getsToken();
        String sCorpID = appInfo.getsCorpID();
        String sEncodingAESKey = appInfo.getsEncodingAESKey();
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
        String sEchoStr = null; //需要返回的明文
        try {
            sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,
                    nonce, echostr);
            System.out.println("verifyurl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
            // HttpUtils.SetResponse(sEchoStr);
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            e.printStackTrace();
        }
        return sEchoStr;
    }

}

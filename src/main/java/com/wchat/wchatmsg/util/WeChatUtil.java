package com.wchat.wchatmsg.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;

public class WeChatUtil {

    private static final String CORP_ID = "wwded2338038363bc0";
    private static final String AGENT_SECRET = "IgAQXSmSgLsqhW8d8MCBJumKb76lYindvynuSvK5Gzo";
    private static final String AGENT_ID = "1000002";
    private static final String API_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

    public static void main(String[] args) throws IOException {
    }

    /**
     * 消息发送
     * @param content：发送的消息内容
     * @param toUser：发送给哪个用户，多个用户用|隔开
     * @throws IOException
     */
    public static void sendMsg(String content, String toUser) throws IOException {
        String accessToken = getAccessToken();
        String url = API_URL + "?access_token=" + accessToken;

        HashMap<String, String> textMap = new HashMap<>();
        textMap.put("content", content);

        HashMap<String, Object> m = new HashMap<>();
        m.put("touser", toUser);
        m.put("msgtype", "text");
        m.put("agentid", AGENT_ID);
        m.put("text", textMap);
        m.put("safe", 0);

        HttpServer httpServer = new HttpServer();
        String result = httpServer.sendPost(url, m);
        System.out.println(result);
    }

    /**
     * 获取token
     * @return
     * @throws IOException
     */
    private static String getAccessToken() throws IOException {
        String uri = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORP_ID + "&corpsecret=" + AGENT_SECRET;
        HttpServer httpServer = new HttpServer();
        String responseBody = httpServer.sendGet(uri);
        JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
        String accessToken = jsonObject.get("access_token").getAsString();
        return accessToken;
    }
}

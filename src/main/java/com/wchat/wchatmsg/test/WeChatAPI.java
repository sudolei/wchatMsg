package com.wchat.wchatmsg.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class WeChatAPI {
    private static final String CORP_ID = "wwded2338038363bc0";
    private static final String AGENT_SECRET = "IgAQXSmSgLsqhW8d8MCBJumKb76lYindvynuSvK5Gzo";
    private static final String AGENT_ID = "1000002";
    private static final String API_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

    public static void main(String[] args) throws IOException {
         sendMsg();
    }

    public static void sendMsg() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String accessToken = getAccessToken();
        System.out.println(accessToken);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"touser\" : \"2a4ace25deb05ddd9871dcbaaf4e712c|SunLei\",\"msgtype\" : \"text\",\"agentid\" : " + AGENT_ID + ",\"text\" : {\"content\" : \"Hello, World!\"},\"safe\":0}");
        Request request = new Request.Builder()
                .url(API_URL + "?access_token=" + accessToken)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }



    private static String getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORP_ID + "&corpsecret=" + AGENT_SECRET)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
        String accessToken = jsonObject.get("access_token").getAsString();

        return accessToken;
    }
}
package com.wchat.wchatmsg.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
//prefix前缀需要和yml配置文件里的匹配。
@ConfigurationProperties(prefix = "app")
public class AppInfo {

    private String sToken;

    private String sCorpID;

    private String sEncodingAESKey;

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public String getsCorpID() {
        return sCorpID;
    }

    public void setsCorpID(String sCorpID) {
        this.sCorpID = sCorpID;
    }

    public String getsEncodingAESKey() {
        return sEncodingAESKey;
    }

    public void setsEncodingAESKey(String sEncodingAESKey) {
        this.sEncodingAESKey = sEncodingAESKey;
    }
}

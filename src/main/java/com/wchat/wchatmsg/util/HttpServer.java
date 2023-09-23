package com.wchat.wchatmsg.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzj
 * @createDtae 2021/12/27
 */
@Component
public class HttpServer {

    public HttpEntity<Map<String, Object>> generatePostJson(Map<String, Object> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);

        return httpEntity;
    }

    /**
     * post请求、请求参数为json
     *
     * @return
     */
    public String sendPost(String uri, Map<String, Object> jsonMap) {
        ResponseEntity<String> apiResponse = null;
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        try {
            RestTemplate restTemplate = restTemplateConfig.restTemplate();
            System.out.println(generatePostJson(jsonMap));
            apiResponse=restTemplate.postForEntity(uri, generatePostJson(jsonMap), String.class);

        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ServiceException("网络通讯异常", 200);
        }
        return apiResponse.getBody();
    }

    /**
     * get请求、请求参数为json
     *
     * @return
     */
    public String sendGet(String uri) {
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        RestTemplate restTemplate = restTemplateConfig.restTemplate();
        ResponseEntity<String> apiResponse = restTemplate.getForEntity(uri,String.class);
        return apiResponse.getBody();
    }
    /**
     * get请求、请求参数为json
     *
     * @return
     */
    public String sendCameraGet(String uri,Map<String, Object> jsonMap) {
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        RestTemplate restTemplate = restTemplateConfig.restTemplate();
        HttpHeaders headers = new HttpHeaders();
        if(StringUtils.isNotNull(jsonMap.get("Authorization"))){
            String authorization = jsonMap.get("Authorization").toString();
            headers.add("Authorization", authorization);
        }
        ResponseEntity<String> apiResponse = restTemplate.exchange(uri, HttpMethod.GET,new HttpEntity<String>(headers),String.class,"");
        return apiResponse.getBody();
    }
    /**
     * post请求、请求参数为json
     *
     * @return
     */
    public String sendCameraPost(String uri, Map<String, Object> jsonMap) {
        ResponseEntity<String> apiResponse = null;
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        try {
            RestTemplate restTemplate = restTemplateConfig.restTemplate();
            apiResponse=restTemplate.postForEntity(uri, generatePostJsonForCamera(jsonMap), String.class);

        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ServiceException("网络通讯异常", 200);
        }
        return apiResponse.getBody();
    }
    public HttpEntity<Map<String, Object>> generatePostJsonForCamera(Map<String, Object> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, Object> map = new HashMap<>();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        if(StringUtils.isNotNull(jsonMap.get("Authorization"))){
            String authorization = jsonMap.get("Authorization").toString();
            httpHeaders.set("Authorization", authorization);
        }

        httpHeaders.setContentType(type);


        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, httpHeaders);

        return httpEntity;
    }
}

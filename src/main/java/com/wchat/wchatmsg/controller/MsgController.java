package com.wchat.wchatmsg.controller;

import com.wchat.wchatmsg.config.AppInfo;
import com.wchat.wchatmsg.test.WeChatAPI;
import com.wchat.wchatmsg.util.AesException;
import com.wchat.wchatmsg.util.MsgUtil;
import com.wchat.wchatmsg.util.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
    private AppInfo appInfo;

    @GetMapping("/receiveMsg")
    public String receiveMsg(String msg_signature, String timestamp, String nonce, String echostr) throws UnsupportedEncodingException {
        System.out.println(appInfo.getsCorpID());
        echostr = MsgUtil.handleEchoStr(echostr);
        String res = null;
        try {
            res = MsgUtil.getEchoStr(appInfo, msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * POST回调
     *
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param request
     * @return
     * @throws IOException
     * @throws AesException
     */
    @PostMapping("/receiveMsg")
    public String res(String msg_signature, String timestamp, String nonce, HttpServletRequest request) throws IOException, AesException {
        System.out.println("post recevieMsg");
        System.out.println(msg_signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        String sToken = appInfo.getsToken();
        String sCorpID = appInfo.getsCorpID();
        String sEncodingAESKey = appInfo.getsEncodingAESKey();
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String postData = sb.toString();
        System.out.println(postData);
        String Content = null;
        try {
            String sMsg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
            System.out.println("after decrypt msg: " + sMsg);
            // TODO: 解析出明文xml标签的内容进行处理
            // For example:
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(sMsg));
            Document doc = builder.parse(inputSource);
            // 获取根节点
            Element root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getTagName() + ": " + element.getTextContent());
                }
            }
            Content = nodeList.item(0).getTextContent();
            System.out.println("Content：" + Content);
        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            e.printStackTrace();
        }
        return Content;
    }

    /**
     * 消息推送
     */
    @GetMapping("/sendMsg")
    public void sendMsg() {
        try {
            WeChatAPI.sendMsg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 消息发送
     */
    @GetMapping("/sendMessage")
    public void sendMessage() {
        try {
            WeChatAPI.sendMsg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

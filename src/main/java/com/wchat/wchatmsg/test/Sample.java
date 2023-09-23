package com.wchat.wchatmsg.test;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.wchat.wchatmsg.util.WXBizMsgCrypt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import javax.xml.parsers.DocumentBuilderFactory;
//import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class Sample {

	public static void main(String[] args) throws Exception {
		String sToken = "QDG6eK";
		String sCorpID = "wx5823bf96d3bd56c7";
		String sEncodingAESKey = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";

		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		/*
		------------使用示例一：验证回调URL---------------
		*企业开启回调模式时，企业微信会向验证url发送一个get请求 
		假设点击验证时，企业收到类似请求：
		* GET /cgi-bin/wxpush?msg_signature=5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3&timestamp=1409659589&nonce=263014780&echostr=P9nAzCzyDtyTWESHep1vC5X9xho%2FqYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp%2B4RPcs8TgAE7OaBO%2BFZXvnaqQ%3D%3D 
		* HTTP/1.1 Host: qy.weixin.qq.com

		接收到该请求时，企业应
		1.解析出Get请求的参数，包括消息体签名(msg_signature)，时间戳(timestamp)，随机数字串(nonce)以及企业微信推送过来的随机加密字符串(echostr),
		这一步注意作URL解码。
		2.验证消息体签名的正确性 
		3. 解密出echostr原文，将原文当作Get请求的response，返回给企业微信
		第2，3步可以用企业微信提供的库函数VerifyURL来实现。

		*/
		// 解析出url上的参数值如下：
		// String sVerifyMsgSig = HttpUtils.ParseUrl("msg_signature");
		String sVerifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
		// String sVerifyTimeStamp = HttpUtils.ParseUrl("timestamp");
		String sVerifyTimeStamp = "1409659589";
		// String sVerifyNonce = HttpUtils.ParseUrl("nonce");
		String sVerifyNonce = "263014780";
		// String sVerifyEchoStr = HttpUtils.ParseUrl("echostr");
		String sVerifyEchoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
		String sEchoStr; //需要返回的明文
		try {
			sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,
					sVerifyNonce, sVerifyEchoStr);
			System.out.println("verifyurl echostr: " + sEchoStr);
			// 验证URL成功，将sEchoStr返回
			// HttpUtils.SetResponse(sEchoStr);
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}
	}
}

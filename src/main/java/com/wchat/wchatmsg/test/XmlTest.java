package com.wchat.wchatmsg.test;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
public class XmlTest {

    public static void main(String[] args) throws Exception {
        String sMsg = "<xml><ToUserName><![CDATA[wwded2338038363bc0]]></ToUserName><FromUserName><![CDATA[SunLei]]></FromUserName><CreateTime>1695440103</CreateTime><MsgType><![CDATA[event]]></MsgType><AgentID>1000002</AgentID><Event><![CDATA[LOCATION]]></Event><Latitude>36.6875</Latitude><Longitude>117.11</Longitude><Precision>15</Precision><AppType><![CDATA[wxwork]]></AppType></xml>";
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
//        NodeList nodelist1 = root.getElementsByTagName("Content");
        String Content = nodeList.item(0).getTextContent();
        System.out.println("Content：" + Content);
    }
}

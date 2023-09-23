package com.wchat.wchatmsg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;

@SpringBootTest
class WchatMsgApplicationTests {

    @Test
    void contextLoads() {
        String signature="ASDFQWEXZCVAQFASDFASDFSS";
        System.out.println(URLDecoder.decode(signature));
    }

}

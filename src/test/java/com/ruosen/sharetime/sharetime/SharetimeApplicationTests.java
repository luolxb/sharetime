package com.ruosen.sharetime.sharetime;

import com.ruosen.sharetime.sharetime.rabbitmq.provider.InfoSender;
import com.ruosen.sharetime.sharetime.rabbitmq.provider.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SharetimeApplicationTests {

    @Autowired
    private Sender sender;

    @Autowired
    private InfoSender infoSender;

    @Test
    void contextLoads() {
        sender.send("ssssssss");
    }

    @Test
    void test() {
        infoSender.sendInfo("info......");
        infoSender.sendError("error......");
    }

}

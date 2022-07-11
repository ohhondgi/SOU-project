package com.SOU.mockServer.service;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

// 실제 메시지를 받았을 때 비지니스 로직이 돌아가는 service
// test flow에서 사용할 수 있는 여러 POJO method 제공가능
@MessageEndpoint
@Component
public class TcpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcpService.class);

    @ServiceActivator(inputChannel = "fromTcp")
    public byte[] handleMessage(byte[] msg) {
        LOGGER.info("Received request: {}", new String(msg));
        return "test".getBytes(StandardCharsets.UTF_8);
    }
}
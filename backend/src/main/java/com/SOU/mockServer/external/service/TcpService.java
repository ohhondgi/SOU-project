package com.SOU.mockServer.external.service;

import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.external.serialize.OnlineMessageSerializer;
import java.io.IOException;
import java.net.Socket;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TcpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpService.class);

    @Value("${tcp.vpn.server.host}")
    private String HOST;
    @Value("${tcp.vpn.server.port}")
    private Integer PORT;

    private final OnlineMessageSerializer onlineMessageSerializer;

    public void replyMessage(Message message) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        onlineMessageSerializer.serialize(message, socket.getOutputStream());
        LOGGER.debug("send message for VPN server");

        socket.close();
    }

}

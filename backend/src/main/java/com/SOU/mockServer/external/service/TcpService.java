package com.SOU.mockServer.external.service;

import com.SOU.mockServer.common.message.CommonMessageRequestDto;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TcpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcpService.class);

    //server host, port
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public CommonFieldMessage commonFieldMessage(CommonMessageRequestDto commonMessageDto) throws IOException {
        // create client socket
        Socket socket = new Socket(HOST, PORT);
        CommonFieldMessage message = commonMessageDto.of();

        // when
        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);

        socket.getOutputStream().write(outputStream.toData());

        //then
        Input in = new ByteArrayInput(socket.getInputStream().readAllBytes(),new BytesConverter());
        CommonFieldMessage resultMessage = new CommonFieldMessage();
        resultMessage.readFrom(in);

        socket.close();
        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());
        return resultMessage;
    }
}

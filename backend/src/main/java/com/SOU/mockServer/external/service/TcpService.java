package com.SOU.mockServer.external.service;

import static com.SOU.mockServer.external.message.BankTranTypeCode.CONNECTION_STATE;
import static com.SOU.mockServer.external.message.BankTranTypeCode.NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT;
import static com.SOU.mockServer.external.message.Constants.RECEIVER_CODE;
import static com.SOU.mockServer.external.message.Constants.SENDER_CODE;
import static com.SOU.mockServer.external.message.ResponseCode.SUCCESS;

import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TcpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcpService.class);

    //server host, port
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public String CommonFieldMessageTest() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        CommonFieldMessage message = new CommonFieldMessage(SENDER_CODE,RECEIVER_CODE,
            "1234567890",NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT,CONNECTION_STATE,SUCCESS,"filtertest");

        // when
        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);

        socket.getOutputStream().write(outputStream.toData());

        //then
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        socket.close();
        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", response);
        return response;
    }
}

package com.SOU.mockServer;

import static com.SOU.mockServer.external.message.BankTranTypeCode.CONNECTION_STATE;
import static com.SOU.mockServer.external.message.BankTranTypeCode.INDIVIDUAL_WITHDRAWAL;
import static com.SOU.mockServer.external.message.BankTranTypeCode.NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT;
import static com.SOU.mockServer.external.message.Constants.RECEIVER_CODE;
import static com.SOU.mockServer.external.message.Constants.SENDER_CODE;
import static com.SOU.mockServer.external.message.ResponseCode.SUCCESS;

import com.SOU.mockServer.common.config.TCPConfig;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.SocketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
class MockServerApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServerApplicationTests.class);

    //server host, port
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    @Test
    public void CommonFieldMessageTest() throws IOException {
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
    }

    @Test
    public void NotificationIndividualWithdrawalMessageTest() throws IOException {
        Socket socket = new Socket(HOST, PORT);

//        NotificationIndividualWithdrawalMessage message = new NotificationIndividualWithdrawalMessage();
//        message.getCommonFieldMessage().getTranTypeCode().set(INDIVIDUAL_WITHDRAWAL);

        NotificationIndividualWithdrawalMessage message = new NotificationIndividualWithdrawalMessage();
        message.getCommonFieldMessage().getTranTypeCode().set(INDIVIDUAL_WITHDRAWAL);

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
    }

    public static void main(String[] args) throws Exception {

        LOGGER.info("Sending request to {}:{}", HOST, PORT);
        Socket socket = SocketFactory.getDefault().createSocket(HOST, PORT);

        // test message 생성
//        CommonFieldMessage message = new CommonFieldMessage(SENDER_CODE,RECEIVER_CODE,
//            "1234567890",NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT,INDIVIDUAL_WITHDRAWAL,SUCCESS,"filtertest");

        // 공통 정보부에 대한 test message 생성
        CommonFieldMessage message = new CommonFieldMessage(SENDER_CODE,RECEIVER_CODE,
            "1234567890",NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT,CONNECTION_STATE,SUCCESS,"filtertest");

        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);

        socket.getOutputStream().write(outputStream.toData());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        socket.close();
        LOGGER.info("Received response: {}", response);
    }

}

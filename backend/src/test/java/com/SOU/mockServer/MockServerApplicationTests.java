package com.SOU.mockServer;

import static com.SOU.mockServer.external.message.BankTranTypeCode.CONNECTION_STATE;
import static com.SOU.mockServer.external.message.BankTranTypeCode.NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT;
import static com.SOU.mockServer.external.message.Constants.RECEIVER_CODE;
import static com.SOU.mockServer.external.message.Constants.SENDER_CODE;
import static com.SOU.mockServer.external.message.ResponseCode.SUCCESS;

import com.SOU.mockServer.common.message.Field;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.SOU.mockServer.external.serialize.OnlineMessageSerializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.net.SocketFactory;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageChannel;

@SpringBootTest
class MockServerApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServerApplicationTests.class);

    //server host, port
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    @Test
    public void testSocketTranslate() throws IOException {

        //given

        // when

        //then
    }


    public static void main(String[] args) throws Exception {

        OnlineMessageSerializer serializer = new OnlineMessageSerializer(
            10, 4, new BytesConverter(), true);;

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

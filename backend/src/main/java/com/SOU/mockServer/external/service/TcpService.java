package com.SOU.mockServer.external.service;

import com.SOU.mockServer.common.message.CommonMessageRequestDto;
import com.SOU.mockServer.common.message.NotificationIndividualWithdrawalMessageDto;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.IOException;
import java.net.Socket;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class TcpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpService.class);
    private final BytesConverter bytesConverter = new BytesConverter();
    //server host, port
    @Value("${tcp.server.host}")
    private String HOST;
    @Value("${tcp.server.port}")
    private Integer PORT;

    public CommonFieldMessage commonFieldMessage(CommonMessageRequestDto commonMessageDto)
        throws IOException {
        // create client socket
        Socket socket = new Socket(HOST, PORT);
        CommonFieldMessage message = commonMessageDto.of();

        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);

        socket.getOutputStream().write(outputStream.toData());

        Input in = new ByteArrayInput(socket.getInputStream().readAllBytes(), bytesConverter);
        CommonFieldMessage resultMessage = new CommonFieldMessage();
        resultMessage.readFrom(in);

        socket.close();
        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());
        return resultMessage;
    }

    public NotificationIndividualWithdrawalMessage notificationIndividualWithdrawalMessage(
        NotificationIndividualWithdrawalMessageDto niwmDto) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        NotificationIndividualWithdrawalMessage message = new NotificationIndividualWithdrawalMessage(
            niwmDto.getCommonFiledMessage().of(), niwmDto);

        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);
        socket.getOutputStream().write(outputStream.toData());

        Input in = new ByteArrayInput(socket.getInputStream().readAllBytes(), bytesConverter);
        NotificationIndividualWithdrawalMessage resultMessage = new NotificationIndividualWithdrawalMessage();
        resultMessage.readFrom(in);

        socket.close();
        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());

        return resultMessage;
    }
}

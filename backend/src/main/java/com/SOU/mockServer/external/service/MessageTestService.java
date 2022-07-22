package com.SOU.mockServer.external.service;

import com.SOU.mockServer.common.message.CommonMessageRequestDto;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.message.NotificationIndividualWithdrawalMessageDto;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MessageTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTestService.class);
    private final BytesConverter bytesConverter = new BytesConverter();
    //server host, port
    @Value("${tcp.server.host}")
    private String HOST;
    @Value("${tcp.server.port}")
    private Integer PORT;

    @Value("${tcp.vpn.server.host}")
    private String VPNHOST;
    @Value("${tcp.vpn.server.port}")
    private Integer VPNPORT;


    public CommonFieldMessage commonFieldMessage(CommonMessageRequestDto commonMessageDto)
        throws IOException {
        // create client socket
        CommonFieldMessage message = commonMessageDto.of();

        Input in = sendAndReceive(message);

        CommonFieldMessage resultMessage = new CommonFieldMessage();
        resultMessage.readFrom(in);

        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());
        return resultMessage;
    }

    public NotificationIndividualWithdrawalMessage notificationIndividualWithdrawalMessage(
        NotificationIndividualWithdrawalMessageDto niwmDto) throws IOException {
        NotificationIndividualWithdrawalMessage message = new NotificationIndividualWithdrawalMessage(
            niwmDto.getCommonFiledMessage().of(), niwmDto);

        Input in = sendAndReceive(message);

        NotificationIndividualWithdrawalMessage resultMessage = new NotificationIndividualWithdrawalMessage();
        resultMessage.readFrom(in);

        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());

        return resultMessage;
    }

    public Input sendAndReceive(Message message) throws IOException {

        // create client socket
        Socket socket = new Socket(HOST, PORT);
        ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
        message.writeTo(outputStream);
        socket.getOutputStream().write(outputStream.toData());
        socket.close();

        // create server socket
        ServerSocket serverSocket = new ServerSocket();
        SocketAddress serverSocketAddress = new InetSocketAddress(VPNHOST, VPNPORT);
        serverSocket.bind(serverSocketAddress, 5);
        Socket receivedSocket = serverSocket.accept();
        serverSocket.close();

        return new ByteArrayInput(receivedSocket.getInputStream().readAllBytes(), bytesConverter);
    }
}

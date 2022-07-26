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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MessageTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTestService.class);
    private final BytesConverter bytesConverter = new BytesConverter();

    private final SocketService socketService;

    public MessageTestService(SocketService socketService) {
        this.socketService = socketService;
    }

    public Object MessageTest(CommonMessageRequestDto commonMessageDto){
        List<Message> messageLinkedList = new LinkedList<>();
        for (int i = 0; i< 10000; i++){
            CommonFieldMessage message = commonMessageDto.of();

            Input in = sendAndReceive(message);

            CommonFieldMessage resultMessage = new CommonFieldMessage();
            resultMessage.readFrom(in);
            messageLinkedList.add(resultMessage);
        }
        return messageLinkedList.size();
    }

    public CommonFieldMessage commonFieldMessage(CommonMessageRequestDto commonMessageDto) {
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
        NotificationIndividualWithdrawalMessageDto niwmDto) {
        NotificationIndividualWithdrawalMessage message = new NotificationIndividualWithdrawalMessage(
            niwmDto.getCommonFiledMessage().of(), niwmDto);

        Input in = sendAndReceive(message);

        NotificationIndividualWithdrawalMessage resultMessage = new NotificationIndividualWithdrawalMessage();
        resultMessage.readFrom(in);

        LOGGER.info("Client Socket Sending request to {}:{}", "localhost", 1234);
        LOGGER.info("Client Socket Received response: {}", resultMessage.toString());
        return resultMessage;
    }

    public Input sendAndReceive(Message message) {
        try {
            Socket clientSocket = socketService.getClientSocket();
            ByteArrayOutput outputStream = new ByteArrayOutput(message.getTotalLength());
            message.writeTo(outputStream);
            clientSocket.getOutputStream().write(outputStream.toData());
            clientSocket.getOutputStream().flush();
//            clientSocket.close();

            // create server socket
            ServerSocket serverSocket = socketService.getServerSocket();
            Socket receivedSocket = serverSocket.accept();

//            Input in = new ByteArrayInput(receivedSocket.getInputStream().readAllBytes(),
//                bytesConverter);
            Input in = new ByteArrayInput(
                receivedSocket.getInputStream().readNBytes(message.getTotalLength()),
                bytesConverter);

//            receivedSocket.getInputStream().close();
//            serverSocket.close();
            return in;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT,
                "Socket " + e.getMessage());
        }
    }
}

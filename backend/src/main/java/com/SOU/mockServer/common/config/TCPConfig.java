package com.SOU.mockServer.common.config;

import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.SOU.mockServer.external.serialize.OnlineMessageDeserializer;
import com.SOU.mockServer.external.serialize.OnlineMessageSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@EnableIntegration
// search for @messagingGateway interface
@IntegrationComponentScan
@Configuration
public class TCPConfig {

    private final boolean treatTimeoutAsEndOfMessage = true;
    private final int maxMessageSize = 1000;
    private final int lengthHeaderSize = 4;

    @Value("${tcp.server.port}")
    private int port;
    @Value("${tcp.server.host}")
    private String host;

//    @Bean
//    public AbstractClientConnectionFactory clientConnectionFactory() {
//
//        TcpNetClientConnectionFactory clientCF = new TcpNetClientConnectionFactory(this.host,
//            this.port);
//        clientCF.setSerializer(
//            new OnlineMessageSerializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
//                treatTimeoutAsEndOfMessage));
//        clientCF.setDeserializer(
//            new OnlineMessageDeserializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
//                treatTimeoutAsEndOfMessage));
//        // thread가 message를 보낼 때 마다 새로운 연결을 생성하는 설정(singleUse)
//        // 높은 처리량을 제공 -> 매번 새로운 연결 생성에 따른 오버헤드 증가
////        clientCF.setSingleUse(true);
//        // connection 당 reply 대기시간으로, default는 10000(10 sec)
////        clientCF.setSoTimeout(1000);
//        return clientCF;
//    }

//    @Bean
//    public ThreadAffinityClientConnectionFactory tacf() {
//        return new ThreadAffinityClientConnectionFactory(clientConnectionFactory());
//    }

    // clientConnectionFactory를 사용하여 channel을 통해 message를 전송함,
    // reply message를 수신하면,
//    @ServiceActivator(inputChannel = "inboundChannel", outputChannel = "replyChannel")
//    @Bean
//    public TcpOutboundGateway tcpOutGate() {
//        TcpOutboundGateway outGate = new TcpOutboundGateway();
//        outGate.setReplyChannel(replyChannel());
//        outGate.setConnectionFactory(clientConnectionFactory());
//        return outGate;
//    }


    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNetServerConnectionFactory serverCF = new TcpNetServerConnectionFactory(this.port);
        serverCF.setDeserializer(
            new OnlineMessageDeserializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage));
        serverCF.setSerializer(
            new OnlineMessageSerializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage));
        // message를 수신하고 응답할 때마다 새로운 연결을 생성하는 설정(singleUse=ture)
        serverCF.setSingleUse(true);
        serverCF.setSoTcpNoDelay(true);
        serverCF.setSoKeepAlive(true);
        return serverCF;
    }


    /*
    server client factory 사용하여 message 수신
    1. 전달받는 payload로 message를 구성한 후에, requestChannel에 message를 전송
    2. response message를 수신하면, response message로부터 payload를 connection으로 전송
     */
    @Bean
    public TcpInboundGateway tcpInGate() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverConnectionFactory());
        inGate.setRequestChannel(inboundChannel());
        inGate.setReplyChannel(replyChannel());
        return inGate;
    }

    @Bean
    public MessageChannel inboundChannel() {
        DirectChannel channel = new DirectChannel();
        // 특정 type에 대한 data만 허용
//        channel.setDatatypes(CommonFieldMessage.class,
//            NotificationIndividualWithdrawalMessage.class);
        return channel;
    }

//    @Bean MessageChannel outboundChannel() {
//        return new DirectChannel();
//    }

    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }


}

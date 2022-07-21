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
import org.springframework.integration.annotation.Router;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.router.PayloadTypeRouter;
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

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNetServerConnectionFactory serverCF = new TcpNetServerConnectionFactory(this.port);
        serverCF.setDeserializer(
            new OnlineMessageDeserializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage));
        serverCF.setSerializer(
            new OnlineMessageSerializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage));

//        serverCF.setSoTimeout(1000);
        // 각 socket 요청에 대한 connection을 새로 생성, false일 경우 하나의 connection을 공유하고,
        // 이때 요청을 처리하는 상태일 경우, 다른 process의 request는 block되므로.
        serverCF.setSingleUse(true);

        // server와 client의 session 요청을 유지
        serverCF.setSoKeepAlive(true);
        return serverCF;
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverConnectionFactory());
        inGate.setRequestChannel(inboundChannel());
        inGate.setReplyChannel(outboundChannel());
        inGate.setErrorChannel(errorChannel());
        return inGate;
    }

    @Router(inputChannel = "inboundChannel")
    @Bean
    public PayloadTypeRouter router() {
        PayloadTypeRouter router = new PayloadTypeRouter();
        // 각 전문 channel 로 전송
        router.setChannelMapping(CommonFieldMessage.class.getName(), "CommonMessageChannel");
        router.setChannelMapping(NotificationIndividualWithdrawalMessage.class.getName(),
            "NotificationIndividualWithdrawalMessageChannel");
        return router;
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

}

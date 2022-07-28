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
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
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
    private int PORT;
    @Value("${tcp.server.host}")
    private String HOST;

    @Value("${tcp.vpn.server.host}")
    private String VPNHOST;
    @Value("${tcp.vpn.server.port}")
    private Integer VPNPORT;

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        // reply message time out error 가 Nio(Non-blocking I/O)의 내부 경쟁조건에 의해 무시됨.
        TcpNioServerConnectionFactory serverCF = new TcpNioServerConnectionFactory(this.PORT);
        serverCF.setDeserializer(
            new OnlineMessageDeserializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage));
        // Socket 을 수신할 때, 대기시킬 수 있는 Socket 의 개수
        serverCF.setBacklog(10);

        // 해당 옵션을 사용하여 여러 Socket 을 한 번에 수신 가능
//        serverCF.setSingleUse(true);

        return serverCF;
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverConnectionFactory());
        inGate.setRequestChannel(inboundChannel());
        inGate.setErrorChannel(errorChannel());

        // 단방향 gateway 구현을 위해 reply message를 기다리지 않음
        inGate.setReplyTimeout(0);

        inGate.setLoggingEnabled(true);
        return inGate;
    }

    @Bean
    public AbstractClientConnectionFactory clientConnectionFactory() {
        TcpNioClientConnectionFactory clientCF = new TcpNioClientConnectionFactory(VPNHOST,
            VPNPORT);
        clientCF.setSerializer(
            new OnlineMessageSerializer(maxMessageSize, lengthHeaderSize, new BytesConverter(),
                treatTimeoutAsEndOfMessage)
        );
        clientCF.setSingleUse(true);
        return clientCF;
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public TcpOutboundGateway tcpOutboundGateway() {
        TcpOutboundGateway outGate = new TcpOutboundGateway();
        outGate.setConnectionFactory(clientConnectionFactory());
        outGate.setOutputChannel(outboundChannel());

        // Ignore that reply message is timeout
        outGate.setRemoteTimeout(0);
        // 비동기 처리를 통해 reply message 를 기다리지 않고 outputChannel 로 message 전송
        outGate.setAsync(true);

        return outGate;
    }

    @Router(inputChannel = "inboundChannel")
    @Bean
    public PayloadTypeRouter router() {
        PayloadTypeRouter router = new PayloadTypeRouter();
        // 전문 type에 따라 각 전문에 해당하는 channel 로 전송
        router.setChannelMapping(CommonFieldMessage.class.getName(), "CommonMessageChannel");
        router.setChannelMapping(NotificationIndividualWithdrawalMessage.class.getName(),
            "NotificationIndividualWithdrawalMessageChannel");
        return router;
    }

    @Bean
    public MessageChannel inboundChannel() {
        DirectChannel channel = new DirectChannel();
        channel.setLoggingEnabled(true);
        return channel;
    }

    @Bean
    public MessageChannel outboundChannel() {
        QueueChannel channel = new QueueChannel();
        channel.setLoggingEnabled(true);
        return channel;
    }

    @Bean
    public MessageChannel errorChannel() {
        DirectChannel channel = new DirectChannel();
        channel.setLoggingEnabled(true);
        return channel;
    }

}

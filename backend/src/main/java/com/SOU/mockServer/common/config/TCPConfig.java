package com.SOU.mockServer.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.ThreadAffinityClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;
import org.springframework.messaging.MessageChannel;

//
@EnableIntegration
// search for @messagingGateway interface
@IntegrationComponentScan
@Configuration
public class TCPConfig {

    @Value("${tcp.server.port}")
    private int port;

    @Value("${tcp.server.host}")
    private String host;


    @Bean
    public AbstractClientConnectionFactory clientConnectionFactory() {
        TcpNetClientConnectionFactory clientCF = new TcpNetClientConnectionFactory(this.host, this.port);
        clientCF.setSerializer(new ByteArrayCrLfSerializer());
        clientCF.setDeserializer(new ByteArrayCrLfSerializer());
        // thread가 message를 보낼 때 마다 동일한 연결을 재사용.
        clientCF.setSingleUse(true);
        return clientCF;
    }

    @Bean
    public ThreadAffinityClientConnectionFactory tacf() {
        return new ThreadAffinityClientConnectionFactory(clientConnectionFactory());
    }

//    @Bean
//    public FailoverClientConnectionFactory failoverClientConnectionFactory() {
//        List<AbstractClientConnectionFactory> listClientConnectionFactory = new LinkedList<>();
//        listClientConnectionFactory.add(clientConnectionFactory());
//        listClientConnectionFactory.add(clientConnectionFactory());
//        FailoverClientConnectionFactory failoverClientConnectionFactory = new FailoverClientConnectionFactory(
//            listClientConnectionFactory);
//        failoverClientConnectionFactory.setCloseOnRefresh(false);
//        return new FailoverClientConnectionFactory(listClientConnectionFactory);
//    }

    // clientConnectionFactory를 사용하여 channel을 통해 message를 전송함,
    // reply message를 수신하면,
    @Bean
    @ServiceActivator(inputChannel = "fromTcp")
    public TcpOutboundGateway tcpOutGate() {
        TcpOutboundGateway outGate = new TcpOutboundGateway();
        outGate.setConnectionFactory(clientConnectionFactory());
        outGate.setOutputChannelName("resultToString");
        return outGate;
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
        inGate.setRequestChannel(fromTcp());
        return inGate;
    }

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(this.port);
        serverCf.setSerializer(new ByteArrayCrLfSerializer());
        serverCf.setDeserializer(new ByteArrayCrLfSerializer());
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        return new TcpNetServerConnectionFactory(this.port);
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

//    @MessagingGateway(defaultRequestChannel = "toTcp")
//    public interface Gateway {
//
//        String viaTcp(String in);
//    }

}

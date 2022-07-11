package com.SOU.mockServer.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;

public class socketServerGateway {

    public IntegrationFlow server() {
        return IntegrationFlows.from(Tcp.inboundGateway(Tcp.netServer(1234)
                    .deserializer(TcpCodecs.lengthHeader1())
                    .serializer(TcpCodecs.lengthHeader1())
                    .backlog(30))
                .errorChannel("tcpIn.errorChannel")
                .id("tcpIn"))
            .transform(Transformers.objectToString())
            .channel("tcpInbound")
            .get();
    }

    public IntegrationFlow client() {
        return f -> f.handle(Tcp.outboundGateway(Tcp.nioClient("localhost", 1234)
            .deserializer(TcpCodecs.lengthHeader1())
            .serializer(TcpCodecs.lengthHeader1())));
    }
}

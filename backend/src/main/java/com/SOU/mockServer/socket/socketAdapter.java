package com.SOU.mockServer.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;

public class socketAdapter {

    public IntegrationFlow server() {
        return IntegrationFlows.from(Tcp.inboundAdapter(Tcp.netServer(1234)
                    .deserializer(TcpCodecs.lengthHeader1())
                    .backlog(30))
                .errorChannel("tcpIn.errorChannel")
                .id("tcpIn"))
            .transform(Transformers.objectToString())
            .channel("tcpInbound")
            .get();
    }

    public IntegrationFlow client() {
        return f -> f.handle(Tcp.outboundAdapter(Tcp.nioClient("localhost", 1234)
            .serializer(TcpCodecs.lengthHeader1())));
    }
}

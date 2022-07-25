package com.SOU.mockServer.external.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SocketService{

    private static Socket clientSocket;
    private static ServerSocket serverSocket;

    @Value("${tcp.server.host}")
    private String HOST;
    @Value("${tcp.server.port}")
    private Integer PORT;

    @Value("${tcp.vpn.server.host}")
    private String VPNHOST;
    @Value("${tcp.vpn.server.port}")
    private Integer VPNPORT;

    public Socket getClientSocket() throws IOException {
        if (this.clientSocket == null){
            this.clientSocket = new Socket(HOST, PORT);
        }
            return this.clientSocket;
    }

    public ServerSocket getServerSocket() throws IOException {
        if (this.serverSocket == null){
            this.serverSocket = new ServerSocket();
            SocketAddress serverSocketAddress = new InetSocketAddress(VPNHOST, VPNPORT);
            serverSocket.bind(serverSocketAddress, 5);
        }
        return this.serverSocket;
    }

}

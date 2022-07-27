package com.SOU.mockServer.external.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SocketService {

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
        if (this.clientSocket == null || this.clientSocket.isClosed()) {
            setClientSocket();
        }
        return this.clientSocket;
    }

    public ServerSocket getServerSocket() throws IOException {
        if (this.serverSocket == null || this.serverSocket.isClosed()) {
            setServerSocket();
        }
        return this.serverSocket;
    }

    public void setServerSocket() throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.setSoTimeout(5000);
        SocketAddress serverSocketAddress = new InetSocketAddress(VPNHOST, VPNPORT);
        serverSocket.bind(serverSocketAddress, 5);
    }

    public void setClientSocket() throws IOException {
        this.clientSocket = new Socket(HOST, PORT);
    }

}

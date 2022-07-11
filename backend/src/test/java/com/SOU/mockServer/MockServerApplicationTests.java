package com.SOU.mockServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.SocketFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MockServerApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServerApplicationTests.class);

    //server host, port
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) throws Exception {

        String DATA = "hello_from_client";
        LOGGER.info("Sending request to {}:{}", HOST, PORT);
        // create client socket && connect server socket
        Socket socket = SocketFactory.getDefault().createSocket(HOST, PORT);

        // ByteArrayCrLfSerializer의 message 구분 방식은 \r\n 으로 구분
        socket.getOutputStream().write((DATA + "\r\n").getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        LOGGER.info("Received response: {}", response);
    }

//	@Test
//	void test_send_sensor_status() throws IOException {
//		MessagingTemplate messagingTemplate = new MessagingTemplate(this.inboundChannel);
//
//		byte[] bytes = {0x02, 0x10, 0x11, 0x01, 0x01, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xCF, (byte) 0xEC, 0x03};
//		Message<byte[]> message = MessageBuilder.withPayload(bytes).build();
//		Message<?> receive = messagingTemplate.sendAndReceive(message);
//
//		byte[] response = new byte[0];
//
//		Assertions.assertTrue(Arrays.equals((byte[]) receive.getPayload(), response));
//		Socket socket = new Socket("localhost", 1234);
//		OutputStream output = socket.getOutputStream();
//
//		byte[] data = {0x02, 0x10, 0x11, 0x01, 0x01, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xCF, (byte) 0xEC, 0x03};
//		output.write(data);
//
//		socket.close();
//	}

//    @Test
//    void contextLoads() {
//
//    }
//
}

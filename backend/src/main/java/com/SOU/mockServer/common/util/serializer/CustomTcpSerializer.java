package com.SOU.mockServer.common.util.serializer;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;

public class CustomTcpSerializer extends ByteArrayCrLfSerializer {

    @Override
    public byte[] doDeserialize(InputStream inputStream, byte[] buffer) throws IOException {
        byte[] Data = new byte[400];
        int n = 0;
        int bite = inputStream.read();
        int available = inputStream.available();
        logger.debug(() -> "Available to read: " + available);
        try{
            while (true){
                bite = inputStream.read();
                if (bite < 0 && n == 0) {
                    throw new SoftEndOfStreamException("Stream closed between payloads");
                }
                checkClosure(bite);
                if (n > 0 && bite == '\n' && buffer[n - 1] == '\r') {
                    break;
                }
                buffer[n++] = (byte) bite;
                if (n >= getMaxMessageSize()) {
                    throw new IOException("CRLF not found before max message length: " + getMaxMessageSize());
                }
            }

        }catch (SoftEndOfStreamException e) { // NOSONAR catch and throw
            throw e; // it's an IO exception and we don't want an event for this
        }
        catch (IOException | RuntimeException e) {
            publishEvent(e, buffer, n);
            throw e;
        }



        return super.doDeserialize(inputStream, buffer);
    }
}

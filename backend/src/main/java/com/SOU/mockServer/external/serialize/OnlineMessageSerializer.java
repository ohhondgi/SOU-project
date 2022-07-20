package com.SOU.mockServer.external.serialize;

import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.Output;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import java.io.IOException;
import java.io.OutputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.Serializer;

@Slf4j
public class OnlineMessageSerializer implements Serializer<Message> {

    private static final int SIZE_LENGTH_HEADER = 4; // size of length header

    private final boolean treatTimeoutAsEndOfMessage;
    private final int maxMessageSize;
    private final int lengthHeaderSize;
    private final BytesConverter bytesConverter;

    public OnlineMessageSerializer(int maxMessageSize) {
        this(maxMessageSize, SIZE_LENGTH_HEADER, new BytesConverter(), false);
    }

    public OnlineMessageSerializer(int maxMessageSize, int lengthHeaderSize) {
        this(maxMessageSize, lengthHeaderSize, new BytesConverter(), false);
    }

    public OnlineMessageSerializer(int maxMessageSize, int lengthHeaderSize,
        @NonNull BytesConverter bytesConverter) {
        this(maxMessageSize, lengthHeaderSize, bytesConverter, false);
    }

    public OnlineMessageSerializer(int maxMessageSize, int lengthHeaderSize,
        @NonNull BytesConverter bytesConverter, boolean treatTimeoutAsEndOfMessage) {
        this.maxMessageSize = maxMessageSize;
        this.lengthHeaderSize = lengthHeaderSize;
        this.treatTimeoutAsEndOfMessage = treatTimeoutAsEndOfMessage;
        this.bytesConverter = bytesConverter;
    }

    /**
     * Serialize Message to outputStream
     *
     * @param message
     * @param outputStream
     * @throws IOException
     */
    @Override
    public void serialize(Message message, OutputStream outputStream) throws IOException {
        // message to byte array
        byte[] data = new byte[message.getTotalLength()];
        Output out = new ByteArrayOutput(data, bytesConverter);
        message.writeTo(out);

        // byte array to outputStream
        doSerialize((byte[]) out.toData(), outputStream);
    }

    public void doSerialize(byte[] bytes, OutputStream outputStream) throws IOException {
//        writeLengthHeader(outputStream, bytes.length); // header inclusive
        log.info("bytes length = " + bytes.length + " bytes write = {}",
            bytesConverter.toString(bytes));
        outputStream.write(bytes); // body part
        outputStream.flush();
    }

}

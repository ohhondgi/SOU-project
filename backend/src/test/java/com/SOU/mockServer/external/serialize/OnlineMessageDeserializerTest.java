package com.SOU.mockServer.external.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.Output;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnlineMessageDeserializerTest {

    private PipedInputStream inputStream;
    private OutputStream outputStream;

    @BeforeEach
    void setup() throws IOException {
        inputStream = new PipedInputStream();
        outputStream = new PipedOutputStream(inputStream);
    }

    @Test
    @DisplayName("Deserialize test")
    void Deserialize() throws IOException {
        // given
        OnlineMessageDeserializer deserializer = new OnlineMessageDeserializer(100);
        CommonFieldMessage originalMessage = new CommonFieldMessage(999, 270, "1234567890", "0800",
            "0010", "000", "test");

        Output output = new ByteArrayOutput(originalMessage.getTotalLength());
        originalMessage.writeTo(output);

        outputStream.write((byte[]) output.toData());

        // when
        // Deserialize byte that converted originalMessage
        Message resultMessage = deserializer.deserialize(inputStream);

        // then
        // compare the structure of originalMessage and resultMessage
        assertEquals(originalMessage.toString(), resultMessage.toString());
    }

}
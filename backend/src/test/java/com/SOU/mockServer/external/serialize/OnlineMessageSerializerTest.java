package com.SOU.mockServer.external.serialize;

import static org.junit.jupiter.api.Assertions.*;

import com.SOU.mockServer.common.util.DateUtil;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnlineMessageSerializerTest {
    private PipedInputStream inputStream;
    private OutputStream outputStream;

    @BeforeEach
    void setup() throws IOException {
        inputStream = new PipedInputStream();
        outputStream = new PipedOutputStream(inputStream);
    }

    @Test
    @DisplayName("Serializer test")
    void Serialize() throws IOException {
        //given
        OnlineMessageSerializer serializer = new OnlineMessageSerializer(100);
        BytesConverter bytesConverter = new BytesConverter();

        String totalLength = "0100";
        String senderCode = "999";
        String receiverCode = "270";
        String bankTranId = "1234567890";
        String tranData = DateUtil.format(DateUtil.nowDate());
        String tranTime = DateUtil.format(DateUtil.nowTime());
        String messageCategoryCode = "0800";
        String tranTypeCode = "0010";
        String responseCode = "000";
        String filter = "test";

        CommonFieldMessage originalMessage = new CommonFieldMessage(Integer.parseInt(senderCode),
            Integer.parseInt(receiverCode),
            bankTranId, messageCategoryCode, tranTypeCode, responseCode, filter);

        String originalData =
            totalLength + senderCode + receiverCode + tranData + tranTime + bankTranId
                + messageCategoryCode + tranTypeCode + responseCode + filter;

        // when
        // Serialize message
        serializer.serialize(originalMessage, outputStream);

        // read inputStream that serialized
        byte[] resultMessageByte = inputStream.readNBytes(originalMessage.getTotalLength());

        // remove blank
        String[] resultStrings = bytesConverter.toString(resultMessageByte).split(" ");
        String resultString = "";

        for (String parse : resultStrings) {
            if (!parse.isEmpty()) {
                resultString += parse;
            }
        }

        // then
        assertEquals(originalData, resultString);

    }

}
package com.SOU.mockServer.common.util.serializer;

import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.sun.jdi.connect.spi.ClosedConnectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * Byte serializer for having fixed length header
 * |  length (n bytes)  |         body (size = length value - n bytes)         |
 * length value = n bytes numeric characters. including length field + body part
 */
@Slf4j
//public class ByteArrayLengthHeaderSerializer
//        implements Serializer<byte[], OutputStream>, Deserializer<byte[], InputStream> {
public class ByteArrayLengthHeaderSerializer
        implements Serializer<byte[], OutputStream>, Deserializer<byte[], InputStream> {

  private static final int SIZE_LENGTH_HEADER = 4; // size of length header

  private final boolean treatTimeoutAsEndOfMessage;
  public final int maxMessageSize;
  public final int lengthHeaderSize;
  public final BytesConverter bytesConverter;

  public ByteArrayLengthHeaderSerializer(int maxMessageSize) {
    this(maxMessageSize, SIZE_LENGTH_HEADER, new BytesConverter(), false);
  }

  public ByteArrayLengthHeaderSerializer(int maxMessageSize,
                                         int sizeOfLengthHeader) {
    this(maxMessageSize, sizeOfLengthHeader, new BytesConverter(), false);
  }

  public ByteArrayLengthHeaderSerializer(int maxMessageSize,
                                         int sizeOfLengthHeader,
                                         BytesConverter bytesConverter) {
    this(maxMessageSize, sizeOfLengthHeader, bytesConverter, false);
  }

  public ByteArrayLengthHeaderSerializer(int maxMessageSize,
                                         int sizeOfLengthHeader,
                                         BytesConverter bytesConverter,
                                         boolean treatTimeoutAsEndOfMessage) {
    this.maxMessageSize = maxMessageSize;
    this.lengthHeaderSize = sizeOfLengthHeader;
    this.treatTimeoutAsEndOfMessage = treatTimeoutAsEndOfMessage;
    this.bytesConverter = bytesConverter;
  }

  public int getLengthHeaderSize() {
    return lengthHeaderSize;
  }

  @Override
  public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
    writeLengthHeader(outputStream, bytes.length + lengthHeaderSize); // header inclusive
    log.info("bytes write = {}", bytesConverter.toString(bytes));
    outputStream.write(bytes); // body part
    outputStream.flush();
  }

  @Override
  public byte[] deserialize(InputStream inputStream) throws IOException {
    // header read
    int totalLength = readLengthHeader(inputStream);
    int messageLength = totalLength - lengthHeaderSize; // header inclusive
    byte[] bodyPart = null;

//    if (totalLength < lengthHeaderSize) {
//      throw new IllegalLengthHeaderException("Illegal Message length = " + totalLength
//              + ". Message length must be greater than lengthHeaderSize = " + lengthHeaderSize);
//    }
//
//    if (messageLength > maxMessageSize) {
//      throw new ExceededMaxMessageLengthException("Message length " + messageLength +
//              " exceeds max message length = " + maxMessageSize);
//    }

    bodyPart = new byte[messageLength];
    read(inputStream, bodyPart);

    log.info("bytes length = {}, bytes read = {}", totalLength, bytesConverter.toString(bodyPart));

    return bodyPart;
  }

  private int read(InputStream inputStream, byte[] bytes)
          throws IOException {
    int lengthRead = 0;
    int needed = bytes.length;
    while (lengthRead < needed) {
      int len;
      len = inputStream.read(bytes, lengthRead, needed - lengthRead);

      if (len < 0) {
        throw new ClosedConnectionException("Stream closed after " + lengthRead + " of " + needed);
      }

      lengthRead += len;
    }
    return 0;
  }

  private void writeLengthHeader(OutputStream outputStream, int length) throws IOException {
    byte[] lengthPart = new byte[lengthHeaderSize];
    bytesConverter.fromInt(length, lengthPart, 0, lengthHeaderSize);
    outputStream.write(lengthPart); // header
  }

  private int readLengthHeader(InputStream inputStream) throws IOException {
    byte[] lengthPart = new byte[lengthHeaderSize];
    read(inputStream, lengthPart);
    int length = -1;

    try {
      length = bytesConverter.toInt(lengthPart);
    } catch (IllegalArgumentException ignored) {
      // ignored. just return -1
    }

    return length;
  }
}

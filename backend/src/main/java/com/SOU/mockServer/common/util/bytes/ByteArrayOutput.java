package com.SOU.mockServer.common.util.bytes;
import com.SOU.mockServer.common.util.Output;
import lombok.NonNull;

public class ByteArrayOutput implements Output<byte[]> {

  private final byte[] output;
  private final int length;
  private final BytesConverter bytesConverter;

  public ByteArrayOutput(int length) {
    this(length, new byte[length], new BytesConverter());
  }

  public ByteArrayOutput(byte[] output,
                         @NonNull BytesConverter bytesConverter) {
    this(output.length, output, bytesConverter);
  }

  public ByteArrayOutput(int length,
                         byte[] output,
                         @NonNull BytesConverter bytesConverter) {
    this.length = length;
    this.output = output;
    this.bytesConverter = bytesConverter;
  }

  @Override
  public void writeString(String value, int offset, int len) {
    try {
      bytesConverter.fromString(value, this.output, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to write String value = " + value
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
  }

  @Override
  public void writeLong(long value, int offset, int len) {
    try {
      bytesConverter.fromLong(value, this.output, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to write Long value = " + value
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
  }

  @Override
  public void writeInt(int value, int offset, int len) {
    try {
      bytesConverter.fromInt(value, this.output, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to write Int value = " + value
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
  }

  @Override
  public byte[] toData() {
    return this.output;
  }
}

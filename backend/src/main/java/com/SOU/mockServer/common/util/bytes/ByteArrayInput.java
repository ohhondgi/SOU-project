package com.SOU.mockServer.common.util.bytes;
import com.SOU.mockServer.common.util.Input;
import lombok.NonNull;

public class ByteArrayInput implements Input {

  private final byte[] data;
  private final BytesConverter bytesConverter;

  public ByteArrayInput(byte[] bytes) {
    this(bytes, new BytesConverter());
  }

  public ByteArrayInput(byte[] bytes,
                        @NonNull BytesConverter bytesConverter) {
    this.data = bytes;
    this.bytesConverter = bytesConverter;
  }

  @Override
  public String readString(int offset, int len) {
    String ret = "";
    try {
      ret = bytesConverter.toString(data, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to read String"
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
    return ret;
  }

  @Override
  public long readLong(int offset, int len) {
    long ret = 0L;
    try {
      ret = bytesConverter.toLong(data, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to read Long"
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
    return ret;
  }

  @Override
  public int readInt(int offset, int len) {
    int ret = 0;
    try {
      ret = bytesConverter.toInt(data, offset, len);
    } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Failed to read Int"
              + ", offset = " + offset + ", len = " + len + ", reason : " + e.getMessage());
    }
    return ret;
  }
}

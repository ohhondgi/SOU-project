package com.SOU.mockServer.common.util;

public interface Output<T> {
  void writeString(String value, int offset, int len);
  void writeLong(long value, int offset, int len);
  void writeInt(int value, int offset, int len);
  T toData();
}

package com.SOU.mockServer.common.util;

public interface Input {
  String readString(int offset, int len);
  long readLong(int offset, int len);
  int readInt(int offset, int len);
}

package com.SOU.mockServer.common.util.bytes;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class BytesConverter {

  public static final Charset CHARSET = Charset.forName("euc-kr");
  public static final byte ZERO = CHARSET.encode("0").get();
  public static final byte SPACE = CHARSET.encode(" ").get();

  private final Charset charset;
  private final byte zero;
  private final byte space;

  public BytesConverter() {
    this(CHARSET, ZERO, SPACE);
  }

  public BytesConverter(Charset charset, byte zero, byte space) {
    this.charset = charset;
    this.zero = zero;
    this.space = space;
  }

  /**
   * Convert byte array to integer
   * @param bytes
   * @return
   */
  public int toInt(byte[] bytes) {
    // 검증
    ensureByteArray(bytes);
    return Integer.parseInt(toString(bytes));
  }

  /**
   * Convert byte array to integer
   * from bytes[offset:offset + len - 1]
   * @param bytes
   * @param offset
   * @param len
   * @return
   */
  public int toInt(byte[] bytes, int offset, int len) {
    return toInt(slice(bytes, offset, len));
  }

  /**
   * Convert byte array to long
   * @param bytes
   * @return
   */
  public long toLong(byte[] bytes) {
    ensureByteArray(bytes);
    return Long.parseLong(toString(bytes));
  }

  /**
   * Convert byte array to long
   * from bytes[offset:offset + len - 1]
   * @param bytes
   * @param offset
   * @param len
   * @return
   */
  public long toLong(byte[] bytes, int offset, int len) {
    return toLong(slice(bytes, offset, len));
  }

  /**
   * Convert byte array to String
   * @param bytes
   * @return
   */
  public String toString(byte[] bytes) {
    ensureByteArray(bytes);
    return new String(bytes, charset);
  }

  /**
   * Convert byte array to String
   * from bytes[offset:offset + len - 1]
   * @param bytes
   * @param offset
   * @param len
   * @return
   */
  public String toString(byte[] bytes, int offset, int len) {
    return toString(slice(bytes, offset, len));
  }

  /**
   * Convert integer value to byte array
   * @param value
   * @return
   */
  public byte[] fromInt(int value) {
    ensureZeroOrPositiveInteger(value);
    return Integer.toString(value).getBytes(charset);
  }

  /**
   * Write integer value into bytes[offset:offset + len - 1]
   * with right alignment, ZERO padding
   * @param value - integer value to write
   * @param bytes - destination byte array
   * @param offset - offset of bytes
   * @param len - length to write in bytes
   * @throws IllegalArgumentException
   */
  public void fromInt(int value, byte[] bytes, int offset, int len) {
    byte[] src = fromInt(value);
    copyBytes(src, 0, src.length, // src
            bytes, offset, len,            // dst
            false,                         // right alignment
            ZERO);                         // ZERO padding
  }

  /**
   * Convert long value to byte array
   * @param value - long value to write
   * @return
   */
  public byte[] fromLong(long value) {
    ensureZeroOrPositiveLong(value);
    return Long.toString(value).getBytes(charset);
  }

  /**
   * Write long value into bytes[offset:offset + len - 1]
   * with right alignment, ZERO padding
   * @param value - long value to write
   * @param bytes - destination byte array
   * @param offset - offset of bytes
   * @param len - length to write in bytes
   */
  public void fromLong(long value, byte[] bytes, int offset, int len) {
    byte[] src = fromLong(value);
    copyBytes(src, 0, src.length, // src
            bytes, offset, len,            // dst
            false,                         // right alignment
            ZERO);                         // ZERO padding
  }

  /**
   * Convert string value to byte array
   * @param value - string value to write
   * @return
   */
  public byte[] fromString(String value) {
    return value.getBytes(charset);
  }

  /**
   * Write String value into bytes[offset:offset + len - 1]
   * with left alignment, SPACE padding
   * @param value - string value to write
   * @param bytes - destination byte array
   * @param offset - offset of bytes
   * @param len - length to write in bytes
   */
  public void fromString(String value, byte[] bytes, int offset, int len) {
    byte[] src = fromString(value);
    copyBytes(src, 0, src.length, // src
            bytes, offset, len,            // dst
            true,                          // left alignment
            SPACE);                        // space padding
  }

  private byte[] slice(byte[] bytes, int offset, int len) {
    ensureByteArraySlice(bytes, offset, len);
    return Arrays.copyOfRange(bytes, offset, offset + len);
  }


  /**
   * Copy source bytes src[srcOffset:srcOffset + srcLen - 1] to destination bytes dst[dstOffset:dstOffset + dstLen - 1]
   * if dstLen > srcLen, then add ZERO padding
   *
   * @param src - source byte array
   * @param srcOffset - source's offset
   * @param srcLen - source's length
   * @param dst - destination byte array
   * @param dstOffset - destination's offset
   * @param dstLen - write length
   * @param alignLeft - if false, align right
   * @param paddingByte - if padding needed, use paddingByte to add padding
   * @throws IllegalArgumentException - if srcLen > dstLen
   */
  private void copyBytes(byte[] src, int srcOffset, int srcLen,
                         byte[] dst, int dstOffset, int dstLen,
                         boolean alignLeft, byte paddingByte)
          throws IllegalArgumentException {

    ensureByteArraySlice(src, srcOffset, srcLen);
    ensureByteArraySlice(dst, dstOffset, dstLen);

    int padding = dstLen - srcLen;

    if (padding < 0) {
      throw new IllegalArgumentException("dstLen cannot be smaller than srcLen");
    }

    if (padding > 0) {
      // padding needed
      if (alignLeft) {
        // align left
        // 1. copy value
        System.arraycopy(src, srcOffset, dst, dstOffset, srcLen);

        // 2. padding right
        for (int i = 0; i < padding; i++) {
          int idx = i + dstOffset + srcLen;
          dst[idx] = paddingByte;
        }

      } else {
        // align right
        // 1. padding left
        for (int i = 0; i < padding; i++) {
          int idx = i + dstOffset;
          dst[idx] = paddingByte;
        }

        // 2. copy value
        System.arraycopy(src, srcOffset, dst, dstOffset + padding, srcLen);
      }
    } else {
      // padding no needed
      System.arraycopy(src, srcOffset, dst, dstOffset, srcLen);
    }
  }

  /**
   * Validation: Ensure bytes is available
   * @param bytes
   * @throws NullPointerException - if bytes is null
   * @throws IllegalArgumentException - if bytes.length == 0
   */
  private void ensureByteArray(byte[] bytes)
          throws NullPointerException, IllegalArgumentException {

    if (bytes == null) throw new NullPointerException("bytes array cannot be null");
    if (bytes.length == 0) throw new IllegalArgumentException("bytes array length must be > 0");
  }

  /**
   * Validation: Ensure bytes can be sliced
   * @param bytes
   * @param offset
   * @param len
   * @throws IndexOutOfBoundsException - if offset < 0
   *                                      or offset > bytes.length - 1
   *                                      or offset + len > bytes.length
   */
  private void ensureByteArraySlice(byte[] bytes, int offset, int len)
          throws IndexOutOfBoundsException {

    if (len == 0 && offset == 0) return;

    if (len < 0) {
      throw new IllegalArgumentException("len = " + len + " must be >= 0");
    }

    if (offset < 0 || offset > bytes.length - 1) {
      throw new IndexOutOfBoundsException("offset = " + offset + " out of array bounds");
    }

    if (offset + len > bytes.length) {
      throw new IndexOutOfBoundsException("offset = " + offset
              + " len = " + len + " exceeds arr.length = " + bytes.length);
    }
  }

  /**
   * Validation: Ensure value is positive or zero integer number
   * @param value
   * @throws IllegalArgumentException
   */
  private void ensureZeroOrPositiveInteger(int value)
          throws IllegalArgumentException {
    if (value < 0) throw new IllegalArgumentException("value = " + value + " must be positive number");
  }


  /**
   * Validation: Ensure value is positive or zero long number
   * @param value
   * @throws IllegalArgumentException
   */
  private void ensureZeroOrPositiveLong(long value)
          throws IllegalArgumentException {
    if (value < 0) throw new IllegalArgumentException("value = " + value + " must be positive number");
  }

}
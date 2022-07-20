package com.SOU.mockServer.common.util;
import lombok.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {

  protected static final String PATTERN_DATE = "yyyyMMdd";
  protected static final String PATTERN_TIME = "HHmmss";
  private static final String zoneId = "Asia/Seoul";

  public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE);
  public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME);

  public static String convertTimestampToISODateString(long timestamp) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(new Date(timestamp));
  }

  public static String convertDateObjectToISODateString(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(date);
  }

  public static String convertDateObjectToDateString(@NonNull Date date, @NonNull String pattern) {
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(date);
  }

  public static LocalDate nowDate() {
    return LocalDate.now(ZoneId.of(zoneId));
  }

  public static LocalTime nowTime() {
    return LocalTime.now(ZoneId.of(zoneId));
  }

  public static String formatDate(int year, int month, int day) {
    return String.format("%4d%02d%02d", year, month, day);
  }

  public static String formatYearMonth(int year, int month) {
    return String.format("%4d%02d", year, month);
  }

  public static String formatTime(int hour, int minute, int second) {
    return String.format("%02d%02d%02d", hour, minute, second);
  }

  public static String format(LocalDate date) {
    return format(date, FORMATTER_DATE);
  }

  public static String format(LocalDate date, DateTimeFormatter dateFormatter) {
    return date.format(dateFormatter);
  }

  public static String format(LocalTime time) {
    return format(time, FORMATTER_TIME);
  }

  public static String format(LocalTime time, DateTimeFormatter timeFormatter) {
    return time.format(timeFormatter);
  }

  public static String nowDateFormat() {
    return nowDateFormat(FORMATTER_DATE);
  }

  public static String nowDateFormat(DateTimeFormatter dateFormatter) {
    return format(LocalDate.now(), dateFormatter);
  }

  public static String nowTimeFormat() {
    return nowTimeFormat(FORMATTER_TIME);
  }

  public static String nowTimeFormat(DateTimeFormatter timeFormatter) {
    return format(LocalTime.now(), timeFormatter);
  }

  public static int getYear(String yyyyMMdd) {
    ensureHasYearPattern(yyyyMMdd);
    return Integer.parseInt(yyyyMMdd.substring(0, 4));
  }

  public static int getMonth(String yyyyMMdd) {
    ensureHasMonthPattern(yyyyMMdd);
    return Integer.parseInt(yyyyMMdd.substring(4, 6));
  }

  public static int getDay(String yyyyMMdd) {
    ensureHasDayPattern(yyyyMMdd);
    return Integer.parseInt(yyyyMMdd.substring(6, 8));
  }

  public static int getHourOfDay(String hhmmss) {
    ensureHourMinuteSecond(hhmmss);
    return Integer.parseInt(hhmmss.substring(0, 2));
  }

  public static int getMinute(String hhmmss) {
    ensureHourMinuteSecond(hhmmss);
    return Integer.parseInt(hhmmss.substring(2, 4));
  }

  public static int getSecond(String hhmmss) {
    ensureHourMinuteSecond(hhmmss);
    return Integer.parseInt(hhmmss.substring(4, 6));
  }

  public static void ensureHasYearPattern(String str) {
    if (str == null || str.length() < 4) {
      throw new IllegalArgumentException("Failed to ensure yyyyMMdd pattern. input str = " + str);
    }
  }

  public static void ensureHasMonthPattern(String str) {
    if (str == null || str.length() < 6) {
      throw new IllegalArgumentException("Failed to ensure yyyyMMdd pattern. input str = " + str);
    }
  }

  public static void ensureHasDayPattern(String str) {
    if (str == null || str.length() < 8) {
      throw new IllegalArgumentException("Failed to ensure yyyyMMdd pattern. input str = " + str);
    }
  }

  public static void ensureHourMinuteSecond(String str) {
    if (str == null || str.length() != 6) {
      throw new IllegalArgumentException("Failed to ensure HHmmss pattern. input str = " + str);
    }
  }
}

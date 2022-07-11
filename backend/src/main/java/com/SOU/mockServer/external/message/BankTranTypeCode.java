package com.SOU.mockServer.external.message;

public final class BankTranTypeCode {
  /**
   * connection state
   */
  public static final String CONNECTION_STATE = "0010";
  public static final String CONNECTION_STATE_FROM_BANK = "0020";
  public static final String CONNECTION_OPEN = "1000";
  public static final String CONNECTION_READY_CLOSE = "1100";
  public static final String CONNECTION_CLOSE = "1200";
  public static final String CONNECTION_SYSTEM_FAILURE = "1300";
  public static final String CONNECTION_SYSTEM_FAILURE_RECOVERY = "1400";
  public static final String CONNECTION_TEST_CALL = "1500";

  /**
   * account
   */
  public static final String NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT = "1010";
  public static final String VERIFICATION_IS_CORPORATION_OWNER = "1020";
  public static final String NOTIFICATION_CREATE_CORPORATION_ACCOUNT = "1030";
  public static final String VERIFICATION_IS_AVAILABLE_CANCEL_ACCOUNT = "1040";
  public static final String NOTIFICATION_CANCEL_ACCOUNT = "1050";

  /**
   * banking
   */
  public static final String CORPORATION_DEPOSIT = "2010";
  public static final String INDIVIDUAL_WITHDRAWAL = "2020";
  public static final String RESULT_INDIVIDUAL_WITHDRAWAL = "2030";
  public static final String VERIFICATION_IS_AVAILABLE_CORPORATION_WITHDRAWAL = "2040";
  public static final String CORPORATION_WITHDRAWAL = "2070";
  public static final String CORPORATION_CANCEL_DEPOSIT = "2080";
  public static final String CORPORATION_CANCEL_WITHDRAWAL = "2090";
  public static final String BATCH_TRANSFER = "2060";
  public static final String VERIFICATION_IS_AVAILABLE_CORPORATION_DEPOSIT = "2100";
  public static final String VERIFICATION_CANCELABLE_CORPORATION_DEPOSIT = "2110";
  public static final String VERIFICATION_CANCELABLE_CORPORATION_WITHDRAWAL = "2120";
  public static final String DEPOSIT_FEE = "2050";

  /**
   * product
   */
  public static final String CREATE_STOCK = "3010";
  public static final String NOTIFICATION_ASSIGNMENT = "3020";
  public static final String ASSIGNMENT_ISSUANCE_COMPLETED = "3030";
  public static final String NOTIFICATION_CLOSING_PRICE = "3040";
  public static final String NOTIFICATION_PRODUCT_SCHEDULE = "3080";

  /**
   * settlement
   */
  public static final String DAILY_SETTLEMENT = "5010";
  public static final String BALANCE_COMPARISON = "5030";
  public static final String PRODUCT_UNIT_COMPARISON = "5040";
  public static final String DAILY_FEE_SETTLEMENT = "5020";
  public static final String DAILY_TAX_SETTLEMENT = "5050";
  public static final String TOTAL_OFFERING_UNIT_COMPARISON = "5060";

  /**
   * rights
   */
  public static final String NOTIFICATION_DIVIDEND = "4010";
  public static final String NOTIFICATION_DIVIDEND_RESULT = "4030";
}

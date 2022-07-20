package com.SOU.mockServer.external.message;

public final class MessageCategoryCode {
  /**
   * connection state
   */
  public static final String REQUEST_CONNECTION_STATE = "0800";
  public static final String RESPONSE_CONNECTION_STATE = "0810";

  /**
   * account
   */
  public static final String NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT = "0320";
  public static final String RESPONSE_CREATE_INDIVIDUAL_ACCOUNT = "0330";
  
  public static final String VERIFICATION_IS_CORPORATION_OWNER = "0300";
  public static final String RESPONSE_IS_CORPORATION_OWNER = "0310";
  
  public static final String NOTIFICATION_CREATE_CORPORATION_ACCOUNT = "0320";
  public static final String RESPONSE_CREATE_CORPORATION_ACCOUNT = "0330";

  public static final String VERIFICATION_IS_AVAILABLE_CLOSE_ACCOUNT = "0300";
  public static final String RESPONSE_IS_AVAILABLE_CLOSE_ACCOUNT = "0310";

  public static final String NOTIFICATION_CLOSE_ACCOUNT = "0320";
  public static final String RESPONSE_CLOSE_ACCOUNT = "0330";

  /**
   * banking
   */
  public static final String NOTIFICATION_CORPORATION_DEPOSIT = "0220";
  public static final String RESPONSE_CORPORATION_DEPOSIT = "0230";

  public static final String REQUEST_INDIVIDUAL_WITHDRAWAL = "0200";
  public static final String RESPONSE_INDIVIDUAL_WITHDRAWAL = "0210";
  public static final String NOTIFICATION_INDIVIDUAL_WITHDRAWAL = "0220";

  public static final String VERIFICATION_IS_AVAILABLE_CORPORATION_WITHDRAWAL = "0200";
  public static final String RESPONSE_IS_AVAILABLE_CORPORATION_WITHDRAWAL = "0210";
  public static final String NOTIFICATION_CORPORATION_WITHDRAWAL = "0220";
  public static final String RESPONSE_CORPORATION_WITHDRAWAL = "0210";

  public static final String REQUEST_BATCH_TRANSFER = "0200";
  public static final String RESPONSE_BATCH_TRANSFER = "0210";

  public static final String VERIFICATION_IS_AVAILABLE_CORPORATION_DEPOSIT = "0200";
  public static final String RESPONSE_IS_AVAILABLE_CORPORATION_DEPOSIT = "0210";

  public static final String VERIFICATION_CANCELABLE_CORPORATION_DEPOSIT = "0200";
  public static final String RESPONSE_CANCELABLE_CORPORATION_DEPOSIT = "0210";

  public static final String VERIFICATION_CANCELABLE_CORPORATION_WITHDRAWAL = "0200";
  public static final String RESPONSE_CANCELABLE_CORPORATION_WITHDRAWAL = "0210";

  public static final String NOTIFICATION_DEPOSIT_FEE = "0200";
  public static final String RESPONSE_DEPOSIT_FEE = "0210";

  /**
   * product
   */
  public static final String REQUEST_CREATE_STOCK = "0500";
  public static final String RESPONSE_CREATE_STOCK = "0510";

  public static final String NOTIFICATION_ASSIGNMENT = "0500";
  public static final String RESPONSE_NOTIFICATION_ASSIGNMENT = "0510";
  public static final String NOTIFICATION_ASSIGNMENT_COMPLETED = "0520";
  public static final String RESPONSE_NOTIFICATION_ASSIGNMENT_ISSUANCE_COMPLETED = "0530";

  public static final String NOTIFICATION_CLOSING_PRICE = "0500";
  public static final String RESPONSE_CLOSING_PRICE = "0510";

  public static final String NOTIFICATION_PRODUCT_SCHEDULE = "0520";
  public static final String RESPONSE_PRODUCT_SCHEDULE = "0530";

  /**
   * settlement
   */
  public static final String REQUEST_DAILY_SETTLEMENT = "0700";
  public static final String RESPONSE_DAILY_SETTLEMENT = "0710";

  public static final String REQUEST_BALANCE_COMPARISON = "0700";
  public static final String RESPONSE_BALANCE_COMPARISON = "0710";

  public static final String REQUEST_PRODUCT_UNIT_COMPARISON = "0700";
  public static final String RESPONSE_PRODUCT_UNIT_COMPARISON = "0710";

  public static final String VERIFICATION_DAILY_TAX_SETTLEMENT = "0700";
  public static final String RESPONSE_DAILY_TAX_SETTLEMENT = "0710";

  public static final String VERIFICATION_DAILY_FEE_SETTLEMENT = "0700";
  public static final String RESPONSE_DAILY_FEE_SETTLEMENT = "0710";

  public static final String REQUEST_TOTAL_OFFERING_UNIT_COMPARISON = "0700";
  public static final String RESPONSE_TOTAL_OFFERING_UNIT_COMPARISON = "0710";

  /**
   * rights
   */
  public static final String NOTIFICATION_DIVIDEND = "0600";
  public static final String RESPONSE_NOTIFICATION_DIVIDEND = "0610";
  public static final String NOTIFICATION_DIVIDEND_RESULT = "0640";
  public static final String RESPONSE_NOTIFICATION_DIVIDEND_RESULT = "0650";

}

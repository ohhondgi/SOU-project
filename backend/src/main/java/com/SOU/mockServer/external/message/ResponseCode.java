package com.SOU.mockServer.external.message;

public final class ResponseCode {

  public static final String SUCCESS = "000";
  public static final String FAILURE_CREATE_INDIVIDUAL_ACCOUNT = "111";
  public static final String NOT_REGISTERED_CORPORATION = "121";
  public static final String FAILURE_CREATE_CORPORATION_ACCOUNT = "131";
  public static final String FAILURE_CLOSE_ACCOUNT_OWNER_NOT_DELETED = "141";
  public static final String FAILURE_CLOSE_ACCOUNT_OWNER_NOT_FOUND = "142";
  public static final String FAILURE_CLOSE_ACCOUNT_RENTAL_INCOME_EXIST = "143";
  public static final String FAILURE_DEPOSIT_CORPORATION = "211";
  public static final String FAILURE_REGISTER_WITHDRAWAL_INDIVIDUAL = "221";
  public static final String FAILURE_WITHDRAWAL_INDIVIDUAL = "231";
  public static final String FAILURE_WITHDRAWAL_REQUESTED_AMOUNT_NOT_AVAILABLE = "241";
  public static final String FAILURE_PAYMENT_CHARGE = "251";
  public static final String FAILURE_TRANSFER = "261";
  public static final String FAILURE_CREATE_STOCK = "311";
  public static final String FAILURE_REGISTER_ASSIGN = "321";
  public static final String FAILURE_ASSIGN = "331";
  public static final String FAILURE_INCOMING_STOCK = "341";
  public static final String FAILURE_END = "351";
  public static final String FAILURE_REGISTER_PROFIT_SHARING = "411";
  public static final String FAILURE_PROFIT_SHARING = "421";
  public static final String FAILURE_SETTLEMENT = "511";
  public static final String FAILURE_SETTLEMENT_MONTHLY_FEE = "521";
  public static final String NOT_MATCHED_BALANCE = "531";
  public static final String NOT_MATCHED_STOCK = "541";

}

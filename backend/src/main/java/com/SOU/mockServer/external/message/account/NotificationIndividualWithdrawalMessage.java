package com.SOU.mockServer.external.message.account;

import com.SOU.mockServer.common.message.Field;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.Output;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import java.util.Map;
import lombok.Getter;

public class NotificationIndividualWithdrawalMessage extends Message {

  @Getter
  private final CommonFieldMessage commonFieldMessage;

  @Getter
  private final Field<String> stockAccountNumber;
  @Getter
  private final Field<Long> availableAmount;
  @Getter
  private final Field<Long> requestedAmount;
  @Getter
  private final Field<Integer> transferFee;
  @Getter
  private final Field<Integer> receiverBankCode;
  @Getter
  private final Field<String> receiverBankAccountNumber;
  @Getter
  private final Field<String> depositText;
  @Getter
  private final Field<String> withdrawalText;
  @Getter
  private final Field<Integer> memberCompanyTranDate;
  @Getter
  private final Field<String> memberCompanyAccountNumber;
  @Getter
  private final Field<Integer> memberCompanyTranId;
  @Getter
  private final Field<Integer> memberCompanyCancelTranId;
  @Getter
  private final Field<Integer> handleType;

  private final Field<String> filler;

  public NotificationIndividualWithdrawalMessage() {
    this.commonFieldMessage = new CommonFieldMessage();
    this.commonFieldMessage.getTotalMessageLength().set(getTotalLength());

    this.stockAccountNumber = new Field<>(LENGTH_STOCK_ACCOUNT_NUMBER, "");
    this.availableAmount = new Field<>(LENGTH_AVAILABLE_AMOUNT, 0L);
    this.requestedAmount = new Field<>(LENGTH_REQUESTED_AMOUNT, 0L);
    this.transferFee = new Field<>(LENGTH_TRANSFER_FEE, 0);
    this.receiverBankCode = new Field<>(LENGTH_RECEIVER_CODE, 0);
    this.receiverBankAccountNumber = new Field<>(LENGTH_RECEIVER_ACCOUNT_NUMBER, "");
    this.depositText = new Field<>(LENGTH_DEPOSIT_TEXT, "");
    this.withdrawalText = new Field<>(LENGTH_WITHDRAWAL_TEXT, "");
    this.memberCompanyTranDate = new Field<>(LENGTH_MEMBER_COMPANY_TRAN_DATE, 0);
    this.memberCompanyAccountNumber = new Field<>(LENGTH_MEMBER_COMPANY_ACCOUNT_NUMBER, "");
    this.memberCompanyTranId = new Field<>(LENGTH_MEMBER_COMPANY_TRAN_ID, 0);
    this.memberCompanyCancelTranId = new Field<>(LENGTH_MEMBER_COMPANY_CANCEL_TRAN_ID, 0);
    this.handleType = new Field<>(LENGTH_HANDLE_TYPE, 0);
    this.filler = new Field<>(LENGTH_FILLER, "");

    addField(this.stockAccountNumber);
    addField(this.availableAmount);
    addField(this.requestedAmount);
    addField(this.transferFee);
    addField(this.receiverBankCode);
    addField(this.receiverBankAccountNumber);
    addField(this.depositText);
    addField(this.withdrawalText);
    addField(this.memberCompanyTranDate);
    addField(this.memberCompanyAccountNumber);
    addField(this.memberCompanyTranId);
    addField(this.memberCompanyCancelTranId);
    addField(this.handleType);
    addField(this.filler);
  }
  public NotificationIndividualWithdrawalMessage(int senderCode, int reveiverCode, String bankTranId, String messageCategoryCode,
      String tranTypeCode, String responseCode, String filter) {
    this.commonFieldMessage = new CommonFieldMessage(senderCode, reveiverCode, bankTranId, messageCategoryCode,
        tranTypeCode, responseCode, filter);
    this.commonFieldMessage.getTotalMessageLength().set(getTotalLength());

    this.stockAccountNumber = new Field<>(LENGTH_STOCK_ACCOUNT_NUMBER, "");
    this.availableAmount = new Field<>(LENGTH_AVAILABLE_AMOUNT, 0L);
    this.requestedAmount = new Field<>(LENGTH_REQUESTED_AMOUNT, 0L);
    this.transferFee = new Field<>(LENGTH_TRANSFER_FEE, 0);
    this.receiverBankCode = new Field<>(LENGTH_RECEIVER_CODE, 0);
    this.receiverBankAccountNumber = new Field<>(LENGTH_RECEIVER_ACCOUNT_NUMBER, "");
    this.depositText = new Field<>(LENGTH_DEPOSIT_TEXT, "");
    this.withdrawalText = new Field<>(LENGTH_WITHDRAWAL_TEXT, "");
    this.memberCompanyTranDate = new Field<>(LENGTH_MEMBER_COMPANY_TRAN_DATE, 0);
    this.memberCompanyAccountNumber = new Field<>(LENGTH_MEMBER_COMPANY_ACCOUNT_NUMBER, "");
    this.memberCompanyTranId = new Field<>(LENGTH_MEMBER_COMPANY_TRAN_ID, 0);
    this.memberCompanyCancelTranId = new Field<>(LENGTH_MEMBER_COMPANY_CANCEL_TRAN_ID, 0);
    this.handleType = new Field<>(LENGTH_HANDLE_TYPE, 0);
    this.filler = new Field<>(LENGTH_FILLER, "");

    addField(this.stockAccountNumber);
    addField(this.availableAmount);
    addField(this.requestedAmount);
    addField(this.transferFee);
    addField(this.receiverBankCode);
    addField(this.receiverBankAccountNumber);
    addField(this.depositText);
    addField(this.withdrawalText);
    addField(this.memberCompanyTranDate);
    addField(this.memberCompanyAccountNumber);
    addField(this.memberCompanyTranId);
    addField(this.memberCompanyCancelTranId);
    addField(this.handleType);
    addField(this.filler);
  }

  @Override
  public void writeTo(Output out) {
    commonFieldMessage.writeTo(out);
    writeTo(out, commonFieldMessage.getTotalLength());
  }

  @Override
  public void readFrom(Input input) {
    commonFieldMessage.readFrom(input);
    readFrom(input, commonFieldMessage.getTotalLength());
  }

  @Override
  public String toString() {
    return "NotificationIndividualWithdrawalMessage{" +
            "commonFieldMessage=" + commonFieldMessage +
            ", stockAccountNumber=" + stockAccountNumber.get() +
            ", availableAmount=" + availableAmount.get() +
            ", requestedAmount=" + requestedAmount.get() +
            ", transferFee=" + transferFee.get() +
            ", receiverCode=" + receiverBankCode.get() +
            ", receiverAccountNumber=" + receiverBankAccountNumber.get() +
            ", depositText=" + depositText.get() +
            ", withdrawalText=" + withdrawalText.get() +
            ", memberCompanyTranDate=" + memberCompanyTranDate.get() +
            ", memberCompanyAccountNumber=" + memberCompanyAccountNumber.get() +
            ", memberCompanyTranId=" + memberCompanyTranId.get() +
            ", memberCompanyCancelTranId=" + memberCompanyCancelTranId.get() +
            ", handleType=" + handleType.get() +
            '}';
  }

  @Override
  public int getTotalLength() {
    return commonFieldMessage.getTotalLength() + LENGTH_TOTAL;
  }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    private static final int LENGTH_STOCK_ACCOUNT_NUMBER = 16;
  private static final int LENGTH_AVAILABLE_AMOUNT = 15;
  private static final int LENGTH_REQUESTED_AMOUNT = 15;
  private static final int LENGTH_TRANSFER_FEE = 4;
  private static final int LENGTH_RECEIVER_CODE = 3;
  private static final int LENGTH_RECEIVER_ACCOUNT_NUMBER = 16;
  private static final int LENGTH_DEPOSIT_TEXT = 50;
  private static final int LENGTH_WITHDRAWAL_TEXT = 50;
  private static final int LENGTH_MEMBER_COMPANY_TRAN_DATE = 8;
  private static final int LENGTH_MEMBER_COMPANY_ACCOUNT_NUMBER = 11;
  private static final int LENGTH_MEMBER_COMPANY_TRAN_ID = 6;
  private static final int LENGTH_MEMBER_COMPANY_CANCEL_TRAN_ID = 6;
  private static final int LENGTH_HANDLE_TYPE = 1;
  private static final int LENGTH_FILLER = 99;
  private static final int LENGTH_TOTAL = 300;
}

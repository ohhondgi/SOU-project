package com.SOU.mockServer.external.message.common;

import com.SOU.mockServer.common.message.Field;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.DateUtil;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.Output;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;

public class CommonFieldMessage extends Message {

    public static final int LENGTH_TOTAL = 100;
    private static final int LENGTH_TOTAL_FIELD = 4;
    private static final int LENGTH_SENDER_CODE = 3;
    private static final int LENGTH_RECEIVER_CODE = 3;
    private static final int LENGTH_TRAN_DATE = 8;
    private static final int LENGTH_TRAN_TIME = 6;
    private static final int LENGTH_BANK_TRAN_ID = 10;
    private static final int LENGTH_MESSAGE_CATEGORY_CODE = 4;
    private static final int LENGTH_TRAN_TYPE_CODE = 4;
    private static final int LENGTH_RESPONSE_CODE = 3;
    private static final int LENGTH_FILLER = 55;

    @Getter
    private final Field<Integer> totalMessageLength;
    @Getter
    private final Field<Integer> senderCode;
    @Getter
    private final Field<Integer> receiverCode;
    @Getter
    private final Field<String> tranDate;
    @Getter
    private final Field<String> tranTime;
    @Getter
    private final Field<String> bankTranId;
    @Getter
    private final Field<String> messageCategoryCode;
    @Getter
    private final Field<String> tranTypeCode;
    @Getter
    private final Field<String> responseCode;

    @Getter
    private final Field<String> filler;

    public CommonFieldMessage() {
        this.totalMessageLength = new Field<>(LENGTH_TOTAL_FIELD, LENGTH_TOTAL);
        this.senderCode = new Field<>(LENGTH_SENDER_CODE, 0);
        this.receiverCode = new Field<>(LENGTH_RECEIVER_CODE, 0);
        this.tranDate = new Field<>(LENGTH_TRAN_DATE, DateUtil.format(DateUtil.nowDate()));
        this.tranTime = new Field<>(LENGTH_TRAN_TIME, DateUtil.format(DateUtil.nowTime()));
        this.bankTranId = new Field<>(LENGTH_BANK_TRAN_ID, "");
        this.messageCategoryCode = new Field<>(LENGTH_MESSAGE_CATEGORY_CODE, "");
        this.tranTypeCode = new Field<>(LENGTH_TRAN_TYPE_CODE, "");
        this.responseCode = new Field<>(LENGTH_RESPONSE_CODE, "");
        this.filler = new Field<>(LENGTH_FILLER, "");

        addField(this.totalMessageLength);
        addField(this.senderCode);
        addField(this.receiverCode);
        addField(this.tranDate);
        addField(this.tranTime);
        addField(this.bankTranId);
        addField(this.messageCategoryCode);
        addField(this.tranTypeCode);
        addField(this.responseCode);
        addField(this.filler);
    }

    public CommonFieldMessage(int senderCode, int reveiverCode, String bankTranId,
        String messageCategoryCode,
        String tranTypeCode, String responseCode, String filter) {
        this.totalMessageLength = new Field<>(LENGTH_TOTAL_FIELD, getTotalLength());
        this.senderCode = new Field<>(LENGTH_SENDER_CODE, senderCode);
        this.receiverCode = new Field<>(LENGTH_RECEIVER_CODE, reveiverCode);
        this.tranDate = new Field<>(LENGTH_TRAN_DATE, DateUtil.format(DateUtil.nowDate()));
        this.tranTime = new Field<>(LENGTH_TRAN_TIME, DateUtil.format(DateUtil.nowTime()));
        this.bankTranId = new Field<>(LENGTH_BANK_TRAN_ID, bankTranId);
        this.messageCategoryCode = new Field<>(LENGTH_MESSAGE_CATEGORY_CODE, messageCategoryCode);
        this.tranTypeCode = new Field<>(LENGTH_TRAN_TYPE_CODE, tranTypeCode);
        this.responseCode = new Field<>(LENGTH_RESPONSE_CODE, responseCode);
        this.filler = new Field<>(LENGTH_FILLER, filter);

        addField(this.totalMessageLength);
        addField(this.senderCode);
        addField(this.receiverCode);
        addField(this.tranDate);
        addField(this.tranTime);
        addField(this.bankTranId);
        addField(this.messageCategoryCode);
        addField(this.tranTypeCode);
        addField(this.responseCode);
        addField(this.filler);
    }


    @Override
    public Object clone() {
        CommonFieldMessage message = new CommonFieldMessage();

        message.totalMessageLength.set(totalMessageLength.get());
        message.senderCode.set(senderCode.get());
        message.receiverCode.set(receiverCode.get());
        message.tranDate.set(tranDate.get());
        message.tranTime.set(tranTime.get());
        message.bankTranId.set(bankTranId.get());
        message.messageCategoryCode.set(messageCategoryCode.get());
        message.tranTypeCode.set(tranTypeCode.get());
        message.responseCode.set(responseCode.get());

        return message;
    }

    @Override
    public int getTotalLength() {
        return LENGTH_TOTAL;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalMessageLength", totalMessageLength.get());
        map.put("senderCode", senderCode.get());
        map.put("receiverCode", receiverCode.get());
        map.put("tranDate", tranDate.get());
        map.put("tranTime", tranTime.get());
        map.put("bankTranId", bankTranId.get());
        map.put("messageCategoryCode", messageCategoryCode.get());
        map.put("tranTypeCode", tranTypeCode.get());
        map.put("responseCode", responseCode.get());

        return map;
    }

    @Override
    public void writeTo(Output out) {
        super.writeTo(out, 0);
    }

    @Override
    public void readFrom(Input input) {
        super.readFrom(input, 0);
    }

    @Override
    public String toString() {
        return "CommonFieldMessage{" +
            "totalLength=" + totalMessageLength.get() +
            "senderCode=" + senderCode.get() +
            ", receiverCode=" + receiverCode.get() +
            ", tranDate=" + tranDate.get() +
            ", tranTime=" + tranTime.get() +
            ", bankTranId=" + bankTranId.get() +
            ", messageCategoryCode=" + messageCategoryCode.get() +
            ", tranTypeCode=" + tranTypeCode.get() +
            ", responseCode=" + responseCode.get() +
            '}';
    }
}

package com.SOU.mockServer.external.serialize;

import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.Output;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.ByteArrayOutput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.common.util.serializer.Deserializer;
import com.SOU.mockServer.common.util.serializer.Serializer;
import com.SOU.mockServer.external.message.BankTranTypeCode;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnlineMessageSerializer implements Serializer<Message, OutputStream>,
    Deserializer<Message, InputStream> {

    private static final int OFFSET_BANK_TRAN_TYPE_CODE = 34;
    private static final int LENGTH_BANK_TRAN_TYPE_CODE = 4;

    private final BytesConverter bytesConverter;
    private final Serializer<byte[], OutputStream> byteArraySerializer;
    private final Deserializer<byte[], InputStream> byteArrayDeserializer;

    public OnlineMessageSerializer(@NonNull BytesConverter bytesConverter,
        @NonNull Serializer<byte[], OutputStream> byteArraySerializer,
        @NonNull Deserializer<byte[], InputStream> byteArrayDeserializer) {
        this.bytesConverter = bytesConverter;
        this.byteArraySerializer = byteArraySerializer;
        this.byteArrayDeserializer = byteArrayDeserializer;
    }

    /**
     * Deserialize inputStream to Message
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    @Override
    public Message deserialize(InputStream inputStream) throws IOException {

        // inputStream to byte array
        byte[] input = byteArrayDeserializer.deserialize(inputStream);

        log.debug(bytesConverter.toString(input));

        String bankTranTypeCode = getBankTranTypeCode(input);
        Input in = new ByteArrayInput(input, bytesConverter);
        Message message = null;
        String type = null;

        // byte array to message
        // TODO: add autowired feature
        switch (bankTranTypeCode) {
            case BankTranTypeCode.CONNECTION_STATE:
            case BankTranTypeCode.CONNECTION_STATE_FROM_BANK:
            case BankTranTypeCode.CONNECTION_OPEN:
            case BankTranTypeCode.CONNECTION_CLOSE:
            case BankTranTypeCode.CONNECTION_READY_CLOSE:
            case BankTranTypeCode.CONNECTION_SYSTEM_FAILURE:
            case BankTranTypeCode.CONNECTION_TEST_CALL:
            case BankTranTypeCode.INDIVIDUAL_WITHDRAWAL:
                message = new NotificationIndividualWithdrawalMessage();
                message.readFrom(in);
                break;
//            case BankTranTypeCode.CONNECTION_SYSTEM_FAILURE_RECOVERY:
//                message = new ConnectionStateMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.NOTIFICATION_CREATE_INDIVIDUAL_ACCOUNT:
//                message = new NotificationCreateIndividualAccountMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_IS_CORPORATION_OWNER:
//                message = new VerificationIsCorporationOwnerMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.NOTIFICATION_CREATE_CORPORATION_ACCOUNT:
//                message = new NotificationCreateCorporationAccountMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_IS_AVAILABLE_CANCEL_ACCOUNT:
//                message = new VerificationIsAvailableCloseAccountMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.NOTIFICATION_CANCEL_ACCOUNT:
//                message = new NotificationCloseAccountMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.CORPORATION_DEPOSIT:
//                message = new NotificationCorporationDepositMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.CORPORATION_CANCEL_DEPOSIT:
//                message = new NotificationCorporationCancelDepositMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.INDIVIDUAL_WITHDRAWAL:
//                message = new IndividualWithdrawalMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_IS_AVAILABLE_CORPORATION_WITHDRAWAL:
//                message = new VerificationIsAvailableCorporationWithdrawalMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.CORPORATION_WITHDRAWAL:
//                message = new NotificationCorporationWithdrawalMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.CORPORATION_CANCEL_WITHDRAWAL:
//                message = new NotificationCorporationCancelWithdrawalMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.CREATE_STOCK:
//                message = new CreateStockMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_IS_AVAILABLE_CORPORATION_DEPOSIT:
//                message = new VerificationIsAvailableCorporationDepositMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_CANCELABLE_CORPORATION_DEPOSIT:
//                message = new VerificationCancelableCorporationDepositMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.VERIFICATION_CANCELABLE_CORPORATION_WITHDRAWAL:
//                message = new VerificationCancelableCorporationWithdrawalMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.DAILY_FEE_SETTLEMENT:
//                message = new DailyFeeSettlementMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.DAILY_TAX_SETTLEMENT:
//                message = new DailyTaxSettlementMessage();
//                message.readFrom(in);
//                break;
//            case BankTranTypeCode.NOTIFICATION_PRODUCT_SCHEDULE:
//                message = new ProductScheduleMessage();
//                message.readFrom(in);
//                break;
            default:
                // cannot find matched BankTranTypeCode
                log.error("Failed to deserialize inputStream to Message: " +
                    "Cannot find matched BankTranTypeCode = " + bankTranTypeCode);
        }

        return message;
    }

    /**
     * Serialize Message to outputStream
     *
     * @param message
     * @param outputStream
     * @throws IOException
     */
    @Override
    public void serialize(Message message, OutputStream outputStream) throws IOException {
        // message to byte array
        byte[] data = new byte[message.getTotalLength()];
        Output out = new ByteArrayOutput(data, bytesConverter);
        message.writeTo(out);

        // byte array to outputStream
        byteArraySerializer.serialize((byte[]) out.toData(), outputStream);
    }

    /**
     * get bank transaction type code
     *
     * @param input
     * @return
     */
    private String getBankTranTypeCode(byte[] input) {
        try {
            return bytesConverter.toString(input,
                OFFSET_BANK_TRAN_TYPE_CODE, LENGTH_BANK_TRAN_TYPE_CODE);
        } catch (Exception e) {
            return "";
        }
    }
}

package com.SOU.mockServer.external.serialize;

import com.SOU.mockServer.common.exception.ExceededMaxMessageLengthException;
import com.SOU.mockServer.common.exception.IllegalLengthHeaderException;
import com.SOU.mockServer.common.message.Message;
import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.bytes.ByteArrayInput;
import com.SOU.mockServer.common.util.bytes.BytesConverter;
import com.SOU.mockServer.external.message.BankTranTypeCode;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.sun.jdi.connect.spi.ClosedConnectionException;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.Deserializer;

/**
 * Byte serializer for having fixed length header |  length (n bytes)  |         body (size = length
 * value - n bytes)         | length value = n bytes numeric characters. including length field +
 * body part
 */
@Slf4j
public class OnlineMessageDeserializer implements Deserializer<Message> {

    private static final int SIZE_LENGTH_HEADER = 4; // size of length header
    private static final int OFFSET_BANK_TRAN_TYPE_CODE = 38;
    private static final int LENGTH_BANK_TRAN_TYPE_CODE = 4;
    private final boolean treatTimeoutAsEndOfMessage;
    private final int maxMessageSize;
    private final int lengthHeaderSize;
    private final BytesConverter bytesConverter;

    public OnlineMessageDeserializer(int maxMessageSize) {
        this(maxMessageSize, SIZE_LENGTH_HEADER, new BytesConverter(), false);
    }

    public OnlineMessageDeserializer(int maxMessageSize, int sizeOfLengthHeader) {
        this(maxMessageSize, sizeOfLengthHeader, new BytesConverter(), false);
    }

    public OnlineMessageDeserializer(int maxMessageSize, int sizeOfLengthHeader,
        BytesConverter bytesConverter) {
        this(maxMessageSize, sizeOfLengthHeader, bytesConverter, false);
    }

    public OnlineMessageDeserializer(int maxMessageSize, int sizeOfLengthHeader,
        BytesConverter bytesConverter, boolean treatTimeoutAsEndOfMessage) {
        this.maxMessageSize = maxMessageSize;
        this.lengthHeaderSize = sizeOfLengthHeader;
        this.bytesConverter = bytesConverter;
        this.treatTimeoutAsEndOfMessage = treatTimeoutAsEndOfMessage;
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
        byte[] inputs = doDeserialize(inputStream);

        log.debug(bytesConverter.toString(inputs));

        String bankTranTypeCode = getBankTranTypeCode(inputs);
        Input in = new ByteArrayInput(inputs, bytesConverter);
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
                // replacement ConnectionStateMessage
                message = new CommonFieldMessage();
                message.readFrom(in);
                break;
            case BankTranTypeCode.INDIVIDUAL_WITHDRAWAL:
                message = new NotificationIndividualWithdrawalMessage();
                message.readFrom(in);
                break;
            default:
                // cannot find matched BankTranTypeCode
                log.error("Failed to deserialize inputStream to Message: " +
                    "Cannot find matched BankTranTypeCode = " + bankTranTypeCode);
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
        }
        return message;
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

    public byte[] doDeserialize(InputStream inputStream) throws IOException {
        byte[] message = read(inputStream);

        log.info("bytes length = {}, bytes read = {}", message.length,
            bytesConverter.toString(message));
        return message;
    }

    private byte[] read(InputStream inputStream)
        throws IOException {

        int lengthRead = lengthHeaderSize;
        byte[] message = readLengthHeader(inputStream);

        int needed = message.length;
        while (lengthRead < needed) {
            int len;
            len = inputStream.read(message, lengthRead, needed - lengthRead);

            if (len < 0) {
                throw new ClosedConnectionException(
                    "Stream closed after " + lengthRead + " of " + needed);
            }

            lengthRead += len;
        }
        return message;
    }

    private byte[] readLengthHeader(InputStream inputStream) throws IOException {
        byte[] lengthPart = new byte[lengthHeaderSize];
        inputStream.read(lengthPart, 0, lengthPart.length);
        int messageLength = -1;

        try {
            messageLength = bytesConverter.toInt(lengthPart);
        } catch (IllegalArgumentException ignored) {
            // ignored. just return -1
        }

        if (messageLength < lengthHeaderSize) {
            throw new IllegalLengthHeaderException("Illegal Message length = " + messageLength
                + ". Message length must be greater than lengthHeaderSize = " + lengthHeaderSize);
        }

        if (messageLength > maxMessageSize) {
          throw new ExceededMaxMessageLengthException("Message length " + messageLength +
                  " exceeds max message length = " + maxMessageSize);
        }

        byte[] totalMessage = new byte[messageLength];
        System.arraycopy(lengthPart,0,totalMessage,0,lengthPart.length);

        return totalMessage;
    }


}

package com.SOU.mockServer.common.message;

import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class NotificationIndividualWithdrawalMessageDto {

    @Schema(description = "공통정보부", maxLength = 100)
    @JsonProperty(index = 1)
    private CommonMessageRequestDto commonFiledMessage;

    @Schema(description = "증권 계좌 번호", maxLength = 16, defaultValue = "2")
    @JsonProperty(index = 2)
    @NotEmpty
    private String stockAccountNumber;

    @Schema(description = "계좌 비밀 번호", maxLength = 24)
    @JsonProperty(index = 3)
    @NotEmpty
    private Long availableAmount;

    @Schema(description = "출금 요청 금액", maxLength = 15)
    @JsonProperty(index = 4)
    @NotEmpty
    private Long requestedAmount;

    @Schema(description = "타행이체 수수료", maxLength = 4)
    @JsonProperty(index = 5)
    @NotEmpty
    private Integer transferFee;

    @Schema(description = "수취 기관코드", maxLength = 3)
    @JsonProperty(index = 6)
    @NotEmpty
    private Integer receiverBankCode;

    @Schema(description = "수취 계좌번호", maxLength = 16)
    @JsonProperty(index = 7)
    @NotEmpty
    private String receiverBankAccountNumber;

    @Schema(description = "입금계좌 인자내용", maxLength = 50)
    @JsonProperty(index = 8)
    @NotEmpty
    private String depositText;

    @Schema(description = "출금계좌 인자내용", maxLength = 50)
    @JsonProperty(index = 9)
    @NotEmpty
    private String withdrawalText;

    @Schema(description = "회원사 거래 일자", maxLength = 8)
    @JsonProperty(index = 10)
    @NotEmpty
    private Integer memberCompanyTranDate;

    @Schema(description = "회원사 계좌번호", maxLength = 11)
    @JsonProperty(index = 11)
    @NotEmpty
    private String memberCompanyAccountNumber;

    @Schema(description = "회원사원 거래 번호", maxLength = 6)
    @JsonProperty(index = 12)
    @NotEmpty
    private Integer memberCompanyTranId;

    @Schema(description = "회원사 취소 거래번호", maxLength = 8)
    @JsonProperty(index = 13)
    @NotEmpty
    private Integer memberCompanyCancelTranId;

    @JsonProperty(index = 14)
    private Integer handleType;

    @Schema(description = "Filter", maxLength = 91)
    @JsonProperty(index = 15)
    private String filler;

    public NotificationIndividualWithdrawalMessage of() {
        NotificationIndividualWithdrawalMessage message =
            new NotificationIndividualWithdrawalMessage(this.commonFiledMessage.of(), this);
        return message;
    }

}

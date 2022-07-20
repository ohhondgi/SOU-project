package com.SOU.mockServer.common.message;

import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonMessageRequestDto {

    @Schema(description = "송신 기관 코드", maxLength = 3, defaultValue = "999")
    @JsonProperty(index = 1)
    @NotEmpty
    private Integer senderCode;

    @Schema(description = "수신 기관 코드", maxLength = 3, defaultValue = "270")
    @JsonProperty(index = 2)
    @NotEmpty
    private Integer receiverCode;

    @Schema(description = "거래 고유 번호", maxLength = 10)
    @JsonProperty(index = 3)
    @NotEmpty
    private String bankTranId;

    @Schema(description = "전문 종별 코드", maxLength = 4, defaultValue = "0800")
    @JsonProperty(index = 4)
    @NotEmpty
    private String messageCategoryCode;

    @Schema(description = "거래 구분 코드", maxLength = 4, defaultValue = "0010")
    @JsonProperty(index = 5)
    @NotEmpty
    private String tranTypeCode;

    @Schema(description = "응답 코드", maxLength = 3, defaultValue = "000")
    @JsonProperty(index = 6)
    private String responseCode;

    @Schema(description = "Filter", maxLength = 55)
    @JsonProperty(index = 7)
    private String filler;

    public CommonFieldMessage of(){
        return new CommonFieldMessage(
            this.getSenderCode(),
            this.getReceiverCode(),
            this.getBankTranId(),
            this.getMessageCategoryCode(),
            this.getTranTypeCode(),
            this.getResponseCode(),
            this.getFiller()
        );
    }
}

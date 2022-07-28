package com.SOU.mockServer.external.controller;

import com.SOU.mockServer.external.controller.dto.CommonMessageRequestDto;
import com.SOU.mockServer.external.controller.dto.NotificationIndividualWithdrawalMessageDto;
import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.SOU.mockServer.external.service.MessageTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "전문 Test", description = "전문 Test를 위해 Client socket을 활용하여 test하는 API입니다.")
@RestController
@RequestMapping("/test")
public class MessageTestController {

    private final MessageTestService messageTestService;

    public MessageTestController(
        MessageTestService messageTestService) {
        this.messageTestService = messageTestService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공통정보부 테스트 성공", content = @Content(schema = @Schema(implementation = CommonFieldMessage.class))),
        @ApiResponse(responseCode = "400", description = "알맞지 않은 message 구조", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    })
    @Operation(summary = "공통정보부 테스트", description = "공통정보부에 대한 tcp socket 테스트를 진행합니다.")
    @PostMapping("/common-message")
    public ResponseEntity<CommonFieldMessage> commonMessage(
        @Valid @RequestBody CommonMessageRequestDto commonMessageDto) {
        return ResponseEntity.ok(messageTestService.commonFieldMessage(commonMessageDto));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "개인 회원 출금 요청 전문 테스트 성공", content = @Content(schema = @Schema(implementation = NotificationIndividualWithdrawalMessage.class))),
        @ApiResponse(responseCode = "400", description = "알맞지 않은 message 구조", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    })
    @Operation(summary = "개인 회원 출금 요청 전문 테스트", description = "개인 회원 출금 요청 전문에 대한 tcp socket 테스트를 진행합니다.")
    @PostMapping("/notification-individual-withdrawal-message")
    public ResponseEntity<NotificationIndividualWithdrawalMessage> NotificationIndividualWithdrawalMessage(
        @Valid @RequestBody NotificationIndividualWithdrawalMessageDto messageDto) {
        return ResponseEntity.ok(
            messageTestService.notificationIndividualWithdrawalMessage(messageDto));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전문의 공통정보부 테스트 성공", content = @Content(schema = @Schema(implementation = Integer.class))),
        @ApiResponse(responseCode = "400", description = "알맞지 않은 message 구조", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    })
    @Operation(summary = "공통정보부에 대한 대용량 메시지 테스트", description = "전문의 공통정보부에 대한 tcp socket 테스트를 진행합니다. 반환값은 ")
    @PostMapping("/MassiveMessage")
    public ResponseEntity<Object> MassiveMessageTest(
        @Valid @RequestBody CommonMessageRequestDto commonMessageDto,
        @RequestParam(name = "message 개수", defaultValue = "100") int messageNumber) {
        return ResponseEntity.ok(
            messageTestService.MassiveMessageTest(commonMessageDto, messageNumber));
    }

}

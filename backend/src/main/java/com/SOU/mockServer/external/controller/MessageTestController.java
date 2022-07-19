package com.SOU.mockServer.external.controller;

import com.SOU.mockServer.common.message.CommonMessageRequestDto;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.SOU.mockServer.external.service.TcpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "전문 Test", description = "전문 Test를 위해 Client socket을 활용하여 test하는 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MessageTestController {

    private final TcpService tcpService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공통정보부 테스트 성공", content = @Content(schema = @Schema(implementation = CommonFieldMessage.class))),
        @ApiResponse(responseCode = "400", description = "알맞지 않은 message 구조", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "공통정보부 테스트", description = "공통정보부에 대한 tcp socket 테스트를 진행합니다.")
    @PostMapping("/common-message")
    public ResponseEntity<CommonFieldMessage> commonMessage(
        @RequestBody CommonMessageRequestDto commonMessageDto) throws IOException {
        return ResponseEntity.ok(tcpService.commonFieldMessage(commonMessageDto));
    }

}

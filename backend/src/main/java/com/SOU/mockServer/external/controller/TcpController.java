package com.SOU.mockServer.external.controller;

import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import com.SOU.mockServer.external.service.TcpService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// inbound 요청을 서비스 계층 호출로 변환, 서비스 계층 반환 값을 아웃바운드 응답으로 변환하는 하나의 layer
// pipe & filter architecture 의 filter에 해당
// test flow에서 사용할 수 있는 여러 POJO(plain old java object) method 제공가능 (inputChannel의 data type에 따라 분리)
@MessageEndpoint
@Component
public class TcpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcpController.class);

    // inputChannel은 TCPConfig에서 등록한 bean이름과 동일시하기
    @ServiceActivator(inputChannel = "inboundChannel")
    public CommonFieldMessage handleCommonFieldMessage(CommonFieldMessage msg) {
        return msg;
    }

    @ServiceActivator(inputChannel = "inboundChannel")
    public NotificationIndividualWithdrawalMessage handleNotificationIndividualWithdrawalMessage(NotificationIndividualWithdrawalMessage msg) {
        return msg;
    }

}
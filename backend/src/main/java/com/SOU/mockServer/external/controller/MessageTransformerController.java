package com.SOU.mockServer.external.controller;

import com.SOU.mockServer.external.message.account.NotificationIndividualWithdrawalMessage;
import com.SOU.mockServer.external.message.common.CommonFieldMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

// inbound 요청을 서비스 계층 호출로 변환, 서비스 계층의 처리 후 반환 값을 아웃바운드 응답으로 변환하는 하나의 layer
// pipe & filter architecture 의 filter 에 해당
// test flow에서 사용할 수 있는 여러 POJO(plain old java object) method 제공가능
@MessageEndpoint
@RequiredArgsConstructor
public class MessageTransformerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        MessageTransformerController.class);

    @ServiceActivator(inputChannel = "CommonMessageChannel", outputChannel = "messageChannel")
    public CommonFieldMessage handleCommonFieldMessage(CommonFieldMessage msg) {
        LOGGER.debug("Arrive CommonMessage at Controller");
        // 추후 Common message logic에 대한 처리 가능

        return msg;
    }

    @ServiceActivator(inputChannel = "NotificationIndividualWithdrawalMessageChannel", outputChannel = "messageChannel")
    public NotificationIndividualWithdrawalMessage handleNotificationIndividualWithdrawalMessage(
        NotificationIndividualWithdrawalMessage msg) {
        LOGGER.debug("Arrive NotificationIndividualWithdrawalMessage at Controller");
        // 추후 NotificationIndividualWithdrawalMessage logic에 대한 처리 가능

        return msg;
    }

}
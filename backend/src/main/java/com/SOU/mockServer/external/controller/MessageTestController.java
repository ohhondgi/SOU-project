package com.SOU.mockServer.external.controller;

import com.SOU.mockServer.external.service.TcpService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageTestController {
    private final TcpService tcpService;

    @GetMapping("/messageTest")
    public ResponseEntity<String> test() throws IOException {
        return ResponseEntity.ok(tcpService.CommonFieldMessageTest());
    }

}

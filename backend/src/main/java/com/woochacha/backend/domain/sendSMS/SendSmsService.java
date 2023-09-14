package com.woochacha.backend.domain.sendSMS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woochacha.backend.domain.admin.dto.sendMessage.MessageDto;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SendSmsService {
    void sendSms(MessageDto messageDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException;
}

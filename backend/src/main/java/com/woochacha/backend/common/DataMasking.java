package com.woochacha.backend.common;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import retrofit2.http.HEAD;

@Component
public class DataMasking {

    private final TextEncryptor textEncryptor;

    public DataMasking(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }
    public String encoding(String plainText) {
        return textEncryptor.encrypt(plainText);
    }

    public String decoding(String encryptText) {
        if(encryptText.matches("010(.*)"))
            return encryptText;
        return textEncryptor.decrypt(encryptText);
    }
}

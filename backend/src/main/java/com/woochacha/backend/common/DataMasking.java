package com.woochacha.backend.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class DataMasking {

    private final TextEncryptor textEncryptor;

    public DataMasking(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }
    public String encoding(String plainText) {
        return textEncryptor.encrypt(plainText);
    }

//    public String decoding(@NotNull String encryptText) {
//        if(encryptText.contains("010") || encryptText.contains(".com"))
//            return encryptText;
//        return textEncryptor.decrypt(encryptText);
//    }

    public String decoding(String encryptText) {
        return textEncryptor.decrypt(encryptText);
    }
}

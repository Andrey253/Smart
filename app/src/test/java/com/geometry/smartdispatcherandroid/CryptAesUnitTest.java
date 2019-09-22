package com.geometry.smartdispatcherandroid;

import org.junit.Test;

import crypto.CryptAes;

import static junit.framework.Assert.assertEquals;

/**
 * Класс для тестирования криптографии
 * @author DushinovSV
 * Created by house on 20.11.2018.
 */
public class CryptAesUnitTest {
    @Test
    public void encodeMessageTest() throws Exception {
        String password = "test_password";
        CryptAes cryptAes = new CryptAes(password);

        String message = "Привет Мир!!! Hello World!!!";
        for (int i = 0; i < 10; i++) {
            String encryptMessage = cryptAes.encrypt(message);
            String realResult = cryptAes.decrypt(encryptMessage);
            assertEquals(realResult, message);
        }
    }
}
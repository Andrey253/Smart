package crypto;

import android.annotation.SuppressLint;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

import common.SystemConstants;

/**
 * Класс предназначен для шифрования данных
 * @author DushinovSV
 * Created by house on 22.11.2018.
 */
public class CryptAes {

    /**
     * Алгоритм для шифрования данных
     */
    private static final String ALGORITHM = "AES";

    /**
     * Секретный ключ для шифрования данных
     */
    private SecretKey secretKey;

    public CryptAes(String password) {
        try {
            this.secretKey = generateKey(password);
        } catch (Exception e) {
            Log.e(SystemConstants.LOG_TAG, "error create secret key", e);
        }
    }

    /**
     * Метод генерирует секретный ключ
     * @param password Пароль для генерации ключа
     */
    private SecretKey generateKey(String password) throws Exception {
        byte[] keyBytes = password.getBytes(SystemConstants.UTF_8);
        MessageDigest sha = MessageDigest.getInstance(SystemConstants.SHA_1);
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * Метод кодирует сообщение
     * @param message Текст сообщения
     */
    public String encrypt(String message) throws Exception {
        byte[] messageBytes = message.getBytes(SystemConstants.UTF_8);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        byte[] resultBytes = Base64.encodeBytesToBytes(cipher.doFinal(messageBytes));
        return new String(resultBytes, SystemConstants.UTF_8);
    }

    /**
     * Метод раскодирует сообщение
     * @param cipherMessage Текст закодированного сообщения
     */
    public String decrypt(String cipherMessage) throws Exception {
        byte[] cipherMessageBytes = Base64.decode(cipherMessage);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        return new String(cipher.doFinal(cipherMessageBytes), SystemConstants.UTF_8);
    }
}
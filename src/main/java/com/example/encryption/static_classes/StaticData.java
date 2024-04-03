package com.example.encryption.static_classes;

import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Base64;

public class StaticData {
    static private Stage stage;
    
    static private SecretKey secretSKey;
    
    static private KeyPair keyPairPrivPub;
    
    public static KeyPair getKeyPairPrivPub() {
        return keyPairPrivPub;
    }
    
    public static void setKeyPairPrivPub(KeyPair keyPairPrivPub) {
        StaticData.keyPairPrivPub = keyPairPrivPub;
    }
    
    public static void setSecretSKey(SecretKey secretSKey) {
        StaticData.secretSKey = secretSKey;
    }
    public static SecretKey getSecretSKey() {
        return secretSKey;
    }
    public static String getSymKeyString() {
        byte[] keyBytes = secretSKey.getEncoded();
        String keyString = Base64.getEncoder().encodeToString(keyBytes);
        return keyString;
    }
    
    public static String getAsymPublicKeyString() {
        byte[] keyBytes =keyPairPrivPub.getPublic().getEncoded();
        String keyString = Base64.getEncoder().encodeToString(keyBytes);
        return keyString;
    }
    
    public static String getAsymPrivateKeyString() {
        byte[] keyBytes =keyPairPrivPub.getPrivate().getEncoded();
        String keyString = Base64.getEncoder().encodeToString(keyBytes);
        return keyString;
    }
    
    public static String getEncodedText(Cipher cipher, String plainText) throws IllegalBlockSizeException, BadPaddingException {
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
    public static String getTextEncoded(Cipher cipher, String encodedText) throws IllegalBlockSizeException, BadPaddingException {
        return new String(cipher.doFinal(Base64.getDecoder().decode(encodedText)));
    }
    static public void setStage(Stage stage_){
        stage=stage_;
    }
    static public Stage getStage(){
        return stage;
    }
}

package com.example.encryption.static_classes;

import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.MessageDigest;
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
        //byte[] keyBytes = secretSKey.getEncoded();
        //String keyString = Base64.getEncoder().encodeToString(keyBytes);
        //return keyString;
        
        byte[] keyBytes = secretSKey.getEncoded();
        BigInteger number = new BigInteger(1, keyBytes);
        String keyHex = number.toString(16);
        return keyHex;
    }
    
    public static String getAsymPublicKeyString() {
        //byte[] keyBytes =keyPairPrivPub.getPublic().getEncoded();
        //String keyString = Base64.getEncoder().encodeToString(keyBytes);
        //return keyString;
        
        byte[] keyBytes = keyPairPrivPub.getPublic().getEncoded();
        BigInteger number = new BigInteger(1, keyBytes);
        String keyHex = number.toString(16);
        return keyHex;
    }
    
    public static String getAsymPrivateKeyString() {
        //byte[] keyBytes =keyPairPrivPub.getPrivate().getEncoded();
        //String keyString = Base64.getEncoder().encodeToString(keyBytes);
        //return keyString;
        
        byte[] keyBytes = keyPairPrivPub.getPrivate().getEncoded();
        BigInteger number = new BigInteger(1, keyBytes);
        String keyHex = number.toString(16);
        return keyHex;
    }
    
    public static String getEncodedText(Cipher cipher, String plainText) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        BigInteger number = new BigInteger(1, encryptedBytes);
        String encodedText = number.toString(16);
        return encodedText;
        //return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
    public static String getTextEncoded(Cipher cipher, String encodedText) throws IllegalBlockSizeException, BadPaddingException {
        return new String(cipher.doFinal(Base64.getDecoder().decode(encodedText)));
    }
    
    public static String stringToHash(MessageDigest md, String text){
        byte[] hashBytes = md.digest(text.getBytes());
        BigInteger number = new BigInteger(1, hashBytes);
        String hashText = number.toString(16);
        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }
        return hashText;
    }
    static public void setStage(Stage stage_){
        stage=stage_;
    }
    static public Stage getStage(){
        return stage;
    }
}

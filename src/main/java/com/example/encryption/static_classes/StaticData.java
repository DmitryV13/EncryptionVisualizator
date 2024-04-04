package com.example.encryption.static_classes;

import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.util.Arrays;

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
    
    public static String getEncodedText(Cipher cipher, String plainText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        BigInteger number = new BigInteger(1, encryptedBytes);
        String encodedText = number.toString(16);
        return encodedText;
        
        //return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
    
    public static String getDecodedText(Cipher cipher, String encodedText) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encodedBytes =hexStringToByteArray(encodedText);
        System.out.println(encodedBytes.length);
        byte[] decryptedBytes = cipher.doFinal(encodedBytes);
        String result=new String(decryptedBytes);
        return result;
        
        //return new String(cipher.doFinal(Base64.getDecoder().decode(encodedText)));
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
    
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[(len%2 == 0) ? (len / 2) : (len/2+1)];
        if(len%2!=0){
            byteArray[len/2]=0;
            hexString+="0";
        }
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }
    
    public static byte[] addPadding(byte[] data) {
        int paddingLength = 16 - (data.length % 16);
        byte[] paddedData = Arrays.copyOf(data, data.length + paddingLength);
        Arrays.fill(paddedData, data.length, paddedData.length, (byte) paddingLength);
        return paddedData;
    }
    
    public static byte[] removePadding(byte[] data) {
        int paddingLength = data[data.length - 1];
        return Arrays.copyOf(data, data.length - paddingLength);
    }
    static public void setStage(Stage stage_){
        stage=stage_;
    }
    static public Stage getStage(){
        return stage;
    }
}

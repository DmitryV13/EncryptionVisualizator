package com.example.encryption;


import com.example.encryption.static_classes.StaticData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public class DigitalSignatureController {
    @FXML
    TextField publicKey;
    @FXML
    TextField privateKey;
    @FXML
    TextArea plainText1;
    @FXML
    TextArea plainText2;
    @FXML
    TextArea hash1;
    @FXML
    TextArea signature1;
    @FXML
    TextArea hash2;
    @FXML
    TextArea signature2;
    @FXML
    Label verified;
    @FXML
    ChoiceBox<Integer> keyLength;
    
    @FXML
    private void initialize(){
        ObservableList<Integer> keyLengths= FXCollections.observableArrayList(1024, 2048, 4096);
        keyLength.setValue(2048);
        keyLength.setItems(keyLengths);
    }
    
    @FXML
    private void onBack() throws IOException {
        Stage stage = StaticData.getStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    private void onConvertEPubT() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //if(StaticData.getKeyPairPrivPub()==null){
        //    onGenerateKey();
        //}
        //setText();
        //Cipher cipher = Cipher.getInstance("RSA");
        //if(encodedPrivateText2.getText().isEmpty()){
        //    cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        //    encodedPrivateText2.setText(StaticData.getEncodedText(cipher, "qwerty"));
        //}
        //cipher.init(Cipher.DECRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        //plainText2.setText(StaticData.getTextEncoded(cipher, encodedPrivateText2.getText()));
    }
    
    @FXML
    private void onConvertEPrivT() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //if(StaticData.getKeyPairPrivPub()==null){
        //    onGenerateKey();
        //}
        //setText();
        //Cipher cipher = Cipher.getInstance("RSA");
        //if(encodedPublicText2.getText().isEmpty()){
        //    cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        //    encodedPublicText2.setText(StaticData.getEncodedText(cipher, "qwerty"));
        //}
        //cipher.init(Cipher.DECRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        //plainText2.setText(StaticData.getTextEncoded(cipher, encodedPublicText2.getText()));
    }
    
    @FXML
    private void onConvertTPubE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //if(StaticData.getKeyPairPrivPub()==null){
        //    onGenerateKey();
        //}
        //setText();
        //if(plainText1.getText().isEmpty()){
        //    plainText1.setText("qwerty");
        //}
        //Cipher cipher = Cipher.getInstance("RSA");
        //cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        //encodedPublicText1.setText(StaticData.getEncodedText(cipher, plainText1.getText()));
    }
    @FXML
    private void onConvertTPrivE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //if(StaticData.getKeyPairPrivPub()==null){
        //    onGenerateKey();
        //}
        //setText();
        //if(plainText1.getText().isEmpty()){
        //    plainText1.setText("qwerty");
        //}
        //Cipher cipher = Cipher.getInstance("RSA");
        //cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        //encodedPrivateText1.setText(StaticData.getEncodedText(cipher, plainText1.getText()));
    }
    
    @FXML
    private void onCreateSignature() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(StaticData.getKeyPairPrivPub()==null){
            onGenerateKey();
        }
        setText();
        if(plainText1.getText().isEmpty()){
            plainText1.setText("qwerty");
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        hash1.setText(StaticData.stringToHash(md, plainText1.getText()));
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        signature1.setText(StaticData.getEncodedText(cipher, hash1.getText()));
    }
    
    @FXML
    private void onSend() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(plainText1.getText().isEmpty() || signature1.getText().isEmpty()){
            onCreateSignature();
        }
        plainText2.setText(plainText1.getText());
        signature2.setText(signature1.getText());
    }
    
    @FXML
    private void onVerify() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(plainText2.getText().isEmpty() || signature2.getText().isEmpty()){
            onSend();
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        hash2.setText(StaticData.stringToHash(md, plainText2.getText()));
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        if(StaticData.getDecodedText(cipher, signature2.getText()).equals(hash2.getText())){
            verified.setText("VERIFICATION PASSED");
        }else{
            verified.setText("VERIFICATION FAILED");
        }
    }
    
    @FXML
    private void onGenerateKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength.getValue());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        StaticData.setKeyPairPrivPub(keyPair);
        publicKey.setText(StaticData.getAsymPublicKeyString());
        privateKey.setText(StaticData.getAsymPrivateKeyString());
    }
    
    private void setText(){
        if(publicKey.getText().isEmpty() && privateKey.getText().isEmpty() && StaticData.getKeyPairPrivPub() != null){
            publicKey.setText(StaticData.getAsymPublicKeyString());
            privateKey.setText(StaticData.getAsymPrivateKeyString());
        }
    }
}

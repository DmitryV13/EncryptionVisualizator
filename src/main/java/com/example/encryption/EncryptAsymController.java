package com.example.encryption;

import com.example.encryption.static_classes.StaticData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.*;
import java.io.IOException;
import java.security.*;

public class EncryptAsymController {
    @FXML
    TextField publicKey;
    @FXML
    TextField privateKey;
    @FXML
    TextArea plainText1;
    @FXML
    TextArea plainText2;
    @FXML
    TextArea encodedPublicText1;
    @FXML
    TextArea encodedPrivateText1;
    @FXML
    TextArea encodedPublicText2;
    @FXML
    TextArea encodedPrivateText2;
    @FXML
    ChoiceBox<Integer> keyLength;
    
    @FXML
    private void initialize(){
        ObservableList<Integer> keyLengths= FXCollections.observableArrayList(1024, 2048, 4096);
        keyLength.setValue(2048);
        keyLength.setItems(keyLengths);
    }
    
    @FXML
    private void onBack() throws IOException{
        Stage stage = StaticData.getStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    private void onConvertEPubT() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getKeyPairPrivPub()==null){
            onGenerateKey();
        }
        setText();
        Cipher cipher = Cipher.getInstance("RSA");
        if(encodedPrivateText2.getText().isEmpty()){
            cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
            encodedPrivateText2.setText(StaticData.getEncodedText(cipher, "qwerty"));
        }
        cipher.init(Cipher.DECRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        plainText2.setText(StaticData.getTextEncoded(cipher, encodedPrivateText2.getText()));
    }
    
    @FXML
    private void onConvertEPrivT() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getKeyPairPrivPub()==null){
            onGenerateKey();
        }
        setText();
        Cipher cipher = Cipher.getInstance("RSA");
        if(encodedPublicText2.getText().isEmpty()){
            cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
            encodedPublicText2.setText(StaticData.getEncodedText(cipher, "qwerty"));
        }
        cipher.init(Cipher.DECRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        plainText2.setText(StaticData.getTextEncoded(cipher, encodedPublicText2.getText()));
    }
    
    @FXML
    private void onConvertTPubE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getKeyPairPrivPub()==null){
            onGenerateKey();
        }
        setText();
        if(plainText1.getText().isEmpty()){
            plainText1.setText("qwerty");
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPublic());
        encodedPublicText1.setText(StaticData.getEncodedText(cipher, plainText1.getText()));
    }
    @FXML
    private void onConvertTPrivE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getKeyPairPrivPub()==null){
            onGenerateKey();
        }
        setText();
        if(plainText1.getText().isEmpty()){
            plainText1.setText("qwerty");
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, StaticData.getKeyPairPrivPub().getPrivate());
        encodedPrivateText1.setText(StaticData.getEncodedText(cipher, plainText1.getText()));
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

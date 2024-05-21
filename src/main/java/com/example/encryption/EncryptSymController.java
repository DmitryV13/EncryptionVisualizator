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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptSymController {
    @FXML
    TextField publicKey;
    @FXML
    TextArea plainText;
    @FXML
    TextArea encodedText;
    @FXML
    ChoiceBox<Integer> keyLength;
    
    @FXML
    private void initialize(){
        ObservableList<Integer> keyLengths= FXCollections.observableArrayList(128, 192, 256);
        keyLength.setValue(256);
        keyLength.setItems(keyLengths);
    }
    
    @FXML
    private void onBack() throws IOException{
        Stage stage = StaticData.getStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    private void onConvertET() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getSecretSKey()==null){
            onGenerateKey();
        }
        setText();
        Cipher cipher = Cipher.getInstance("AES");
        if(encodedText.getText().isEmpty()){
            cipher.init(Cipher.ENCRYPT_MODE, StaticData.getSecretSKey());
            encodedText.setText(StaticData.getEncodedText(cipher, "qwerty"));
        }
        cipher.init(Cipher.DECRYPT_MODE, StaticData.getSecretSKey());
        plainText.setText(StaticData.getDecodedText(cipher, encodedText.getText()));
    }
    
    @FXML
    private void onConvertTE() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(StaticData.getSecretSKey()==null){
            onGenerateKey();
        }
        setText();
        if(plainText.getText().isEmpty()){
            plainText.setText("qwerty");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, StaticData.getSecretSKey());
        encodedText.setText(StaticData.getEncodedText(cipher, plainText.getText()));
    }
    
    @FXML
    private void onGenerateKey() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keyLength.getValue());
        SecretKey key = keyGen.generateKey();
        StaticData.setSecretSKey(key);
        publicKey.setText(StaticData.getSymKeyString());
    }
    
    private void setText(){
        if(publicKey.getText().isEmpty() && StaticData.getSecretSKey() != null){
            publicKey.setText(StaticData.getSymKeyString());
        }
    }
}

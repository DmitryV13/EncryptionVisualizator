package com.example.encryption;

import com.example.encryption.static_classes.StaticData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label welcomeText;
    
    @FXML
    private ChoiceBox<String> encryptionType;
    
    @FXML
    private void initialize(){
        //ObservableList<String> encryptionTypes= FXCollections.observableArrayList("Symmetric", "Asymmetric");
        //encryptionType.setValue("Symmetric");
        //encryptionType.setItems(encryptionTypes);

        welcomeText=new Label();
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    @FXML
    protected void onAsym() throws IOException {
        Stage stage = StaticData.getStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("encryption-asym-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 550);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    protected void onSym() throws IOException {
        Stage stage = StaticData.getStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("encryption-sym-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        
        stage.show();
    }
    
    @FXML
    protected void onDigit() throws IOException {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //encryptionType.getItems().addAll("Symmetric", "Asymmetric");
    }
}
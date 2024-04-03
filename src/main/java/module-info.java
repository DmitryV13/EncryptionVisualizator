module com.example.encryption {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.encryption to javafx.fxml;
    exports com.example.encryption;
}
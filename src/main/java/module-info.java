module MyApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens com.example.javafx to javafx.fxml;
    opens prod to javafx.fxml;

    exports com.example.javafx;
    exports prod;
}

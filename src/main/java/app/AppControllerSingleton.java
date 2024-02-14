package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class AppControllerSingleton {
    private static AppControllerSingleton instance;
    private final StackPane contentArea;

    private AppControllerSingleton(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    public static synchronized AppControllerSingleton getInstance(StackPane contentArea) {
        if (instance == null) {
            instance = new AppControllerSingleton(contentArea);
        }
        return instance;
    }

    public void loadFXML(String fxmlFileName) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFileName));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}

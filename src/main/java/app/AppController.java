package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppController implements Initializable {
    private static AppController instance;

    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    @FXML
    private StackPane contentArea;
    @FXML
    private Label exit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("accueil.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void stocks(ActionEvent actionEvent) {
        try {
            AppControllerSingleton.getInstance(contentArea).loadFXML("stocks.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void calculs(ActionEvent actionEvent) {
        try {
            AppControllerSingleton.getInstance(contentArea).loadFXML("calculs.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void chaine(ActionEvent actionEvent) {
        try {
            AppControllerSingleton.getInstance(contentArea).loadFXML("chaine.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void home(ActionEvent actionEvent) {
        try {
            AppControllerSingleton.getInstance(contentArea).loadFXML("accueil.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void commandes(ActionEvent actionEvent) {
        try {
            AppControllerSingleton.getInstance(contentArea).loadFXML("commandes.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setContent(Parent root) {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
    }
}

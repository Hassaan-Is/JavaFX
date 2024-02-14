package prod;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class StocksController {

    @FXML
    private TableView<Stock> tableView;

    @FXML
    private TableColumn<StockItem, String> c1code;

    @FXML
    private TableColumn<StockItem, String> c2nom;

    @FXML
    private TableColumn<Stock, Integer> c3quantite;

    @FXML
    private TableColumn<Stock, String> c4unite;

    @FXML
    private TableColumn<Stock, Integer> c5achat;

    @FXML
    private TableColumn<Stock, Integer> c6vente;


    // Initialisation de la TableView et des colonnes
    public void initialize() {
        c1code.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        c3quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        c4unite.setCellValueFactory(new PropertyValueFactory<>("unite"));
        c5achat.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        c6vente.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        loadCsvData();
    }

    private void loadCsvData() {
        String csvFile = "src/main/prod/elements.csv";
        String line = "";
        String csvSeparator = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);

                String code = data[0];
                String nom = data[1];
                int quantite = Integer.parseInt(data[2]);
                String unite = data[3];
                int prixAchat = Integer.parseInt(data[4]);
                int prixVente = Integer.parseInt(data[5]);

                // Créer un objet Stock avec les données extraites
                Stock element = new Stock(code, nom, quantite, unite, prixAchat, prixVente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

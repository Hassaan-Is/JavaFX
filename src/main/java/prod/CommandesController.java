package prod;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import prod.SessionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CommandesController {

    @FXML
    private TableView<Commandes> tableView;

    @FXML
    private TableColumn<Commandes, String> c1Column;

    @FXML
    private TableColumn<Commandes, Integer> c2Column;

    @FXML
    private TableColumn<Commandes, Integer> c3Column;

    @FXML
    private TableColumn<Commandes, Integer> c4Column;

    @FXML
    private ListView<String> listCommandes;

    @FXML
    private TextField nbField;

    @FXML
    private Button validCommandes;

    public void initialize() {
        c1Column.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixAchat()).asObject());
        c3Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixVente()).asObject());
        c4Column.setCellValueFactory(cellData -> {
            String code = cellData.getValue().getCode();
            int quantite = SessionManager.getQuantite(code);
            return new SimpleIntegerProperty(quantite).asObject();
        });

        loadCsvData();
        rempliListeCode();

        // Ajout de l'action du bouton pour valider la commande
        validCommandes.setOnAction(event -> {
            String selectedCode = listCommandes.getSelectionModel().getSelectedItem();
            if (selectedCode != null && !nbField.getText().isEmpty()) {
                int newQuantite = Integer.parseInt(nbField.getText());
                modifQuantite(selectedCode, newQuantite);
            }
        });
    }

    private void loadCsvData() {
        String csvFile = "src/main/java/prod/prix.csv";
        String line;
        String csvSeparator = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignorer la première ligne du csv
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);
                String code = data[0];
                int prixAchat = "NA".equals(data[1]) ? 0 : Integer.parseInt(data[1]);
                int prixVente = "NA".equals(data[2]) ? 0 : Integer.parseInt(data[2]);
                Commandes commande = new Commandes(code, prixAchat, prixVente, 0);
                tableView.getItems().add(commande);
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void rempliListeCode() {
        for (Commandes commande : tableView.getItems()) {
            listCommandes.getItems().add(commande.getCode());
        }
    }

    private void modifQuantite(String selectedCode, int newQuantite) {
        for (Commandes commande : tableView.getItems()) {
            if (commande.getCode().equals(selectedCode)) {
                commande.setQuantite(newQuantite);
                SessionManager.setQuantite(selectedCode, newQuantite);
                System.out.println("Nouvelle quantité pour " + selectedCode + ": " + newQuantite);
                break;
            }
        }
        tableView.refresh(); // Rafraîchir la TableView pour refléter les modifications
    }
}

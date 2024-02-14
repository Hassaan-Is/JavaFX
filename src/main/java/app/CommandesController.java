package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

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
    private Button validCommande;

    private List<Commandes> commandesList;

    public void initialize() {
        commandesList = new ArrayList<>();

        loadCsvData();

        c1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCode()));
        c2Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixAchat()).asObject());
        c3Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixVente()).asObject());
        c4Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());

        loadListViewData();
        remplirListeCode();
        applyIntegerFormatter();
        // Gérer l'action du bouton de validation
        handleValidButtonAction();
    }

    private void loadCsvData() {
        String csvFile = "src/main/java/app/prix.csv";
        String line;
        String csvSeparator = ";";
        CommandesModel commandesModel = CommandesModel.getInstance();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignorer la première ligne du csv
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);
                String code = data[0];
                int prixAchat = "NA".equals(data[1]) ? 0 : Integer.parseInt(data[1]);
                int prixVente = "NA".equals(data[2]) ? 0 : Integer.parseInt(data[2]);

                int quantite = commandesModel.getQuantite(code);

                Commandes commande = new Commandes(code, prixAchat, prixVente, quantite);
                commandesList.add(commande);
                tableView.getItems().add(commande);
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    // Créer un formatteur de texte limitant l'entrée à des entiers
    private UnaryOperator<TextFormatter.Change> createIntegerFilter() {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) { // Vérifier si le nouveau texte est un nombre entier
                return change; // Autoriser le changement
            }
            return null; // Ignorer le changement
        };
    }

    // Appliquer le formatteur de texte au champ de texte
    private void applyIntegerFormatter() {
        nbField.setTextFormatter(new TextFormatter<>(createIntegerFilter()));
    }

    private void handleValidButtonAction() {
        validCommande.setOnAction(event -> {
            String selectedCode = listCommandes.getSelectionModel().getSelectedItem();
            if (selectedCode != null && !nbField.getText().isEmpty()) {
                int newQuantite = Integer.parseInt(nbField.getText());
                modifQuantite(selectedCode, newQuantite);
                // Mettre à jour la cellule correspondante dans la TableView
                for (Commandes commandes : commandesList) {
                    if (commandes.getCode().equals(selectedCode)) {
                        commandes.setQuantite(newQuantite);
                        break;
                    }
                }
                tableView.refresh(); // Rafraîchir la TableView pour refléter les modifications
            }
        });
    }


    private void loadListViewData() {
        for (Commandes commande : commandesList) {
            listCommandes.getItems().add(commande.getCode());
        }
    }

    private void modifQuantite(String selectedCode, int newQuantite) {
        // Affichage de la quantité avant la modificationSystem.out.println("Quantité avant la modification pour " + selectedCode + ": " + CommandesModel.getInstance().getQuantite(selectedCode));
        for (Commandes commandes : commandesList) {
            if (commandes.getCode().equals(selectedCode)) {
                commandes.setQuantite(newQuantite);
                break;
            }
        }
        CommandesModel.getInstance().setQuantite(selectedCode,newQuantite);// Affichage de la quantité après la modificationSystem.out.println("Nouvelle quantité pour " + selectedCode + ": " + CommandesModel.getInstance().getQuantite(selectedCode));
        tableView.refresh(); // Rafraîchir la TableView pour refléter les modifications
    }

    private void remplirListeCode() {
        for (Commandes commande : commandesList) {
            listCommandes.getItems().add(commande.getCode());
        }
    }
}

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

public class ChaineController  {

    @FXML
    private TableView<Chaine> tableView;

    @FXML
    private TableColumn<Chaine, String> c1Column;

    @FXML
    private TableColumn<Chaine, String> c2Column;

    @FXML
    private TableColumn<Chaine, String> c2Column1;

    @FXML
    private TableColumn<Chaine, String> c2Column2;

    @FXML
    private TableColumn<Chaine, Integer> c2Column21;

    @FXML
    private ListView<String> listCode;

    @FXML
    private TextField nbField;

    @FXML
    private Button validButton;

    private List<Chaine> chaines;



    public void initialize() {

        // Initialisation de la liste des chaines
        chaines = new ArrayList<>();

        // Charger les données depuis le fichier CSV
        loadCsvData();

        // Configuration des cellules de la TableView
        c1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeUnique()));
        c2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        c2Column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().formatHashMap(cellData.getValue().getEntrees())));
        c2Column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().formatHashMap(cellData.getValue().getSorties())));
        c2Column21.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNiveauActivite()).asObject());

        // Remplir la liste des codes
        remplirListeCode();
        // Appliquer le formatteur de texte au champ de texte
        applyIntegerFormatter();
        // Gérer l'action du bouton de validation
        handleValidButtonAction();
    }



    private void loadCsvData() {
        String csvFile = "src/main/java/app/chaines.csv";
        String line;
        String csvSeparator = ";";
        ChaineModel model = ChaineModel.getInstance();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignorer la première ligne du csv
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);
                String code = data[0];
                String nom = data[1];

                // Parsing des entrées et sorties
                Map<String, Float> entrees = parseElements(data[2]);
                Map<String, Float> sorties = parseElements(data[3]);
                // Récupérer le niveau d'activité à partir du modèle de données
                int niveauActivite = model.getNiveauActivite(code);

                // Création d'un nouvel objet Chaine et ajout à la liste
                Chaine chaine = new Chaine(code, nom, entrees, sorties, niveauActivite);
                model.setChaine(code,chaine);
                tableView.getItems().add(chaine);
                chaines.add(chaine);
            }
        } catch (IOException e) {
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

    // Gérer l'action du bouton de validation
    private void handleValidButtonAction() {
        validButton.setOnAction(event -> {
            String selectedCode = listCode.getSelectionModel().getSelectedItem();
            if (selectedCode != null && !nbField.getText().isEmpty()) {
                int newNiveauActivite = Integer.parseInt(nbField.getText());
                modifNiveau(selectedCode, newNiveauActivite);
                // Mettre à jour la cellule correspondante dans la TableView
                for (Chaine chaine : chaines) {
                    if (chaine.getCodeUnique().equals(selectedCode)) {
                        chaine.setNiveauActivite(newNiveauActivite);
                        break;
                    }
                }
                tableView.refresh(); // Rafraîchir la TableView pour refléter les modifications
            }
        });
    }


    private void modifNiveau(String codeUnique, int newNiveauActivite) {
        // Mise à jour du niveau d'activité dans le modèle de données
        ChaineModel.getInstance().setNiveauActivite(codeUnique, newNiveauActivite);
        // Rafraîchir l'affichage dans la TableView si nécessaire
        tableView.refresh(); // Rafraîchir l'affichage de la TableView
    }


    private Map<String, Float> parseElements(String elementsData) {
        Map<String, Float> elements = new HashMap<>();
        // Recherche des occurrences de paires clé-valeur
        String[] keyValuePairs = elementsData.split("\\),\\(");
        for (String pair : keyValuePairs) {
            // Supprimer les parenthèses et diviser la paire clé-valeur en code et quantité
            String[] keyValue = pair.replaceAll("[()]", "").split(",");
            if (keyValue.length == 2) { // Assurez-vous qu'il y a deux éléments (clé et valeur)
                String code = keyValue[0].trim();
                Float quantite = Float.parseFloat(keyValue[1].trim());
                elements.put(code, quantite);
            }
        }
        return elements;
    }

    private void remplirListeCode() {
        for (Chaine chaine : chaines) {
            listCode.getItems().add(chaine.getCodeUnique());
        }
    }

}
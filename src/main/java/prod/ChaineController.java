package prod;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChaineController {

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

    public void initialize() {
        c1Column.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2Column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        c2Column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElementsEntree()));
        c2Column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElementsSortie()));
        c2Column21.setCellValueFactory(cellData -> new SimpleIntegerProperty(SessionManager.getNiveauActivite(cellData.getValue().getCode())).asObject());

        loadCsvData();
        rempliListeCode();

        // Ajout de l'action du bouton pour modifier le niveau d'activité
        validButton.setOnAction(event -> {
            String selectedCode = listCode.getSelectionModel().getSelectedItem();
            if (selectedCode != null && !nbField.getText().isEmpty()) {
                int newNiveauActivite = Integer.parseInt(nbField.getText());
                modifNiveau(selectedCode, newNiveauActivite);
                SessionManager.setNiveauActivite(selectedCode, newNiveauActivite); // Mettre à jour la valeur dans SessionManager
            }
        });
    }

    private void loadCsvData() {
        String csvFile = "src/main/java/prod/chaines.csv";
        String line;
        String csvSeparator = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignorer la première ligne du csv
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);
                String code = data[0];
                String nom = data[1];
                String elementsEntree = data[2];
                String elementsSortie = data[3];
                Chaine chaine = new Chaine(code, nom, elementsEntree, elementsSortie, 0);
                tableView.getItems().add(chaine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rempliListeCode() {
        for (Chaine chaine : tableView.getItems()) {
            listCode.getItems().add(chaine.getCode());
        }
    }

    private void modifNiveau(String selectedCode, int newNiveauActivite) {
        for (Chaine chaine : tableView.getItems()) {
            if (chaine.getCode().equals(selectedCode)) {
                chaine.setNiveauActivite(newNiveauActivite);System.out.println("Nouveau niveau d'activité pour " + selectedCode + ": " + newNiveauActivite);
                break;
            }
        }
        tableView.refresh(); // Rafraîchir la TableView pour refléter les modifications
    }


}

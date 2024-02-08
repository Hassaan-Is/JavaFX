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

    public void initialize() {
        c1Column.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2Column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        c2Column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElementsEntree()));
        c2Column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElementsSortie()));
        c2Column21.setCellValueFactory(new PropertyValueFactory<>("niveauActivite"));


        loadCsvData();
    }

    private void loadCsvData() {
        String csvFile = "src/main/java/prod/chaines.csv";
        String line = "";
        String csvSeparator = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);

                // Extraire les données de chaque champ du CSV
                String code = data[0];
                String nom = data[1];
                String elementsEntree = data[2];
                String elementsSortie = data[3];

                // Créer un objet Chaine avec les données extraites et l'ajouter à la TableView
                Chaine chaine = new Chaine(code, nom, elementsEntree, elementsSortie, 0); // Niveau d'activité à 0 pour le moment
                tableView.getItems().add(chaine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rempliListeCode() {
        ObservableList<String> items = FXCollections.observableArrayList();

        String csvFile = "src/main/java/prod/chaines.csv";
        String line;
        String csvSeparator = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);
                // Ajoute le code à la liste des items
                items.add(data[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajoute les items à la ListView
        listCode.setItems(items);

        // Test console pour vérifier les codes ajoutés à la liste
        for (String item : items) {
            System.out.println("Code: " + item);
        }
    }

}

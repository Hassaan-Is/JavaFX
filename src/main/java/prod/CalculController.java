package prod;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.CharacterStringConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculController {
    @FXML
    private TableView<Chaine> tableChaine;
    @FXML
    private TableView<Chaine> tableCode;
    @FXML
    private TableColumn<Chaine, String> c1;
    @FXML
    private TableColumn<Chaine, String> c2;
    @FXML
    private TableColumn<Chaine, String> c3;
    @FXML
    private Button calculButton;
    @FXML
    private Label lblIndic;

    public void initialize() {
        c1.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2.setCellValueFactory(new PropertyValueFactory<>("niveauActivite"));
        c3.setCellValueFactory(new PropertyValueFactory<>("code"));

        loadChaineCSV();

        calculButton.setOnAction(event -> {
            ArrayList<Element> listElement = new ArrayList<>();
            ArrayList<Chaine> listChaine = new ArrayList<>();

            //Récupération des éléments
            String csvFile = "src/main/java/prod/element.csv";
            String line;
            String csvSeparator = ";";
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // Ignorer la première ligne du csv
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(csvSeparator);
                    String code = data[0];
                    String nom = data[1];
                    int qte = Integer.parseInt(data[2]);
                    String unite = data[3];
                    listElement.add(new Element(code, nom, qte, unite));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Récupération des chaines
            csvFile = "src/main/java/prod/chaines.csv";
            csvSeparator = ";";
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // Ignorer la première ligne du csv
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(csvSeparator);
                    String code = data[0];
                    String nom = data[1];
                    String elementsEntree = data[2];
                    String elementsSortie = data[3];
                    listChaine.add( new Chaine(code, nom, elementsEntree, elementsSortie, SessionManager.getNiveauActivite(code)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Calcul
            double total = 0;

            for(Chaine c : listChaine) {
                for (int i = 0; i < c.getNiveauActivite(); i++) {
                    //Element en entrée
                    HashMap<String, Double> map = extractData(c.getElementsEntree());
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        for (Element e : listElement) {
                            if (e.getCode().equals(entry.getKey())) {
                                csvFile = "src/main/java/prod/prix.csv";
                                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                                    br.readLine(); // Ignorer la première ligne du csv
                                    while ((line = br.readLine()) != null) {
                                        String[] data = line.split(csvSeparator);
                                        if (e.getCode().equals(data[0])) {
                                            total -= entry.getValue() * Integer.parseInt(data[1]);
                                            System.out.println(total);
                                        }
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                    }

                    //Element en entrée
                    map = extractData(c.getElementsSortie());
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        for (Element e : listElement) {
                            if (e.getCode().equals(entry.getKey())) {
                                csvFile = "src/main/java/prod/prix.csv";
                                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                                    br.readLine(); // Ignorer la première ligne du csv
                                    while ((line = br.readLine()) != null) {
                                        String[] data = line.split(csvSeparator);
                                        if (e.getCode().equals(data[0])) {
                                            total += entry.getValue() * Integer.parseInt(data[2]);
                                            System.out.println(total);
                                        }
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(total);
            lblIndic.setText(String.valueOf(total));

        });
    }

    private void loadChaineCSV() {
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
                Chaine chaine = new Chaine(code, nom, elementsEntree, elementsSortie, SessionManager.getNiveauActivite(code));
                tableChaine.getItems().add(chaine);
                tableCode.getItems().add(chaine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Double> extractData(String input) {
        // Utiliser une expression régulière pour trouver les occurrences de (EXXX, num)
        Pattern pattern = Pattern.compile("\\((E\\d+),(\\d+(\\.\\d+)?)\\)");
        Matcher matcher = pattern.matcher(input);

        // Créer une HashMap pour stocker les données extraites
        HashMap<String, Double> dataMap = new HashMap<>();

        // Parcourir les correspondances et les ajouter à la HashMap
        while (matcher.find()) {
            String code = matcher.group(1); // Code EXXX
            double quantity = Double.parseDouble(matcher.group(2)); // Quantité
            dataMap.put(code, quantity);
        }

        // Renvoyer la HashMap résultante
        return dataMap;
    }
}

package app;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class StockController {

    @FXML
    private  TextField codeTextField;
    @FXML
    private TextField nomTextField;

    @FXML
    private TextField quantiteTextField;

    @FXML
    private TextField uniteTextField;

    @FXML
    private TextField prixAchatTextField;

    @FXML
    private TextField prixVenteTextField;


    @FXML
    private TableView<Stock> tableView;

    @FXML
    private TableColumn<Stock, String> c1code;

    @FXML
    private TableColumn<Stock, String> c2nom;

    @FXML
    private TableColumn<Stock, Integer> c3quantite;

    @FXML
    private TableColumn<Stock, String> c4unite;

    @FXML
    private TableColumn<Stock, String> c5achat;

    @FXML
    private TableColumn<Stock, String> c6vente;

    //private List<Stock> elements;

    private List<Stock> elements = new ArrayList<>();


    public void initialize() {
        c1code.setCellValueFactory(new PropertyValueFactory<>("code"));
        c2nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        c3quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        c4unite.setCellValueFactory(new PropertyValueFactory<>("unite"));
        c5achat.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        c6vente.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        // Ajout du listener d'événements pour détecter les double-clics sur la TableView
        tableView.setRowFactory(tv -> {
            TableRow<Stock> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Stock rowData = row.getItem();
                    // Mettre à jour les zones de texte avec les valeurs de l'élément sélectionné
                    majTextFields(rowData);
                }
            });
            return row;
        });

        loadCsvData("src/main/java/app/elements.csv");
    }

    public void loadCsvData(String filename) {
        String line = null; // Initialisation de line à null
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 6) {
                    String code = data[0];
                    String nom = data[1];
                    int quantite = Integer.parseInt(data[2]);
                    String unite = data[3];
                    String prixAchat = data[4];
                    String prixVente = data[5];
                    Stock element = new Stock(code, nom, quantite, unite, prixAchat, prixVente);
                    tableView.getItems().add(element);
                    elements.add(element);
                } else {
                    System.err.println("La ligne CSV ne contient pas le bon nombre de colonnes : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format numérique dans la ligne CSV : " + line);
        }
    }

    // Méthode pour mettre à jour les zones de texte avec les valeurs de l'élément sélectionné
    private void majTextFields(Stock stock) {
        // Récupérer les valeurs de l'élément sélectionné
        String code = stock.getCode();
        String nom = stock.getNom();
        int quantite = stock.getQuantite();
        String unite = stock.getUnite();
        String prixAchat = stock.getPrixAchat();
        String prixVente = stock.getPrixVente();

        // Mettre à jour les zones de text avec les valeurs récupérées
        codeTextField.setText(code);
        nomTextField.setText(nom);
        quantiteTextField.setText(String.valueOf(quantite));
        uniteTextField.setText(unite);
        prixAchatTextField.setText(prixAchat);
        prixVenteTextField.setText(prixVente);
    }

    @FXML
    private void modifierElement() {
        Stock stockSelection = tableView.getSelectionModel().getSelectedItem();
        if (stockSelection == null) {
            // Aucun élément sélectionné, afficher un message
            System.out.println("Aucun élément sélectionné.");
            return;
        }

        // Vérifier si tous les champs de texte sont vides
        if (nomTextField.getText().isEmpty() &&
                quantiteTextField.getText().isEmpty() &&
                uniteTextField.getText().isEmpty() &&
                prixAchatTextField.getText().isEmpty() &&
                prixVenteTextField.getText().isEmpty())
        {
            System.out.println("Tous les champs sont vides. Veuillez saisir les valeurs à modifier.");
            return;
        }

        // Mettre à jour les valeurs de l'élément sélectionné
        stockSelection.setNom(nomTextField.getText());
        if (!quantiteTextField.getText().isEmpty()) {
            stockSelection.setQuantite(Integer.parseInt(quantiteTextField.getText()));
        }
        stockSelection.setUnite(uniteTextField.getText());
        stockSelection.setPrixAchat(prixAchatTextField.getText());
        stockSelection.setPrixVente(prixVenteTextField.getText());

        // Mettre à jour l'élément dans le TableView
        tableView.refresh();

        // Mettre à jour l'élément dans la liste 'elements'
        int index = elements.indexOf(stockSelection);
        if (index != -1) {
            elements.set(index, stockSelection);
        }

        // Sauvegarder les modifications dans le fichier elements.csv
        sauvegarderElementsCSV();

        // Mettre à jour le fichier prix.csv
        mettreAJourPrixCSV(stockSelection);
    }

    // Méthode pour mettre à jour le fichier prix.csv
    private void mettreAJourPrixCSV(Stock stock) {
        try {
            Path fichierPrix = Paths.get("src/main/java/app/prix.csv");
            List<String> lignes = Files.readAllLines(fichierPrix);

            // Mettre à jour les prix dans le fichier prix.csv
            List<String> nouvellesLignes = new ArrayList<>();
            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (parts[0].equals(stock.getCode())) {
                    // Mettre à jour les prix pour l'élément modifié
                    nouvellesLignes.add(String.format("%s;%s;%s;%s", stock.getCode(), stock.getPrixAchat(), stock.getPrixVente(), stock.getQuantite()));
                } else {
                    nouvellesLignes.add(ligne);
                }
            }

            // Écrire les nouvelles lignes dans le fichier prix.csv
            Files.write(fichierPrix, nouvellesLignes, StandardCharsets.UTF_8);
            System.out.println("Modifications sauvegardées dans le fichier prix.csv.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la mise à jour du fichier prix.csv : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouterElement() {
        // Récupérer les valeurs des champs de texte
        String code = codeTextField.getText().trim();
        String nom = nomTextField.getText().trim();
        String quantiteStr = quantiteTextField.getText().trim();
        String unite = uniteTextField.getText().trim();
        String prixAchat = prixAchatTextField.getText().trim();
        String prixVente = prixVenteTextField.getText().trim();

        // Vérifier que tous les champs sont remplis
        if (code.isEmpty() ||
                nom.isEmpty() ||
                quantiteStr.isEmpty() ||
                unite.isEmpty() ||
                prixAchat.isEmpty() ||
                prixVente.isEmpty())
        {
            // Afficher un message d'erreur si un champ est vide
            System.err.println("Erreur : Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si le code existe déjà dans le fichier CSV
        for (Stock stock : elements) {
            if (stock.getCode().equals(code)) {
                System.err.println("Erreur : Le code existe déjà dans le fichier.");
                return;
            }
        }

        // Convertir la quantité en entier
        int quantite;
        try {
            quantite = Integer.parseInt(quantiteStr);
        } catch (NumberFormatException e) {
            System.err.println("Erreur : La quantité doit être un entier.");
            return;
        }

        // Ajouter le nouvel élément à la liste
        Stock nouvelElement = new Stock(code, nom, quantite, unite, prixAchat, prixVente);
        elements.add(nouvelElement);

        // Ajouter le nouvel élément au fichier elements.csv
        sauvegarderElementsCSV();

        // Ajouter le nouvel élément au fichier prix.csv
        ajouterElementAuPrixCSV(nouvelElement);

        // Effacer les champs de texte après l'ajout
        codeTextField.clear();
        nomTextField.clear();
        quantiteTextField.clear();
        uniteTextField.clear();
        prixAchatTextField.clear();
        prixVenteTextField.clear();

        // Rafraîchir la table view pour afficher le nouvel élément
        tableView.getItems().add(nouvelElement);
    }

    // Méthode pour sauvegarder les modifications dans le fichier elements.csv
    private void sauvegarderElementsCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/app/elements.csv"))) {
            // Écrire l'en-tête
            writer.write("Code;Nom;Quantite;unite;achat;vente");
            writer.newLine();

            // Écrire les lignes mises à jour dans le fichier
            for (Stock stock : elements) {
                writer.write(String.format("%s;%s;%d;%s;%s;%s",
                        stock.getCode(),
                        stock.getNom(),
                        stock.getQuantite(),
                        stock.getUnite(),
                        stock.getPrixAchat(),
                        stock.getPrixVente()));
                writer.newLine();
            }
            System.out.println("Modifications sauvegardées dans le fichier elements.csv.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des modifications dans le fichier elements.csv : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un élément au fichier prix.csv
    private void ajouterElementAuPrixCSV(Stock stock) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/app/prix.csv", true))) {
            // Écrire la ligne du nouvel élément dans le fichier
            writer.write(String.format("%s;%s;%s;%d",
                    stock.getCode(),
                    stock.getPrixAchat(),
                    stock.getPrixVente(),
                    stock.getQuantite()));
            writer.newLine();
            System.out.println("Nouvel élément ajouté au fichier prix.csv.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du nouvel élément au fichier prix.csv : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void supprimerElement() {
        // Vérifier qu'un élément est sélectionné dans la TableView
        Stock element = tableView.getSelectionModel().getSelectedItem();
        if (element == null) {
            System.err.println("Erreur : Aucun élément sélectionné.");
            return;
        }

        // Supprimer l'élément de la liste et de la TableView
        elements.remove(element);
        tableView.getItems().remove(element);

        // Mettre à jour le fichier elements.csv
        Path fichierElements = Paths.get("src/main/java/app/elements.csv");
        try {
            List<String> lignes = Files.readAllLines(fichierElements);
            List<String> nouvellesLignes = new ArrayList<>();

            // Parcourir toutes les lignes du fichier
            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (!parts[0].equals(element.getCode())) {
                    nouvellesLignes.add(ligne);
                }
            }

            // Écrire les nouvelles lignes dans le fichier
            Files.write(fichierElements, nouvellesLignes, StandardCharsets.UTF_8);

            System.out.println("Élément supprimé du fichier elements.csv.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la mise à jour du fichier elements.csv : " + e.getMessage());
            e.printStackTrace();
        }

        // Supprimer l'élément correspondant du fichier prix.csv
        supprimerElementDuPrixCSV(element);
    }

    // Méthode pour supprimer un élément du fichier prix.csv
    private void supprimerElementDuPrixCSV(Stock stock) {
        try {
            // Lire toutes les lignes du fichier prix.csv
            List<String> lignes = Files.readAllLines(Paths.get("src/main/java/app/prix.csv"));

            // Créer un nouveau fichier temporaire pour stocker les lignes sans l'élément supprimé
            File fichierTemp = new File("src/main/java/app/prix_temp.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichierTemp));

            // Parcourir toutes les lignes
            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (!parts[0].equals(stock.getCode())) {
                    // Ecrire la ligne dans le fichier temporaire si le code ne correspond pas à celui de l'élément supprimé
                    writer.write(ligne);
                    writer.newLine();
                }
            }

            // Fermer le writer
            writer.close();

            // Remplacer le fichier prix.csv par le fichier temporaire
            File fichierPrixCsv = new File("src/main/java/app/prix.csv");
            if (fichierPrixCsv.delete()) {
                if (!fichierTemp.renameTo(fichierPrixCsv)) {
                    System.err.println("Erreur : Impossible de renommer le fichier temporaire.");
                }
            } else {
                System.err.println("Erreur : Impossible de supprimer le fichier original.");
            }

            System.out.println("Élément supprimé du fichier prix.csv.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la mise à jour du fichier prix.csv : " + e.getMessage());
            e.printStackTrace();
        }
    }

}


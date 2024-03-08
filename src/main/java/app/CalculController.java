package app;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Map;

public class CalculController {

    @FXML
    private TextFlow indicateurCommande;
    @FXML
    private TextFlow indicateurValeur;

    public void handleCalculButtonAction() {
        calcul();
    }

    public void calcul() {
        ChaineModel chaineModel = ChaineModel.getInstance();
        CommandesModel commandesModel = CommandesModel.getInstance();

        float beneficeTotal = 0.0f;
        int chainesSatisfaites = 0; // Initialiser le compteur de chaînes satisfaites

        for (Chaine chaine : chaineModel.getAllChaine()) {
            int niveauActivite = chaine.getNiveauActivite();
            if (niveauActivite > 0) {
                Map<String, Float> entrees = chaine.getEntrees();
                Map<String, Float> sorties = chaine.getSorties();

                boolean tousStocksSuffisants = true;

                for (Map.Entry<String, Float> entree : entrees.entrySet()) {
                    String codeElement = entree.getKey();
                    Float quantiteDemandee = entree.getValue() * niveauActivite;
                    float quantiteDisponible = commandesModel.getQuantite(codeElement);
                    if (quantiteDisponible < quantiteDemandee) {
                        tousStocksSuffisants = false;
                    }
                }

                if (tousStocksSuffisants) {
                    float beneficeChaine = 0.0f;

                    for (Map.Entry<String, Float> entree : entrees.entrySet()) {
                        String codeElementEntree = entree.getKey();
                        Float quantiteDemandee = entree.getValue() * niveauActivite;
                        Commandes commandeEntree = commandesModel.getCommandes(codeElementEntree);
                        if (commandeEntree != null) {
                            int prixAchat = commandeEntree.getPrixAchat();
                            float coutAchat = prixAchat * quantiteDemandee;
                            beneficeChaine -= coutAchat;
                            commandesModel.setQuantite(codeElementEntree, commandesModel.getQuantite(codeElementEntree) - quantiteDemandee); // Soustraire les stocks utilisés
                        }
                    }

                    for (Map.Entry<String, Float> sortie : sorties.entrySet()) {
                        String codeElementSortie = sortie.getKey();
                        Float quantiteSortieProduite = sortie.getValue() * niveauActivite;
                        Commandes commandeSortie = commandesModel.getCommandes(codeElementSortie);
                        if (commandeSortie != null) {
                            Float prixVente = (float) commandeSortie.getPrixVente();
                            float revenuVente = prixVente * quantiteSortieProduite;
                            beneficeChaine += revenuVente;
                            commandesModel.setQuantite(codeElementSortie, commandesModel.getQuantite(codeElementSortie) + quantiteSortieProduite);
                        }
                    }

                    beneficeTotal += beneficeChaine;
                    chainesSatisfaites++;
                }
            }
        }

        // Calculer le pourcentage de chaînes satisfaites
        int nombreTotalChaines = chaineModel.getAllChaine().size();
        double pourcentageChainesSatisfaites = (double) chainesSatisfaites / nombreTotalChaines * 100;

        // Ajouter le bénéfice total et le pourcentage de chaînes satisfaites dans le TextFlow
        Text textBenefice = new Text("Indicateur de valeur : " + String.format("%.1f", beneficeTotal) + "\n");
        Text textPourcentage = new Text("Indicateur de commande : " + String.format("%.1f", pourcentageChainesSatisfaites) + "%\n");
        indicateurCommande.getChildren().addAll(textPourcentage);
        indicateurValeur.getChildren().addAll(textBenefice);
    }
}

package app;

import java.util.Map;

public class CalculController {

    public void handleCalculButtonAction() {
        calcul();
    }


    public void calcul() {
        ChaineModel chaineModel = ChaineModel.getInstance();
        CommandesModel commandesModel = CommandesModel.getInstance();

        // Récupérer la chaîne de production avec le code "C002"
        Chaine chaine = chaineModel.getChaine("C002");
        if (chaine != null) {
            // Vérifier si le niveau d'activité est supérieur à zéro
            int niveauActivite = chaine.getNiveauActivite();
            if (niveauActivite > 0) {
                // Récupérer les entrées de la chaîne de production
                Map<String, Float> entrees = chaine.getEntrees();

                // Initialiser le flag pour indiquer si le stock est suffisant
                boolean stockSuffisant = true;

                // Parcourir chaque élément et sa quantité nécessaire
                for (Map.Entry<String, Float> entree : entrees.entrySet()) {
                    String codeElement = entree.getKey();
                    Float quantiteDemandee = entree.getValue() * niveauActivite;

                    // Récupérer la quantité disponible dans la commande pour cet élément
                    float quantiteDisponible = commandesModel.getQuantite(codeElement);

                    // Vérifier si la quantité disponible est suffisante pour cet élément
                    if (quantiteDisponible >= quantiteDemandee) {
                        // Déduire la quantité utilisée du stock
                        commandesModel.decrementerStock(codeElement, quantiteDemandee);
                    } else {
                        // Le stock est insuffisant pour cet élément
                        stockSuffisant = false;
                        System.out.println("Attention : Stock insuffisant pour l'élément '" + codeElement + "'.");
                    }
                }

                // Si le stock est suffisant pour tous les éléments en entrée
                if (stockSuffisant) {
                    // Réinitialiser le niveau d'activité à zéro
                    chaine.setNiveauActivite(0);
                    System.out.println("Chaîne de production satisfaite. Niveau d'activité réinitialisé à zéro.");

                    // Mettre à jour les stocks pour refléter la production
                    for (Map.Entry<String, Float> entree : entrees.entrySet()) {
                        String codeElement = entree.getKey();
                        Float quantiteDemandee = entree.getValue() * niveauActivite;
                        // Déduire la quantité utilisée du stock
                        commandesModel.decrementerStock(codeElement, quantiteDemandee);
                    }

                    // Effectuer d'autres opérations si nécessaire
                } else {
                    System.out.println("Stock insuffisant pour au moins un élément en entrée de la chaîne de production.");
                }
            } else {
                // Le niveau d'activité est égal à zéro, ne rien faire
                System.out.println("Le niveau d'activité est égal à zéro, aucun calcul nécessaire.");
            }
        } else {
            System.out.println("Aucune chaîne de production trouvée");
        }
    }
}

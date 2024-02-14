package app;

import java.util.Map;

public class CalculController {

    public void handleCalculButtonAction() {
        verifierDisponibiliteStockEtBenefices();
    }


    public void verifierDisponibiliteStockEtBenefices() {
        ChaineModel chaineModel = ChaineModel.getInstance();
        CommandesModel commandesModel = CommandesModel.getInstance();

        // Récupérer la chaîne de production avec le code "C001"
        Chaine chaine = chaineModel.getChaine("C001");
        if (chaine != null) {
            // Vérifier si le niveau d'activité est supérieur à zéro
            int niveauActivite = chaine.getNiveauActivite();
            if (niveauActivite > 0) {
                // Récupérer les entrées de la chaîne de production
                Map<String, Float> entrees = chaine.getEntrees();

                // Initialiser le flag pour indiquer si le stock est suffisant
                boolean stockSuffisant = true;

                // Initialiser la variable pour calculer les bénéfices
                float benefices = 0;

                // Parcourir chaque élément et sa quantité nécessaire
                for (Map.Entry<String, Float> entree : entrees.entrySet()) {
                    String codeElement = entree.getKey();
                    Float quantiteDemandee = entree.getValue();
                    quantiteDemandee=quantiteDemandee*niveauActivite;

                    // Récupérer la quantité disponible dans la commande pour cet élément
                    float quantiteDisponible = commandesModel.getQuantite(codeElement);

                    // Afficher les informations sur l'élément en cours de traitement
                    System.out.println("Traitement de l'élément : " + codeElement);
                    System.out.println("Quantité demandée : " + quantiteDemandee);
                    System.out.println("Quantité disponible en stock : " + quantiteDisponible);

                    // Vérifier si la quantité disponible est suffisante pour cet élément
                    if (quantiteDisponible >= quantiteDemandee) {
                        // Récupérer l'objet Commandes correspondant à cet élément
                        Commandes commande = commandesModel.getCommandes(codeElement);
                        if (commande != null) {
                            // Calculer le coût des éléments en entrée pour cet élément
                            float coutEntree = commande.getPrixAchat() * quantiteDemandee;

                            // Ajouter le coût de cet élément au coût total des éléments en entrée
                            benefices -= coutEntree;

                            // Afficher le coût de l'élément en entrée
                            System.out.println("Coût d'entrée pour l'élément : " + coutEntree);
                        }
                    } else {
                        // Le stock est insuffisant pour cet élément
                        stockSuffisant = false;
                        System.out.println("Attention : Stock insuffisant pour l'élément '" + codeElement + "'.");
                    }
                }

                // Récupérer le code de l'élément de sortie
                String codeElementSortie = chaine.getSorties().keySet().iterator().next();
                Commandes commandeSortie = commandesModel.getCommandes(codeElementSortie);

                // Vérifier si le stock est suffisant pour tous les éléments en entrée et si l'élément de sortie est disponible
                if (stockSuffisant && commandeSortie != null) {
                    // Calculer le prix d'achat total des éléments en entrée
                    // Cela peut nécessiter des ajustements en fonction de la structure de vos données
                    // Ici, nous supposons que chaque élément en entrée a le même prix d'achat
                    float prixAchatTotalEntrees = entrees.size() * commandeSortie.getPrixAchat() * niveauActivite;

                    // Calculer les bénéfices en soustrayant le prix de vente de l'élément de sortie du prix d'achat total des éléments en entrée
                    benefices += commandeSortie.getPrixVente() - prixAchatTotalEntrees;

                    // Afficher le prix d'achat total des éléments en entrée
                    System.out.println("Prix d'achat total des éléments en entrée : " + prixAchatTotalEntrees);

                    // Afficher les bénéfices totaux
                    System.out.println("Bénéfices totaux : " + benefices);
                } else {
                    // Le stock est insuffisant pour au moins un élément en entrée ou l'élément de sortie n'est pas disponible
                    System.out.println("Stock insuffisant pour au moins un élément en entrée de la chaîne de production ou l'élément de sortie n'est pas disponible.");
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

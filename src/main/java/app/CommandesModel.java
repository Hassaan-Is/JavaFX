package app;

import java.util.HashMap;
import java.util.Map;

public class CommandesModel {
    private Map<String, Float> quantiteMap;
    private Map<String, Commandes> commandeMap;

    private static CommandesModel instance;

    private CommandesModel() {
        quantiteMap = new HashMap<>();
        commandeMap = new HashMap<>(); // Initialise la Map pour stocker les instances de Commandes
    }

    public static synchronized CommandesModel getInstance() {
        if (instance == null) {
            instance = new CommandesModel();
        }
        return instance;
    }



    // Méthode pour soustraire les stocks utilisés
    public void soustraireStock(String codeElement, float quantiteUtilisee) {
        // Récupérer la quantité actuelle en stock
        Float quantiteActuelle = quantiteMap.getOrDefault(codeElement, 0f); // Conversion explicite

        // Mettre à jour la quantité en stock en soustrayant la quantité utilisée
        quantiteMap.put(codeElement, quantiteActuelle - quantiteUtilisee);
    }


    public float getQuantite(String codeUnique) { // Modification du type de retour de int à float
        return quantiteMap.getOrDefault(codeUnique, 0f); // Modification de 0 à 0f pour indiquer un float
    }

    public void setQuantite(String codeUnique, float quantite) { // Modification du type de données de int à float
        quantiteMap.put(codeUnique, quantite);
    }

    public Commandes getCommandes(String code) {
        return commandeMap.get(code);
    }

    public void setCommandes(String code, Commandes commandes) {
        commandeMap.put(code, commandes);
    }
}

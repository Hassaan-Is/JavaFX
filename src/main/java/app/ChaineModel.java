package app;

import java.util.HashMap;
import java.util.Map;

public class ChaineModel {
    private Map<String, Integer> niveauActiviteMap;
    private Map<String, Chaine> chaineMap;

    private static ChaineModel instance;

    private ChaineModel() {
        niveauActiviteMap = new HashMap<>();
        chaineMap = new HashMap<>(); // Initialiser la Map pour stocker les instances de Chaine
    }

    public static synchronized ChaineModel getInstance() {
        if (instance == null) {
            instance = new ChaineModel();
        }
        return instance;
    }
    public Map<String, Chaine> getChaineMap() {
        return chaineMap;
    }


    // Getter pour récupérer une instance de Chaine à partir du codeUnique
    public Chaine getChaine(String codeUnique) {
        return chaineMap.get(codeUnique);
    }

    // Setter pour ajouter ou mettre à jour une instance de Chaine dans la Map
    public void setChaine(String codeUnique, Chaine chaine) {
        chaineMap.put(codeUnique, chaine);
    }

    // Autres getters et setters pour niveauActiviteMap
    public int getNiveauActivite(String codeUnique) {
        return niveauActiviteMap.getOrDefault(codeUnique, 0);
    }

    public void setNiveauActivite(String codeUnique, int niveauActivite) {
        niveauActiviteMap.put(codeUnique, niveauActivite);
    }
}

package app;

import java.util.HashMap;
import java.util.Map;

public class ChaineModel {
    private Map<String, Integer> niveauActiviteMap;

    private static ChaineModel instance;

    private ChaineModel() {
        niveauActiviteMap = new HashMap<>();
    }

    public static synchronized ChaineModel getInstance() {
        if (instance == null) {
            instance = new ChaineModel();
        }
        return instance;
    }

    public int getNiveauActivite(String codeUnique) {
        return niveauActiviteMap.getOrDefault(codeUnique, 0);
    }

    public void setNiveauActivite(String codeUnique, int niveauActivite) {
        niveauActiviteMap.put(codeUnique, niveauActivite);
    }

    public int getQuantite(String codeUnique) {
        return getNiveauActivite(codeUnique);
    }

}

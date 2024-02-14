package app;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Integer> niveauActiviteMap;

    private static Model instance;

    private Model() {
        niveauActiviteMap = new HashMap<>();
    }

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
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

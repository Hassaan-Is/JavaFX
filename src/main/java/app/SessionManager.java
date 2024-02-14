package app;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, Integer> niveauActiviteMap = new HashMap<>();
    private static final Map<String, Integer> quantiteMap = new HashMap<>();

    public static void setNiveauActivite(String code, int niveauActivite) {
        niveauActiviteMap.put(code, niveauActivite);
    }

    public static int getNiveauActivite(String code) {
        return niveauActiviteMap.getOrDefault(code, 0);
    }

    public static int getQuantite(String code) {
        return quantiteMap.getOrDefault(code, 0);
    }
    public static void setQuantite(String code, int quantite) {
        quantiteMap.put(code, quantite);
    }

}

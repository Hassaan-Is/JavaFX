package app;

import java.util.HashMap;
import java.util.Map;

public class CommandeModel {
    private Map<String, Integer> quantiteMap;
    private static CommandeModel instance;

    CommandeModel() {
        quantiteMap = new HashMap<>();
    }

    public static synchronized CommandeModel getInstance() {
        if (instance == null) {
            instance = new CommandeModel();
        }
        return instance;
    }

    public int getQuantite(String codeUnique) {
        return quantiteMap.getOrDefault(codeUnique, 0);
    }

    public void setQuantite(String codeUnique, int quantite) {
        quantiteMap.put(codeUnique, quantite);
    }
}

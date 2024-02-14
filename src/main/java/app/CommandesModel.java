package app;

import java.util.HashMap;
import java.util.Map;

public class CommandesModel {
    private Map<String, Integer> quantiteMap;
    private static CommandesModel instance;

    private CommandesModel() {
        quantiteMap = new HashMap<>();
    }

    public static synchronized CommandesModel getInstance() {
        if (instance == null) {
            instance = new CommandesModel();
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

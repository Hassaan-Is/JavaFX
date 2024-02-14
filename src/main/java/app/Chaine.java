package app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Chaine {
    private String codeUnique;
    private String nom;
    private Integer niveauActivite;
    private Map<String, Float> entrees;
    private Map<String, Float> sorties;

    public Chaine(String codeUnique, String nom, Map<String, Float> entrees, Map<String, Float> sorties, Integer niveauActivite) {
        this.codeUnique = codeUnique;
        this.nom = nom;
        this.entrees = entrees;
        this.sorties = sorties;
        this.niveauActivite = niveauActivite;
    }


    public void ajouterEntree(String element, Float quantite) {
        this.entrees.put(element, quantite);
    }

    public void ajouterSortie(String element, Float quantite) {
        this.sorties.put(element, quantite);
    }

    // Getters et Setters
    public String getCodeUnique() {
        return codeUnique;
    }

    public void setCodeUnique(String codeUnique) {
        this.codeUnique = codeUnique;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public Integer getNiveauActivite() {
        return niveauActivite;
    }
    public void setNiveauActivite(Integer niveauActivite) {
        this.niveauActivite = niveauActivite;
    }


    public Map<String, Float> getEntrees() {
        return entrees;
    }

    public void setEntrees(Map<String, Float> entrees) {
        this.entrees = entrees;
    }

    public Map<String, Float> getSorties() {
        return sorties;
    }
    public void setSorties(Map<String, Float> sorties) {
        this.sorties = sorties;
    }


    public Set<String> getElementSortie() {
        return sorties.keySet();
    }
    public Set<Float> getQuantiteSortie() {
        return new HashSet<>(sorties.values());
    }

    public Set<String> getElementsEntree() {
        return entrees.keySet();
    }
    public Set<String> setElementsEntree() {
        return entrees.keySet();
    }

    public Set<Float> getQuantiteEntree() {
        return new HashSet<>(entrees.values());
    }





    // Fonction pour récupérer uniquement les clés des sorties (les éléments)
    public Set<String> getElementsSortie() {
        return sorties.keySet();
    }

    // Méthode pour convertir les données de la HashMap en une chaîne de caractères formatée
    public String formatHashMap(Map<String, Float> hashMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Float> entry : hashMap.entrySet()) {
            builder.append("(").append(entry.getKey()).append(", ").append(entry.getValue()).append("), ");
        }
        // Supprimer la virgule et l'espace supplémentaires à la fin
        if (builder.length() > 2) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }
}

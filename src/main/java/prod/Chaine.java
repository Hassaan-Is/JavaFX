package prod;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Chaine {
    private final StringProperty code;
    private final StringProperty nom;
    private final StringProperty elementsEntree;
    private final StringProperty elementsSortie;
    private final StringProperty niveauActivite;

    public Chaine(String code, String nom, String elementsEntree, String elementsSortie, int niveauActivite) {
        this.code = new SimpleStringProperty(code);
        this.nom = new SimpleStringProperty(nom);
        this.elementsEntree = new SimpleStringProperty(elementsEntree);
        this.elementsSortie = new SimpleStringProperty(elementsSortie);
        this.niveauActivite = new SimpleStringProperty(String.valueOf(niveauActivite));
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getElementsEntree() {
        return elementsEntree.get();
    }

    public StringProperty elementsEntreeProperty() {
        return elementsEntree;
    }

    public void setElementsEntree(String elementsEntree) {
        this.elementsEntree.set(elementsEntree);
    }

    public String getElementsSortie() {
        return elementsSortie.get();
    }

    public StringProperty elementsSortieProperty() {
        return elementsSortie;
    }

    public void setElementsSortie(String elementsSortie) {
        this.elementsSortie.set(elementsSortie);
    }

    public int getNiveauActivite() {
        return Integer.parseInt(niveauActivite.get());
    }

    public StringProperty niveauActiviteProperty() {
        return niveauActivite;
    }

    public void setNiveauActivite(int niveauActivite) {
        this.niveauActivite.set(String.valueOf(niveauActivite));
    }
}

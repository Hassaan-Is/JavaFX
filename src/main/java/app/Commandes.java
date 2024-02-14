package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Commandes {
    private final SimpleStringProperty code;
    private final SimpleIntegerProperty prixAchat;
    private final SimpleIntegerProperty prixVente;
    private final SimpleIntegerProperty quantite;

    public Commandes(String code, int prixAchat, int prixVente, int quantite) {
        this.code = new SimpleStringProperty(code);
        this.prixAchat = new SimpleIntegerProperty(prixAchat);
        this.prixVente = new SimpleIntegerProperty(prixVente);
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    // Getters and setters
    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public int getPrixAchat() {
        return prixAchat.get();
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat.set(prixAchat);
    }

    public int getPrixVente() {
        return prixVente.get();
    }

    public void setPrixVente(int prixVente) {
        this.prixVente.set(prixVente);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    // Property accessors
    public SimpleStringProperty codeProperty() {
        return code;
    }

    public SimpleIntegerProperty prixAchatProperty() {
        return prixAchat;
    }

    public SimpleIntegerProperty prixVenteProperty() {
        return prixVente;
    }

    public SimpleIntegerProperty quantiteProperty() {
        return quantite;
    }
}

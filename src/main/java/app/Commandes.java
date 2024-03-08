package app;

import javafx.beans.property.SimpleFloatProperty; // Import de SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Commandes {
    private final SimpleStringProperty code;
    private final SimpleIntegerProperty prixAchat;
    private final SimpleIntegerProperty prixVente;
    private final SimpleFloatProperty quantite; // Modification de SimpleIntegerProperty à SimpleFloatProperty

    public Commandes(String code, int prixAchat, int prixVente, float quantite) { // Modification du type de données de quantite en float
        this.code = new SimpleStringProperty(code);
        this.prixAchat = new SimpleIntegerProperty(prixAchat);
        this.prixVente = new SimpleIntegerProperty(prixVente);
        this.quantite = new SimpleFloatProperty(quantite); // Modification de SimpleIntegerProperty à SimpleFloatProperty
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

    public float getQuantite() { // Modification du type de retour en float
        return quantite.get();
    }

    public void setQuantite(float quantite) { // Modification du type de données en float
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

    public SimpleFloatProperty quantiteProperty() { // Modification de SimpleIntegerProperty à SimpleFloatProperty
        return quantite;
    }
}

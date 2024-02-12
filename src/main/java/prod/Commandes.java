package prod;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Commandes {
    private final StringProperty code;
    private final IntegerProperty prixAchat;
    private final IntegerProperty prixVente;
    private final IntegerProperty quantite;

    public Commandes(String code, Integer prixAchat, Integer prixVente, Integer quantite) {
        this.code = new SimpleStringProperty(code);
        this.prixAchat = new SimpleIntegerProperty(prixAchat);
        this.prixVente = new SimpleIntegerProperty(prixVente);
        this.quantite = new SimpleIntegerProperty(quantite);
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

    public int getPrixAchat() {
        return prixAchat.get();
    }

    public IntegerProperty prixAchatProperty() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat.set(prixAchat);
    }

    public int getPrixVente() {
        return prixVente.get();
    }

    public IntegerProperty prixVenteProperty() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente.set(prixVente);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }
}

package app;

public class Stock {
    private String code;
    private String nom;
    private int quantite;
    private String unite;

    private String prixAchat;
    private String prixVente;

    public Stock(String code, String nom, int quantite, String unite, String prixAchat, String prixVente) {
        this.code = code;
        this.nom = nom;
        this.quantite = quantite;
        this.unite = unite;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }

    public String getCode() {
        return this.code;
    }

    public String getNom() {
        return this.nom;
    }

    public int getQuantite() {
        return this.quantite;
    }

    public String getUnite() {
        return this.unite;
    }

    public String getPrixAchat() {
        return this.prixAchat;
    }

    public String getPrixVente() {
        return this.prixVente;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setPrixAchat(String prixAchat) {
        this.prixAchat = prixAchat;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }
}

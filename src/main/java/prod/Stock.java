package prod;
public class Stock {
    private String code;
    private String nom;
    private int quantite;
    private String unite;

    private int prixAchat;
    private int prixVente;

    public Stock(String code, String nom, int quantite, String unite, int prixAchat, int prixVente) {
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

    public int getPrixAchat() {
        return this.prixAchat;
    }

    public int getPrixVente() {
        return this.prixVente;
    }
}
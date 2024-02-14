package prod;

public class Element {
    private String code;
    private String nom;
    private int quantite;
    private String unite;

    public Element(String code, String nom, int quantite, String unite) {
        this.code = code;
        this.nom = nom;
        this.quantite = quantite;
        this.unite = unite;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getUnite() {
        return unite;
    }

    @Override
    public String toString(){
        return this.code + ", " + this.nom + ", " + this.quantite + this.unite;
    }
}


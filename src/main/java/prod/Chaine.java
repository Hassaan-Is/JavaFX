package prod;

public class Chaine {
    private String code;
    private String nom;
    private String elementsEntree;
    private String elementsSortie;
    private Integer niveauActivite;

    public Chaine(String code, String nom, String elementsEntree, String elementsSortie, Integer niveauActivite) {
        this.code = code;
        this.nom = nom;
        this.elementsEntree = elementsEntree;
        this.elementsSortie = elementsSortie;
        this.niveauActivite = niveauActivite;
    }

    public String getCode(){
        return this.code;
    }

    public String getNom(){
        return this.nom;
    }

    public String getElementsEntree() {
        return elementsEntree;
    }

    public String getElementsSortie() {
        return elementsSortie;
    }

    public Integer getNiveauActivite(){
        return this.niveauActivite;
    }
}

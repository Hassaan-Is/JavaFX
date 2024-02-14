package app;

public class SharedDataModel {
    private static int niveauActivite = 0;

    public static int getNiveauActivite() {
        return niveauActivite;
    }

    public static void setNiveauActivite(int newNiveauActivite) {
        niveauActivite = newNiveauActivite;
    }
}

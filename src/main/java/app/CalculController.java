package app;

public class CalculController {

    public void handleCalculButtonAction() {
        int niveau1 = ChaineModel.getInstance().getNiveauActivite("C001");
        System.out.println("Niveau : " + niveau1);
        int niveau2 = ChaineModel.getInstance().getNiveauActivite("C002");
        System.out.println("Niveau : " + niveau2);
        int niveau3 = ChaineModel.getInstance().getNiveauActivite("C003");
        System.out.println("Niveau : " + niveau3);
        int quantite = CommandesModel.getInstance().getQuantite("E002");
        System.out.println("Quantite : " + quantite);
    }
}

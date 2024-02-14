package app;

public class  CalculController{

    public void handleCalculButtonAction(){
       int niveau1=  Model.getInstance().getNiveauActivite("C001");
        System.out.println("Niveau : "+niveau1);
        int niveau2=  Model.getInstance().getNiveauActivite("C002");
        System.out.println("Niveau : "+niveau2);
        int niveau3=  Model.getInstance().getNiveauActivite("C003");
        System.out.println("Niveau : "+niveau3);
        int quantite=  CommandeModel.getInstance().getQuantite("E002");
        System.out.println("Quantite : "+quantite);
    }
}
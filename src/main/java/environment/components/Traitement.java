package environment.components;

/**
 * Modele pour les traitements des virus.
 */
public class Traitement{

  private String nom;
  private int invProbaSoin;
  private int quantite;

  /**
   * Constructeur d'un traitement.
   * @param nom, nom du traitement.
   * @param p, probabilité de l'efficacité du traitement.
   * @param q, quantité de traitements disponibles.
   */
  public Traitement(String nom,int p,int q){
    this.nom = nom;
    this.invProbaSoin = p;
    this.quantite = q;

  }

  /**
   * Obtient la valeur de la propriété "invProbaSoin".
   */
  public int getInvProbaSoin(){
    return invProbaSoin;
  }

  /**
   * Obtient la valeur de la propriété "quantite".
   */
  public int getQuantite(){
    return quantite;
  }

  /**
   * Obtient la valeur de la propriété "nom".
   */
  public String getNom(){
    return nom;
  }

  
  /**
   * Modélise l'utilisation d'une dose du traitement.
   * @return true si le traitement a été utiliser, false sinon ( false implique fin des stock ).
   */
  public boolean utiliser(){
    if (quantite ==0){
      return false;
    }
    quantite -= 1;
    return true;
  }

}

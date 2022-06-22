package environment.components;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Modele pour les virus.
 */
public class Virus{

  private int invProbaCont;
  private int nbJours;
  private int invProbaSympt;
  private int dureeImmu;

  //permet de connaitre la proba de contamination (l'inverse)
  public Virus(int proba,int nbJours,int invProbaSympt,int dureeImmunite){
    this.invProbaCont = proba;
    this.nbJours = nbJours;
    this.invProbaSympt = invProbaSympt;
    this.dureeImmu = dureeImmunite;
  }

  /**
   * Obtient la valeur de la propriété "nbJours".
   */
  public int getNbJours(){
    return nbJours;
  }

  /**
   * Obtient la valeur de la propriété "invProbaSympt".
   */
  public int getInvProbaSympt(){
    return invProbaSympt;
  }

  /**
   * Obtient la valeur de la propriété "dureeImmu".
   */
  public int getDureeImmunisee(){
    return dureeImmu;
  }

  /**
   * Gère la contamination par un virus.
   * @param p la personne contaminer par this.
   * @param coeff coefficiant de contamination, plus coef est grand moins il y'a de chance d'être contaminé.
   * @return true si la contamination a eu lieu, false sinon.
   */
  public boolean contaminer(People p,int coeff){
    if (p.getContamine() || p.getImmunise() || !p.getEstVivant()) return false;
    Random r = new Random();
    int tmp = r.nextInt()%(invProbaCont*coeff);
    if (tmp == 0){
      p.setContamine(true);
      p.setColor(Color.YELLOW);
      return true;
    }
    else return false;
  }

}

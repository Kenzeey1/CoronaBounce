package environment.components;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Modele d'hopital pour l'environement de simulation.
 */
public class Hopital{

  private int nbLitsRea =1;
  private int nbLitsSimples =2;

  private int[] hopX ={700,670,590};
  private int[] hopY ={450,490,480};
  private double[] pvx = {0,0,0};
  private double[] pvy = {0,0,0};
  private ArrayList<Traitement> stock = new ArrayList<>();

  public Hopital(){
    stock.add(new Traitement("Chloroquine",220,1000));
    stock.add(new Traitement("Ivermectine",350,500));
  }
/**
* @param ind permet d'obtenir le traitement d'indice ind dans les stocks
*/
  public Traitement getStock(int ind){
    if (ind < stock.size()){
      return stock.get(ind);
    }
    return null;
  }
/**
* L'un des trois lits est réservé aux personnes en état critique.
* @param p la personne que l'on veut mettre à l'Hopital
* @return le premier lit disponible
*/
  private int premierLitLibre(People p){
    //On renvoie -1 si il n'y a plus de lit disponible
    if (p.getEtatCritique()){
      if (pvx[2] == 0) return 2;
    }
    for (int i=0; i < 2;i++){
      if (pvx[i] == 0){
        return i;
      }
    }
    return -1;
  }
  /**
  * On regarde si il y a un lit disponible et dans ce cas on déplace la personne à l'hopital tout en mettant à jour ses informations ainsi que celles de l'hopital
  * @param p la personne que l'on déplace à l'Hopital
  */
  public void deplacerHop(People p){
    int LitDispo = this.premierLitLibre(p);
    if (LitDispo != -1){
      p.setInHospital(true);
      pvx[LitDispo] = p.getDx();
      pvy[LitDispo] = p.getDy();
      p.setNumeroLit(LitDispo);
      p.setDx(0);
      p.setDy(0);
      p.setCenterX(hopX[LitDispo]);
      p.setCenterY(hopY[LitDispo]);
    }
  }
  /**
  * On fait sortir la personne de l'hopital, en differenciant bien le cas ou cette personne est confinee ou non
  * On met ensuite à jour les données de l'hopital.
  *@param p la personne que l'on fait sortir de l'Hopital
  */
  public void sortir(People p){
    p.setInHospital(false);
    int num = p.getNumLit();
    if (p.getConfine()){
      p.setCenterX(p.getLockDownPosX());
      p.setCenterY(p.getLockDownPosY());
    }
    else{
      p.setCenterX(p.getInitialCenterX());
      p.setCenterY(p.getInitialCenterY());
    }
    p.setDx(pvx[num]);
    p.setDy(pvy[num]);
    pvx[num] = 0;
    pvy[num] = 0;
  }
  /**
  * Si la personne est en état critique, on la soigne avec de l'ivermectine,sinon avec de l'hydroxichloroquine.
  * Dans le cas ou il n'a plus de traitement, on a une probabilité beaucoup plus faible de soigner.
  * Si la personne est en état critique, elle a aussi une probabilité de mourir.
  * Dans le cas où elle est soignée, on la fait sortir de l'hopital et la personne devient immunisée.
  * @param p : La personne que l'on veut soigner
  * @return  vrai si la personne a été soignée
  */
  public boolean soigner(People p){
    Traitement t;
    if (p.getEtatCritique()) t = this.getStock(1);
    else t = this.getStock(0);

    Random r = new Random();
    int invProba;
    if (t.utiliser()){
      invProba = t.getInvProbaSoin();
    }
    else{
      invProba = 1500;
    }
    int tmp = r.nextInt()%invProba;
    if (tmp == 0){
      this.sortir(p);
      p.setSymptomatique(false);
      p.setContamine(false);
      p.setImmunise(true);
      p.setColor(Color.GREEN);
      return true;
    }
    else if (p.getEtatCritique()){
      tmp = r.nextInt()%300;
      if (tmp == 0){
        this.sortir(p);
        p.mourir();
    }
  }
  return false;
  }
}

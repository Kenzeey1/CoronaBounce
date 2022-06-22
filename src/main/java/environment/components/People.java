package environment.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Classe modele et controlleur d'une personne.
 */
public class People extends Circle{

    private double dx;
    private double dy;
    private int age;
    private boolean speedReduced = false;

    private final int lockDownPosX;
    private final int lockDownPosY;

    private final double initialCenterX;
    private final double initialCenterY;

    private int numeroLit = 0;

    private Color color;

    private Environement env;

    private boolean rebond = true;
    private boolean confine = false;
    private boolean travelling = false;

    public boolean contamine;
    private int nbJoursContamines = 0;
    private int nbJoursImmunises = 0;
    private boolean immunise;
    private boolean symptomatique;
    private int coeffConta = 2;
    private boolean inHospital;
    private boolean etatCritique = false;
    private boolean vivant = true;
    private static int nbrDeath = 0;

    /**
     * constructeur d'un people.
     * @param centerX, position initial en x. ( amener à changer ).
     * @param centerY, position initial en y.(amener à changer ).
     * @param radius, rayon d'un people.
     * @param dx, vitesse initial en x.
     * @param dy, vitesse initial en y.
     * @param contamine, vérifie si la personne est malade ou pas.
     * @param color, définie la couleur d'une personne selon son état de santé.
     * @param lockDownPosX, position de confinement en x.
     * @param lockDownPosY, position de confinement en y.
     * @param e, environement dans le quel le "people" va évoluer.
     * @param age, age du people.
     */
    public People(Double centerX, Double centerY,int radius, int dx, int dy, boolean contamine, Color color, int lockDownPosX, int lockDownPosY,Environement e,int age){
        super(centerX,centerY,radius);
        this.initialCenterX = centerX;
        this.initialCenterY = centerY;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.contamine = contamine;
        this.setFill(color);
        this.lockDownPosX = lockDownPosX;
        this.lockDownPosY = lockDownPosY;
        this.env = e;
        this.age = age;
        this.setOnMouseClicked( t -> {
            if ( this.vivant ) {
                if ( !this.confine ){
                    this.setCenterX( this.lockDownPosX );
                    this.setCenterY( this.lockDownPosY );
                    this.env.getUnconfinedPopulation().remove(this);
                    this.env.getConfinedPopulation().add(this);
                    this.confine = true;
                } else {
                    this.setCenterX( this.initialCenterX );
                    this.setCenterY( this.initialCenterY );
                    this.env.getConfinedPopulation().remove(this);
                    this.env.getUnconfinedPopulation().add(this);
                    this.confine = false;
                }
            }
        });
    }

    /**
     * Une personne asymptomatique aura deux fois moins de chances de contaminer qu'une personne symptomatique
     * @return 1 si le "People" est symptomatique, 2 sinon.
     */
    public int getCoeff() {
        if (this.symptomatique) return 1;
        else return 2;
    }

    /**
     * Obtient la valeur de la propriété "vivant".
     */
    public boolean getEstVivant(){
       return vivant;
     }

    /**
     * Définit la valeur de la propriété "vivant".
     */
    public void setEstVivant(boolean b){
       vivant = b;
     }

    /**
     * deuxieme constructeur de la classe people.
     * @param p, "People" dont on copie tout les attribut pour en créer un nouveau.
     */
    public People(People p) {
        super(p.getCenterX(),p.getCenterY(),p.getRadius());
        this.initialCenterX = p.initialCenterX;
        this.initialCenterY = p.initialCenterY;
        this.dx = p.dx;
        this.dy = p.dy;
        this.color = p.color;
        this.contamine = p.contamine;
        this.setFill(color);
        this.lockDownPosX = p.lockDownPosX;
        this.lockDownPosY = p.lockDownPosY;
    }

    /**
     * Obtient la valeur de la propriété "numeroList".
     */
    public int getNumLit(){
        return this.numeroLit;
    }

    /**
     * Définit la valeur de la propriété "numeroList".
     */
    public void setNumeroLit(int x){
        this.numeroLit = x;
    }

    /**
     * Obtient la valeur de la propriété "initialCenterX".
     */
    public double getInitialCenterX(){
        return this.initialCenterX;
    }

    /**
     * Obtient la valeur de la propriété "initialCenterY".
     */
    public double getInitialCenterY(){
        return this.initialCenterY;
    }

    /**
     * Obtient la valeur de la propriété "lockDownPosX".
     */
    public int getLockDownPosX(){
        return this.lockDownPosX;
    }

    /**
     * Obtient la valeur de la propriété "lockDownPosY".
     */
    public int getLockDownPosY(){
        return this.lockDownPosY;
    }

    /**
     * Obtient la valeur de la propriété "confine".
     */
    public boolean getConfine(){
        return this.confine;
    }

    /**
     * Définit la valeur de la propriété "numeroList".
     */
    public void setConfine(boolean b) {
        this.confine = b;
    }

    /**
     * Obtient la valeur de la propriété "symptomatique".
     */
    public boolean getSymptomatique () {
        return symptomatique;
    }

    /**
     * Définit la valeur de la propriété "symptomatique".
     */
    public void setSymptomatique ( boolean b){
        this.symptomatique = b;
    }

    /**
     * Obtient la valeur de la propriété "dx".
     */
    public double getDx () {
        return this.dx;
    }

    /**
     * Obtient la valeur de la propriété "contamine".
     */
    public boolean getContamine () {
        return this.contamine;
    }

    /**
     * Obtient le numero de la zone de confinement à la quel l'individu ( le "People") est assigné.
     */
    public int getZoneLockdown(){
        if ( this.lockDownPosX <= 100 ) return 1;
        else if ( this.lockDownPosX <= 200 ) return 2;
        else if ( this.lockDownPosX <= 300 ) return 3;
        else return 0;
    }

    /**
     * Définit la valeur de la propriété "contamine".
     */
    public void setContamine ( boolean b){
        contamine = b;
    }

    /**
     * Obtient la valeur de la propriété "dy".
     */
    public double getDy () {
        return this.dy;
    }

    /**
     * Définit la valeur de la propriété "dx".
     */
    public void setDx ( double dx){
        this.dx = dx;
    }

    /**
     * Définit la valeur de la propriété "dy".
     */
    public void setDy ( double dy){
        this.dy = dy;
    }

    /**
     * Définit la valeur de la propriété "color", et change la couleur de l'individu ( du "People" ).
     */
    public void setColor (Color color){
        this.color = color;
        this.setFill(color);
    }

    /**
     * Obtient la valeur de la propriété "rebond".
     */
    public boolean getRebond () {
        return this.rebond;
    }

    /**
     * Définit la valeur de la propriété "rebond".
     */
    public void setRebond ( boolean b){
        this.rebond = b;
    }

    /**
     * Obtient la valeur de la propriété "immunise".
     */
    public boolean getImmunise () {
        return immunise;
    }

    /**
     * Définit la valeur de la propriété "immunise".
     */
    public void setImmunise ( boolean b){
        immunise = b;
    }

    /**
     * Obtient la valeur de la propriété "travelling".
     */
    public boolean getTravelling(){
        return this.travelling;
    }

    /**
     * Définit la valeur de la propriété "travelling".
     */
    public void setTravelling(boolean travelling){
        this.travelling = travelling;
    }

    /**
     * Obtient la valeur de la propriété "inHospital".
     */
    public boolean getInHospital(){
        return inHospital;
    }

    /**
     * Définit la valeur de la propriété "inHospital".
     */
    public void setInHospital(boolean b){
        inHospital = b;
    }

    /**
     * Obtient la valeur de la propriété "contamine".
     */
    public boolean getEtatCritique(){
      return etatCritique;
    }

    /**
     * Définit la valeur de la propriété "etatCritique".
     */
    public void setEtatCritique(boolean b){
      etatCritique = b;
    }


    /**
     * Gère les rebond entre "this" et un obstacle de type Rectangle.
     * @param rec, objet de la classe Rectangle avec le quel "this" a une collision.
     */
    public void rebond_hor_ver (Rectangle rec){
        if (getRebond()) {
            if ((getCenterY() >= rec.getLayoutY()) && (getCenterY() <= rec.getLayoutY()+rec.getHeight())) {
                setDx(-getDx());
            } else if((getCenterY()<= rec.getLayoutY()) && (getCenterX() <= rec.getLayoutX() + rec.getWidth())){
                setDy(-getDy());
            } else if ((getCenterY() >= rec.getLayoutY()) && (getCenterX() <= rec.getLayoutX() + rec.getWidth())){
                setDy(-getDy());
            }
        }
        People p = new People(this);
        p.setCenterX(p.getCenterX() + p.getDx());
        p.setCenterY(p.getCenterY() + p.getDy());
        this.setRebond(!p.getBoundsInParent().intersects(rec.getBoundsInParent()));
    }

    /**
     * Gère les rebond entre "this" et un obstacle de type Cercle.
     * @param c, objet de la classe Cercle avec le quel "this" a une collision.
     */
    public void rebond_cercle(Circle c){
        if (getRebond()) {
            if ((getCenterY() >= c.getLayoutY()) && (getCenterY() <= c.getLayoutY() + c.getRadius())) {
                setDx(-getDx());
            } else if((getCenterY() <= c.getLayoutY()) && (getCenterX() <= c.getLayoutX() + c.getRadius())){
                setDy(-getDy());
            } else if ((getCenterY() >= c.getLayoutY()) && (getCenterX() <= c.getLayoutX() + c.getRadius())){
                setDy(-getDy());
            }
        }
        People p = new People(this);
        p.setCenterX(p.getCenterX() + p.getDx());
        p.setCenterY(p.getCenterY() + p.getDy());
        this.setRebond(!p.getBoundsInParent().intersects(c.getBoundsInParent()));
    }

    /**
     * Gère les rebond entre "this" et un obstacle de type Ellipse.
     * @param e, objet de la classe Rectangle avec le quel "this" a une collision.
     */
    public void rebond_ellipse(Ellipse e){
        if (getRebond()) {
            if ((getCenterY() >= e.getLayoutY()) && (getCenterY() <= e.getLayoutY() + e.getRadiusY())) {
                setDx(-getDx());
            } else if((getCenterY() <= e.getLayoutY()) && (getCenterX() <= e.getLayoutX() + e.getRadiusX())){
                setDy(-getDy());
            } else if ((getCenterY() >= e.getLayoutY()) && (getCenterX() <= e.getLayoutX() + e.getRadiusX())){
                setDy(-getDy());
            }
        }
        People p = new People(this);
        p.setCenterX(p.getCenterX() + p.getDx());
        p.setCenterY(p.getCenterY() + p.getDy());
        this.setRebond(!p.getBoundsInParent().intersects(e.getBoundsInParent()));
    }

    /**
     * Calcul la distance entre deux individu ( "People" ) si ils appartiennent au meme environement.
     * @return La distance entre "this" et p si ils appartiennent au même environement sinon retourne -1.
     */
    public double distance (People p){
        if ( this.env != p.env ) return -1;
        double deltaX = this.getCenterX() - p.getCenterX();
        double deltaY = this.getCenterY() - p.getCenterY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /** deux individu ( "People" ) si ils appartiennent au meme environement.
     * Obtient si il y'a un contact entre
     * @return, true si il y'a un contact entre "this" et p et qu'ils appartiennent au même environement sinon retourne false.
     */
    public boolean contact (People p){
        if ( this.env != p.env ) return false;
        return this.distance(p) <= 2 * p.getRadius();
    }

    /**
     * Gère les rebond et la transmission du virus entre deux individu ( "People" ).
     */
    public void rebond_contact (People p, Virus v) {
        if ( getRebond() && p.getRebond() ) {
            dx = -dx;
            dy = -dy;
            p.setDx(-p.getDx());
            p.setDy(-p.getDy());
        }
        People p1 = new People(this);
        p1.setCenterX(p1.getCenterX() + p1.getDx());
        p1.setCenterY(p1.getCenterY() + p1.getDy());
        People p2 = new People(p);
        p2.setCenterX(p2.getCenterX() + p2.getDx());
        p2.setCenterY(p2.getCenterY() + p2.getDy());
        if (p1.contact(p2)) {
            this.setRebond(false);
            p.setRebond(false);
        } else {
            this.setRebond(true);
            p.setRebond(true);
        }
        if ( this.getContamine() ) {
            if ( this.getConfine() && p.getConfine() ) {
                if ( this.getZoneLockdown() == p.getZoneLockdown() ){
                    v.contaminer(p, this.getCoeff());
                }
            } else {
                v.contaminer(p, this.getCoeff());
            }
        }
        else if (p.getContamine()){
            if ( this.getConfine() && p.getConfine() ){
                if ( this.getZoneLockdown() == p.getZoneLockdown() ) {
                    v.contaminer(this, p.getCoeff());
                }
            } else {
                v.contaminer(this, p.getCoeff());
            }
        }
    }

    /**
     * Gère la transmission du virus entre deux individu ( "People" ) si la distance entre eux es assez petite.
     */
    public void contaProche (People p, Virus v) {
        if (this.distance(p) <= 3 * p.getRadius()) {
            if ( this.getContamine() ) {
                if ( this.getConfine() && p.getConfine() ) {
                    if ( this.getZoneLockdown() == p.getZoneLockdown() ){
                        v.contaminer(p, this.getCoeff() * 3);
                    }
                } else {
                    v.contaminer(p, this.getCoeff() * 3);
                }
            }
            else if (p.getContamine()){
                if ( this.getConfine() && p.getConfine() ){
                    if ( this.getZoneLockdown() == p.getZoneLockdown() ) {
                        v.contaminer(this, p.getCoeff() * 3);
                    }
                } else {
                    v.contaminer(this, p.getCoeff() * 3);
                }
            }
        }
    }

    /**
     * Réduit considérablement la vitesse d'un individue.
     */
    public void reduceSpeed(){
        if ( !this.speedReduced ){
            this.dx = this.dx/ 50;
            this.dy = this.dy/50;
            this.speedReduced = true;
        }
    }

    /**
     * Augmente la vitesse d'un indiviue pour la rendre normal, ( jamais utiliser avant reduceSPeed )
     */
    public void increaseSpeed(){
        if ( this.speedReduced ) {
            this.dx = this.dx * 50;
            this.dy = this.dy * 50;
            this.speedReduced = false;
        }
    }

    /**
     *
     * @param age, paramètre important pour déterminer la probabilité de tomber en état critique selon l'age.
     * @return l'inverse de la  probabilité de tomber en état critique.
     */
    public int probaEtatCritique(int age){
      if (age <= 20) return 10000;
      else if (age <= 40) return 1000;
      else if (age <= 60) return 500;
      else if (age <= 80) return 150;
      else return 50;
    }

    /**
     * probabilité de devenir symptomatique plus important selon l'age de la personne
     * @param age, paramètre important pour déterminer la probabilité de devenir symptomatique selon l'age.
     * @return l'inverse de la probabilité de devenir symptomatique
     */
    public int coeffSymptomatique(int age){
      if (age <= 20) return 5000;
      else if (age <= 40) return 500;
      else if (age <= 60) return 200;
      else if (age <= 80) return 50;
      else return 10;
    }

    /**
     * Génère les position d'enterrmement après la mort d'un individue ( "People" ) dans le cimtère de l'environement.
     */
    private int[] generateDeathPosition(int i){
        int[] tabX = {20,20,20,20,45,45,45,45,70,70,70,70,95,95,95,95,120,120,120,120,145,145,145,145,170,170,170,170,195,195};
        int[] tabY = {20,45,70,95,20,45,70,95,20,45,70,95,20,45,70,95,20,45,70,95,20,45,70,95,20,45,70,95,20,45};
        return new int[]{tabX[i], tabY[i]};
    }

    /**
     * Met "this" en état de mort.
     */
    public void mourir(){
        this.setColor(Color.BLACK);
        this.setDx(0);
        this.setDy(0);
        int[] deathPos = generateDeathPosition(nbrDeath);
        this.setCenterX(deathPos[0]);
        this.setCenterY(deathPos[1]);
        nbrDeath++;
        this.setContamine(false);
        this.setSymptomatique(false);
        this.setEtatCritique(false);
        this.setEstVivant(false);
    }

    /**
     * Tout d'abord on compte les jours d'infection, ensuite après un certain temps d'infections sans symptomes, le patient peut commencer à devenir symptomatique
     * Dans le cas ou il devient symptomatique, il a alors une certaine probabilité de devenir en état critique.
     * Si jamais cela se produit, le patient peut alors mourir
     * Si le patient n'est pas en état critique, il guérit naturellement dans tous les cas au bout d'une certaine durée donnée par le paramètre v
     * Il est alors immunisé pendant une certaine durée donnée par le paramètre v
     * @param v le virus est essentiel car il donne l'infectiosité, la durée d'infection, la probabilité de symptomes et la durée d'immunité
     */
    public void mAJContamines (Virus v){
        if ( this.contamine) {
            this.nbJoursContamines += 1;
            if (this.nbJoursContamines >= v.getNbJours() / 2 && this.nbJoursContamines < v.getNbJours() && !this.getSymptomatique()) {
                Random r = new Random();
                int coeffSympt = this.coeffSymptomatique(this.age);
                int tmp = r.nextInt() % (v.getInvProbaSympt()*coeffSympt);
                if (tmp == 0) {
                    this.setSymptomatique(true);
                    this.setColor(Color.ORANGE);
                }
            } else if (nbJoursContamines == v.getNbJours() && !(this.getEtatCritique())) {
                this.nbJoursContamines = 0;
                this.setImmunise(true);
                this.setContamine(false);
                this.setSymptomatique(false);
                this.setColor(Color.GREEN);
            }
        }
        if (this.getSymptomatique() && !this.getEtatCritique()){
          Random r = new Random();
          int invProbaCritique = this.probaEtatCritique(this.age);
          int tmp = r.nextInt() % invProbaCritique;
          if (tmp == 0){
            this.setEtatCritique(true);
            this.setColor(Color.RED);
          }
        }
        if (!this.getInHospital() && this.getEtatCritique()){
          Random r = new Random();
          int tmp = r.nextInt() % 100;
          if (tmp == 0){
            this.mourir();
          }
        }
        if (this.getImmunise()){
          nbJoursImmunises+=1;
          if (nbJoursImmunises == v.getDureeImmunisee()){
            this.setImmunise(false);
            this.setColor(Color.BLUE);
          }
        }

    }

}

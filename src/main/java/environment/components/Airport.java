package environment.components;

import java.util.ArrayList;

/**
 * Modele d'aéroport pour l'environnement de simulation.
 */
public class Airport {

    private boolean borderClosed;
    private int probaOfTravel;
    private ArrayList<People>  peopleTravelling;
    private int pogswt;

    /**
     * Constructeur de la classe Airport.
     * @param borderClosed, définit si les frontières sont fermées ou ouvertes ( voyages autorisés ou pas ).
     * @param pot, Abréviation de : " probability of travelling ", définie la probabilité de voyage d'une personne qui entre dans l'aéroport
     * @param pogswt, Abréviation de : " probability of getting sick when travelling ", définie la probabilité de tomber malade lors d'un voyage.
     */
    public Airport (boolean borderClosed ,int pot ,int pogswt) {
        this.borderClosed = borderClosed;
        if ( pot < 0 ) this.probaOfTravel = 0;
        else this.probaOfTravel = Math.min(pot, 5);
        if ( pogswt < 0 ) this.pogswt= 0;
        else this.pogswt = Math.min(pot, 5);
        this.peopleTravelling = new ArrayList<>();
    }

    /**
     * Obtient la valeur de la propriété "borderClosed".
     */
    public boolean getBorderClosed() { // renvoie false si les frontières sont ouverte et false sinon
        return this.borderClosed;
    }

    /**
     * Définit la valeur de la propriété "borderClosed".
     */
    public void setClosedBorder(boolean borderClosed) {
        this.borderClosed = borderClosed;
    }

    /**
     * Obtient la valeur de la propriété "probaOfTravel".
     */
    public int getProbaOfTravel() {
        return this.probaOfTravel;
    }

    /**
     * Obtient la valeur de la propriété "pogswt".
     */
    public int getPogswt(){
        return this.pogswt;
    }

    /**
     * Ajoute le "People" donné en parametre à la liste des personnes qui sont en voyage.
     * @param p, p un une personne qui est en voyage.
     */
    public void addTraveling(People p) {
        this.peopleTravelling.add(p);
    }

    /**
     * Obtient la valeur de la propriété "peopleTravelling".
     */
    public ArrayList<People> getPeopleTravelling(){
        return this.peopleTravelling;
    }

}

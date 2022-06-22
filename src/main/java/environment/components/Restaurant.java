package environment.components;

import java.util.ArrayList;

/**
 * Model d'aéroport pour l'environnement de simulation.
 */
public class Restaurant {

    private ArrayList<People> peopleInRestaurant = new ArrayList<>();

    /**
     * Contructeur de la classe Restaurant.
     */
    public Restaurant(){}

    /**
     * Ajoute le "People" donné en parametre à la liste des personnes qui sont à l'intérieur du restaurant.
     * @param p, personne qui est au restaurant.
     */
    public void addInRestaurant(People p){
        if ( this.inRestaurantCoordinates(p) ) {
            this.peopleInRestaurant.add(p);
            p.reduceSpeed();
        }
    }

    /**
     * Supprime le "People" donné en parametre de la liste des personnes qui sont à l'intérieur du restaurant.
     * @param p, personne qui sort du restaurant.
     */
    public void getOut(People p){
        if ( !this.inRestaurantCoordinates(p) ){
            this.peopleInRestaurant.remove(p);
            p.increaseSpeed();
        }
    }

    /**
     * Vérifie l'emplacement du "People" donné en parametre vis à vis du restaurant.
     * @param p, la personne dont on vérifie l'emplacement.
     * @return true si p est à l'intérieur du restaurant, false sinon.
     */
    public boolean inRestaurant(People p){
        for ( People people : this.peopleInRestaurant ) {
            if ( people == p ) return true;
        }
        return false;
    }

    /**
     * Vérifie que le "People" donné en parametre est dans le restaurant d'un point de vue des coordonées.
     * @param , la personne dont on vérifie l'emplacement.
     * @return, true si p est à l'intérieur du restaurant, false sinon.
     */
    public boolean inRestaurantCoordinates(People p){
        return p.getCenterX() <= 105 && p.getCenterY() >= 300;
    } // on en a besoin car parfois un people se déplace sur les bord du restaurant ce qui crée des bugs.

    /**
     * Fait sortir toutes les personnes du restaurant.
     */
    public void getOutAll(){
        this.peopleInRestaurant.removeIf(t-> {
            t.increaseSpeed();
            return t.getClass() == People.class;
        });
    }

}

package note.model;

import java.io.Serializable;

/**
 * Modele de transcription d'information.
 */
public class Transcription implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Information information;

    /**
     * Constructeur de la classe Transcription.
     * @param name, le nom de la transcription ( de la simulation ).
     * @param information, informations à sauvgarder sur la simulation.
     */
    public Transcription(String name, Information information){
        this.name = name;
        this.information = information;
    }

    /**
     * Rédige la transcription selon un format désiré.
     */
    public String toString(){
        String str =" - Experience name : " + this.name + ".\n";
        str = str + this.information.toString();
        return str;
    }

}


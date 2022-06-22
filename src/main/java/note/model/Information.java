package note.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Modele d'information.
 */
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String note;
    private final ArrayList<String> IP;
    private final HashMap<String, String> chartNotes = new HashMap<>();

    /**
     * Constructeur de la classe Information.
     * @param note, note personnel.
     * @param ip, Abréviation pour : "important note".
     * @param SPNote, Abréviation pour : "sick people note".
     * @param UNNote, Abréviation pour : "unnaffected people note".
     * @param SPWSNote, Abréviation pour : "sick people without symptoms note".
     * @param PHNote, Abréviation pour : "people healded note".
     */
    public Information(String note ,ArrayList<String> ip ,String SPNote ,String UNNote ,String SPWSNote ,String PHNote ){
        this.note = note;
        this.IP = ip;
        chartNotes.put("SPNotes",SPNote);
        chartNotes.put("UNNote",UNNote);
        chartNotes.put("SPWSNote",SPWSNote);
        chartNotes.put("PHNote",PHNote);
    }

    /**
     * Rédige la transcription selon un format désiré.
     */
    public String toString(){
        StringBuilder str = new StringBuilder(" - Personal note: " + this.note + "\n" +
                " - Important points : \n");
        int cmp = 1;
        for ( String s : this.IP ){
            cmp++;
            str.append("  ").append(cmp).append(" - ").append(s).append(".\n");
        }
        str.append("\n");
        str.append("- Char notes : \n");
        for (Map.Entry<String, String> e : this.chartNotes.entrySet()) {
            str.append("   -").append(e.getKey()).append(" : ").append(e.getValue()).append(".\n");
        }
        str.append("\n");
        return str.toString();
    }

}

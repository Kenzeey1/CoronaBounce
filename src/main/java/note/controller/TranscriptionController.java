package note.controller;

import note.model.Information;
import note.model.Transcription;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller de la classe Transcritpion.
 */
public class TranscriptionController implements Initializable {

    @FXML public TextField name;
    @FXML public TextArea note;

    @FXML public TextArea SPNote;
    @FXML public TextArea UNNote;
    @FXML public TextArea SPWSNote;
    @FXML public TextArea PHNote;

    @FXML public TextField IP1;
    @FXML public TextField IP2;
    @FXML public TextField IP3;
    @FXML public TextField IP4;
    @FXML public TextField IP5;
    @FXML public TextField IP6;
    @FXML public TextField IP7;

    @FXML public Label warning;

    public static Stage tohide; // attribut pour hider l'environement

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Redirige vers le menu principal sans sauvegarde.
     */
    @FXML
    private void dontSave(ActionEvent actionEvent) {
        backHome(actionEvent);
    }

    /**
     *Redirige vers le menu principal avec sauvegarde des informations transcrite.
     */
    @FXML
    private void save(ActionEvent actionEvent) {
        if ( this.name.getText().length() < 1 ) warning();
        else if ( this.note.getText().length() < 1 ) warning();
        else if ( this.SPNote.getText().length() < 1 ) warning();
        else if ( this.UNNote.getText().length() < 1 ) warning();
        else if ( this.SPWSNote.getText().length() < 1 ) warning();
        else if ( this.PHNote.getText().length() < 1 ) warning();
        else {
            ArrayList<String> ip = new ArrayList<>();
            if ( IP1.getText().length() > 1 ) ip.add(IP1.getText());
            if ( IP2.getText().length() > 1 ) ip.add(IP2.getText());
            if ( IP3.getText().length() > 1 ) ip.add(IP3.getText());
            if ( IP4.getText().length() > 1 ) ip.add(IP4.getText());
            if ( IP5.getText().length() > 1 ) ip.add(IP5.getText());
            if ( IP6.getText().length() > 1 ) ip.add(IP6.getText());
            if ( IP7.getText().length() > 1 ) ip.add(IP7.getText());
            Information information = new Information(this.note.getText(),ip,this.SPNote.getText(),this.UNNote.getText(),this.SPWSNote.getText(),this.PHNote.getText());
            Transcription transcription = new Transcription(name.getText(),information);
            this.saveTranscription(transcription);
            backHome(actionEvent);
        }
    }

    /**
     * Sauvegarde la "Transcription" entrée en parametre.
     * @param t, Transcription à sauvegardée.
     */
    private void saveTranscription(Transcription t){
        Transcription[] transcriptions;
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/backup.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                Transcription[] tab = ( Transcription[] ) ois.readObject() ;
                transcriptions =  new Transcription[ tab.length + 1 ];
                System.arraycopy(tab, 0, transcriptions, 0, tab.length);
            } catch ( Exception e ) {
                transcriptions = new Transcription[1];
            }
        } catch (Exception e ) {
            transcriptions = new Transcription[1];
        }
        try {
            FileOutputStream fos = new FileOutputStream("src/main/resources/backup.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            transcriptions[ transcriptions.length - 1 ] = t;
            oos.writeObject( transcriptions );
            oos.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche un message d'erreur si un champ obligatoire à été laissé vide.
     */
    private void warning(){
        this.warning.setText("-There are empty mandatory fields, please fill them in.");
    }

    /**
     * Permet le retour vers le menu principal.
     */
    private void backHome(ActionEvent actionEvent){
        try {

            String location = "../../resources/fxml/Main.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(location));
            Parent root = loader.load();

            Scene scene = new Scene(root,600,400);

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Corona-Bounce");

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            tohide.hide();
            tohide = null;

        } catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    /**
     * Supprime le message d'erreur.
     */
    @FXML
    private void clear(){
        try {
            this.warning.setText("");
        } catch (Exception ignored){}
    }

}

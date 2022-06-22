package note.controller;

import note.model.Transcription;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Controller de la classe Note.
 */
public class NoteController implements Initializable {

    @FXML public VBox vbox;

    /**
     * Initialise l'affichage des notes sauvegardés.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Transcription[] transcriptions = new Transcription[0];
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/backup.ser");
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                transcriptions =  ( Transcription[] ) ois.readObject() ;
            } catch (Exception ignored){}
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Transcription> transcriptionList = new ArrayList<>();
        Collections.addAll(transcriptionList, transcriptions);
        if ( transcriptionList.size() == 0 ) {
            TextArea ta = new TextArea();
            ta.setText("\n\n  - NO EXPERIENCE HAVE BEEN DONE YET. ");
            ta.setEditable(false);
            this.vbox.getChildren().add(ta);
        } else {
            for ( Transcription t : transcriptionList ){
                TextArea ta = new TextArea();
                ta.setText(t.toString());
                ta.setEditable(false);

                Button delete = new Button("Delete");
                delete.setOnAction(e -> this.deleteTranscription(ta,t,delete));

                this.vbox.getChildren().addAll(ta,delete);
            }
        }
    }

    /**
     * Permet de supprimer une note si voulu.
     * @param ta, Zone de text ou les informations sauvegardés sont affichés.
     * @param transcript, Object contenant les informations sauvegardés.
     * @param button, Bouton de suppression de l'information.
     */
    private void deleteTranscription(TextArea ta,Transcription transcript,Button button){
        this.vbox.getChildren().remove(ta);
        this.vbox.getChildren().remove(button);
        Transcription[] transcriptions = new Transcription[0];
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/backup.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                transcriptions = ( Transcription[] ) ois.readObject();
            } catch (Exception ignored){}
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Transcription> transcriptionList = new ArrayList<>();
        Collections.addAll(transcriptionList, transcriptions);

        for ( Transcription t : transcriptions ){
            if ( t.toString().equals(transcript.toString())){
                transcriptionList.remove(t);
            }
        }

        Transcription[] transcriptionsToSave = new Transcription[transcriptionList.size()];
        for ( int i = 0 ; i < transcriptionList.size() ; i++ ){
            transcriptionsToSave[i] = transcriptionList.get(i);
        }

        if ( transcriptionsToSave.length == 0 ){
            TextArea textArea = new TextArea();
            textArea.setText("\n\n  - NO EXPERIENCE HAVE BEEN DONE YET. ");
            textArea.setEditable(false);
            this.vbox.getChildren().add(textArea);
        }

        try {
            FileOutputStream fos = new FileOutputStream("src/main/resources/backup.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for ( int i = 0 ; i < transcriptionList.size() ; i++) {
                oos.writeObject( transcriptionsToSave );
            }
            oos.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Répond à l'appuie du bouton "Back" dans le bloc note en renvoyant vers le menu principal.
     * @param actionEvent, ActionEvent grace auquel la fonction détermine le stage actuel pour pouvoir lui donner la scene de la page principale.
     */
    public void back(ActionEvent actionEvent){
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

        } catch ( Exception ex ){
            ex.printStackTrace();
        }
    }
}

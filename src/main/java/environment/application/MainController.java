package environment.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller de la classe principale.
 */
public class MainController implements Initializable {

    @FXML Button btn1;
    @FXML Button btn2;
    @FXML Button btn3;

    /**
     * Réagit à l'appuie du bouton "Create environment" pour rediriger vers l'éditeur d'environement.
     */
    @FXML
    private void switchSceneToCreateEnvironment(ActionEvent e){
        try {

            String location = "../../resources/fxml/CreationWorkshop.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(location));
            Parent creationWorkshopParent = loader.load();

            Scene scene = new Scene(creationWorkshopParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.setResizable(false);
            window.show();

        } catch ( Exception exception ){
            exception.printStackTrace();
        }
    }

    /**
     * Réagit à l'appuie du bouton "Start simulation" pour rediriger vers la page de configuration d'avant simulation.
     */
    @FXML
    private void startSimulation(ActionEvent e){
        try {

            String location = "../../resources/fxml/Settings.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(location));
            Parent creationWorkshopParent = loader.load();

            Scene scene = new Scene(creationWorkshopParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.setResizable(false);
            window.show();

        } catch ( Exception exception ){
            exception.printStackTrace();
        }
    }

    /**
     * Réagit à l'appuie du bouton "Show notes" pour rediriger vers l'espace d'affichage des notes de simulation sauvegardés.
     */
    @FXML
    private void showNotes(ActionEvent e) {
        try {
            String location = "../../resources/fxml/Note.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(location));
            Parent creationWorkshopParent = loader.load();

            Scene scene = new Scene(creationWorkshopParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.setResizable(false);
            window.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}

package environment.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale.
 */
public class Main extends Application {

    /**
     * Représente le point d'entrée principal de l'application.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Corona-Bounce !");

        String location = "../../resources/fxml/Main.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        Parent root = loader.load();

        Scene scene = new Scene(root,600,400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

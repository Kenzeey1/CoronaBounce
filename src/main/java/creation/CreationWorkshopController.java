package creation;

import environment.components.Environement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Controller de l'éditeur d'environement.
 */
public class CreationWorkshopController implements Initializable {

    @FXML private VBox vbox;

    @FXML private TextField radiusTF;
    @FXML private TextField heightRecTF;
    @FXML private TextField widthRecTF;

    /**
     * Initialise l'ouverture d'un environement sans graphique et sans population, qui permet la modification des éléments.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Environement env = new Environement(0,0,0,0);
        env.setEditing(true);
        ((HBox)(env.getChildren().get(0))).getChildren().remove(1);
        ((HBox)(env.getChildren().get(0))).setPrefHeight(600);
        ((HBox)(env.getChildren().get(0))).setPrefWidth(800); // on fait ça car après le remove la fenêtre était trop grande.
        this.vbox.getChildren().add(env);
        ((Environement)this.vbox.getChildren().get(2)).setDeplacementsAll();
    }

    /**
     * Ajoute un rectangle à l'environement.
     */
    @FXML
    public void addRectangle() {
        String heightRec = this.heightRecTF.getText();
        String widthRec = this.widthRecTF.getText();
        int heightRecInt;
        int widthRecInt;
        try{
            heightRecInt = parseInt(heightRec);
        } catch (Exception exception){
            heightRecInt = 0;
        }
        try{
            widthRecInt = parseInt(widthRec);
        } catch (Exception exception){
            widthRecInt = 0;
        }
        Rectangle rec;
        if ( heightRecInt == 0 && widthRecInt == 0 )
            rec = new Rectangle(50,50,Color.BLACK);
        else if ( heightRecInt == 0 && widthRecInt != 0 )
            rec = new Rectangle(50,heightRecInt, Color.BLACK);
        else if ( heightRecInt != 0 && widthRecInt == 0 )
            rec = new Rectangle(widthRecInt,50, Color.BLACK);
        else
            rec = new Rectangle(widthRecInt,heightRecInt, Color.BLACK);
        rec.setLayoutX(200);
        rec.setLayoutY(200);
        rec.setFill(Color.DODGERBLUE);
        rec.setStroke(Color.BLACK);
        rec.setArcHeight(5);
        rec.setArcWidth(5);
        ((Environement)this.vbox.getChildren().get(2)).getObstacles().add(rec);
        ((Pane)((HBox)(((Environement)this.vbox.getChildren().get(2)).getChildren().get(0))).getChildren().get(0)).getChildren().add(rec);
        ((Environement)this.vbox.getChildren().get(2)).setDeplacements(rec);
        this.heightRecTF.clear();
        this.widthRecTF.clear();
    }

    /**
     * Ajoute un cercle à l'environement.
     */
    @FXML
    public void addCircle() {
        String radius = this.radiusTF.getText();
        int radiusInt;
        try{
            radiusInt = parseInt(radius);
        } catch (Exception exception){
            radiusInt = 0;
        }
        Circle c;
        if ( radiusInt != 0 )
            c = new Circle(radiusInt,Color.BLACK);
        else
            c = new Circle(5,Color.BLACK);
        c.setLayoutX(200);
        c.setLayoutY(200);
        c.setFill(Color.DODGERBLUE);
        c.setStroke(Color.BLACK);
        ((Environement)this.vbox.getChildren().get(2)).getObstacles().add(c);
        ((Pane)((HBox)(((Environement)this.vbox.getChildren().get(2)).getChildren().get(0))).getChildren().get(0)).getChildren().add(c);
        ((Environement)this.vbox.getChildren().get(2)).setDeplacements(c);
        this.radiusTF.clear();
    }

    /**
     * Ajoute une Ellipse à l'environement.
     */
    @FXML
    public void addEllipse() {
        Ellipse el = new Ellipse(30,20);
        el.setLayoutX(200);
        el.setLayoutY(200);
        el.setFill(Color.DODGERBLUE);
        el.setStroke(Color.BLACK);
        ((Environement)this.vbox.getChildren().get(2)).getObstacles().add(el);
        ((Pane)((HBox)(((Environement)this.vbox.getChildren().get(2)).getChildren().get(0))).getChildren().get(0)).getChildren().add(el);
        ((Environement)this.vbox.getChildren().get(2)).setDeplacements(el);
    }

    /**
     * Répond à l'appuie du bouton "Save" dans l'éditeur d'environement en sauvgardant l'environement actuel.
     * Pour la sauvgarde cette fonction modifie directement le fichier fxml correspondant à l'environement de simulation.
     */
    @FXML
    public void Save(){
        String path = "src/main/resources/fxml/Environment.fxml"; // src
        String path2 = "build/resources/main/resources/fxml/Environment.fxml"; // build
        try {
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<String> ObstaclesFXML = new ArrayList<>();
        int idfx = 1;
        for (Shape s : ((Environement)this.vbox.getChildren().get(2)).getObstacles() ) {
            if ( s.getClass() == Rectangle.class ) {
                double lX = s.getTranslateX() + s.getLayoutX();
                double lY = s.getTranslateY() + s.getLayoutY();
                double w = ((Rectangle) s).getWidth();
                double h = ((Rectangle) s).getHeight();
                ObstaclesFXML.add(
                        "    <Rectangle fx:id=\"obstacle" + idfx + "" +
                                "\" arcHeight=\"5.0\" " +
                                "arcWidth=\"5.0\" " +
                                "fill=\"DODGERBLUE\" " +
                                "height=\"" + h + "\" " +
                                "layoutX=\"" + lX + "\" " +
                                "layoutY=\"" + lY + "\" " +
                                "stroke=\"BLACK\" " +
                                "strokeType=\"INSIDE\" " +
                                "width=\"" + w + "\" />"
                );
                idfx++;
            } else if ( s.getClass() == Circle.class ) {
                double lX = s.getLayoutX() + s.getTranslateX();
                double lY = s.getLayoutY() + s.getTranslateY();
                double radius = ((Circle)s).getRadius();
                ObstaclesFXML.add(
                        "    <Circle fill=\"DODGERBLUE\" " +
                                "layoutX=\""+lX+"\" " +
                                "layoutY=\""+lY+"\" " +
                                "radius=\""+radius+"\" " +
                                "stroke=\"BLACK\" strokeType=\"INSIDE\" />"
                );
            } else if ( s.getClass() == Ellipse.class ) {
                double lX = s.getLayoutX() + s.getTranslateX();
                double lY = s.getLayoutY() + s.getTranslateY();
                double radiusX = ((Ellipse)s).getRadiusX();
                double radiusY = ((Ellipse)s).getRadiusY();
                ObstaclesFXML.add(
                        "    <Ellipse fill=\"DODGERBLUE\" " +
                                "layoutX=\""+lX+"\" " +
                                "layoutY=\""+lY+"\" " +
                                "radiusX=\""+radiusX+"\"" +
                                " radiusY=\""+radiusY+"\"" +
                                " stroke=\"BLACK\" strokeType=\"INSIDE\" />"
                );
            }
        }

        try {
            Scanner sc = new Scanner(new File("src/main/resources/fxml/Modele.fxml"));
            sc.useDelimiter("\n");
            File file = new File(path); // src
            File file2 = new File(path2); // build
            PrintWriter writer = new PrintWriter(file); // src
            PrintWriter writer2 = new PrintWriter(file2); // build
            while (sc.hasNext()) {
                String tmp = sc.next();
                writer.println(tmp); // src
                writer2.println(tmp); // build
                if (tmp.length() > 10) {
                    String find = tmp.substring(0,13 );
                    if (find.equals("        <Pane")) {
                        for ( String str : ObstaclesFXML ) {
                            writer.println(str); // src
                            writer2.println(str); // build
                        }
                    }
                }
            }
            writer.close(); // src
            writer2.close(); // build
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     * Répond à l'appuie du bouton "Back" dans l'éditeur d'environement en renvoyant vers le menu principal.
     * @param e, ActionEvent grace auquel la fonction détermine le stage actuel pour pouvoir lui donner la scene de la page principale.
     */
    public void goBack(ActionEvent e) {
        try {

            String location = "../resources/fxml/Main.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(location));
            Parent root = loader.load();

            Scene scene = new Scene(root,600,400);

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setTitle("Corona-Bounce");

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

}

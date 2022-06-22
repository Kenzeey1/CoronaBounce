package environment.application;

import environment.components.Environement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe controller pour la configuration avant simulation.
 */
public class SettingsController implements Initializable {

    @FXML private TextField porbaOfVirusInfection;
    @FXML private TextField nbrPeople;
    @FXML private TextField probaOfTravel;
    @FXML private TextField probaOfGettingSickWhenTravel;

    private int probaOfVirusInfectionInt;
    private int nbrPeopleInt;
    private int probaOfGettingSickWhenTravelInt;
    private int probaOfTravelInt;

    @FXML private Label warning1;
    @FXML private Label warning2;
    @FXML private Label warning3;
    @FXML private Label warning4;
    @FXML private Label warning5;

    /**
     * Initialise les alertes à des lignes vides.
     */
    private void initializeWarnings(){
        this.warning1.setText("");
        this.warning2.setText("");
        this.warning3.setText("");
        this.warning4.setText("");
        this.warning5.setText("");
    }

    /**
     * Vérifie que les paramètres entrés ont un format correct.
     */
    @FXML
    private void verification(ActionEvent e){
        this.initializeWarnings();
        boolean start = true;
        try {
            if ( Integer.parseInt(this.porbaOfVirusInfection.getText()) <= 0 ) {
                this.warning1.setText("Value can't be null,negative or double ,please change.");
                start = false;
            } else {
                this.probaOfVirusInfectionInt = Integer.parseInt(this.porbaOfVirusInfection.getText());
            }
            if ( Integer.parseInt(this.nbrPeople.getText()) > 30 || Integer.parseInt(this.nbrPeople.getText()) < 1 ){
                this.warning2.setText("Wrong value, please change.");
                start = false;
            } else {
                this.nbrPeopleInt = Integer.parseInt(this.nbrPeople.getText());
            }
            if ( Integer.parseInt(this.probaOfGettingSickWhenTravel.getText()) <= 0 ){
                this.warning3.setText("Value can't be null,negative or double ,please change.");
                start = false;
            } else {
                this.probaOfGettingSickWhenTravelInt = Integer.parseInt(this.probaOfGettingSickWhenTravel.getText());
            }
            if ( Integer.parseInt(this.probaOfTravel.getText()) <= 0 ) {
                this.warning5.setText("Value can't be null,negative or double ,please change.");
                start = false;
            } else {
                this.probaOfTravelInt = Integer.parseInt(this.probaOfTravel.getText());
            }
            if ( start ) {
                startSimulation(e);
            }
        } catch ( Exception exception ) {
            this.warning4.setText("Wrong value(s),please write int value(s).");
        }

    }

    /**
     * Démarre la simulation.
     */
    private void startSimulation(ActionEvent e){
        try {
            Environement root = new Environement(this.nbrPeopleInt,this.probaOfGettingSickWhenTravelInt,this.probaOfVirusInfectionInt,this.probaOfTravelInt);

            Scene scene = new Scene(root);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            root.timerFillChart(1000,root.LineChart.getData().get(0),root.LineChart.getData().get(1),root.LineChart.getData().get(2),root.LineChart.getData().get(3),root.LineChart.getData().get(4),root.LineChart.getData().get(5));
            root.move(50);
            root.backHome(3000);
            

            window.setScene(scene);
            window.setResizable(false);
            window.show();

        } catch ( Exception exception ){
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

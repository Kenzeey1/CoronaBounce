package environment.components;

import note.controller.TranscriptionController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe modele et controlleur de l'environement de simulation.
 */
public class Environement extends Parent implements Initializable {

    private boolean end = false;
    private boolean stop = false;
    private boolean editing = false;

    //L'hopital n'est pas flexible, il a un lit pour soin intensif et deux lits simples.
    private Hopital hop = new Hopital();
    private Virus corona;

    private final Airport airport;
    private final Restaurant restaurant = new Restaurant();

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> shapeOnMousePressedEventHandler;
    EventHandler<MouseEvent> shapeOnMouseDraggedEventHandler;

    @FXML private Button btnPP = new Button();
    @FXML private Button btnLD = new Button();
    @FXML private Button btnCB = new Button();

    @FXML public LineChart<String,Number> LineChart;

    private ArrayList<Shape> obstacles = new ArrayList<>();

    private Parent root;

    private Circle deleter0;
    @FXML private Line deleter1;
    @FXML private Line deleter2;
    @FXML private Line deleter3;
    @FXML private Line deleter4;

    @FXML private ImageView trash;

    @FXML private Line airportDoor1;
    @FXML private Line airportDoor2;

    @FXML private Line restaurantDoor1;
    @FXML private Line restaurantDoor2;

    private ArrayList<People> unconfinedPopulation = new ArrayList<>();
    private ArrayList<People> confinedPopulation = new ArrayList<>();
    private ArrayList<People> deadPopulation = new ArrayList<>();

    private Timeline loop = new Timeline();
    private Timeline loop2 = new Timeline();
    private Timeline loop3 = new Timeline();

    private AtomicInteger i = new AtomicInteger();

    private static final double EXIT_AIRPORT_X = 610;
    private static final double EXIT_AIRPORT_Y = 155;

    /**
     * Génère une position correcte dans l'environement.
     * @return La position initial d'une personne ( un "People" ).
     */
    private double[] generateInitialPosition(){
        double[] ret = new double[2];
        Random r = new Random();
        do {
            ret[0] = r.nextInt()% 800; // 800 taille sur x de l'environement
            if ( ret[0] <= 105 ){
                ret[1] = r.nextInt()%280; // précaution pour ne générer personne à l'intérieur du restaurant
            } else {
                ret[1] = r.nextInt()% 400; // 400 taille sur y de l'environement
            }
        }while ( ret[0] <= 0 || ret[1] <= 0 );
        return ret;
    }

    /**
     * Génère une position de confinement correcte.
     * @param i indice de la prochaine place de confinement libre.
     * @return les coordonées de la place de confinement attribuée.
     */
    private int[] generateLockDownPosition(int i){
        int[] tabX = {50,100,150,200,250,300,50,100,150,200,250,300,50,100,150,200,250,300,50,100,150,200,250,300,50,100,150,200,250,300};
        int[] tabY = {430,430,430,430,430,430,460,460,460,460,460,460,490,490,490,490,490,490,520,520,520,520,520,520,560,560,560,560,560,560};
        return new int[]{tabX[i], tabY[i]};
    }//il y a 30 positions de confinement car il y a un maximum de 30 personnes à confiner par simulation.

    /**
     * génère la maladie chez un " People " avec une chance de 1/10.
     */
    private boolean generer_Maladie(){
        Random r = new Random();
        int res = r.nextInt()%10;
        return res == 0;
    }

    /**
     * Génère la couleur adaptée selon malade ou pas.
     * @param malade, boolean qui définit si une personne est malade ou pas.
     * @return La couleur adaptée à l'état de santé.
     */
    public Color generer_Couleur(boolean malade){
      if (malade) return Color.YELLOW;
      else return Color.BLUE;
    }

    /**
     * Constructeur de l'environement de simulation.
     * @param sizeOfPopulation, nombre de personnes présentes dans l'environement.
     * @param pogswt, probabilité de tomber malade lors d'un voyage.
     * @param probaMaladie, probabilité de contagion lors d'un contact.
     * @param probaOfTravel, probabilité de voyage lors d'un contact avec l'aéroport.
     */
    public Environement(int sizeOfPopulation, int pogswt,int probaMaladie,int probaOfTravel) {
        super();
        try{
            String location = "../../resources/fxml/Environment.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource(location));
            this.root = loader.load();

            this.getChildren().add(root);
            corona = new Virus(probaMaladie,400,1,400);
            for ( int i = 0 ; i < sizeOfPopulation ; i++ ) {
                Random r = new Random();
                boolean malade = generer_Maladie();
                double[] InitialPos = generateInitialPosition();
                int[] posLD = generateLockDownPosition(i);
                People people = new People(
                        InitialPos[0],
                        InitialPos[1],
                        10,
                        r.nextInt()%5+5,
                        r.nextInt()%5+5,
                        malade,
                        generer_Couleur(malade),
                        posLD[0],
                        posLD[1],
                        this,
                        r.nextInt()%100);
                unconfinedPopulation.add(people);
            }

            ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().addAll(unconfinedPopulation);

            if ( sizeOfPopulation == 0 ){
                deleter0 = new Circle(20,20,20);
                deleter0.setFill(Color.BLACK);
            } else {
                ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().remove(this.trash);
            }

            for ( Node n : ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren() ){
                if ( n.getClass() == Rectangle.class ) {
                    this.obstacles.add((Rectangle)n);
                } if ( n.getClass() == Circle.class){
                    this.obstacles.add((Circle)n);
                } if ( n.getClass() == Ellipse.class ){
                    this.obstacles.add((Ellipse)n);
                }
            }

        } catch ( Exception e) {
            e.printStackTrace();
        }

        this.shapeOnMousePressedEventHandler =
                t -> {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Shape)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Shape)(t.getSource())).getTranslateY();
                };

        this.shapeOnMouseDraggedEventHandler =
                t -> {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Shape)(t.getSource())).setTranslateX(newTranslateX);
                    ((Shape)(t.getSource())).setTranslateY(newTranslateY);

                    intersectDeleter();
                };

        this.airport = new Airport(false,probaOfTravel,pogswt);

        this.btnPP.setText("Pause");
        this.btnLD.setText("Lockdown");
        this.btnCB.setText("Close border");

        XYChart.Series<String,Number> critique = new XYChart.Series<>();
        critique.setName("Etat critique");

        XYChart.Series<String,Number> asymptomatique = new XYChart.Series<>();
        asymptomatique.setName("Malade asymptomatique");

        XYChart.Series<String,Number> symptomatique = new XYChart.Series<>();
        symptomatique.setName("Malade symptomatique");

        XYChart.Series<String,Number> safe = new XYChart.Series<>();
        safe.setName("Sain non immunisé");

        XYChart.Series<String,Number> cured = new XYChart.Series<>();
        cured.setName("Immunisé");

        XYChart.Series<String,Number> dead = new XYChart.Series<>();
        dead.setName("Mort");

        this.LineChart.getData().add(critique);
        this.LineChart.getData().add(asymptomatique);
        this.LineChart.getData().add(symptomatique);
        this.LineChart.getData().add(safe);
        this.LineChart.getData().add(cured);
        this.LineChart.getData().add(dead);

    }

    /**
     * Rend tous les obstacles de l'environement déplaçables.
     */
    public void setDeplacementsAll(){
        for( Shape s : this.obstacles ) {
            s.setOnMousePressed(this.shapeOnMousePressedEventHandler);
            s.setOnMouseDragged(this.shapeOnMouseDraggedEventHandler);
        }
    }

    /**
     * Rend l'objet s fournit en parametre déplaçable.
     */
    public void setDeplacements(Shape s){
        s.setOnMousePressed(this.shapeOnMousePressedEventHandler);
        s.setOnMouseDragged(this.shapeOnMouseDraggedEventHandler);
    }

    /**
     * Définit la valeur de la propriété "editing".
     * @param editing, true si l'environement est en mode edition, false sinon.
     */
    public void setEditing(boolean editing){
        this.editing = editing;
    } // editing est a true, ça implique qu'on ne pet plus modifier l'environement.

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    /**
     * Obtient la valeur de la propriété "populationNonConfine".
     */
    public ArrayList<People> getUnconfinedPopulation() {
        return this.unconfinedPopulation;
    }

    /**
     * Obtient la valeur de la propriété "populationConfine".
     */
    public ArrayList<People> getConfinedPopulation(){
        return this.confinedPopulation;
    }

    /**
     * Obtient la valeur de la propriété "obstacles".
     */
    public ArrayList<Shape> getObstacles(){
        return this.obstacles;
    }

    /**
     * Vérifie l'intersection avec les obstacles de l'environement.
     * @param p personne sur qui on teste la collision avec un obstacle.
     * @return L'obstacle avec lequel p est entrée en collision.
     */
    public Shape intersectObstacles(People p){
        try {
            for ( Shape s : this.obstacles ){
                if ( p.getBoundsInParent().intersects( s.getBoundsInParent() ) ) return s;
            }
            return null;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Vérifie la collision des obstacles avec les "deleter", et supprime les obstacles si il y'a une collision.
     */
    public void intersectDeleter(){
        ArrayList<Shape> tmp = new ArrayList<>();
        for ( Shape s : this.obstacles) {
            if ( s.getBoundsInParent().intersects( deleter0.getBoundsInParent() ) ) {
                tmp.add(s);
            }else if ( s.getBoundsInParent().intersects( deleter1.getBoundsInParent() )) {
                tmp.add(s);
            } else if ( s.getBoundsInParent().intersects( deleter2.getBoundsInParent() )) {
                tmp.add(s);
            } else if ( s.getBoundsInParent().intersects( deleter3.getBoundsInParent() )) {
                tmp.add(s);
            } else if ( s.getBoundsInParent().intersects( deleter4.getBoundsInParent() )) {
                tmp.add(s);
            }
        }
        for ( Shape s : tmp ) {
            this.obstacles.remove(s);
            ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().remove(s);
        }
    }

    /**
     * Permet de mettre une simulation sur pause, puis de la reprendre avec play.
     */
    @FXML
    private void pausePlay() {
        if ( !this.end ) {
            if (this.unconfinedPopulation.size() == 0 && this.confinedPopulation.size() == 0 ){
                return;
            }
            if (!this.stop){
                this.loop.stop();
                this.loop2.stop();
                this.loop3.stop();
                this.loop.getKeyFrames().clear();
                this.loop2.getKeyFrames().clear();
                this.loop3.getKeyFrames().clear();
                this.btnPP.setText("Play");
                this.stop = true;
            } else {
                this.stop = false;
                this.move(50);
                this.timerFillChart(1000,this.LineChart.getData().get(0),this.LineChart.getData().get(1),this.LineChart.getData().get(2),this.LineChart.getData().get(3),this.LineChart.getData().get(4),this.LineChart.getData().get(5));
                this.backHome(5000);
                this.btnPP.setText("Pause");
            }
        }
    }

    /**
     * Permet de confiné toutes les personnes de l'environement sauf les personnes mortes ou qui sont à l'hopital.
     */
    @FXML
    private void confineAll(){
        if ( !editing ){ // confine
            if ( this.btnLD.getText().equals("Lockdown") ) {
                for( People p : this.unconfinedPopulation) {
                    if ( !p.getTravelling() && !p.getInHospital() && p.getEstVivant()){
                        p.setCenterX( p.getLockDownPosX() );
                        p.setCenterY( p.getLockDownPosY() );
                        p.setConfine(true);
                        this.confinedPopulation.add(p);
                    }
                }
                this.unconfinedPopulation.removeIf(t-> ( !t.getTravelling()));
                this.restaurant.getOutAll();// vu que les personnes sont confinées on leurs rend leurs vitesse et les sort du restaurant
                this.btnLD.setText("Unlockdown");
            } else { // déconfine
                for( People p : this.confinedPopulation){
                    if (!p.getInHospital()){
                        p.setCenterX( p.getInitialCenterX() );
                        p.setCenterY( p.getInitialCenterY() );
                    }
                    p.setConfine(false);
                }
                this.unconfinedPopulation.addAll(this.confinedPopulation);
                this.confinedPopulation.removeIf(t-> t.getClass() == People.class);
                this.btnLD.setText("Lockdown");
                if ( this.btnPP.getText().equals("Play") ) {
                    stop = false;
                    this.move(50);
                    this.btnPP.setText("Pause");
                }
            }
        }
    }

    /**
     * Stop la simulation et ouvre une fenêtre configurer de manière à permettre la transcription d'informations ou de données récoltées pendant la simulation.
     */
    @FXML
    private void saveStats(ActionEvent actionEvent) {
        if ( !this.editing ){
            this.editing = true;
            this.end = true;
            this.loop.stop();
            this.loop2.stop();
            this.loop.getKeyFrames().clear();
            this.loop2.getKeyFrames().clear();

            try {
                String location = "../../resources/fxml/Transcription.fxml";
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(location));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Collect information");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                TranscriptionController.tohide = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            } catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     * Ferme les frontière de l'environement et fait en sorte que plus personne ne peut voyager.
     */
    @FXML
    private void closeBorder(){
        if( !this.airport.getBorderClosed() ) {
            this.airport.setClosedBorder(true);
            this.btnCB.setText("Open border");
        } else {
            this.airport.setClosedBorder(false);
            this.btnCB.setText("Close border");
        }
    }

    /**
     * Vérifie si le "People" entrée en parametre est entré dans l'aérport, si oui il a une probabilité de voyager puis de tomber malade selon les configurations d'avant simulation.
     * @param p, un "People" dont on vérifie l'entrée dans l'aéroport.
     */
    public void goToAirport(People p){
        if( !this.airport.getBorderClosed() ){
            if ( p.getBoundsInParent().intersects(this.airportDoor1.getBoundsInParent()) || p.getBoundsInParent().intersects(this.airportDoor2.getBoundsInParent()) ) {
                Random r = new Random();
                int tmp1 = Math.abs(r.nextInt()%this.airport.getProbaOfTravel());
                int tmp2 = Math.abs(r.nextInt()%this.airport.getPogswt());
                if ( tmp1 == 0 ) {
                    this.airport.addTraveling(p);
                    p.setTravelling(true);
                    ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().remove(p);
                    if ( tmp2 == 0 ) {
                      corona.contaminer(p,1);
                    }
                }
            }
        }
    }

    /**
     * Vérifie si le "People" entré en parametre est entré/sortie ou aucun des deux dans le restaurant, si oui il y entre,sinon il en sort, si aucun des deux alors il ne se passe rien.
     * @param p, un "People" dont on vérifie l'entrée dans le restaurant.
     */
    public void intersectResaurant(People p){
        if ( p.getBoundsInParent().intersects(this.restaurantDoor1.getBoundsInParent()) || p.getBoundsInParent().intersects(this.restaurantDoor2.getBoundsInParent()) ){
            if ( !this.restaurant.inRestaurant(p) ) { // p n'est pas au restaurant mais il y rentre
                if ( !this.restaurant.inRestaurant(p) ){
                    this.restaurant.addInRestaurant(p);
                }
            } else {  // p est au restaurant et il en sort
                if ( this.restaurant.inRestaurant(p) ){
                    this.restaurant.getOut(p);
                }
            }
        }
    }

    /**
     * Remet les "People" en voyage sur l'environement après une certaine durée.
     * @param duration, durée du voyage.
     */
    public void backHome(int duration){
        this.loop3.getKeyFrames().add(
                new KeyFrame(Duration.millis(duration), event -> {
                    Iterator<People> iter = this.airport.getPeopleTravelling().iterator();
                    while (iter.hasNext() ){
                        People p = iter.next();
                        try {
                            ((Pane)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().add(p);
                        } catch ( Exception ignored){}
                        p.setCenterX( EXIT_AIRPORT_X );
                        p.setCenterY( EXIT_AIRPORT_Y );
                        p.setTravelling(false);
                        iter.remove();
                    }
                }));
        this.loop3.setCycleCount(Timeline.INDEFINITE);
        this.loop3.play();
    }

    /**
     * Vérifie les rebonds du "People" entré en parametre avec les bords de l'environement et les obstacles si il n'est pas confiné et les réalise.
     */
    public void checkRebonds(People p){
        if( p.getCenterY() <= 10) {
            p.setDy( Math.abs( p.getDy() ) );
        }
        if ( p.getCenterX() <= 10){
            p.setDx( Math.abs( p.getDx() ));
        }
        if ( p.getCenterY() >= 390){
            p.setDy( -Math.abs( p.getDy() ));
        }
        if ( p.getCenterX() >= 790) {
            p.setDx( -Math.abs( p.getDx() ));
        }
        Shape s = this.intersectObstacles(p);
        if ( s != null ){
            if ( s.getClass() == Rectangle.class) p.rebond_hor_ver((Rectangle)s);
            else if ( s.getClass() == Circle.class ) p.rebond_cercle((Circle)s);
            else if ( s.getClass() == Ellipse.class ) p.rebond_ellipse((Ellipse)s);
        }
    }

    /**
     * Vérifie les rebonds  du "People" entrée en parametre avec les bords de la zone de confinement et les réalise.
     */
    public void checkRebondsConfine(People p){
        int zone = p.getZoneLockdown();
        if ( zone == 1 ){
            if( p.getCenterY() <= 411 ) {
                p.setDy( Math.abs( p.getDy() ) );
            }
            if ( p.getCenterX() <= 10 ){
                p.setDx( Math.abs( p.getDx() ));
            }
            if ( p.getCenterY() >= 590){
                p.setDy( -Math.abs( p.getDy() ));
            }
            if ( p.getCenterX() >= 111.5) {
                p.setDx( -Math.abs( p.getDx() ));
            }
        } else if ( zone == 2 ) {
            if( p.getCenterY() <= 411 ) {
                p.setDy( Math.abs( p.getDy() ) );
            }
            if ( p.getCenterX() <= 131.5 ){
                p.setDx( Math.abs( p.getDx() ));
            }
            if ( p.getCenterY() >= 590){
                p.setDy( -Math.abs( p.getDy() ));
            }
            if ( p.getCenterX() >= 215.5) {
                p.setDx( -Math.abs( p.getDx() ));
            }
        } else if ( zone == 3 ) {
            if( p.getCenterY() <= 411 ) {
                p.setDy( Math.abs( p.getDy() ) );
            }
            if ( p.getCenterX() <= 235.5 ){
                p.setDx( Math.abs( p.getDx() ));
            }
            if ( p.getCenterY() >= 590){
                p.setDy( -Math.abs( p.getDy() ));
            }
            if ( p.getCenterX() >= 321.5) {
                p.setDx( -Math.abs( p.getDx() ));
            }
        }
    }

    /**
     * Actualise la position des "People" présents dans l'environement selon leurs vitesses et leurs directions, et gère toute autre dynamique liée à la simulation.
     * @param duration, temps d'actualisation des positions.
     */
    public void move(int duration){

        loop.getKeyFrames().add(
                new KeyFrame(Duration.millis(duration), event -> {
                    this.unconfinedPopulation.removeIf(t-> {
                            if ( !t.getEstVivant()) this.deadPopulation.add(t);
                            return !t.getEstVivant();
                    });
                    this.confinedPopulation.removeIf(t-> {
                        if ( !t.getEstVivant()) this.deadPopulation.add(t);
                        return !t.getEstVivant();
                    });
                    int n = this.unconfinedPopulation.size();

                    for ( int i = 0; i < n ; i++ ) {
                        People p = this.unconfinedPopulation.get(i);
                        p.setCenterX(p.getCenterX() + p.getDx() );
                        p.setCenterY(p.getCenterY() + p.getDy() );
                        p.mAJContamines(corona);

                        if (p.getSymptomatique() && (!p.getInHospital()) ){
                            hop.deplacerHop(p);
                        }
                        if (p.getInHospital()){
                            hop.soigner(p);
                        }
                        if (p.getImmunise() && p.getInHospital()){
                            hop.sortir(p);
                        }
                        //rebonds:
                        this.checkRebonds(p);
                        this.goToAirport(p);
                        this.intersectResaurant(p);
                        for (int j = i+1; j< n; j++){
                            People q = this.unconfinedPopulation.get(j);
                            if ( p.contact(q) ){
                                p.rebond_contact(q,corona);
                            }
                            else p.contaProche(q,corona);
                        }
                    }

                    int n2 = confinedPopulation.size();
                    for ( int i = 0; i < n2 ; i++ ) {
                        People p = confinedPopulation.get(i);
                        p.setCenterX(p.getCenterX() + p.getDx() );
                        p.setCenterY(p.getCenterY() + p.getDy() );
                        p.mAJContamines(corona);

                        //rebonds zone de confinement:
                        this.checkRebondsConfine(p);
                        if (p.getSymptomatique() && (!p.getInHospital()) ){
                            hop.deplacerHop(p);
                        }
                        if (p.getInHospital()){
                            hop.soigner(p);
                        }
                        if (p.getImmunise() && p.getInHospital()){
                            hop.sortir(p);
                        }
                        for (int j = i+1; j < n2; j++) {
                            People q = confinedPopulation.get(j);
                            if (p.contact(q)){
                                p.rebond_contact(q,corona);
                            }
                            else {
                                p.contaProche(q,corona);
                          }
                        }
                    }
                }));
        this.loop.setCycleCount(Timeline.INDEFINITE);
        this.loop.play();
    }

    /**
     * Gère le developpement du graphique d'évolution des données en fonction du temps.
     */
    public void timerFillChart(int duration ,XYChart.Series<String,Number> critique ,XYChart.Series<String,Number> asymptomatique,XYChart.Series<String,Number> symptomatique
    ,XYChart.Series<String,Number> safe ,XYChart.Series<String,Number> cured ,XYChart.Series<String,Number> dead){
        this.loop2.getKeyFrames().add(
                new KeyFrame(Duration.millis(duration), event -> {
                    this.fillChart(
                            String.valueOf(i.get()),
                            critique,
                            asymptomatique,
                            symptomatique,
                            safe,
                            cured,
                            dead
                    );
                    i.getAndIncrement();
                }));
        this.loop2.setCycleCount(Timeline.INDEFINITE);
        this.loop2.play();
    }

    /**
     * Actualise les valeurs des parametres du graphique.
     */
    private void fillChart(String tmp ,XYChart.Series<String,Number> critique ,XYChart.Series<String,Number> asymptomatique,XYChart.Series<String,Number> symptomatique
    ,XYChart.Series<String,Number> safe ,XYChart.Series<String,Number> cured,XYChart.Series<String,Number> dead ){
        int cmpCrit = 0;
        int cmpAsymp = 0;
        int cmpSymp = 0;
        int cmpSafe = 0;
        int cmpCured = 0;
        int cmpdead = this.deadPopulation.size();

        for ( People p : this.getUnconfinedPopulation() ) {
            if ( p.getEtatCritique() ) cmpCrit++;
            if (!p.getSymptomatique() && p.getContamine()) cmpAsymp++;
            if (p.getSymptomatique()) cmpSymp++;
            if (p.getImmunise())cmpCured++;
            else if (!p.getContamine()) cmpSafe++;
        }
        for ( People p : this.getConfinedPopulation() ){
          if ( p.getEtatCritique() ) cmpCrit++;
          if (!p.getSymptomatique() && p.getContamine()) cmpAsymp++;
          if (p.getSymptomatique()) cmpSymp++;
          if (p.getImmunise())cmpCured++;
          else if (!p.getContamine()) cmpSafe++;
        }

        XYChart.Data<String,Number> dCrit = new XYChart.Data<>(tmp,cmpCrit);
        XYChart.Data<String,Number> dAsymp = new XYChart.Data<>(tmp,cmpAsymp);
        XYChart.Data<String,Number> dSymp = new XYChart.Data<>(tmp,cmpSymp);
        XYChart.Data<String,Number> dsafe = new XYChart.Data<>(tmp,cmpSafe);
        XYChart.Data<String,Number> dcured = new XYChart.Data<>(tmp,cmpCured);
        XYChart.Data<String,Number> ddead = new XYChart.Data<>(tmp,cmpdead);

        critique.getData().add(dCrit);
        asymptomatique.getData().add(dAsymp);
        symptomatique.getData().add(dSymp);
        safe.getData().add(dsafe);
        cured.getData().add(dcured);
        dead.getData().add(ddead);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

}

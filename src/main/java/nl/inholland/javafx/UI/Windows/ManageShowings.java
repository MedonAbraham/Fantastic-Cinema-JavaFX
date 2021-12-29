package nl.inholland.javafx.UI.Windows;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.Model.*;
import nl.inholland.javafx.UI.StylesScene;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ManageShowings extends MainWindow{

    private Stage stage;
    private Database db;
    private Employee employee;
    private Movie movie;
    private Scene scene;

    private GridPane manageShowings;
    private GridPane showingsPane;

    private VBox layout;
    private Label pageTitle;

    private ComboBox cmdMovieTitle;
    private ComboBox cmbRoom;

    private DatePicker startDatePicker;

    private TextField txtStartTime;

    private Label endTime;
    private Label nrOfSeats;
    private Label price;
    private Label lblWarning;

    private Button addShowingButton;
    private Button clearButton;

    private TableView<Showing> room2TableView;
    private TableView<Showing> room1TableView;

    private MenuBar menuBar;

    public ManageShowings(Database db, Employee employee){
        super(db,employee);

        movie = new Movie();

        this.layout = getLayout();
        this.db = db;
        this.employee = employee;
        this.stage = getStage();
        this.menuBar = getMenuBar();

        this.pageTitle = getPageTitle();
        pageTitle.setText("Manage showings");

        stage.setTitle("Fantastic Cinema -- -- manage showings -- username: "+ employee.getUserName());

        this.room1TableView = getRoom1TableView();
        this.room2TableView = getRoom2TableView();

        this.showingsPane = getShowings();

        manageShowings = createManagingForm();


        for (Movie movie: db.getMovies()){
            cmdMovieTitle.getItems().addAll(movie.getTitle());

        }

        cmbRoom.getItems().addAll("1","2");


        cmdMovieTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Movie m: db.getMovies()){
                    if(cmdMovieTitle.getValue().equals(m.getTitle())){
                        price.setText(Double.toString(m.getPrice()));

                        movie.setRunningTime(m.getRunningTime());
                    }
                }
            }
        });



        cmbRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cmbRoom.getValue().equals("1")){
                    nrOfSeats.setText("200");
                }
                else {
                    nrOfSeats.setText("100");
                }

            }
        });


        txtStartTime.setOnAction(actionEvent -> {
            for(Movie m: db.getMovies()){
                try{
                    if(cmdMovieTitle.getValue().equals(m.getTitle())){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime startTime = LocalDateTime.parse(startDatePicker.getValue() + " " + txtStartTime.getText(),formatter);
                        endTime.setText(startTime.plusMinutes(m.getRunningTime()).format(formatter));
                    }
                }catch (DateTimeParseException dtpe){
                    lblWarning.setText("Wrong date time format.Required: HH:mm, 24 hour format");
                }

            }

        });



        addShowingButton.setOnAction(actionEvent -> {
            try {

                if(!cmdMovieTitle.getValue().equals("") & !cmbRoom.getValue().equals("") & startDatePicker!= null & !txtStartTime.getText().equals("")){

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime newStartTime = LocalDateTime.parse(startDatePicker.getValue() + " " + txtStartTime.getText(),formatter).minusMinutes(15);
                    LocalDateTime newEndTime = LocalDateTime.parse(endTime.getText(),formatter).plusMinutes(15);

                    Movie selectedMovie = new Movie(cmdMovieTitle.getValue().toString(),movie.getRunningTime(), Double.parseDouble(price.getText()));

                    Showing newShowing = new Showing(selectedMovie,Integer.parseInt(nrOfSeats.getText()),newStartTime,newEndTime,Integer.parseInt(cmbRoom.getValue().toString()));

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Time overlap");
                    alert.setContentText("Room is occupied during this time");


                    if(cmbRoom.getValue().equals("1")){

                        if(isDateTimeOverlapping(db.getRoom1(),newStartTime,newEndTime)){

                            alert.show();
                        }
                        else {
                            db.getRoom1().add(newShowing);
                            clearForm();
                        }


                    }else {
                        if(isDateTimeOverlapping(db.getRoom2(),newStartTime,newEndTime)){

                            alert.show();

                        }
                        else {
                            db.getRoom2().add(newShowing);
                            clearForm();

                        }
                    }



                }
                else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Missing information");
                    alert.setContentText("Please fill in all the necessary information.");

                    alert.show();
                }

            }catch (DateTimeParseException dtpe){

                lblWarning.setText("Incorrect date format." + dtpe.getMessage());

            }catch (Exception e){
                lblWarning.setText("Oops!Something went wrong.");
            }

        });


        clearButton.setOnAction(actionEvent -> {

            clearForm();

        });


        layout.getChildren().addAll(menuBar, pageTitle, showingsPane,manageShowings);

        scene = new StylesScene(layout);
        stage.setScene(scene);
        stage.show();


    }

    public GridPane createManagingForm(){

        manageShowings = new GridPane();

        manageShowings.setPadding(new Insets(25));
        manageShowings.setPrefWidth(800);
        manageShowings.setVgap(10);
        manageShowings.setHgap(40);
        manageShowings.setStyle("-fx-background-color: #00325A");

        Label lblMovie = new Label("Movie title ");
        Label lblRoom = new Label("Room ");
        Label lblSeats = new Label("No. of seats ");
        Label lblStartTime = new Label("Start ");
        Label lblEndTime = new Label("End");
        Label lblPrice = new Label("Price");
        lblWarning = new Label();
        lblWarning.setStyle("-fx-text-fill: RED");


        nrOfSeats = new Label();
        endTime = new Label();
        price = new Label();
        cmdMovieTitle = new ComboBox<>();
        cmbRoom = new ComboBox<>();

        startDatePicker = new DatePicker();
        txtStartTime = new TextField();

        addShowingButton = new Button("Add showing");
        addShowingButton.setMinWidth(120);

        clearButton = new Button("Clear");
        clearButton.setMinWidth(120);


        GridPane.setConstraints(lblMovie,0,0);
        GridPane.setConstraints(lblRoom,0,1);
        GridPane.setConstraints(lblSeats,0,2);

        GridPane.setConstraints(cmdMovieTitle,1,0);
        GridPane.setConstraints(cmbRoom,1,1);
        GridPane.setConstraints(nrOfSeats,1,2);

        GridPane.setConstraints(lblStartTime,2,0);
        GridPane.setConstraints(lblEndTime,2,1);
        GridPane.setConstraints(lblPrice,2,2);

        GridPane.setConstraints(startDatePicker,3,0);
        GridPane.setConstraints(txtStartTime,4,0);
        GridPane.setConstraints(endTime,3,1);
        GridPane.setConstraints(price,3,2);

        GridPane.setConstraints(addShowingButton,5,1);
        GridPane.setConstraints(clearButton,5,2);
        GridPane.setConstraints(lblWarning,5,0);

        manageShowings.getChildren().addAll(lblMovie,lblRoom,lblSeats, cmdMovieTitle, cmbRoom,nrOfSeats,lblStartTime,lblEndTime,lblPrice, startDatePicker, txtStartTime,endTime,price, addShowingButton, clearButton, lblWarning);


        return  manageShowings;

    }

    public boolean isDateTimeOverlapping(ObservableList<Showing> showings, LocalDateTime newShowingStart, LocalDateTime newShowingEnd) {

        for(Showing s: showings){

            if((newShowingStart.isAfter(s.getStartTime()) && newShowingStart.isBefore(s.getEndTime())) || (newShowingEnd.isAfter(s.getStartTime()) && newShowingEnd.isBefore(s.getEndTime()))){
                return true;
            }
        }
        return false;
    }



    public void clearForm(){
        cmdMovieTitle.setValue("");
        cmbRoom.setValue("");
        nrOfSeats.setText("");
        startDatePicker.getEditor().clear();
        startDatePicker.setValue(null);
        txtStartTime.clear();
        endTime.setText("");
        price.setText("");
        lblWarning.setText("");

    }


}

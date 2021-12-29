package nl.inholland.javafx.UI.Windows;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.Model.Employee;
import nl.inholland.javafx.Model.Movie;
import nl.inholland.javafx.UI.StylesScene;

public class ManageMovies extends MainWindow {

    private Stage stage;
    private Database db;
    private Employee employee;

    private VBox layout;
    private GridPane container;
    private GridPane addMovieForm;

    private Label pageTitle;
    private Label lblWarning;
    private Label movieListTitle;
    private Label formTitle;
    private Label lblMovieTitle;
    private Label lblPrice;
    private Label lblRunningTime;


    private Button addButton;
    private Button clearButton;

    private TableView<Movie> movieTableView;

    private MenuBar menuBar;

    private TextField txtMovieTitle;
    private TextField txtPrice;
    private TextField txtRunningTime;

    public ManageMovies(Database db, Employee employee){
        super(db,employee);

        this.db = db;
        this.stage = getStage();
        this.employee = employee;

        layout = new VBox();
        layout.setPadding(new Insets(10));

        this.pageTitle = getPageTitle();
        pageTitle.setText("Manage Movies");

        this.menuBar = getMenuBar();

        stage.setTitle("Fantastic Cinema -- -- manage movies -- username " + employee.getUserName());
        stage.setWidth(950);
        stage.setHeight(500);


        movieTableView = showMovies();
        addMovieForm = createAddMovieForm();
        container = createContainer();


        addButton.setOnAction(actionEvent -> {

            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");

                if(!txtMovieTitle.getText().equals("") && !txtPrice.getText().equals("") && !txtRunningTime.getText().equals("") ){

                    if(txtPrice.getText().equals("0") | txtRunningTime.getText().equals("0")){
                        alert.setHeaderText("Zero input!");
                        alert.setContentText("Price or duration cannot be set as 0. ");
                        alert.show();

                    }
                    else {
                        if(movieExists()){
                            alert.setHeaderText("Movie exists!");
                            alert.setContentText("This movie already exists in the database.");
                            alert.show();

                        }
                        else {
                            Movie movie = new Movie(txtMovieTitle.getText(),Integer.parseInt(txtRunningTime.getText()),Double.parseDouble(txtPrice.getText()));
                            db.getMovies().add(movie);
                        }

                        clearForm();
                    }


                }
                else {
                    alert.setHeaderText("Missing information.");
                    alert.setContentText("Please fill in all the necessary information.");

                    alert.show();

                }

                movieTableView.refresh();

            }catch (NumberFormatException nfe) {
                lblWarning.setText("Incorrect number format!");

            }catch (Exception e){
                lblWarning.setText("Oops! Something went wrong.\n Try again/");
            }

        });


        clearButton.setOnAction(actionEvent -> {
            clearForm();
        });


        layout.getChildren().addAll(menuBar,pageTitle,container);

        Scene scene = new StylesScene(layout);
        stage.setScene(scene);
        stage.show();

    }

    private GridPane createAddMovieForm(){

        addMovieForm = new GridPane();

        addMovieForm.setPadding(new Insets(20));
        addMovieForm.setVgap(10);
        addMovieForm.setHgap(20);

        lblMovieTitle = new Label("Movie title ");
        lblPrice = new Label("Price ");
        lblRunningTime = new Label("Running time ");
        lblWarning = new Label();
        lblWarning.setStyle("-fx-text-fill: RED");


        txtMovieTitle = new TextField();
        txtPrice = new TextField();
        txtRunningTime = new TextField();

        addButton = new Button("Add Movie");
        addButton.setMinWidth(120);

        clearButton = new Button("Clear");
        clearButton.setMinWidth(120);


        GridPane.setConstraints(lblMovieTitle,0,0);
        GridPane.setConstraints(lblPrice,0,1);
        GridPane.setConstraints(lblRunningTime,0,2);

        GridPane.setConstraints(txtMovieTitle,1,0);
        GridPane.setConstraints(txtPrice,1,1);
        GridPane.setConstraints(txtRunningTime,1,2);

        GridPane.setConstraints(addButton,0,5);
        GridPane.setConstraints(clearButton,0,6);
        GridPane.setConstraints(lblWarning,1,4);

        addMovieForm.getChildren().addAll(lblMovieTitle,txtMovieTitle,lblPrice,txtPrice,lblRunningTime,txtRunningTime,addButton,clearButton, lblWarning);
        addMovieForm.setStyle("-fx-background-color: #00325A");

        return addMovieForm;

    }

    private TableView<Movie> showMovies(){

        movieTableView = new TableView<>();

        TableColumn<Movie, String> movieTitleCol = new TableColumn<>("Movie title");
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieTitleCol.setMinWidth(350);

        TableColumn<Movie, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setMinWidth(70);


        TableColumn<Movie, Integer> runningTimeCol = new TableColumn<>("Running time");
        runningTimeCol.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
        runningTimeCol.setMinWidth(130);

        movieTableView.setItems(db.getMovies());
        movieTableView.getColumns().addAll(movieTitleCol,priceCol,runningTimeCol);
        return movieTableView;

    }


    private GridPane createContainer(){

        container = new GridPane();

        container.setPadding(new Insets(10));
        container.setVgap(10);
        container.setHgap(8);

        movieListTitle = new Label("Movies ");
        formTitle = new Label("Add a new move: ");

        GridPane.setConstraints(movieListTitle,0,0);
        GridPane.setConstraints(formTitle,1,0);
        GridPane.setConstraints(movieTableView,0,1);

        GridPane.setConstraints(addMovieForm,1,1);


        movieListTitle.setFont(Font.font(13));
        movieListTitle.setStyle("-fx-text-fill: #2E537A");

        formTitle.setFont(Font.font(13));
        formTitle.setStyle("-fx-text-fill: #2E537A");

        container.setStyle("-fx-border-insets: 10; -fx-border-color: #2E537A");
        container.getChildren().addAll(movieListTitle,movieTableView,formTitle,addMovieForm);

        return container;

    }



    public boolean movieExists(){

        boolean exists = false;

        for (Movie movie: db.getMovies()) {
            if(txtMovieTitle.getText().equals(movie.getTitle())){
                exists = true;
            }
        }
        return exists;

    }


    public void clearForm(){

        lblWarning.setText("");
        txtMovieTitle.clear();
        txtPrice.clear();
        txtRunningTime.clear();

    }




}






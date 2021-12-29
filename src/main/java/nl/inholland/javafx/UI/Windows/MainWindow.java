package nl.inholland.javafx.UI.Windows;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.Model.AccessLevel;
import nl.inholland.javafx.Model.Employee;
import nl.inholland.javafx.Model.Showing;

public class MainWindow {

    private Stage stage;
    private Employee employee;
    private Database db;
    private GridPane showings;
    private VBox layout;
    private Label pageTitle;
    private TableView<Showing> room2TableView;
    private TableView<Showing> room1TableView;
    private MenuBar menuBar;


    public Stage getStage() {return stage;}

    public VBox getLayout() {return layout;}

    public Label getPageTitle() {return pageTitle;}

    public MenuBar getMenuBar() {return menuBar;}

    public GridPane getShowings() {return showings;}

    public TableView<Showing> getRoom2TableView() {return room2TableView;}

    public TableView<Showing> getRoom1TableView() {return room1TableView;}


    public MainWindow(Database db, Employee employee) {

        layout = new VBox();
        layout.setPadding(new Insets(10));

        this.db = db;
        this.employee = employee;

        pageTitle = new Label();
        pageTitle.setFont(Font.font(20));
        pageTitle.setPadding(new Insets(10));
        pageTitle.setStyle("-fx-text-fill: #2E537A;");


        menuBar = createMenuBar(layout);


        stage = new Stage();
        stage.setTitle("" + employee.getUserName());
        stage.setWidth(1300);
        stage.setHeight(700);


        room1TableView = showRoom1Showings();

        room2TableView = showRoom2Showings();


        showings = showingGridPane();


    }


    private TableView<Showing> showRoom1Showings(){

        room1TableView = new TableView<>();

        TableColumn<Showing, String> startTimeCol1 = new TableColumn<>("Start");
        startTimeCol1.setCellValueFactory(new PropertyValueFactory<>("localStartTime"));
        startTimeCol1.setMinWidth(140);

        TableColumn<Showing, String> endTimeCol1 = new TableColumn<>("End");
        endTimeCol1.setCellValueFactory(new PropertyValueFactory<>("localEndTime"));
        endTimeCol1.setMinWidth(140);

        TableColumn<Showing, String> titleCol1 = new TableColumn<>("Title");
        titleCol1.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        titleCol1.setMinWidth(180);

        TableColumn<Showing, Integer> seatsCol1 = new TableColumn<>("Seats");
        seatsCol1.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        seatsCol1.setMinWidth(50);

        TableColumn<Showing, Double> priceCol1 = new TableColumn<>("Price");
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("moviePrice"));
        priceCol1.setMinWidth(50);

        room1TableView.setItems(db.getRoom1());
        room1TableView.getColumns().addAll(startTimeCol1,endTimeCol1,titleCol1,seatsCol1,priceCol1);

        return room1TableView;


    }

    private TableView<Showing> showRoom2Showings(){

        room2TableView = new TableView<>();

        TableColumn<Showing, String> startTimeCol2 = new TableColumn<>("Start");
        startTimeCol2.setCellValueFactory(new PropertyValueFactory<>("localStartTime"));
        startTimeCol2.setMinWidth(140);

        TableColumn<Showing, String> endTimeCol2 = new TableColumn<>("End");
        endTimeCol2.setCellValueFactory(new PropertyValueFactory<>("localEndTime"));
        endTimeCol2.setMinWidth(140);

        TableColumn<Showing, String> titleCol2 = new TableColumn<>("Title");
        titleCol2.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        titleCol2.setMinWidth(180);

        TableColumn<Showing, Integer> seatsCol2 = new TableColumn<>("Seats");
        seatsCol2.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        seatsCol2.setMinWidth(50);

        TableColumn<Showing, Double> priceCol2 = new TableColumn<>("Price");
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("moviePrice"));
        priceCol2.setMinWidth(50);

        room2TableView.setItems(db.getRoom2());
        room2TableView.getColumns().addAll(startTimeCol2,endTimeCol2,titleCol2,seatsCol2,priceCol2);

        return room2TableView;

    }

    private GridPane showingGridPane(){

        showings = new GridPane();
        showings.setPadding(new Insets(10));
        showings.setVgap(2);
        showings.setHgap(10);

        Label room1 = new Label("Room 1");
        Label room2 = new Label("Room 2");

        room1.setFont(Font.font(12));
        room1.setStyle("-fx-text-fill: #2E537A");

        room2.setFont(Font.font(12));
        room2.setStyle("-fx-text-fill: #2E537A");

        GridPane.setConstraints(room1, 0, 0);
        GridPane.setConstraints(room1TableView, 0, 1);
        GridPane.setConstraints(room2, 1, 0);
        GridPane.setConstraints(room2TableView, 1, 1);

        showings.getChildren().addAll(room1, room1TableView, room2, room2TableView);
        showings.setStyle("-fx-border-insets: 10; -fx-border-color: #2E537A");

        return showings;
    }




    private MenuBar createMenuBar(VBox box) {

        MenuBar menuBar = new MenuBar();

        Menu help = new Menu("Help");
        Menu logout = new Menu("Logout");
        Menu admin = new Menu("Admin");

        MenuItem aboutDetails = new MenuItem("About");
        MenuItem logoutOption = new MenuItem("Logout...");
        logoutOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new LoginWindow(db);
                stage.close();

            }
        });

        MenuItem manageShowings = new MenuItem("Manage showings");
        manageShowings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ManageShowings(db, employee);
                stage.close();

            }
        });


        MenuItem manageMovies = new MenuItem("Manage movies");
        manageMovies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ManageMovies(db,employee);
                stage.close();
            }
        });

        MenuItem purchaseTickets = new MenuItem("Purchase tickets");
        purchaseTickets.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new PurchaseTickets(db,employee);
                stage.close();
            }
        });

        help.getItems().add(aboutDetails);
        logout.getItems().add(logoutOption);
        admin.getItems().addAll(manageShowings, manageMovies,purchaseTickets);

        if (employee.getTypeOfEmployee() == AccessLevel.ADMIN) {
            menuBar.getMenus().addAll(admin, help, logout);
        } else {
            menuBar.getMenus().addAll(help, logout);
        }


        return menuBar;
    }
}

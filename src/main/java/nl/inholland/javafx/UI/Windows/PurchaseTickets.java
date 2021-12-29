package nl.inholland.javafx.UI.Windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.Model.Employee;
import nl.inholland.javafx.Model.Showing;
import nl.inholland.javafx.UI.StylesScene;

public class PurchaseTickets extends MainWindow{

    private Stage stage;
    private Employee employee;
    private Database db;

    private GridPane showings;
    private GridPane purchaseTickets;

    private Label pageTitle;
    private VBox layout;

    private Label lblRoom;
    private Label lblStart;
    private Label lblEnd;
    private Label lblTitle;
    private Label lblSeats;
    private Label lblName;
    private Label roomNumber;
    private Label startTime;
    private Label endTime;
    private Label movieTitle;

    private TextField txtCustomerName;

    private ComboBox nrOfSeats;

    private Button purchaseButton;
    private Button clearButton;


    private TableView<Showing> room2TableView;
    private TableView<Showing> room1TableView;


    private MenuBar menuBar;


    public PurchaseTickets(Database db, Employee employee) {
        super(db,employee);

        this.layout = getLayout();
        this.db = db;
        this.employee = employee;
        this.stage = getStage();
        this.menuBar = getMenuBar();

        this.pageTitle = getPageTitle();
        pageTitle.setText("Purchase Tickets");

        stage.setTitle("Fantastic Cinema -- -- purchase tickets-- username: "+ employee.getUserName());

        this.room1TableView = getRoom1TableView();
        this.room2TableView = getRoom2TableView();

        this.showings = getShowings();

        purchaseTickets = createPurchaseForm();

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                purchaseTickets.setVisible(false);
                txtCustomerName.clear();
                nrOfSeats.setValue(0);
            }
        });


        room1TableView.setOnMouseClicked(mouseEvent ->  {
            purchaseTickets.setVisible(true);
            Showing showings = room1TableView.getSelectionModel().getSelectedItem();
            roomNumber.setText("Room " + showings.getRoomNumber());
            startTime.setText(showings.getLocalStartTime());
            endTime.setText(showings.getLocalEndTime());
            movieTitle.setText(showings.getMovie().getTitle());
            nrOfSeats.getItems().clear();

            for (int i = 1; i < showings.getNumberOfSeats() + 1; i++) {
                nrOfSeats.getItems().addAll(i);
            }

            txtCustomerName.clear();
            nrOfSeats.setValue(0);
        });



        room2TableView.setOnMouseClicked(mouseEvent -> {
            purchaseTickets.setVisible(true);
            Showing showings = room2TableView.getSelectionModel().getSelectedItem();
            roomNumber.setText("Room " + showings.getRoomNumber());
            startTime.setText(showings.getLocalStartTime());
            endTime.setText(showings.getLocalEndTime());
            movieTitle.setText(showings.getMovie().getTitle());
            nrOfSeats.getItems().clear();

            for (int i = 1; i < showings.getNumberOfSeats() + 1; i++) {
                nrOfSeats.getItems().addAll(i);
            }

            txtCustomerName.clear();
            nrOfSeats.setValue(0);
        });


        purchaseButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                if (!txtCustomerName.getText().equals("") && !nrOfSeats.getValue().equals(0)) {
//
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to confirm purchase?", ButtonType.YES, ButtonType.CANCEL);
                    confirmationAlert.showAndWait();

                    if (roomNumber.getText().equals("Room 1")) {

                        for (Showing showing : db.getRoom1()) {

                            if (showing.getMovieTitle().equals(movieTitle.getText()) && showing.getLocalStartTime().equals(startTime.getText()) && confirmationAlert.getResult().equals(ButtonType.YES)) {

                                int newNumberOfSeats = showing.getNumberOfSeats() - (int) nrOfSeats.getValue();
                                showing.setNumberOfSeats(newNumberOfSeats);

                                room1TableView.refresh();


                            }
                            purchaseTickets.setVisible(false);
                        }


                    } else if (roomNumber.getText().equals("Room 2")) {

                        for (Showing showing : db.getRoom2()) {
                            if (showing.getMovieTitle().equals(movieTitle.getText()) && showing.getLocalStartTime().equals(startTime.getText()) && confirmationAlert.getResult().equals(ButtonType.YES)) {

                                showing.setNumberOfSeats(showing.getNumberOfSeats() - (int) nrOfSeats.getValue());
                                room2TableView.refresh();

                            }
                            purchaseTickets.setVisible(false);

                        }
                    }


                }else {

                    Alert missingAlert = new Alert(Alert.AlertType.WARNING,"Missing fields.",ButtonType.OK);
                    missingAlert.show();
                }

            }
        });

        layout.getChildren().addAll(menuBar,pageTitle,showings,purchaseTickets);

        Scene scene = new StylesScene(layout);
        stage.setScene(scene);
        stage.show();
    }


    public GridPane createPurchaseForm(){

        purchaseTickets = new GridPane();
        purchaseTickets.setPadding(new Insets(25));
        purchaseTickets.setPrefWidth(800);
        purchaseTickets.setVgap(10);
        purchaseTickets.setHgap(40);

        lblRoom = new Label("Room");
        lblStart = new Label("Start");
        lblEnd = new Label("End");
        lblTitle = new Label("Movie title");
        lblSeats = new Label("No. of seats");
        lblName = new Label("Name");

        roomNumber = new Label();
        startTime = new Label();
        endTime = new Label();
        movieTitle = new Label();

        txtCustomerName = new TextField();
        txtCustomerName.setPromptText("Name...");

        nrOfSeats = new ComboBox();

        purchaseButton = new Button("Purchase");
        purchaseButton.setMinWidth(120);

        clearButton = new Button("Clear");
        clearButton.setMinWidth(120);

        GridPane.setConstraints(lblRoom, 0, 0);
        GridPane.setConstraints(roomNumber, 1, 0);

        GridPane.setConstraints(lblStart, 0, 1);
        GridPane.setConstraints(startTime, 1, 1);

        GridPane.setConstraints(lblEnd, 0, 2);
        GridPane.setConstraints(endTime, 1, 2);

        GridPane.setConstraints(lblTitle, 2, 0);
        GridPane.setConstraints(movieTitle, 3, 0);

        GridPane.setConstraints(lblSeats, 2, 1);
        GridPane.setConstraints(nrOfSeats, 3, 2);

        GridPane.setConstraints(lblName, 2, 2);
        GridPane.setConstraints(txtCustomerName, 3, 2);

        GridPane.setConstraints(purchaseButton, 4, 1);
        GridPane.setConstraints(clearButton, 4, 2);
        GridPane.setConstraints(nrOfSeats, 3, 1);

        purchaseTickets.getChildren().addAll(lblRoom, roomNumber, lblStart, startTime, lblEnd, endTime, lblTitle, movieTitle, lblSeats, nrOfSeats, lblName, txtCustomerName, purchaseButton, clearButton);

        purchaseTickets.setVisible(false);
        purchaseTickets.setStyle("-fx-background-color: #00325A");

        return purchaseTickets;

    }


}

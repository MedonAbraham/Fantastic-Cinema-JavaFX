package nl.inholland.javafx.UI.Windows;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.Model.Employee;
import nl.inholland.javafx.UI.StylesScene;

public class LoginWindow {

    private Label usernameLabel;
    private Label passwordLabel;
    private Label warningLabel;
    private TextField usernameInput;
    private PasswordField passwordInput;
    private Button login;

    private Database db;

    public LoginWindow(Stage window){
        db = new Database();
        createLoginScreen(db,window);

    }

    public LoginWindow(Database db){
        this.db = db;
        createLoginScreen(db,new Stage());
    }

    public void createLoginScreen(Database db,Stage window){

        window.setHeight(250);
        window.setWidth(350);
        window.setTitle("Fabulous Cinema -- Login");

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setPadding(new Insets(20));

        VBox box = new VBox();
        box.setPadding(new Insets(20));

        usernameLabel = new Label("Username");
        passwordLabel = new Label("Password");
        warningLabel = new Label();
        warningLabel.setStyle("-fx-text-fill: RED");

        usernameInput = new TextField();
        usernameInput.setPromptText("username...");

        passwordInput =  new PasswordField();
        passwordInput.setPromptText("password...");

        login = new Button("Login");
        login.setPrefHeight(40);
        login.setPrefWidth(60);


        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String username = usernameInput.getText();
                    String password = passwordInput.getText();

                    if(!username.isEmpty() && !password.isEmpty()){

                        Employee employee = db.checkAuthentication(username,password);

                        if(employee != null){

                            new PurchaseTickets(db,employee);
                            window.close();
                        }
                        else {
                            usernameInput.clear();
                            passwordInput.clear();

                            warningLabel.setText("Invalid login credentials. Please try again.");


                        }

                    } else {

                        warningLabel.setText("Required fields have not been filled in !");

                    }
                }catch (Exception e){
                    warningLabel.setText("Error! Try again.");
                }
            }
        });

        GridPane.setConstraints(usernameLabel,0,0);
        GridPane.setConstraints(passwordLabel,0,1);
        GridPane.setConstraints(usernameInput,1,0);
        GridPane.setConstraints(passwordInput,1,1);
        GridPane.setConstraints(login,0,2);

        pane.getChildren().addAll(usernameLabel,usernameInput,passwordLabel,passwordInput,login);
        box.getChildren().addAll(pane, warningLabel);


        Scene loginWindow = new StylesScene(box);
        box.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        window.setScene(loginWindow);
        window.show();


    }

}

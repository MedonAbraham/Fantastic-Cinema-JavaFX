package nl.inholland.javafx;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.javafx.DAO.Database;
import nl.inholland.javafx.UI.Windows.LoginWindow;
import javafx.application.Application;
import nl.inholland.javafx.UI.Windows.MainWindow;
import nl.inholland.javafx.Model.Employee;

public class App extends Application {

    private Database db;

   
    public static void main(String[] args) {launch(args);}

    public void start(Stage stage) throws Exception{

        new LoginWindow(stage);


    }
}

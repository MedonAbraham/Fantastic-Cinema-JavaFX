package nl.inholland.javafx.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.javafx.Model.*;

import java.util.List;

public class Database {

    private List<Employee> employees;

    private ObservableList<Movie> movies;
    private ObservableList<Showing> showings;



    private ObservableList<Showing> room1;
    private ObservableList<Showing> room2;


    public Database(){

        movies = FXCollections.observableArrayList();

        employees = new ArrayList<>();

        showings = FXCollections.observableArrayList();
        room1 = FXCollections.observableArrayList();
        room2 = FXCollections.observableArrayList();

        addMovies();
        addShowings();
        addEmployee();

        room1Showings();
        room2Showings();
    }

    private void addEmployee(){
        employees.add(new Employee("Noah","Abebe","noAbe","noah123", AccessLevel.USER));
        employees.add(new Employee("Medon","Abraham","medAb","medon234", AccessLevel.ADMIN));

    }

    private void addMovies(){
        movies.add(new Movie("No time to die", 95,12.0));
        movies.add(new Movie( "The Addams Family 19", 62,9.0));
    }

    private void addShowings(){


        showings.add(new Showing(movies.get(0),200,LocalDateTime.of(2021,10,9,20,0),LocalDateTime.of(2021,10,9,22,5),1));
        showings.add(new Showing(movies.get(1),100,LocalDateTime.of(2021,10,9,20,0),LocalDateTime.of(2021,10,9,21,32),2));
        showings.add(new Showing(movies.get(0),100,LocalDateTime.of(2021,10,9,22,0),LocalDateTime.of(2021,10,10,0,5),2));
        showings.add(new Showing(movies.get(1),200,LocalDateTime.of(2021,10,9,22,30),LocalDateTime.of(2021,10,10,0,2),1));


    }

    private void room1Showings(){
        for (Showing showing: showings) {
            if(showing.getRoomNumber() == 1){
                room1.add(showing);
            }
        }
    }

    private void room2Showings(){
        for (Showing showing:showings) {
            if(showing.getRoomNumber() == 2){
                room2.add(showing);
            }
        }
    }

    public ObservableList<Movie> getMovies() {return movies;}

    public void setMovies(ObservableList<Movie> movies) {
        this.movies = movies;
    }

    public ObservableList<Showing> getRoom1() {return room1;}

    public ObservableList<Showing> getRoom2() {return room2;}

    public ObservableList<Showing> getShowings() {return showings;}

    public List<Employee> getEmployees() {return employees;}


    public Employee checkAuthentication(String username, String password){

        Employee employee;
        for (Employee e: employees){
            if(e.getUserName().equals(username)){
                employee = e;

                if(e.getPassword().equals(password)){
                    return  employee;
                }
            }
        }
        return null;
    }


}

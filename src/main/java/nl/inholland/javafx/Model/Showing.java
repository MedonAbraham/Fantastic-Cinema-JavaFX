package nl.inholland.javafx.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Showing {

    private Movie movie;
    private String movieTitle;
    private double moviePrice;

    private LocalDateTime startTime;
    private String localStartTime;

    private LocalDateTime endTime;
    private String localEndTime;

    private int roomNumber;
    private int numberOfSeats;

    public Showing(Movie movie, int numberOfSeats , LocalDateTime startTime, LocalDateTime endTime,int roomNumber) {
        this.movie = movie;
        this.numberOfSeats = numberOfSeats;

        this.startTime = startTime;
        this.localStartTime = startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        this.endTime = endTime;
        this.localEndTime = endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        this.roomNumber = roomNumber;
        this.movieTitle = movie.getTitle();
        this.moviePrice = movie.getPrice();

    }

    public String getLocalStartTime() {
        return localStartTime;
    }

    public void setLocalStartTime(String localStartTime) {
        this.localStartTime = localStartTime;
    }

    public String getLocalEndTime() {return localEndTime;}

    public void setLocalEndTime(String localEndTime) {
        this.localEndTime = localEndTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(double moviePrice) {
        this.moviePrice = moviePrice;
    }

    public LocalDateTime getStartTime() {return startTime.minusMinutes(15);}

    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    public LocalDateTime getEndTime() {return endTime;}

    public void setEndTime(LocalDateTime endTime) {this.endTime = endTime;}

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}

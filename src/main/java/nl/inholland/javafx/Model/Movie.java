package nl.inholland.javafx.Model;



public class Movie {

    private String title;
    private int runningTime;
    private double price;

    public Movie(String title, int duration, double price) {

        this.title = title;
        this.runningTime = duration;
        this.price = price;
    }

    public Movie() {

    }


    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public int getRunningTime() {return runningTime;}

    public void setRunningTime(int runningTime) {this.runningTime = runningTime;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}
}

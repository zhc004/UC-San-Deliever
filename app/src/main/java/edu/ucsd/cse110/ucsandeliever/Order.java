package edu.ucsd.cse110.ucsandeliever;

/**
 * Created by Peterli on 10/23/16.
 */

public class Order {


    private String restaurants;

    private String item;

    private String time;
    private String destination;
    private String requestor;

    public Order(){}


    public String getRestaurants( ) {
        return this.restaurants;
    }

    public void setRestaurants(String restaurants) {
        this.restaurants = restaurants;
    }

    public String getItem( ) {
        return this.item;
    }

    public void setItem(String item2) {
        this.item = item2;
    }

    public String getTime( ) {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDestination( ) {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRequestor( ) {
        return this.requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }




}
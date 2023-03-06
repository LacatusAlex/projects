package org.example.business;

import java.util.Objects;

public class MenuItem {

    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;
    private int timesOrdered=0;

    public MenuItem(String title, double rating, int calories, int protein, int fat, int sodium, int price) {

        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
        this.title=title;
    }
    public MenuItem(String title){
        this.title=title;
    }
    public MenuItem(){
        //empty
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int computePrice(){
        return price;
    }
    public boolean setParams(String[] params){
        if(params.length==7){
            title=params[0];
            rating=Double.parseDouble( params[1]);
            calories=Integer.parseInt( params[2]);
            protein=Integer.parseInt( params[3]);
            fat=Integer.parseInt( params[4]);
            sodium=Integer.parseInt( params[5]);
            price=Integer.parseInt( params[6]);
            return true;
        }
        if(params.length==6){

            rating=Double.parseDouble( params[1]);
            calories=Integer.parseInt( params[2]);
            protein=Integer.parseInt( params[3]);
            fat=Integer.parseInt( params[4]);
            sodium=Integer.parseInt( params[5]);
            price=Integer.parseInt( params[6]);
            return true;
        }
        System.out.println("Wrong number of args");
        return false;



    }

    public void update(){
    }
    public void incrementTimesOrdered(){
        timesOrdered++;
    }

    public int getTimesOrdered() {
        return timesOrdered;
    }

    public void setTimesOrdered(int timesOrdered) {
        this.timesOrdered = timesOrdered;
    }
    public boolean isWellGraded(){
        if(getRating()>3) return true;
        return false;
    }
    @Override
    public int hashCode(){return Objects.hash(title);}
    @Override
    public boolean equals(Object o){

        if(o instanceof MenuItem){
            return title.equals(((MenuItem) o).getTitle());
        }
        return false;
    }
}

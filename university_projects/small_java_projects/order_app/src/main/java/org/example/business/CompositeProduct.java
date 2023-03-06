package org.example.business;


import java.util.HashSet;
import java.util.Set;

public class CompositeProduct extends MenuItem{
    private Set<MenuItem> itemSet;
    public CompositeProduct(String title,Set<MenuItem> itemSet) {
        super(title);

        this.itemSet=itemSet;
        update();

    }

    @Override
    public int computePrice() {
        int sum=0;
        for(MenuItem item:itemSet){
            sum+=item.computePrice();
        }
        setPrice(sum);
        return sum;
    }
    @Override
    public void update(){
        double rating=0;
        int calories=0;
        int protein=0;
        int fat=0;
        int sodium=0;
        for(MenuItem item:itemSet){
            rating+=item.getRating();
            calories+=item.getCalories();
            protein+=item.getProtein();
            fat+=item.getFat();
            sodium+=item.getSodium();
        }
        rating/=itemSet.size();
        setRating(rating);
        setCalories(calories);
        setProtein(protein);
        setFat(fat);
        setSodium(sodium);
        computePrice();
    }
}

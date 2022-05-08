package org.example.data.models;

import org.jetbrains.annotations.NotNull;

public class Monomial implements Comparable<Monomial>{
    private int power;

    public Monomial(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
    public Polynomial toPolynomial(){
        Polynomial p = new Polynomial();
        p.addMonomial(this);
        return p;
    }

    @Override
    public int compareTo(@NotNull Monomial o) {
        if(getPower()>o.getPower()){
            return -1;
        }
        else return 1;

    }
}

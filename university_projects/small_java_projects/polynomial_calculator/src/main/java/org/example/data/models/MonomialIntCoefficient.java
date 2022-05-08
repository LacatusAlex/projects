package org.example.data.models;

public class MonomialIntCoefficient extends Monomial{
    private int coefficient;


    public MonomialIntCoefficient(int power,int coefficient) {
        super(power);
        this.coefficient=coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
    public String toString(){
        return "coeff"+coefficient+" power"+ getPower();
    }
}

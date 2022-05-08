package org.example.logic;

import org.example.data.models.Monomial;
import org.example.data.models.MonomialDoubleCoefficient;
import org.example.data.models.MonomialIntCoefficient;
import org.example.data.models.Polynomial;

import java.util.ArrayList;
import java.util.Collections;

public class Operations {
    public static Polynomial add(Polynomial p1,Polynomial p2) {

        Polynomial res=new Polynomial();
        for(Monomial m1: p1.getMonomials()) {
            res.addMonomial(m1);

        }
        for(Monomial m2: p2.getMonomials()) {
            res.addMonomial(m2);

        }
        Collections.sort(res.getMonomials());
        return res;
    }

    public static Polynomial subtract(Polynomial p1,Polynomial p2) {

        Polynomial res=new Polynomial();
        for(Monomial m1: p1.getMonomials()) {



            res.addMonomial(m1);

        }
        for(Monomial m2: p2.getMonomials()) {
            if( m2 instanceof MonomialIntCoefficient) {
                int coefficient = ((MonomialIntCoefficient) m2).getCoefficient();
                ((MonomialIntCoefficient) m2).setCoefficient(-coefficient);
                res.addMonomial(m2);
            }
            else{
                double coefficient = ((MonomialDoubleCoefficient) m2).getCoefficient();
                ((MonomialDoubleCoefficient) m2).setCoefficient(-coefficient);
                res.addMonomial(m2);

            }

        }
        Collections.sort(res.getMonomials());
        return res;
    }

    public static Polynomial multiply(Polynomial p1,Polynomial p2){


        Polynomial res=new Polynomial();
        for(Monomial m1:p1.getMonomials()){
            for(Monomial m2:p2.getMonomials()){
                if(m1 instanceof MonomialDoubleCoefficient){
                    int power= m1.getPower() + m2.getPower();
                    double coefficient=((MonomialIntCoefficient) m2).getCoefficient() * ((MonomialDoubleCoefficient) m1).getCoefficient();
                    MonomialDoubleCoefficient mRes = new MonomialDoubleCoefficient(power, coefficient);
                    res.addMonomial(mRes);
                }
                else {
                    int power = m1.getPower() + m2.getPower();
                    int coefficient = ((MonomialIntCoefficient) m2).getCoefficient() * ((MonomialIntCoefficient) m1).getCoefficient();
                    MonomialIntCoefficient mRes = new MonomialIntCoefficient(power, coefficient);
                    res.addMonomial(mRes);
                }

            }
        }
        Collections.sort(res.getMonomials());
        return res;
    }

    public static Polynomial derivative(Polynomial p1){

        Polynomial res=new Polynomial();
        for(Monomial m1:p1.getMonomials()){
            int power=m1.getPower();
            int coefficient=((MonomialIntCoefficient)m1).getCoefficient();
            MonomialIntCoefficient mRes=new MonomialIntCoefficient(power-1,coefficient*power);
            if(power>=1){
                res.addMonomial(mRes);
            }

        }
        Collections.sort(res.getMonomials());
        return res;
    }

    public static Polynomial integration(Polynomial p1){


        Polynomial res=new Polynomial();
        for(Monomial m1:p1.getMonomials()){
            int power=m1.getPower();
            int coefficient=((MonomialIntCoefficient)m1).getCoefficient();
            Monomial mRes  ;
            if(coefficient%(power+1)==0) {
                 mRes = new MonomialIntCoefficient(power + 1, coefficient / (power+1));
            }
            else {
                double dPower=power;
                double dCoefficient=coefficient;
                 mRes = new MonomialDoubleCoefficient(power +1,dCoefficient/(dPower+1));
            }

            res.addMonomial(mRes);


        }
        Collections.sort(res.getMonomials());
        return res;

    }
    public static ArrayList<Polynomial> divide(Polynomial p1,Polynomial p2){
        Polynomial q=new Polynomial();
        Polynomial r=p1.copy();
        ArrayList<Polynomial> res = new ArrayList<>();
        while(!r.getMonomials().isEmpty() && r.degree()>=p2.degree()){
            Polynomial t=new Polynomial();
            Monomial d1=p2.copy().leading();
          //  System.out.println(d1);
            Monomial r1=r.copy().leading();
        //    System.out.println(r1);
            double coefficient1;
            double coefficient2;
            if(d1 instanceof MonomialIntCoefficient) coefficient1= ((MonomialIntCoefficient) d1).getCoefficient();
            else coefficient1=((MonomialDoubleCoefficient)d1).getCoefficient();
            if(r1 instanceof MonomialIntCoefficient) coefficient2= ((MonomialIntCoefficient) r1).getCoefficient();
            else coefficient2=((MonomialDoubleCoefficient)r1).getCoefficient();
            Monomial m = new MonomialDoubleCoefficient(r1.getPower()- d1.getPower(),coefficient2/coefficient1);
            t.addMonomial(m);
           // System.out.println(t.toString());
            q=add(q.copy(),t.copy());
            r=subtract(r.copy(),multiply(t.copy(),p2.copy()));
           // System.out.println(q);
           // System.out.println(r);

        }
        Collections.sort(r.getMonomials());
        Collections.sort(q.getMonomials());
        res.add(r);
        res.add(q);
        return res;

    }






}

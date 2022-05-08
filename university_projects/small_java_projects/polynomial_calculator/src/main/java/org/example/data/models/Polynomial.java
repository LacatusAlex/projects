package org.example.data.models;

import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Monomial> monomials = new ArrayList<>();

    public ArrayList<Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
    }
    public int addMonomial(Monomial monomial) {
        if (monomial != null) {
            for (Monomial mono : monomials) {
                if (mono.getPower() == monomial.getPower()) {
                    double coeff1 = 0;
                    double coeff2 = 0;
                    if (mono instanceof MonomialIntCoefficient)
                        coeff1 = ((MonomialIntCoefficient) mono).getCoefficient();
                    else coeff1 = ((MonomialDoubleCoefficient) mono).getCoefficient();
                    if (monomial instanceof MonomialIntCoefficient)
                        coeff2 = ((MonomialIntCoefficient) monomial).getCoefficient();
                    else coeff2 = ((MonomialDoubleCoefficient) monomial).getCoefficient();
                    if (mono instanceof MonomialIntCoefficient) {
                        MonomialIntCoefficient m = (MonomialIntCoefficient) mono;
                        m.setCoefficient((int) (coeff1 + coeff2));
                        if (m.getCoefficient() == 0) monomials.remove(mono);
                    } else {
                        MonomialDoubleCoefficient m = (MonomialDoubleCoefficient) mono;
                        m.setCoefficient(coeff1 + coeff2);
                        if (m.getCoefficient() <= 0.001 && m.getCoefficient()>=-0.001) monomials.remove(mono);
                    }
                    return 1;
                }
            }
            if (monomial instanceof MonomialIntCoefficient) {
                MonomialIntCoefficient m = (MonomialIntCoefficient) monomial;
                if(m.getCoefficient()!=0)monomials.add(monomial);}
            else{
                MonomialDoubleCoefficient m = (MonomialDoubleCoefficient) monomial;
                if(m.getCoefficient()>=0.001 || m.getCoefficient()<=-0.001)monomials.add(monomial);}
            return 1;
        } else return 0;
    }

    public int degree(){
        int maxPower=0;
        for(Monomial m1:monomials){
            if(m1.getPower()>maxPower) maxPower= m1.getPower();
        }
        return maxPower;
    }

    public Monomial leading(){
        Monomial m= new MonomialIntCoefficient(0,0) ;
        for(Monomial m1:monomials){
            if(m1.getPower()>=m.getPower()) m= m1;
        }
        return m;

    }
    public static Polynomial getPolynomialFromString(String polynomial){
        Polynomial p=new Polynomial();
        int c=0;
        while(c<polynomial.length()){
            int sign=1;
            if(polynomial.charAt(c)=='+')c++;
            if(c<polynomial.length()&&polynomial.charAt(c)=='-'){
                sign=-1;
                c++;
            }
            int coefficient=0;
            int power=0;
            if(c>=polynomial.length())break;
            int caux=c;
            while(polynomial.charAt(c)>='0' &&polynomial.charAt(c)<='9'){
                coefficient=coefficient*10+(polynomial.charAt(c)-'0');
                c++;
                if(c>=polynomial.length())break;
            }
            if(caux==c)coefficient=1;
            c+=2;
            if(c>=polynomial.length())break;
            while(polynomial.charAt(c)>='0' &&polynomial.charAt(c)<='9' ){
                power=power*10+(polynomial.charAt(c)-'0');
                c++;
                if(c>=polynomial.length())break;
            }
            MonomialIntCoefficient m=new MonomialIntCoefficient(power,sign*coefficient);
            p.addMonomial(m);
        }
        return p;
    }
    public  String toString(){
        String polynomial="";
        for(Monomial m:monomials) {

            double coeff = 0;

            if (m instanceof MonomialIntCoefficient)
                coeff = ((MonomialIntCoefficient) m).getCoefficient();
            else coeff = ((MonomialDoubleCoefficient) m).getCoefficient();
            if(coeff>0) {
                polynomial += "+" +String.format("%.2f", coeff) + "x^" + m.getPower();
            }
            else polynomial += String.format("%.2f", coeff) + "x^" + m.getPower();

        }
        return polynomial;

    }
    public boolean equals(Polynomial p){
        if(monomials.size()!=p.getMonomials().size()) return false;
        for(int i=0;i<monomials.size();i++){
            if(monomials.get(i) instanceof MonomialIntCoefficient) {
                if (((MonomialIntCoefficient) monomials.get(i)).getCoefficient() != ((MonomialIntCoefficient) p.getMonomials().get(i)).getCoefficient())
                    return false;
            }
            else{

                if((((MonomialDoubleCoefficient) monomials.get(i)).getCoefficient()-((MonomialIntCoefficient) p.getMonomials().get(i)).getCoefficient())>=0.01 ||
                        (((MonomialDoubleCoefficient) monomials.get(i)).getCoefficient()-((MonomialIntCoefficient) p.getMonomials().get(i)).getCoefficient())<=-0.01) return false;
            }
        }
        return true;
    }
    public Polynomial copy(){

        Polynomial p=new Polynomial();
        for(Monomial m:monomials){
            if(m instanceof MonomialIntCoefficient){
                p.addMonomial(new MonomialIntCoefficient(m.getPower(),((MonomialIntCoefficient) m).getCoefficient()));
            }
            else if( m instanceof MonomialDoubleCoefficient){
                p.addMonomial((new MonomialDoubleCoefficient(m.getPower(),((MonomialDoubleCoefficient) m).getCoefficient())));
            }

        }
        return p;
    }


}

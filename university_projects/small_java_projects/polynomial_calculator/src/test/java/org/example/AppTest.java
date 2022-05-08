package org.example;

import static org.junit.Assert.assertTrue;

import org.example.data.models.Monomial;
import org.example.data.models.MonomialIntCoefficient;
import org.example.data.models.Polynomial;
import org.example.logic.Operations;
import org.junit.Test;

import java.util.Collections;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void addTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(0,2));
        p.addMonomial(new MonomialIntCoefficient(1,3));
        Collections.sort(p.getMonomials());
        assertTrue("Operation add doesn't works as intended",Operations.add(Polynomial.getPolynomialFromString("1x^1+1x^0")
                        ,Polynomial.getPolynomialFromString("2x^1+1x^0")).equals(p)
                );
    }
    @Test
    public void subtractTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(0,1));
        p.addMonomial(new MonomialIntCoefficient(1,1));
        Collections.sort(p.getMonomials());
        assertTrue("Operation subtract doesn't works as intended",Operations.subtract(Polynomial.getPolynomialFromString("2x^1")
                ,Polynomial.getPolynomialFromString("1x^1-1x^0")).equals(p)
        );
    }
    @Test
    public void multiplyTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(0,2));
        p.addMonomial(new MonomialIntCoefficient(1,3));
        p.addMonomial(new MonomialIntCoefficient(2,1));
        Collections.sort(p.getMonomials());
        assertTrue("Operation multiply doesn't works as intended",Operations.multiply(Polynomial.getPolynomialFromString("1x^1+2x^0"),
                Polynomial.getPolynomialFromString("1x^1+1x^0")).equals(p)
        );
    }

    @Test
    public void divideTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(0,2));
        p.addMonomial(new MonomialIntCoefficient(1,5));
        Collections.sort(p.getMonomials());
        Polynomial r=new Polynomial();
        r.addMonomial(new MonomialIntCoefficient(0,-2));



        assertTrue("Operation divide doesn't works as intended",Operations.divide(Polynomial.getPolynomialFromString("5x^2+7x^1"),
                Polynomial.getPolynomialFromString("1x^1+1x^0")).get(1).equals(p)
                && Operations.divide(Polynomial.getPolynomialFromString("5x^2+7x^1"),Polynomial.getPolynomialFromString("1x^1+1x^0")).get(0).equals(r)
        );
    }
    @Test
    public void derivativeTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(1,4));
        p.addMonomial(new MonomialIntCoefficient(0,2));



        assertTrue("Operation derivative doesn't works as intended",
                Operations.derivative(Polynomial.getPolynomialFromString("2x^2+2x^1")).equals(p)
        );
    }

    @Test
    public void integrationTest(){
        Polynomial p=new Polynomial();
        p.addMonomial(new MonomialIntCoefficient(1,2));
        p.addMonomial(new MonomialIntCoefficient(2,1));

        Collections.sort(p.getMonomials());
        assertTrue("Operation integration doesn't works as intended",
                Operations.integration(Polynomial.getPolynomialFromString("2x^1+2x^0")).equals(p)
        );
    }

}

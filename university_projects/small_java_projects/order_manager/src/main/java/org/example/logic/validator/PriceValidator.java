package org.example.logic.validator;

import org.example.model.Product;

import java.util.regex.Pattern;

/**
 * Class that validates the price of a product
 */
public class PriceValidator implements Validator<Product> {

    @Override
    public void validate(Product product) {


        if (product.getPrice()<0) {
            throw new IllegalArgumentException("Price is not a valid price!");
        }
    }
}

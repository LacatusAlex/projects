package org.example.logic;

import org.example.data.access.ProductDAO;
import org.example.logic.validator.*;
import org.example.model.Client;
import org.example.model.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * ProductLogic class represents the highest level in the architecture for a product having the ProductDAO and
 * being directly called at the higher level of our app.
 * It is also the one responsible for calling the validators for each product
 */

public class ProductLogic {
    private ArrayList<Validator<Product>> validators;
    private ProductDAO productDAO;
    public ProductLogic(){
        validators= new ArrayList<>();
        validators.add(new PriceValidator());

        productDAO= new ProductDAO();

    }

    public Product findProductById( int id){
        Product product = productDAO.findById(id);
        for(Validator validator:validators){
            validator.validate(product);
        }
        if(product==null){
            throw new NoSuchElementException();
        }
        return product;
    }

    public Product validate(Product product){
        for(Validator validator :validators){

            validator.validate(product);
        }
        if(product==null){
            throw new NoSuchElementException();
        }
        return product;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}

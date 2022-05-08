package org.example.logic;

import org.example.data.access.ClientDAO;
import org.example.logic.validator.*;
import org.example.model.Client;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * ClientLogic class represents the highest level in the architecture for a client having the ClientDAO and
 * being directly called at the higher level of our app.
 * It is also the one responsible for calling the validators for each client
 */

public class ClientLogic {
    private ArrayList<Validator<Client>> validators;
    private ClientDAO clientDAO;
    public ClientLogic(){
        validators= new ArrayList<>();
        validators.add(new AddressValidator());
        validators.add(new EmailValidator());
        validators.add(new ContactValidator());

        clientDAO= new ClientDAO();

    }

    public Client findClientById( int id){
        Client client = clientDAO.findById(id);
        for(Validator validator :validators){

            validator.validate(client);
        }
        if(client==null){
            throw new NoSuchElementException();
        }
        return client;
    }

    public Client validate(Client client){
        for(Validator validator :validators){

            validator.validate(client);
        }
        if(client==null){
            throw new NoSuchElementException();
        }
        return client;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
}

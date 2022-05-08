package org.example.logic.validator;

import org.example.model.Client;

import java.util.regex.Pattern;

/**
 * class that validates the addresses of the clients
 */

public class AddressValidator implements Validator<Client>{

    private static final String ADDRESS_PATTERN =".*?[str]";
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
        if (!pattern.matcher(client.getAddress()).matches()) {
            throw new IllegalArgumentException("Address is not a valid address!");
        }
    }

}

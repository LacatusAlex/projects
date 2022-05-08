package org.example.logic.validator;

import org.example.model.Client;

import java.util.regex.Pattern;

/**
 * Class that validates the contact of the clients
 */

public class ContactValidator implements Validator<Client>{
    private static final String CONTACT_PATTERN ="\\d+";
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(CONTACT_PATTERN);
        if (!pattern.matcher(client.getContact()).matches()) {
            throw new IllegalArgumentException("Contact is not a valid contact!");
        }
    }
}

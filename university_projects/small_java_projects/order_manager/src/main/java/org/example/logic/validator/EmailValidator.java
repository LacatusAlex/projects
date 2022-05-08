package org.example.logic.validator;

import org.example.model.Client;

import java.util.regex.Pattern;

/**
 * Class that validates the email of a client
 */
public class EmailValidator implements Validator<Client> {
    private static final String EMAIL_PATTERN =".*?[@].*?[.].*";

    @Override
    public void validate(Client client) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(client.getEmail()).matches()) {
            throw new IllegalArgumentException("Email is not a valid email!");
        }
    }
}

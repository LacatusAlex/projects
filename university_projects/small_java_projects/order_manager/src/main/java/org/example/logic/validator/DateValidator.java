package org.example.logic.validator;

import org.example.model.Orders;

import java.util.regex.Pattern;

/**
 * Class that validates the date for an order
 */
public class DateValidator implements Validator<Orders> {
    private static final String DATE_PATTERN ="[0-3][0-9][\\/][0-1][0-9][\\/][0-9]{2}";
    @Override
    public void validate(Orders orders) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        if (!pattern.matcher(orders.getDate()).matches()) {
            throw new IllegalArgumentException("Date is not a valid date!");
        }
    }
}

package ddd.customers.domain;

import ddd.core.domain.CustomerId;

import java.time.LocalDate;

/**
 * Looks like CRUD.
 */
public class Customer {
    public final CustomerId id;
    public final String firstName;
    public final String lastName;
    public final LocalDate birthDate;

    public Customer(CustomerId id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}

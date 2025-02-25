package org.example.Assignment;
import java.util.Objects;

// Class representing an Invoice with a customer and a value
public class Invoice {
    private final String customer;  // Customer name associated with the invoice (immutable)
    private final int value;  // Invoice value (immutable)

    // Constructor to initialize the Invoice object with a customer name and value
    public Invoice(String customer, int value) {
        this.customer = customer;
        this.value = value;
    }

    // Overrides the equals method to compare two Invoice objects based on their attributes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Checks if the objects are the same instance
        if (o == null || getClass() != o.getClass()) return false;  // Checks for null or different class types
        Invoice invoice = (Invoice) o;  // Casts the object to an Invoice
        return value == invoice.value &&  // Compares the value attribute
                customer.equals(invoice.customer);  // Compares the customer attribute
    }

    // Overrides the hashCode method to generate a hash based on the customer's name and value
    @Override
    public int hashCode() {
        return Objects.hash(customer, value);  // Generates a hash using Java's Objects utility
    }

    // Overrides the toString method to provide a string representation of the Invoice object
    @Override
    public String toString() {
        return "Invoice{" +
                "customer='" + customer + '\'' +  // Appends the customer name
                ", value=" + value +  // Appends the value
                '}';  // Closes the string representation
    }

    // Getter method to retrieve the customer name
    public String getCustomer() {
        return customer;
    }

    // Getter method to retrieve the invoice value
    public int getValue() {
        return value;
    }
}

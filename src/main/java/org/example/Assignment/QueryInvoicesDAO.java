package org.example.Assignment;

import java.util.ArrayList;
import java.util.List;

// Class responsible for querying and saving invoices in the database
public class QueryInvoicesDAO {

    private Database connection;  // Represents the database connection object

    // Constructor that initializes the database connection using dependency injection
    public QueryInvoicesDAO(Database connection) {
        this.connection = connection;
    }

    // Method to retrieve all invoices from the database
    public List<Invoice> all() {
        return connection.withSql(() -> {  // Executes SQL operations within the database connection
            try (var ps = connection.getConnection().prepareStatement("select * from invoice")) {  // Prepares the SQL query to select all invoices
                final var rs = ps.executeQuery();  // Executes the query and stores the result set

                List<Invoice> allInvoices = new ArrayList<>();  // Creates a list to store all retrieved invoices
                while (rs.next()) {  // Iterates through each row in the result set
                    allInvoices.add(new Invoice(rs.getString("name"), rs.getInt("value")));  // Creates a new Invoice object and adds it to the list
                }
                return allInvoices;  // Returns the list of all invoices
            }
        });
    }

    // Method to retrieve all invoices with a value greater than or equal to the specified value
    public List<Invoice> allWithAtLeast(int value) {
        return connection.withSql(() -> {  // Executes SQL operations within the database connection
            try (var ps = connection.getConnection().prepareStatement("select * from invoice where value >= ?")) {  // Prepares the SQL query with a parameter for minimum value
                ps.setInt(1, value);  // Sets the value for the SQL query parameter
                final var rs = ps.executeQuery();  // Executes the query and stores the result set

                List<Invoice> allInvoices = new ArrayList<>();  // Creates a list to store filtered invoices
                while (rs.next()) {  // Iterates through each row in the result set
                    allInvoices.add(new Invoice(rs.getString("name"), rs.getInt("value")));  // Creates a new Invoice object and adds it to the list
                }
                return allInvoices;  // Returns the list of filtered invoices
            }
        });
    }

    // Method to save an invoice to the database
    public void save(Invoice inv) {
        connection.withSql(() -> {  // Executes SQL operations within the database connection
            try (var ps = connection.getConnection().prepareStatement("insert into invoice (name, value) values (?,?)")) {  // Prepares the SQL query to insert a new invoice
                ps.setString(1, inv.getCustomer());  // Sets the customer name in the query
                ps.setInt(2, inv.getValue());  // Sets the invoice value in the query
                ps.execute();  // Executes the insert query

                connection.getConnection().commit();  // Commits the transaction to make the changes permanent
            }
            return null;  // Returns null as this operation does not need to return any value
        });
    }
}

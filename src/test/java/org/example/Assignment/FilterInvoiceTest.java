package org.example.Assignment;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilterInvoiceTest {
    @Test
    public void filerInvoiceTest() {
        FilterInvoice invoice = new FilterInvoice(); // create a FilterInvoice instance
        List<Invoice> result = invoice.lowValueInvoices(); // call lowValueInvoices() from the instance

        assertNotNull(result); // checks if the result from method call is not null
    }
}


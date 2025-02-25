package org.example.Assignment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterInvoiceTest {
    @Test
    public void filterInvoiceTest() {
        // creates mock database and DAO
        QueryInvoicesDAO stubDao = mock(QueryInvoicesDAO.class);
        when(stubDao.all()).thenReturn(new ArrayList<>());

        FilterInvoice invoice = new FilterInvoice(stubDao); // create a FilterInvoice instance
        List<Invoice> result = invoice.lowValueInvoices(); // call lowValueInvoices() from the instance

        assertNotNull(result); // checks if the result from method call is not null
    }

    @Test
    public void filterInvoicesStubbedTest() {
        Database stubDb = mock(Database.class); // Creates a mock database
        QueryInvoicesDAO stubDao = mock(QueryInvoicesDAO.class); // creates a mock DAO that can return example invoices

        // stub the DAO to return this data when all() is called from the dao
        List<Invoice> invoices = Arrays.asList(
                new Invoice("Customer 1", 115),
                new Invoice("Customer B", 100),
                new Invoice("Customer C", 25));
        when(stubDao.all()).thenReturn(invoices);

        FilterInvoice filterInvoice = new FilterInvoice(stubDao); // injection of stubbed dao into FilterInvoice instance

        // now call the method and make assertion based on example invoices
        List<Invoice> result = filterInvoice.lowValueInvoices();
        assertEquals(1, result.size());
    }
}


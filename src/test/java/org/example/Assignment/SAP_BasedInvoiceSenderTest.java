package org.example.Assignment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SAP_BasedInvoiceSenderTest {
    @Test
    public void testWhenLowInvoicesSent() throws FailToSendSAPInvoiceException {
        // set up mocks for FilterInvoice and SAP
        FilterInvoice filter = mock(FilterInvoice.class);
        SAP sap = mock(SAP.class);

        // create example invoices for stubbed filter to return
        List<Invoice> invoices = Arrays.asList(
                new Invoice("Customer 1", 34),
                new Invoice("Customer B", 32),
                new Invoice("Customer C", 25));
        when(filter.lowValueInvoices()).thenReturn(invoices); // get stubbed filter to return the example invoices

        // create sender instance using stubbed filter and sap, then call method from the instance
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(filter, sap);
        sender.sendLowValuedInvoices();

        // use Mockito verify to check that send() is called once for each invoice
        verify(sap, times(1)).send(invoices.get(0));
        verify(sap, times(1)).send(invoices.get(1));
        verify(sap, times(1)).send(invoices.get(2));
        // verify(sap, times(3)).send(any(Invoice.class));
    }

    @Test
    public void testWhenNoInvoices() throws FailToSendSAPInvoiceException {
        // set up stubbed filter and sap
        FilterInvoice filter = mock(FilterInvoice.class);
        SAP sap = mock(SAP.class);

        // make it so that stubbed filter returns an empty list
        List<Invoice> emptyInvoices = Arrays.asList();
        when(filter.lowValueInvoices()).thenReturn(emptyInvoices);
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(filter, sap);

        // call method from the newly created sender instance, then check with verify that send() was not called
        sender.sendLowValuedInvoices();
        verify(sap, never()).send(any(Invoice.class));
    }

    @Test
    public void testThrowExceptionWhenBadInvoice() throws FailToSendSAPInvoiceException {
        // set up stubbed filter and sap
        FilterInvoice filter = mock(FilterInvoice.class);
        SAP sap = mock(SAP.class);

        // create example invoices for stubbed filter to return
        List<Invoice> invoices = Arrays.asList(
                new Invoice("Customer 1", 34),
                new Invoice("Customer B", 32),
                new Invoice("Customer C", 25));
        when(filter.lowValueInvoices()).thenReturn(invoices); // get stubbed filter to return the example invoices

        // stub the send() method so that it can throw an exception when called from any invoice
        doThrow(new FailToSendSAPInvoiceException("Failed to send")).when(sap).send(any(Invoice.class));

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(filter, sap);
        List<Invoice> failedInvoices = sender.sendLowValuedInvoices();

        verify(sap, times(invoices.size())).send(any(Invoice.class)); // verifies invoice attempts for send()
        assertEquals(invoices.size(), failedInvoices.size()); // all invoices should fail sending
    }
}
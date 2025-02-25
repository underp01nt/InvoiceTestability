package org.example.Assignment;

import java.util.List;
import java.util.ArrayList;


// Class responsible for sending low-valued invoices to the SAP system
public class SAP_BasedInvoiceSender {

    private final FilterInvoice filter;  // Dependency for filtering invoices
    private final SAP sap;  // Dependency for sending invoices to the SAP system
    private List<Invoice> failedValueInvoices = new ArrayList<>();

    // Constructor that uses dependency injection to initialize the filter and sap objects
    public SAP_BasedInvoiceSender(FilterInvoice filter, SAP sap) {
        this.filter = filter;
        this.sap = sap;
    }

    // modified to return failed invoices
    public List<Invoice> sendLowValuedInvoices() {
        List<Invoice> lowValuedInvoices = filter.lowValueInvoices();
        List<Invoice> faildValueInvoices = new ArrayList<>(); // setup list of invoices that have failed to be sent

        for (Invoice invoice : lowValuedInvoices) {  // Iterates through each invoice in the list
            try {
                sap.send(invoice);  // Sends the current invoice to the SAP system
            }
            catch (FailToSendSAPInvoiceException exception) {
                faildValueInvoices.add(invoice); // if send() fails, add failed invoice into faildValueInvoices list
                System.out.println("SAP invoice failed");
            }
        }

        return faildValueInvoices; // return list of failed invoices
    }
}

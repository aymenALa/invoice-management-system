package com.invoice_management_system.service;

import com.invoice_management_system.model.Invoice;
import com.invoice_management_system.model.User;
import com.invoice_management_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice createInvoice(Invoice invoice, User user) {
        if (invoiceRepository.existsByInvoiceNumber(invoice.getInvoiceNumber())) {
            throw new RuntimeException("Invoice number already exists");
        }
        invoice.setUser(user);
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesForUser(User user) {
        return invoiceRepository.findByUser(user);
    }

    public Optional<Invoice> getInvoiceByIdForUser(Long id, User user) {
        return invoiceRepository.findByIdAndUser(id, user);
    }

    public Invoice updateInvoice(Long id, Invoice updatedInvoice, User user) {
        Invoice existingInvoice = invoiceRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Invoice not found or does not belong to the user"));
        
        // Update only the fields that should be updatable
        existingInvoice.setInvoiceNumber(updatedInvoice.getInvoiceNumber());
        existingInvoice.setIssueDate(updatedInvoice.getIssueDate());
        existingInvoice.setDueDate(updatedInvoice.getDueDate());
        existingInvoice.setTotalAmount(updatedInvoice.getTotalAmount());
        existingInvoice.setStatus(updatedInvoice.getStatus());
        
        // The user remains the same
        // existingInvoice.setUser(user); -- This line is not needed as we're not changing the user

        return invoiceRepository.save(existingInvoice);
    }

    public void deleteInvoice(Long id, User user) {
        if (!invoiceRepository.existsByIdAndUser(id, user)) {
            throw new RuntimeException("Invoice not found or does not belong to the user");
        }
        invoiceRepository.deleteById(id);
    }
}
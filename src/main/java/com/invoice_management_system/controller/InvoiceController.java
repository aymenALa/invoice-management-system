package com.invoice_management_system.controller;

import com.invoice_management_system.model.Invoice;
import com.invoice_management_system.model.User;
import com.invoice_management_system.service.InvoiceService;
import com.invoice_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        Invoice createdInvoice = invoiceService.createInvoice(invoice, user);
        return ResponseEntity.ok(createdInvoice);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices(Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        return ResponseEntity.ok(invoiceService.getInvoicesForUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        return invoiceService.getInvoiceByIdForUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        invoice.setId(id);
        Invoice updatedInvoice = invoiceService.updateInvoice(invoice, user);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        invoiceService.deleteInvoice(id, user);
        return ResponseEntity.noContent().build();
    }
}
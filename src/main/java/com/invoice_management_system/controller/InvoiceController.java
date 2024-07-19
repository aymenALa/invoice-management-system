package com.invoice_management_system.controller;

import com.invoice_management_system.model.Invoice;
import com.invoice_management_system.model.User;
import com.invoice_management_system.service.InvoiceService;
import com.invoice_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Page<Invoice>> getInvoices(
            Authentication authentication,
            @RequestParam(required = false) String invoiceNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) Invoice.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "issueDate") String sortBy
    ) {
        User user = userService.getUserFromAuthentication(authentication);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Invoice> invoices = invoiceService.findInvoices(
                user, invoiceNumber, startDate, endDate, minAmount, maxAmount, status, pageRequest
        );

        return ResponseEntity.ok(invoices);
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
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice, user);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        invoiceService.deleteInvoice(id, user);
        return ResponseEntity.noContent().build();
    }
}
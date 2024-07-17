package com.invoice_management_system.repository;

import com.invoice_management_system.model.Invoice;
import com.invoice_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUser(User user);
    boolean existsByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findByIdAndUser(Long id, User user);
    boolean existsByIdAndUser(Long id, User user);
}
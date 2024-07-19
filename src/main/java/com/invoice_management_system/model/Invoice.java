package com.invoice_management_system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    // Constructors
    public Invoice() {}

    public Invoice(String invoiceNumber, User user, LocalDate issueDate, LocalDate dueDate, BigDecimal totalAmount, InvoiceStatus status) {
        this.invoiceNumber = invoiceNumber;
        this.user = user;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    // Enum for Invoice Status
    public enum InvoiceStatus {
        DRAFT, SENT, PAID, OVERDUE, CANCELLED
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", user=" + (user != null ? user.getId() : null) +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
    public enum Status {
        DRAFT, SENT, PAID, OVERDUE, CANCELLED
    }
}
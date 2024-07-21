package com.invoice_management_system.service;

import com.invoice_management_system.model.Invoice;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGenerationService {

	public byte[] generateInvoicePdf(Invoice invoice) throws Exception {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
	    document.add(new Paragraph("Issue Date: " + invoice.getIssueDate()));
	    document.add(new Paragraph("Due Date: " + invoice.getDueDate()));
	    document.add(new Paragraph("Total Amount: $" + invoice.getTotalAmount()));
	    document.add(new Paragraph("Status: " + invoice.getStatus()));
	    if (invoice.getClient() != null) {
	        document.add(new Paragraph("Client: " + invoice.getClient().getName()));
	    } else {
	        document.add(new Paragraph("Client: Not specified"));
	    }

	    document.close();

	    return baos.toByteArray();
	}
	
	public byte[] generateClientInvoicesPdf(List<Invoice> invoices) throws Exception {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    document.add(new Paragraph("Client Invoices Summary"));
	    document.add(new Paragraph("Total Invoices: " + invoices.size()));

	    for (Invoice invoice : invoices) {
	        document.add(new Paragraph("--------------------"));
	        document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
	        document.add(new Paragraph("Issue Date: " + invoice.getIssueDate()));
	        document.add(new Paragraph("Due Date: " + invoice.getDueDate()));
	        document.add(new Paragraph("Total Amount: $" + invoice.getTotalAmount()));
	        document.add(new Paragraph("Status: " + invoice.getStatus()));
	    }

	    document.close();
	    return baos.toByteArray();
	}
	
	
}
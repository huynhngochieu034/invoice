package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InvoiceCriteria;
import com.example.demo.dto.InvoiceDTO;
import com.example.demo.dto.InvoicePage;
import com.example.demo.service.InvoiceService;

@RestController
@RequestMapping({ "/api" })
public class InvoiceController {
	
	@Autowired
    private InvoiceService invoiceService;
	
	@PostMapping("/invoices")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return ResponseEntity.ok(invoiceService.insert(invoiceDTO));
    }
	
	@GetMapping("/invoices/generator")
    public ResponseEntity<List<InvoiceDTO>> generatorInvoice() {
        return ResponseEntity.ok(invoiceService.insertDataSample());
    }
	
	@GetMapping("/invoices")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices(InvoiceCriteria invoiceCriteria ,InvoicePage pageable) {
		Page<InvoiceDTO> page = invoiceService.findByCriteria(invoiceCriteria, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
}

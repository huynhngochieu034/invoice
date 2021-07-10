package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InvoiceDTO;
import com.example.demo.service.InvoiceService;

@RestController
@RequestMapping({ "/api/invoices" })
public class InvoiceController {
	
	@Autowired
    private InvoiceService invoiceService;
	
	@PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return ResponseEntity.ok(invoiceService.insert(invoiceDTO));
    }
}

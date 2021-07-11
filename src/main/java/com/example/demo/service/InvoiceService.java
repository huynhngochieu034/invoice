package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.InvoiceCriteria;
import com.example.demo.dto.InvoiceDTO;
import com.example.demo.dto.InvoicePage;

public interface InvoiceService {

	InvoiceDTO insert(InvoiceDTO invoiceDTO);
	
	List<InvoiceDTO> insertDataSample();
	
	Page<InvoiceDTO> findByCriteria(InvoiceCriteria invoiceCriteria, InvoicePage pageable);
	
}

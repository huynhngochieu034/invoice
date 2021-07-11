package com.example.demo.repository;

import org.springframework.data.domain.Page;

import com.example.demo.dto.InvoiceCriteria;
import com.example.demo.dto.InvoicePage;
import com.example.demo.entity.InvoiceEntity;

public interface InvoiceCustomRepository {
	Page<InvoiceEntity> findByCriteria(InvoiceCriteria invoiceCriteria, InvoicePage pageable);
}

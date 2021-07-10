package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.converter.InvoiceConverter;
import com.example.demo.dto.InvoiceDTO;
import com.example.demo.entity.InvoiceEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
    private InvoiceConverter invoiceConverter;

    @Autowired
    private InvoiceRepository invoiceRepository;

	@Override
	@Transactional
	public InvoiceDTO insert(InvoiceDTO invoiceDTO) {
		
		InvoiceEntity invoiceEntity = invoiceConverter.convertToEntity(invoiceDTO);
		
		List<ItemEntity> items = invoiceEntity.getItems();
		for (ItemEntity item: items)
			item.setInvoice(invoiceEntity);
		invoiceEntity.setItems(items);
		
		InvoiceEntity result = invoiceRepository.save(invoiceEntity);
		return invoiceConverter.convertToDto(result);	
	}

}

package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.converter.InvoiceConverter;
import com.example.demo.dto.InvoiceCriteria;
import com.example.demo.dto.InvoiceDTO;
import com.example.demo.dto.InvoicePage;
import com.example.demo.entity.InvoiceEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.service.InvoiceService;
import com.github.javafaker.Faker;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceConverter invoiceConverter;

	@Autowired
	private InvoiceRepository invoiceRepository;

	private Double total = 0D;

	@Override
	@Transactional
	public InvoiceDTO insert(InvoiceDTO invoiceDTO) {

		InvoiceEntity invoiceEntity = invoiceConverter.convertToEntity(invoiceDTO);
		
		Set<ItemEntity> items = invoiceEntity.getItems();
		for (ItemEntity item : items) {
			item.setInvoice(invoiceEntity);
		}
		invoiceEntity.setItems(items);
		InvoiceEntity result = invoiceRepository.save(invoiceEntity);

		return invoiceConverter.convertToDto(result);
	}

	@Override
	public List<InvoiceDTO> insertDataSample() {

		Faker faker = new Faker();
		List<InvoiceEntity> invoices = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			total = 0D;
			InvoiceEntity invoiceEntity = new InvoiceEntity();
			invoiceEntity.setCustomer(faker.book().author());
			invoiceEntity.setDate(generateDate());
			invoiceEntity.setNote(faker.app().name());
			invoiceEntity.setItems(generateItems(invoiceEntity));
			invoiceEntity.setTotal(total);
			invoices.add(invoiceEntity);
		}
		invoiceRepository.saveAll(invoices);
		return invoices.stream().map(item -> invoiceConverter.convertToDto(item)).collect(Collectors.toList());

	}

	private Date generateDate() {
		int randomYear = (int) Math.floor(Math.random() * (2021 - 1900 + 1) + 1900);
		int dayOfYear = (int) Math.floor(Math.random() * (365) + 1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, randomYear);
		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
		return calendar.getTime();
	}

	private Set<ItemEntity> generateItems(InvoiceEntity invoiceEntity) {
		Set<ItemEntity> items = new HashSet<>();
		Faker faker = new Faker();
		int randomAmountItem = (int) Math.floor(Math.random() * (50 - 3 + 1) + 3);
		for (int i = 0; i < randomAmountItem; i++) {
			ItemEntity itemEntity = new ItemEntity();
			itemEntity.setInvoice(invoiceEntity);
			itemEntity.setName(faker.commerce().productName());
			itemEntity.setNote(faker.app().name());

			Double price = Double.valueOf(faker.commerce().price());
			itemEntity.setPrice(price);
			total += price;
			
			items.add(itemEntity);
		}
		return items;
	}

	@Override
	public Page<InvoiceDTO> findByCriteria(InvoiceCriteria invoiceCriteria, InvoicePage invoicePage) {
		return invoiceRepository.findByCriteria(invoiceCriteria, invoicePage)
				.map(item -> invoiceConverter.convertToDto(item));
	}

}

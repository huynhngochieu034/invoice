package com.example.demo.dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class InvoiceDTO {

	private UUID id;
    private Double total;
    private Date date;
	private String customer;
	private String note;
    private Set<ItemDTO> items;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Set<ItemDTO> getItems() {
		return items;
	}
	public void setItems(Set<ItemDTO> items) {
		this.items = items;
	}
	
}

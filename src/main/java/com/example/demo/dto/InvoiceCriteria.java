package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class InvoiceCriteria {
	
	private Double fromPrice;
	private Double toPrice;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fromDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date toDate;
	
    private Long fromAmountItem;
    private Long toAmountItem;
    
	public Double getFromPrice() {
		return fromPrice;
	}
	public void setFromPrice(Double fromPrice) {
		this.fromPrice = fromPrice;
	}
	public Double getToPrice() {
		return toPrice;
	}
	public void setToPrice(Double toPrice) {
		this.toPrice = toPrice;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Long getFromAmountItem() {
		return fromAmountItem;
	}
	public void setFromAmountItem(Long fromAmountItem) {
		this.fromAmountItem = fromAmountItem;
	}
	public Long getToAmountItem() {
		return toAmountItem;
	}
	public void setToAmountItem(Long toAmountItem) {
		this.toAmountItem = toAmountItem;
	}
	
}

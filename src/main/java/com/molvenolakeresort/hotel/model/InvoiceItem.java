package com.molvenolakeresort.hotel.model;

import javax.persistence.*;

@Entity
public class InvoiceItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int piecePrice; // Price is in cents
	private int totalPrice; // Price is in cents
	private int amount;

	@ManyToOne
	private Invoice invoice;

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}



	public InvoiceItem(String name, int price, int amount) {
		this.name = name;
		this.piecePrice = price;
		this.amount = amount;
		this.totalPrice = price * amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return piecePrice;
	}

	public void setPrice(int price) {
		this.piecePrice = price;
		this.totalPrice = price * amount;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		this.totalPrice = this.piecePrice * this.amount;
	}

}

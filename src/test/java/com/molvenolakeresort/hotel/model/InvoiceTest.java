package com.molvenolakeresort.hotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {
	private Invoice invoice;

	@BeforeEach
	public void init() {
		invoice = new Invoice();
		invoice.postInvoiceItem("Kamer 101", 10000, 6);     // Kamer 101 voor 6 nachten a 100 euro per nacht
		invoice.postInvoiceItem("Roomservice", 2500, 2);    // Roomservice voor 2 personen a 25 euro per stuk
		invoice.postInvoiceItem("Cleaning", 0, 1);          // Gratis kamer schoonmaak
	}

	@Test
	void getTotalCost() {
		assertEquals(65000, invoice.getTotalCost());
		System.out.println("Invoice total: " + invoice.getTotalCost()/100 + " RMB");
	}

	@Test
	void testSetAndGetTotalPaid() {
		final int paid = 3000;
		invoice.setTotalPaid(paid);
		assertEquals(paid, invoice.getTotalPaid());
		System.out.println("Invoice total: " + (invoice.getTotalCost()/100) + " RMB\nTotal paid: " + (invoice.getTotalPaid()/100) + " RMB\nStill to pay: " + ((invoice.getTotalCost() - invoice.getTotalPaid())/100) + " RMB");
	}

	@Test
	void testSetPaymentMethodAndPay() {
		invoice.setPaymentMethod("Alipay");
		invoice.pay();
		assertEquals(invoice.isPaymentComplete(), true);
		assertEquals(invoice.getPaymentMethod(), "Alipay");
		assertEquals(invoice.getTotalCost(), invoice.getTotalPaid());
	}

	@Test
	void getInvoiceItems() {
		System.out.println("Getting the entire invoice:");
		List<InvoiceItem> items = invoice.getInvoiceItems();
		assertEquals(items.size(), 3);
		for(InvoiceItem item : items) {
			System.out.println(item.getAmount() + "x " + item.getName() + " a " + (item.getPrice() / 100) + " RMB = " + (item.getTotalPrice() / 100) + " RMB");
		}
	}

	@Test
	void getInvoiceItem() {
		System.out.println("Getting one item from the invoice:");
		InvoiceItem item = invoice.getInvoiceItem("Roomservice");
		assertEquals(item.getName(), "Roomservice");
		assertEquals(item.getPrice(), 2500);
		assertEquals(item.getAmount(), 2);
		assertEquals(item.getTotalPrice(), 5000);

		System.out.println(item.getAmount() + "x " + item.getName() + " a " + (item.getPrice()/100) + " RMB = " + (item.getTotalPrice()/100) + " RMB");
	}

	@Test
	void postInvoiceItem() {
		assertEquals(invoice.getInvoiceItems().size(), 3);
		invoice.postInvoiceItem("Towels", 500, 2);
		assertEquals(invoice.getInvoiceItems().size(), 4);
	}

	@Test
	void putInvoiceItem() {
		assertEquals(invoice.getInvoiceItem("Roomservice").getAmount(), 2);
		assertEquals(invoice.getInvoiceItem("Roomservice").getPrice(), 2500);
		invoice.postInvoiceItem("Roomservice", 1500, 3);
		assertEquals(invoice.getInvoiceItem("Roomservice").getAmount(), 3);
		assertEquals(invoice.getInvoiceItem("Roomservice").getPrice(), 1500);
	}

	@Test
	void deleteInvoiceItem() {
		System.out.println("Deleting Roomservice item from invoice:");
		List<InvoiceItem> items = invoice.getInvoiceItems();
		for(InvoiceItem item : items) {
			System.out.println(item.getAmount() + "x " + item.getName() + " a " + (item.getPrice() / 100) + " RMB = " + (item.getTotalPrice() / 100) + " RMB");
		}

		invoice.deleteInvoiceItem("Roomservice");
		assertNull(invoice.getInvoiceItem("Roomservice"));
		assertEquals(invoice.getInvoiceItems().size(), 2);

		System.out.println("\nNew invoice:");
		List<InvoiceItem> items2 = invoice.getInvoiceItems();
		for(InvoiceItem item : items2) {
			System.out.println(item.getAmount() + "x " + item.getName() + " a " + (item.getPrice() / 100) + " RMB = " + (item.getTotalPrice() / 100) + " RMB");
		}
	}
}
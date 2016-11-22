package com.atorvdm.contribe.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Basket implements Serializable {
	private static final long serialVersionUID = 981193818715595531L;
	
	private Map<Book, Integer> books;
	
	public Basket() {
		books = new LinkedHashMap<>();
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	public void addBook(Book book, int quantity) {
		if (quantity < 1) return;
		if (books.containsKey(book)) {
			books.put(book, books.get(book) + quantity);
		} else {
			books.put(book, quantity);
		}
	}
	
	public void removeBook(Book book, int quantity) {
		if (quantity < 1) return;
		if (!books.containsKey(book)) return;
		if (quantity < books.get(book)) {
			books.put(book, books.get(book) - quantity);
		} else {
			books.remove(book);
		}
	}
	
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		for (Entry<Book, Integer> entry : books.entrySet()) {
			price = price.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}
		return price;
	}
	
	public void clear() {
		books.clear();
	}
}

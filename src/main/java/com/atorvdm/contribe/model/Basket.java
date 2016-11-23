package com.atorvdm.contribe.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.atorvdm.contribe.util.MapToCoupleArraySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This class represent a basket in the book store
 * @author Maksim Strutinskiy
 */
public class Basket implements Serializable {
	private static final long serialVersionUID = 981193818715595531L;
	private Map<Book, Integer> books;
	
	public Basket() {
		books = new LinkedHashMap<>();
	}
	
	@JsonSerialize(using = MapToCoupleArraySerializer.class)
	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	/**
	 * This method adds a  <code>quantity</code> of <code>book</code> in the basket
	 * If the <code>book</code> is already present in the basket, the <code>quantity</code>
	 * is added to the current amount of this <code>book</code>
	 * @param book
	 * @param quantity
	 */
	public void addBook(Book book, int quantity) {
		if (quantity < 1) return;
		if (books.containsKey(book)) {
			books.put(book, books.get(book) + quantity);
		} else {
			books.put(book, quantity);
		}
	}
	
	/**
	 * This method removed a <code>quantity</code> of <code>book</code> from the basket
	 * The number <code>quantity</code> should exceed 0 and the <code>book</code> should
	 * be present in the basket, otherwise the method doesn't change anything
	 * @param book a book to remove
	 * @param quantity a quantity of books to remove
	 */
	public void removeBook(Book book, int quantity) {
		if (quantity < 1) return;
		if (!books.containsKey(book)) return;
		if (quantity < books.get(book)) {
			books.put(book, books.get(book) - quantity);
		} else {
			books.remove(book);
		}
	}
	
	/**
	 * This method computes the price of all items in the basket
	 * @return price of all items in the basket
	 */
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		for (Entry<Book, Integer> entry : books.entrySet()) {
			price = price.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}
		return price;
	}
	
	/**
	 * Remove all books from the basket
	 */
	public void clear() {
		books.clear();
	}
}

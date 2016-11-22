package com.atorvdm.contribe.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class BasketTest {
	private static final String TEST_TITLE = "Test Title";
	private static final String TEST_AUTHOR = "Test Author";
	private Basket basket;
	private Book book1;
	private Book book2;

	@Before
	public void setUp() throws Exception {
		basket = new Basket();
		book1 = new Book();
		book2 = new Book();
		
		book1.setAuthor(TEST_AUTHOR + 1);
		book2.setAuthor(TEST_AUTHOR + 2);
		book1.setTitle(TEST_TITLE + 1);
		book2.setTitle(TEST_TITLE + 2);
		book1.setPrice(new BigDecimal(1));
		book2.setPrice(new BigDecimal(2));
	}

	@Test
	public void testGetBooks() {
		basket.addBook(book1, 1);
		basket.addBook(book2, 1);
		Map<Book, Integer> books = basket.getBooks();
		assertEquals(new Integer(1), books.get(book1));
		assertEquals(new Integer(1), books.get(book2));
	}

	@Test
	public void testAddBook() {
		Map<Book, Integer> books = basket.getBooks();
		basket.addBook(book1, 1);
		basket.addBook(book2, 2);
		basket.addBook(book1, 4);
		assertEquals(new Integer(5), books.get(book1));
		assertEquals(new Integer(2), books.get(book2));
	}

	@Test
	public void testRemoveBook() {
		Map<Book, Integer> books = basket.getBooks();
		basket.addBook(book1, 1);
		basket.addBook(book2, 2);
		basket.addBook(book1, 4);
		basket.removeBook(book1, 1);
		basket.removeBook(book2, 10);
		basket.removeBook(book1, 3);
		assertEquals(new Integer(1), books.get(book1));
		assertFalse(books.containsKey(book2));
	}

	@Test
	public void testGetPrice() {
		basket.addBook(book1, 4);
		assertEquals(new BigDecimal(4), basket.getPrice());
		
		basket.addBook(book2, 3);
		assertEquals(new BigDecimal(10), basket.getPrice());
		
		basket.addBook(book1, 6);
		assertEquals(new BigDecimal(16), basket.getPrice());
	}
}

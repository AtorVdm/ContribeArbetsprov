package com.atorvdm.contribe.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.atorvdm.contribe.TestUtils;

public class BasketTest {
	private static final String TEST_TITLE = "Test Title";
	private static final String TEST_AUTHOR = "Test Author";
	private Basket basket;
	private Book book1;
	private Book book2;

	@Before
	public void setUp() throws Exception {
		basket = new Basket();
		book1 = TestUtils.initBook(TEST_TITLE + 1, TEST_AUTHOR + 1, 1);
		book2 = TestUtils.initBook(TEST_TITLE + 2, TEST_AUTHOR + 2, 2);
	}

	@Test
	public void testGetBooks() throws Exception {
		final int quantity = 1;
		basket.addBook(book1, quantity);
		basket.addBook(book2, quantity);
		Map<Book, Integer> books = basket.getBooks();
		assertEquals(new Integer(quantity), books.get(book1));
		assertEquals(new Integer(quantity), books.get(book2));
	}

	@Test
	public void testAddBook() throws Exception {
		Map<Book, Integer> books = basket.getBooks();
		basket.addBook(book1, 1);
		basket.addBook(book2, 2);
		basket.addBook(book1, 4);
		assertEquals(new Integer(5), books.get(book1));
		assertEquals(new Integer(2), books.get(book2));
	}

	@Test
	public void testRemoveBook() throws Exception {
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
	public void testGetPrice() throws Exception {
		basket.addBook(book1, 4);
		assertEquals(new BigDecimal(4), basket.getPrice());
		
		basket.addBook(book2, 3);
		assertEquals(new BigDecimal(10), basket.getPrice());
		
		basket.addBook(book1, 6);
		assertEquals(new BigDecimal(16), basket.getPrice());
	}
}

package com.atorvdm.contribe.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.atorvdm.contribe.TestUtils;

public class BookTest {
	private static final String TITLE_1 = "Carrie";
	private static final String TITLE_2 = "Den gröna milen";
	private static final int PRICE = 10;
	private static final String STEPHEN_KING = "Stephen King";
	private Book book;

	@Before
	public void setUp() throws Exception {
		book = new Book();
	}
	
	@Test
	public void search() {
		book = TestUtils.initBook(TITLE_2, STEPHEN_KING, PRICE);
		assertTrue(book.search("gröna"));
		assertTrue(book.search("STEPHEn "));
		assertFalse(book.search(" den "));
		assertFalse(book.search("grona"));
	}

	@Test
	public void testHashCode() throws Exception {
		Map<Book, Integer> books = new HashMap<>();
		book = TestUtils.initBook(TITLE_1, STEPHEN_KING, PRICE);
		Book anotherBook = TestUtils.initBook(TITLE_1, STEPHEN_KING, PRICE);
		books.put(book, 0);
		books.put(anotherBook, 1);
		
		// as book and anotherBook are actually the same book
		assertEquals(new Integer(1), books.get(book));
		assertEquals(1, books.keySet().size());
	}

	@Test
	public void testEqualsObject() throws Exception {
		Book anotherBook = new Book();
		assertEquals(book, book);
		assertEquals(book, anotherBook);
		
		book.setAuthor(STEPHEN_KING);
		anotherBook.setAuthor(STEPHEN_KING);
		assertEquals(book, anotherBook);
		
		book.setPrice(new BigDecimal(PRICE));
		anotherBook.setPrice(new BigDecimal(PRICE));
		assertEquals(book, anotherBook);
		
		book.setTitle(TITLE_1);
		anotherBook.setTitle(TITLE_2);
		assertNotEquals(book, anotherBook);
	}
}

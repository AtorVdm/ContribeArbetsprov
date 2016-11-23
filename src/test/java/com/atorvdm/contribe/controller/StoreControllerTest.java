package com.atorvdm.contribe.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.atorvdm.contribe.TestUtils;
import com.atorvdm.contribe.model.Book;

public class StoreControllerTest {
	private static final String TITLE_1 = "Carrie";
	private static final String TITLE_2 = "Den gröna milen";
	private static final int PRICE = 10;
	private static final String STEPHEN_KING = "Stephen King";
	
	private StoreController storeController;
	private Book book1; 
	private Book book2;
	
	@Before
	public void setUp() throws Exception {
		book1 = TestUtils.initBook(TITLE_1, STEPHEN_KING, PRICE);
		book2 = TestUtils.initBook(TITLE_2, STEPHEN_KING, PRICE + 1);
		storeController = new StoreController(true);
	}

	@Test
	public void testList() {
		storeController.add(book1, 1);
		storeController.add(book2, 1);
		Book[] books = storeController.list("");
		assertEquals(2, books.length);
		List<Book> bookList = Arrays.asList(books);
		assertTrue(bookList.contains(book1) && bookList.contains(book2));
		
		assertEquals(1, storeController.list("gröna").length);
		assertEquals(1, storeController.list("den g").length);
		assertEquals(0, storeController.list("grona").length);
		assertEquals(2, storeController.list("king").length);
	}

	@Test
	public void testAdd() {
		storeController.add(book1, 2);
		storeController.add(book2, 4);
		storeController.add(book1, 3);
		storeController.add(book1, 1);
		storeController.add(book2, 0);
		storeController.add(book2, -10);
		assertEquals(new Integer(6), storeController.getBookMap().get(book1));
		assertEquals(new Integer(4), storeController.getBookMap().get(book2));
	}

	@Test
	public void testBuy() {
		storeController.add(book1, 2);
		int[] actualResult = storeController.buy(book1, book1, book1, book2);
		int[] expectedResult = new int[] {0, 0, 1, 2};
		assertArrayEquals(expectedResult, actualResult);
	}

	@Test
	public void testAddToBasket() {
		storeController.add(book2, 5);
		assertTrue(storeController.addToBasket(book2, 1));
		assertFalse(storeController.addToBasket(book1, 1));
		assertFalse(storeController.addToBasket(book2, 0));
	}

	@Test
	public void testRemoveFromBasket() {
		storeController.add(book1, 3);
		storeController.add(book2, 5);
		storeController.addToBasket(book1, 4);
		storeController.addToBasket(book2, 2);
		assertTrue(storeController.removeFromBasket(book1, 1));
		assertFalse(storeController.removeFromBasket(book1, -1));
		assertTrue(storeController.removeFromBasket(book2, 5));
		assertTrue(storeController.removeFromBasket(book2, 10));
		Map<Book, Integer> basket = storeController.basketStatus().getBooks();
		assertTrue(basket.containsKey(book1));
		assertFalse(basket.containsKey(book2));
		assertEquals(new Integer(3), basket.get(book1));
	}

	@Test
	public void testBuyFromBasket() {
		Book book3 = TestUtils.initBook("Different Title", "Different Author", 1221);
		storeController.add(book1, 2);
		storeController.add(book2, 12);
		storeController.addToBasket(book1, 4);
		storeController.addToBasket(book2, 3);
		storeController.addToBasket(book3, 1);
		int[] actualResult = storeController.buyFromBasket();
		int[] expectedResult = new int[] {0, 0, 1, 1, 0, 0, 0};
		assertArrayEquals(expectedResult, actualResult);
	}

	@Test
	public void testBasketStatus() {
		storeController.add(book1, 3);
		storeController.add(book2, 10);
		storeController.addToBasket(book1, 4);
		storeController.addToBasket(book2, 2);
		assertEquals(new BigDecimal(62), storeController.basketStatus().getPrice());
		Map<Book, Integer> basket = storeController.basketStatus().getBooks();
		assertTrue(basket.containsKey(book1) && basket.containsKey(book2));
		assertEquals(new Integer(4), basket.get(book1));
		assertEquals(new Integer(2), basket.get(book2));
	}
}

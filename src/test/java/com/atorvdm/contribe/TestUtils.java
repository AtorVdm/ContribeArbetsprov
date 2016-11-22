package com.atorvdm.contribe;

import java.math.BigDecimal;

import com.atorvdm.contribe.model.Book;

public class TestUtils {
	public static Book initBook(String title, String author, int price) {
		Book book = new Book();
		book.setAuthor(author);
		book.setTitle(title);
		book.setPrice(new BigDecimal(price));
		return book;
	}
}

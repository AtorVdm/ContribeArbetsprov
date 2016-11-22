package com.atorvdm.contribe.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Map;

import org.junit.Test;

import com.atorvdm.contribe.model.Book;

public class StoreUtilsTest {

	@Test
	public void testFetchBooksOnline()
			throws MalformedURLException, IOException, NumberFormatException, ParseException {
		Map<Book, Integer> books = StoreUtils.fetchBooksOnline();
		assertNotNull(books);
	}
}

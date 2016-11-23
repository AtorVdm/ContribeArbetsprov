package com.atorvdm.contribe.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.atorvdm.contribe.model.Book;

/**
 * This class contains util methods for the project
 * @author Maksim Strutinskiy
 */
public class StoreUtils {
	private final static String CSV_FILE_URL = "http://www.contribe.se/bookstoredata/bookstoredata.txt";
	private final static String ENCODING = "UTF-8";
	private final static String DATA_SET_DEL = "\n";
	private final static String DATA_ELEMENT_DEL = ";";
	
	/**
	 * This method fetches book list in csv format from the url (constant)
	 * Throws a bunch of exceptions in case something is wrong
	 * @return a list of books if everything is OK, exception otherwise
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static Map<Book, Integer> fetchBooksOnline()
			throws MalformedURLException, IOException, ParseException, NumberFormatException {
		// use something like https://sourceforge.net/projects/csvjdbc/ if data persistence is required
		// uses Apache Commons IO
		String[] input = IOUtils.toString(new URL(CSV_FILE_URL), ENCODING)
				.split(DATA_SET_DEL);
		
		return parseInputStringArray(input);
	}
	
	private static Map<Book, Integer> parseInputStringArray(String[] input)
			throws IOException, ParseException, NumberFormatException {
		Map<Book, Integer> books = new LinkedHashMap<>();
		for (String line : input) {
			String[] fields = line.split(DATA_ELEMENT_DEL);
			if (!validateFields(fields))
				throw new IOException("Input data is corrupted, no books will be added");
			
			DecimalFormat df = new DecimalFormat();
			df.setParseBigDecimal(true);
			BigDecimal price = new BigDecimal(fields[2].replaceAll(",", ""));
			int quantity = Integer.parseUnsignedInt(fields[3]);
			
			Book book = new Book();
			book.setTitle(fields[0]);
			book.setAuthor(fields[1]);
			book.setPrice(price);
			books.put(book, quantity);
		}
		return books;
	}
	
	private static boolean validateFields(String[] fields)
			throws ParseException, NumberFormatException {
		if (fields.length != 4) return false;
		boolean result = true;
		result = result & fields[0].length() > 0;
		result = result & fields[1].length() > 0;
		DecimalFormat df = new DecimalFormat();
		df.setParseBigDecimal(true);
		df.parse(fields[2]);
		Integer.parseUnsignedInt(fields[3]);
		return result;
	}
}

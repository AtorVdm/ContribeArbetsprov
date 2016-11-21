package com.atorvdm.contribe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atorvdm.contribe.model.Book;

@RestController
@RequestMapping("/store")
public class BookController implements BookList {
	Map<Book, Integer> bookMap;
	
	public BookController() {
		super();
		this.bookMap = new HashMap<Book, Integer>();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/map")
	public Map<Book, Integer> getMap() {
		return bookMap;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public Book[] list(@RequestParam(value="search", defaultValue="") String searchString) {
		Book[] bookArray = new Book[bookMap.size()];
		if (searchString.equals("")) {
			bookMap.keySet().toArray(bookArray);
			return bookArray;
		}
		return null;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/add")
	public boolean add(@RequestBody Book book, @RequestParam(value="quantity", defaultValue="0") int quantity) {
		if (quantity < 0) return false;
		if (bookMap.containsKey(book)) {
			bookMap.put(book, bookMap.get(book) + quantity);
		} else {
			bookMap.put(book, quantity);
		}
		return true;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/buy")
	public int[] buy(@RequestBody Book... books) {
		int[] result = new int[books.length];
		for (int i = 0; i < books.length; i++) {
			result[i] = buyOneBook(books[i]);
		}
		return result;
	}
	
	private int buyOneBook(Book book) {
		if (!bookMap.containsKey(book))
			return Status.DOES_NOT_EXIST.getValue();
		if (bookMap.get(book) > 0) {
			bookMap.put(book, bookMap.get(book) - 1);
			return Status.OK.getValue();
		} else {
			return Status.NOT_IN_STOCK.getValue();
		}
	}
	
	private enum Status {
		OK(0),
		NOT_IN_STOCK(1),
		DOES_NOT_EXIST(2);

	    private final int value;
	    private Status(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
}

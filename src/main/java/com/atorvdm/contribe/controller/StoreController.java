package com.atorvdm.contribe.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atorvdm.contribe.model.Basket;
import com.atorvdm.contribe.model.Book;

@RestController
@RequestMapping("/store")
public class StoreController implements BookList, BaseBasket {
	private Map<Book, Integer> bookMap;
	private Basket basket;
	
	public StoreController() {
		super();
		bookMap = new HashMap<>();
		basket = new Basket();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/map")
	public Map<Book, Integer> getMap() {
		return bookMap;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public Book[] list(@RequestParam(value="search", defaultValue="") String searchString) {
		Book[] bookArray;
		if (searchString.equals("")) {
			bookArray = new Book[bookMap.keySet().size()];
			bookMap.keySet().toArray(bookArray);
		} else {
			Set<Book> bookSet = new HashSet<>();
			bookMap.keySet().forEach(book -> {
				if (book.getTitle().contains(searchString) ||
						book.getAuthor().contains(searchString)) {
					bookSet.add(book);
				}
			});
			bookArray = new Book[bookSet.size()];
			bookSet.toArray(bookArray);
		}
		return bookArray;
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
	
	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/addToBasket")
	public boolean addToBasket(@RequestBody Book book, @RequestParam(value="quantity", defaultValue="1") int quantity) {
		if (quantity < 0) return false;
		if (bookMap.containsKey(book)) {
			basket.addBook(book, quantity);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/removeFromBasket")
	public boolean removeFromBasket(@RequestBody Book book, @RequestParam(value="quantity", defaultValue="1") int quantity) {
		if (quantity < 0) return false;
		basket.removeBook(book, quantity);
		return true;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/buyFromBasket")
	public int[] buyFromBasket() {
		Set<Map.Entry<Book, Integer>> entrySet = basket.getBooks().entrySet();
		int[] bookArray = new int[entrySet.size()];
	    Iterator<Map.Entry<Book, Integer>> iterator = entrySet.iterator();
	    
	    int i = 0;
	    while(iterator.hasNext()) {
	        Map.Entry<Book, Integer> mapEntry = (Map.Entry<Book, Integer>) iterator.next();
	        for (int j = 0; j < mapEntry.getValue(); j++) {
	        	bookArray[i++] = buyOneBook(mapEntry.getKey());
	        }
	    }
	    
	    return bookArray;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.GET, path = "/basket")
	public Basket basketStatus() {
		return basket;
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

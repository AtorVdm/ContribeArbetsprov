package com.atorvdm.contribe.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atorvdm.contribe.model.Basket;
import com.atorvdm.contribe.model.Book;
import com.atorvdm.contribe.util.MapContainer;
import com.atorvdm.contribe.util.StoreUtils;

@RestController
@RequestMapping("/store")
public class StoreController implements BookList, BaseBasket {
	private Map<Book, Integer> bookMap;
	private Basket basket;
	
	public StoreController(boolean testing) {
		super();
		init(testing);
	}
	
	public StoreController() {
		super();
		init(false);
	}
	
	private void init(boolean testing) {
		// use LinkedHashMap if order matters or SortedMap if sorting is needed
		bookMap = new LinkedHashMap<>();
		basket = new Basket();
		if (testing) return;
		
		try {
			bookMap = StoreUtils.fetchBooksOnline();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/map")
	public MapContainer<Book, Integer> getBookMapSerializable() {
		return new MapContainer<Book, Integer>(getBookMap());
	}
	
	public Map<Book, Integer> getBookMap() {
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
				if (book.search(searchString)) {
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
		if (quantity < 1) return false;
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
		if (quantity < 1) return false;
		basket.removeBook(book, quantity);
		return true;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST, path = "/buyFromBasket")
	public int[] buyFromBasket() {
		Set<Map.Entry<Book, Integer>> entrySet = basket.getBooks().entrySet();
		List<Integer> bookList = new LinkedList<>();
	    Iterator<Map.Entry<Book, Integer>> iterator = entrySet.iterator();
	    
	    while(iterator.hasNext()) {
	        Map.Entry<Book, Integer> mapEntry = (Map.Entry<Book, Integer>) iterator.next();
	        for (int j = 0; j < mapEntry.getValue(); j++) {
	        	bookList.add(buyOneBook(mapEntry.getKey()));
	        }
	    }
	    basket.clear();
	    
	    return bookList.stream().mapToInt(i -> i).toArray();
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

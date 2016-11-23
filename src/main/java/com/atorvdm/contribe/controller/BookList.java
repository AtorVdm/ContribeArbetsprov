package com.atorvdm.contribe.controller;

import com.atorvdm.contribe.model.Book;

/**
 * This interface represents basic behavior of a book list in the book store
 * @author Maksim Strutinskiy
 */
public interface BookList {  
	public Book[] list(String searchString);
	public boolean add(Book book, int quantity);
	public int[] buy(Book... books);
}

package com.atorvdm.contribe.controller;

import com.atorvdm.contribe.model.Book;

public interface BookList {  
	public Book[] list(String searchString);
	public boolean add(Book book, int quantity);
	public int[] buy(Book... books);
}

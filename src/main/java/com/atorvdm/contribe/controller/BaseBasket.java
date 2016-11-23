package com.atorvdm.contribe.controller;

import java.io.IOException;

import com.atorvdm.contribe.model.Basket;
import com.atorvdm.contribe.model.Book;

public interface BaseBasket {
	public boolean addToBasket(Book book, int quantity);
	public boolean removeFromBasket(Book book, int quantity);
	public int[] buyFromBasket();
	public Basket basketStatus() throws IOException;
}

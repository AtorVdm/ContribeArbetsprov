package com.atorvdm.contribe.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Book implements Serializable {
	private static final long serialVersionUID = 8123103198369491608L;
	private String title;
	private String author;
	private BigDecimal price;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public boolean search(String searchString) {
		String searchStringLC = searchString.toLowerCase();
		return title.toLowerCase().contains(searchStringLC) ||
				author.toLowerCase().contains(searchStringLC);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Book)) return false;
		Book book = (Book) obj;
		return Objects.equals(getTitle(), book.getTitle()) &&
				Objects.equals(getAuthor(), book.getAuthor()) &&
				Objects.equals(getPrice(), book.getPrice());
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            append(getTitle()).
            append(getAuthor()).
            append(getPrice()).
            toHashCode();
    }
}

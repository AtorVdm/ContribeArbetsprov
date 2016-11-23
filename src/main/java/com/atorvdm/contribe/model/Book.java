package com.atorvdm.contribe.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class represents a book in the book store
 * @author Maksim Strutinskiy
 */
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
	
	/**
	 * This method searches among titles authors in the store for occurrences
	 * of an <code>input string</code>. The search is case-insensitive
	 * @param searchString string to search for
	 * @return true if string was found, false otherwise
	 */
	public boolean search(String searchString) {
		String searchStringLC = searchString.toLowerCase();
		return title.toLowerCase().contains(searchStringLC) ||
				author.toLowerCase().contains(searchStringLC);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Book)) return false;
		Book book = (Book) obj;
		
		boolean priceEquals = false;
		if (getPrice() == null && book.getPrice() == null) {
			priceEquals = true;
		} else if (getPrice() == null || book.getPrice() == null) {
			priceEquals = false;
		} else if (getPrice().compareTo(book.getPrice()) == 0) {
			priceEquals = true;
		}
		
		return Objects.equals(getTitle(), book.getTitle()) &&
				Objects.equals(getAuthor(), book.getAuthor()) &&
				priceEquals;
	}
	
	@Override
    public int hashCode() {
		// uses Apache Commons Lang
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            append(getTitle()).
            append(getAuthor()).
            append(getPrice().stripTrailingZeros()).
            toHashCode();
    }
}

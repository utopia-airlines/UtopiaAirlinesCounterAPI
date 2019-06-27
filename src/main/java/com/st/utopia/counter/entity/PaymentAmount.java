package com.st.utopia.counter.entity;

/**
 * A simple wrapper around a single integer representing an amount paid for a
 * ticket.
 *
 * @author Jonathan Lovelace
 */
public class PaymentAmount {
	/**
	 * The amount paid.
	 */
	private Integer price;
	/**
	 * @return the amount paid
	 */
	public Integer getPrice() {
		return price;
	}
}

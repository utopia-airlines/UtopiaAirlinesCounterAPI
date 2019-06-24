package com.st.utopia.counter.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class to be the primary key of a {@link Ticket} for JPA, which requires
 * every Entity to have a single primary key.
 *
 * Declared to implement Serializable because "Composite-id class must implement
 * Serializable."
 *
 * @author Al-amine AHMED MOUSSA
 */

public class TicketIdentity implements Serializable {
	/*
	 * Serialization version. Increment on any change to class structure that is
	 * pushed to production.
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * the flight ID.
	 */
	private Integer flight;
	/*
	 * the row of the seat .
	 */
	private Integer row;
	/*
	 * the seat group.
	 */
	private Character seat;

	/*
	 * the Getters and Setters of the Class
	 */
	public Integer getFlight() {
		return flight;
	}

	public Integer getRow() {
		return row;
	}

	public Character getSeat() {
		return seat;
	}

	/*
	 * the Constructors of the Class we need empty Constructor for Hibernate Jpa
	 * Purposes
	 */

	public TicketIdentity() {

	}

	public TicketIdentity(Integer flight, Integer row, Character seat) {
		this.flight = flight;
		this.row = row;
		this.seat = seat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flight, row, seat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof TicketIdentity) {
			return Objects.equals(flight, ((TicketIdentity) obj).flight)
					&& Objects.equals(row, ((TicketIdentity) obj).row)
					&& Objects.equals(seat, ((TicketIdentity) obj).seat);
		}
		return false;
	}

	@Override
	public String toString() {
		return "TicketIdentity [flight=" + flight + ", row=" + row + ", seat=" + seat + "]";
	}

}

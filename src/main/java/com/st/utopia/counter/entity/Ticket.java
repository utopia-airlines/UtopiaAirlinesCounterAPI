package com.st.utopia.counter.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A Ticket model.
 *
 * 
 * Model used for the purpose of the cancellation microservice.
 * 
 * There are no set methods for information about the ticket such as flight
 * number, row/seat, or class.
 * 
 * Upon cancelling the ticket, the microservice needs to set the following
 * fields as null: -reserver -price -reservation_timeout -booking_id
 * 
 * This model does not need to define a composite key since the cancellation
 * microservice will search for the ticket via booking_id which is unique
 * 
 * 
 * @author Al-amine AHMED MOUSSA
 */

public class Ticket {
	/**
	 * The three fields that together constitute the identity of a Ticket.
	 */
	private TicketIdentity id;

	private Integer tier; // class is a keyword

	private Integer reserver;

	private Integer price;

	private LocalDateTime reservationTimeout;

	private String bookingId;

	/**
	 * the Getters and Setters of the Class
	 */
	public TicketIdentity getId() {
		return id;
	}

	public Integer getFlight() {
		if (id == null) {
			return null;
		} else {
			return id.getFlight();
		}
	}

	public Integer getRow() {
		if (id == null) {
			return null;
		} else {
			return id.getRow();
		}
	}

	public char getSeat() {
		if (id == null) {
			return 0;
		} else {
			return id.getSeat();
		}
	}

	public Integer getTier() {
		return tier;
	}

	public void setTier(Integer tier) {
		this.tier = tier;
	}

	public Integer getReserver() {
		return reserver;
	}

	public void setReserver(Integer reserver) {
		this.reserver = reserver;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public LocalDateTime getReservationTimeout() {
		return reservationTimeout;
	}

	public void setReservationTimeout(LocalDateTime reservationTimeout) {
		this.reservationTimeout = reservationTimeout;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/*
	 * we need empty Constructor for Hibernate Jpa Purposes
	 */
	public Ticket() {
	}

	public Ticket(TicketIdentity id) {
		this.id = id;
	}

	public Ticket(TicketIdentity id, Integer tier, Integer reserver, Integer price, LocalDateTime reservationTimeout,
			String bookingId) {
		this.id = id;
		this.tier = tier;
		this.reserver = reserver;
		this.price = price;
		this.reservationTimeout = reservationTimeout;
		this.bookingId = bookingId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Ticket) {
			return Objects.equals(id, ((Ticket) obj).id);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", tier=" + tier + ", reserver=" + reserver + ", price=" + price
				+ ", reservationTimeout=" + reservationTimeout + ", bookingId=" + bookingId + "]";
	}

}

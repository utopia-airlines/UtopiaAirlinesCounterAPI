package com.st.utopia.counter.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.st.utopia.counter.entity.PaymentAmount;
import com.st.utopia.counter.entity.Ticket;
import com.st.utopia.counter.entity.User;

@RestController
@RequestMapping("/counter")
public class CounterBookingController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${utopia.booking-service-hostName}")
	private String hostName;

	@Value("${utopia.booking-service-port}")
	private String port;

	@Value("${utopia.booking-service-controller-root}")
	private String root;

	/**
	 * Helper method to reduce the amount of repetitive code required for "get-all"
	 * methods.
	 * 
	 * @param url the URL to send the REST request to
	 * @param     <T> the type we expect
	 * @return the response the server sent
	 */
	private <T> ResponseEntity<T> methodCall(final String url, final HttpMethod method) {
		return restTemplate.exchange(url, method, null, new ParameterizedTypeReference<T>() {
		});
	}

	/**
	 * Helper method to reduce the amount of repetitive code required for a method
	 * call.
	 * 
	 * @param url        the URL to send the REST request to
	 * @param type       of method to send the REST request to
	 * @param HttpEntity to send in the REST request
	 * @param            <T> the type we expect
	 * @param            <U> the type of the body sent to the server
	 * @return the response the server sent
	 */
	private <T, U> ResponseEntity<T> methodCall(final String url, final HttpMethod method, final HttpEntity<U> body) {
		return restTemplate.exchange(url, method, body, new ParameterizedTypeReference<T>() {
		});
	}

	/**
	 * Reserve a ticket for the given seat.
	 * 
	 * @param flight the flight number of the flight
	 * @param row    the row number of the seat
	 * @param seat   the seat within the row
	 * @param user   the user details
	 * 
	 * @return the ticket that was created by this post
	 */
	@PostMapping("/book/flights/{flight}/rows/{row}/seats/{seat}")
	public ResponseEntity<Ticket> getAllBranchCopies(@PathVariable final int flight, @PathVariable final int row,
			@PathVariable final String seat, @RequestBody final User user) {
		HttpEntity<User> body = new HttpEntity<>(user);
		String url = "http://" + hostName + ":" + port + root + "/book/flights/" + flight + "/rows/" + row + "/seats/"
				+ seat;
		return this.<Ticket, User>methodCall(url, HttpMethod.POST, body);
	}

	/**
	 * Accept payment for a given reserved seat.
	 * 
	 * @param flight  the flight number of the flight
	 * @param row     the row number of the seat
	 * @param seat    the seat within the row
	 * @param payment the price the customer has paid for the ticket
	 * 
	 * @return the ticket that was update from this put
	 */
	@PutMapping("/pay/flights/{flight}/rows/{row}/seats/{seat}")
	public ResponseEntity<Ticket> acceptPayment(@PathVariable final int flight, @PathVariable final int row,
			@PathVariable final String seat, @RequestBody final PaymentAmount payment) {
		HttpEntity<PaymentAmount> body = new HttpEntity<>(payment);
		String url = "http://" + hostName + ":" + port + root + "/pay/flights/" + flight + "/rows/" + row + "/seats/"
				+ seat;
		return this.<Ticket, PaymentAmount>methodCall(url, HttpMethod.PUT, body);
	}

	/**
	 * Accept payment for a given reserved seat.
	 * 
	 * @param bookingId the ID code of the booking
	 * @param payment   the price the customer has paid for the ticket
	 * 
	 * @return the ticket that was update from this put
	 */
	@PutMapping("/pay/bookings/{bookingId}")
	public ResponseEntity<Ticket> acceptPaymentForBookingId(@PathVariable final String bookingId,
			@RequestBody final PaymentAmount payment) {
		HttpEntity<PaymentAmount> body = new HttpEntity<>(payment);
		String url = "http://" + hostName + ":" + port + root + "/pay/bookings/" + bookingId;
		return this.<Ticket, PaymentAmount>methodCall(url, HttpMethod.PUT, body);
	}

	/**
	 * Cancel unpaid reservation for a given seat. TODO: Only the ticket-holder
	 * should be able to cancel it
	 *
	 * @param flight the flight number of the flight
	 * @param row    the row number of the seat
	 * @param seat   the seat within the row
	 * 
	 * @return an Object (with the status)
	 */
	@DeleteMapping("/book/flights/{flight}/rows/{row}/seats/{seat}")
	public ResponseEntity<Object> cancelReservation(@PathVariable final int flight, @PathVariable final int row,
			@PathVariable final String seat) {
		String url = "http://" + hostName + ":" + port + root + "/book/flights/" + flight + "/rows/" + row + "/seats/"
				+ seat;
		return this.<Object>methodCall(url, HttpMethod.DELETE);
	}

	/**
	 * Cancel unpaid reservation for a given booking-ID. TODO: only the ticket
	 * holder should be able to cancel it
	 *
	 * @param bookingId the booking-ID for the seat
	 * 
	 * @return an Object (with the status)
	 */
	@DeleteMapping("/book/bookings/{bookingId}")
	public ResponseEntity<Object> cancelBookingById(@PathVariable final String bookingId) {
		String url = "http://" + hostName + ":" + port + root + "/book/bookings/" + bookingId;
		return this.<Object>methodCall(url, HttpMethod.DELETE);
	}

	/**
	 * Extend the reservation timeout for the given unpaid booking. TODO: limit the
	 * number of times this is allowed
	 *
	 * @param flight the flight number of the flight
	 * @param row    the row number of the seat
	 * @param seat   the seat within the row
	 * 
	 * @return an Object (with the status)
	 */
	@PutMapping("/extend/flights/{flight}/rows/{row}/seats/{seat}")
	public ResponseEntity<Object> extendTimeout(@PathVariable final int flight, @PathVariable final int row,
			@PathVariable final String seat) {
		String url = "http://" + hostName + ":" + port + root + "/extend/flights/" + flight + "/rows/" + row + "/seats/"
				+ seat;
		return this.<Object>methodCall(url, HttpMethod.PUT);
	}

	/**
	 * Extend the reservation timeout for the given unpaid booking. TODO: limit the
	 * number of times this is allowed
	 *
	 * @param bookingId the booking-ID for the seat
	 */
	@PutMapping("/extend/bookings/{bookingId}")
	public ResponseEntity<Object> extendTimeout(@PathVariable final String bookingId) {
		String url = "http://" + hostName + ":" + port + root + "/extend/bookings/" + bookingId;
		return this.<Object>methodCall(url, HttpMethod.PUT);
	}

	/**
	 * Get the details of a ticket.
	 *
	 * @param flightId the flight number of the flight
	 * @param row      the row number of the seat
	 * @param seatId   the seat within the row
	 */
	@GetMapping("/details/flights/{flightId}/rows/{row}/seats/{seatId}")
	public ResponseEntity<Ticket> getBookingDetails(@PathVariable final int flightId, @PathVariable final int row,
			@PathVariable final String seatId) {
		String url = "http://" + hostName + ":" + port + root + "/details/flights/" + flightId + "/rows/" + row
				+ "/seats/" + seatId;
		return this.<Ticket>methodCall(url, HttpMethod.GET);
	}

}

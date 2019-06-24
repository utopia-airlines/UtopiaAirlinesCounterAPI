package com.st.utopia.counter.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.st.utopia.counter.entity.Ticket;

/**
 * Controller for Counter API
 * 
 * @author Al-amine AHMED MOUSSA
 */

@RestController
@CrossOrigin
@RequestMapping("/counter")
public class CounterCancellationController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${utopia.cancellation-service-hostName}")
	private String hostName;

	@Value("${utopia.cancellation-service-port}")
	private String port;

	@Value("${utopia.cancellation-service-controller-root}")
	private String root;

	@PutMapping("/cancel/ticket/booking-id/{bookingId}")
	public ResponseEntity<Ticket> cancelReservationByBookingId(@PathVariable String bookingId) {
		restTemplate.put("http://" + hostName + ":" + port + root + "/ticket/booking-id/" + bookingId, null);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@PutMapping("/cancel/ticket/flight/{flightNumber}/row/{rowNumber}/seat/{seat}")
	public ResponseEntity<Ticket> cancelReservationByID(@PathVariable("flightNumber") final int flight,
			@PathVariable("rowNumber") final int row, @PathVariable("seat") final char seat) {
		restTemplate.put(
				"http://" + hostName + ":" + port + root + "/ticket/flight/" + flight + "/row/" + row + "/seat/" + seat,
				null);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}

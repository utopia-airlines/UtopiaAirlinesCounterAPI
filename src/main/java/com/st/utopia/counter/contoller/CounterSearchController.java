package com.st.utopia.counter.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/counter")
public class CounterSearchController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${utopia.search-service-hostName}")
	private String hostName;

	@Value("${utopia.search-service-port}")
	private String port;

	@Value("${utopia.search-service-controller-root}")
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

	@SuppressWarnings("rawtypes")
	@GetMapping(path = "/search")
	public ResponseEntity<List> getAllBranchCopies(
			@RequestParam(required = false) Map<String, String> allRequestParams) {
		String query = "?";
		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			query = query.concat(entry.getKey() + "=" + entry.getValue() + "&");
		}
		ResponseEntity<List> test = this.<List>methodCall("http://" + hostName + ":" + port + root + query,
				HttpMethod.GET);
		return test;
	}
}

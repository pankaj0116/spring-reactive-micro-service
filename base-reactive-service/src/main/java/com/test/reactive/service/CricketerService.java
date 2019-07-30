package com.test.reactive.service;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.reactive.constants.Constants;
import com.test.reactive.model.CricketerResponse;
import com.test.reactive.model.Request;
import com.test.reactive.model.Response;
import com.test.reactive.repository.Cricketer;
import com.test.reactive.repository.CricketerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("CricketerService")
@Component
@Transactional(rollbackForClassName = { "ServiceException" })
public class CricketerService {

	@Autowired
	CricketerRepository cricketerRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CricketerService.class);

	@HystrixCommand(fallbackMethod = "fallbackDefault", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public CricketerResponse getAllCricketers() {
		LOGGER.info("Enter :: CricketerService :: test");
		CricketerResponse cricketerResponse = new CricketerResponse();

		Flux<Cricketer> cricketerList = cricketerRepository.findAll();

		cricketerResponse.setStatus(Constants.SUCCESS_CODE);
		cricketerResponse.setStatusMessage(Constants.SUCCESS);
		//cricketerResponse.setCricketerList(cricketerList.toStream().collect(Collectors.toList()));
		cricketerResponse.setCricketerList(cricketerList.collectList().block());

		LOGGER.info("Exit :: CricketerService :: test");
		return cricketerResponse;
		
		/*Response responseData = buildResponseData(cricketerList);
		
		LOGGER.info("Exit :: CricketerService :: test");
		return ok().body(Mono.just(cricketerResponse), CricketerResponse.class);

		return cricketerResponse;*/
	}

	@HystrixCommand(fallbackMethod = "fallbackDefault", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public CricketerResponse getCricketer(String id) {
		LOGGER.info("Enter :: CricketerService :: test");
		CricketerResponse cricketerResponse = new CricketerResponse();

		Mono<ResponseEntity<Cricketer>> cricketer = cricketerRepository.findById(id)
				.map(body -> ResponseEntity.ok(body)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

		cricketerResponse.setStatus(Constants.SUCCESS_CODE);
		cricketerResponse.setStatusMessage(Constants.SUCCESS);
		cricketerResponse.setCricketer(cricketer);

		LOGGER.info("Exit :: CricketerService :: test");
		return cricketerResponse;
	}

	@HystrixCommand(fallbackMethod = "fallbackDefault", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public CricketerResponse addCricketer(Cricketer cricketer) {

		LOGGER.info("Enter :: CricketerService :: test");
		CricketerResponse cricketerResponse = new CricketerResponse();

		Mono<Cricketer> cricketerObj = cricketerRepository.save(cricketer);

		cricketerResponse.setStatus(Constants.SUCCESS_CODE);
		cricketerResponse.setStatusMessage(Constants.SUCCESS + "Cricketer added");
		cricketerResponse.setCricketers(cricketerObj);

		LOGGER.info("Exit :: CricketerService :: test");
		return cricketerResponse;
	}

	@HystrixCommand(fallbackMethod = "fallbackDefault", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public CricketerResponse updateCricketer(String id, Cricketer cricketer) {
		LOGGER.info("Enter :: CricketerService :: test");
		CricketerResponse cricketerResponse = new CricketerResponse();

		Mono<ResponseEntity<Cricketer>> updatedCricketerObj = cricketerRepository.findById(id)
				.flatMap(currentCricketer -> {
					currentCricketer.setCountry(cricketer.getCountry());
					currentCricketer.setName(cricketer.getName());
					currentCricketer.setHighestScore(cricketer.getHighestScore());
					return cricketerRepository.save(currentCricketer);
				}).map(updatedCricketer -> new ResponseEntity<Cricketer>(updatedCricketer, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

		cricketerResponse.setStatus(Constants.SUCCESS_CODE);
		cricketerResponse.setStatusMessage(Constants.SUCCESS + "Cricketer Updated");
		cricketerResponse.setCricketer(updatedCricketerObj);
		LOGGER.info("Exit :: CricketerService :: test");
		return cricketerResponse;
	}

	@HystrixCommand(fallbackMethod = "fallbackDefault", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public CricketerResponse deleteCricketer(String id) {

		LOGGER.info("Enter :: CricketerService :: test");
		CricketerResponse cricketerResponse = new CricketerResponse();

		cricketerRepository.deleteById(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));

		cricketerResponse.setStatus(Constants.SUCCESS_CODE);
		cricketerResponse.setStatusMessage(Constants.SUCCESS + "Cricketer Deleted");
		LOGGER.info("Exit :: CricketerService :: test");
		return cricketerResponse;
	}
	
	private Response buildResponseData(Flux<Cricketer> cricketers) {
		Response responseData = new Response();
	    responseData.setStatus(Constants.SUCCESS_CODE);
	    responseData.setStatusMessage(Constants.SUCCESS);
	    responseData.setData(cricketers);
	    return responseData;
	}

	private Response fallbackDefault(Request request) {
		Response response = new Response();
		response.setStatus(Constants.FALLBACK_STATUS);
		response.setStatusMessage(Constants.FALLBACK_MESSAGE);
		return response;
	}
}

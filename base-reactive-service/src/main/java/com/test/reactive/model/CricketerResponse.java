package com.test.reactive.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.test.reactive.repository.Cricketer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CricketerResponse extends Response implements Serializable {

	private static final long serialVersionUID = 6206299169868428879L;

	private Mono<ResponseEntity<Cricketer>> cricketer;

	private List<Cricketer> cricketerList;

	private Mono<Cricketer> cricketers;

}

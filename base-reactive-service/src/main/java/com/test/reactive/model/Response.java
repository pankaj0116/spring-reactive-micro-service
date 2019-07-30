package com.test.reactive.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Response implements Serializable {

	private static final long serialVersionUID = -6537889815349084535L;

	private String status;
	private String statusMessage;
	private String requestId;
	private String timezone;
	private Object data;
}

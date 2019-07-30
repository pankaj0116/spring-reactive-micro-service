package com.test.reactive.repository;

import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "cricketer")
public class Cricketer {

	@Id
	private String id;

	private String name;

	private String country;

	private String highestScore;

}

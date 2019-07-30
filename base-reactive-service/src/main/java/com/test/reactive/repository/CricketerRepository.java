package com.test.reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketerRepository extends ReactiveCrudRepository<Cricketer , String>{

}

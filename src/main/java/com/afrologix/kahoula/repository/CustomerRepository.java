package com.afrologix.kahoula.repository;

import com.afrologix.kahoula.domain.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}

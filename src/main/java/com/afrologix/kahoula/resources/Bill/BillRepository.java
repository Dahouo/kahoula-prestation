package com.afrologix.kahoula.domain.Bill;

import com.afrologix.kahoula.domain.Bill.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Bill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillRepository extends MongoRepository<Bill, String> {

}

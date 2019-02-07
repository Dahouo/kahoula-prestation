package com.afrologix.kahoula.repository;

import com.afrologix.kahoula.resources.JobBid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the JobBid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobBidRepository extends MongoRepository<JobBid, String> {

}

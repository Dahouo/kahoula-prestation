package com.afrologix.kahoula.repository;

import com.afrologix.kahoula.domain.Partner;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Partner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerRepository extends MongoRepository<Partner, String> {

}

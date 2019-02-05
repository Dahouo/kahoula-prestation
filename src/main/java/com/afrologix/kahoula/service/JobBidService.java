package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.JobBid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing JobBid.
 */
public interface JobBidService {

    /**
     * Save a jobBid.
     *
     * @param jobBid the entity to save
     * @return the persisted entity
     */
    JobBid save(JobBid jobBid);

    /**
     * Get all the jobBids.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobBid> findAll(Pageable pageable);


    /**
     * Get the "id" jobBid.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<JobBid> findOne(String id);

    /**
     * Delete the "id" jobBid.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the jobBid corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobBid> search(String query, Pageable pageable);
}

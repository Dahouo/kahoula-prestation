package com.afrologix.kahoula.service.impl;

import com.afrologix.kahoula.service.JobBidService;
import com.afrologix.kahoula.domain.JobBid;
import com.afrologix.kahoula.repository.JobBidRepository;
import com.afrologix.kahoula.repository.search.JobBidSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobBid.
 */
@Service
public class JobBidServiceImpl implements JobBidService {

    private final Logger log = LoggerFactory.getLogger(JobBidServiceImpl.class);

    private final JobBidRepository jobBidRepository;

    private final JobBidSearchRepository jobBidSearchRepository;

    public JobBidServiceImpl(JobBidRepository jobBidRepository, JobBidSearchRepository jobBidSearchRepository) {
        this.jobBidRepository = jobBidRepository;
        this.jobBidSearchRepository = jobBidSearchRepository;
    }

    /**
     * Save a jobBid.
     *
     * @param jobBid the entity to save
     * @return the persisted entity
     */
    @Override
    public JobBid save(JobBid jobBid) {
        log.debug("Request to save JobBid : {}", jobBid);
        JobBid result = jobBidRepository.save(jobBid);
        jobBidSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the jobBids.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<JobBid> findAll(Pageable pageable) {
        log.debug("Request to get all JobBids");
        return jobBidRepository.findAll(pageable);
    }


    /**
     * Get one jobBid by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<JobBid> findOne(String id) {
        log.debug("Request to get JobBid : {}", id);
        return jobBidRepository.findById(id);
    }

    /**
     * Delete the jobBid by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete JobBid : {}", id);        jobBidRepository.deleteById(id);
        jobBidSearchRepository.deleteById(id);
    }

    /**
     * Search for the jobBid corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<JobBid> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobBids for query {}", query);
        return jobBidSearchRepository.search(queryStringQuery(query), pageable);    }
}

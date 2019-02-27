package com.afrologix.kahoula.resources.JobBid;

import com.afrologix.kahoula.repository.JobBidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service Implementation for managing JobBid.
 */
@Service
public class JobBidService {

    private final Logger log = LoggerFactory.getLogger(JobBidService.class);

    private final JobBidRepository jobBidRepository;

    private final JobBidMapper jobBidMapper;

    public JobBidService(JobBidRepository jobBidRepository, JobBidMapper jobBidMapper) {
        this.jobBidRepository = jobBidRepository;
        this.jobBidMapper = jobBidMapper;
    }

    /**
     * Save a jobBid.
     *
     * @param jobBidDTO the entity to save
     * @return the persisted entity
     */
    public JobBidDTO save(JobBidDTO jobBidDTO) {
        log.debug("Request to save JobBid : {}", jobBidDTO);
        JobBid jobBid = jobBidMapper.toEntity(jobBidDTO);
        jobBid = jobBidRepository.save(jobBid);
        JobBidDTO result = jobBidMapper.toDto(jobBid);
//        jobBidSearchRepository.save(jobBid);
        return result;
    }

    /**
     * Get all the jobBids.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<JobBidDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobBids");
        return jobBidRepository.findAll(pageable)
            .map(jobBidMapper::toDto);
    }


    /**
     * Get one jobBid by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<JobBidDTO> findOne(String id) {
        log.debug("Request to get JobBid : {}", id);
        return jobBidRepository.findById(id)
            .map(jobBidMapper::toDto);
    }

    /**
     * Delete the jobBid by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete JobBid : {}", id);
        jobBidRepository.deleteById(id);
//        jobBidSearchRepository.deleteById(id);
    }

    /**
     * Search for the jobBid corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
//    public Page<JobBidDTO> search(String query, Pageable pageable) {
//        log.debug("Request to search for a page of JobBids for query {}", query);
//        return jobBidSearchRepository.search(queryStringQuery(query), pageable)
//            .map(jobBidMapper::toDto);
//    }
}

package com.afrologix.kahoula.web.rest;
import com.afrologix.kahoula.service.JobBidService;
import com.afrologix.kahoula.web.rest.errors.BadRequestAlertException;
import com.afrologix.kahoula.web.rest.util.HeaderUtil;
import com.afrologix.kahoula.web.rest.util.PaginationUtil;
import com.afrologix.kahoula.service.dto.JobBidDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JobBid.
 */
@RestController
@RequestMapping("/api")
public class JobBidResource {

    private final Logger log = LoggerFactory.getLogger(JobBidResource.class);

    private static final String ENTITY_NAME = "jobBid";

    private final JobBidService jobBidService;

    public JobBidResource(JobBidService jobBidService) {
        this.jobBidService = jobBidService;
    }

    /**
     * POST  /job-bids : Create a new jobBid.
     *
     * @param jobBidDTO the jobBidDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobBidDTO, or with status 400 (Bad Request) if the jobBid has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-bids")
    public ResponseEntity<JobBidDTO> createJobBid(@Valid @RequestBody JobBidDTO jobBidDTO) throws URISyntaxException {
        log.debug("REST request to save JobBid : {}", jobBidDTO);
        if (jobBidDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobBid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobBidDTO result = jobBidService.save(jobBidDTO);
        return ResponseEntity.created(new URI("/api/job-bids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-bids : Updates an existing jobBid.
     *
     * @param jobBidDTO the jobBidDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobBidDTO,
     * or with status 400 (Bad Request) if the jobBidDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobBidDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-bids")
    public ResponseEntity<JobBidDTO> updateJobBid(@Valid @RequestBody JobBidDTO jobBidDTO) throws URISyntaxException {
        log.debug("REST request to update JobBid : {}", jobBidDTO);
        if (jobBidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobBidDTO result = jobBidService.save(jobBidDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobBidDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-bids : get all the jobBids.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobBids in body
     */
    @GetMapping("/job-bids")
    public ResponseEntity<List<JobBidDTO>> getAllJobBids(Pageable pageable) {
        log.debug("REST request to get a page of JobBids");
        Page<JobBidDTO> page = jobBidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-bids");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /job-bids/:id : get the "id" jobBid.
     *
     * @param id the id of the jobBidDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobBidDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-bids/{id}")
    public ResponseEntity<JobBidDTO> getJobBid(@PathVariable String id) {
        log.debug("REST request to get JobBid : {}", id);
        Optional<JobBidDTO> jobBidDTO = jobBidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobBidDTO);
    }

    /**
     * DELETE  /job-bids/:id : delete the "id" jobBid.
     *
     * @param id the id of the jobBidDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-bids/{id}")
    public ResponseEntity<Void> deleteJobBid(@PathVariable String id) {
        log.debug("REST request to delete JobBid : {}", id);
        jobBidService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/job-bids?query=:query : search for the jobBid corresponding
     * to the query.
     *
     * @param query the query of the jobBid search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/job-bids")
    public ResponseEntity<List<JobBidDTO>> searchJobBids(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JobBids for query {}", query);
        Page<JobBidDTO> page = jobBidService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-bids");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

package com.afrologix.kahoula.web.rest;
import com.afrologix.kahoula.domain.JobBid;
import com.afrologix.kahoula.service.JobBidService;
import com.afrologix.kahoula.web.rest.errors.BadRequestAlertException;
import com.afrologix.kahoula.web.rest.util.HeaderUtil;
import com.afrologix.kahoula.web.rest.util.PaginationUtil;
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
     * @param jobBid the jobBid to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobBid, or with status 400 (Bad Request) if the jobBid has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-bids")
    public ResponseEntity<JobBid> createJobBid(@Valid @RequestBody JobBid jobBid) throws URISyntaxException {
        log.debug("REST request to save JobBid : {}", jobBid);
        if (jobBid.getId() != null) {
            throw new BadRequestAlertException("A new jobBid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobBid result = jobBidService.save(jobBid);
        return ResponseEntity.created(new URI("/api/job-bids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-bids : Updates an existing jobBid.
     *
     * @param jobBid the jobBid to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobBid,
     * or with status 400 (Bad Request) if the jobBid is not valid,
     * or with status 500 (Internal Server Error) if the jobBid couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-bids")
    public ResponseEntity<JobBid> updateJobBid(@Valid @RequestBody JobBid jobBid) throws URISyntaxException {
        log.debug("REST request to update JobBid : {}", jobBid);
        if (jobBid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobBid result = jobBidService.save(jobBid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobBid.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-bids : get all the jobBids.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobBids in body
     */
    @GetMapping("/job-bids")
    public ResponseEntity<List<JobBid>> getAllJobBids(Pageable pageable) {
        log.debug("REST request to get a page of JobBids");
        Page<JobBid> page = jobBidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-bids");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /job-bids/:id : get the "id" jobBid.
     *
     * @param id the id of the jobBid to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobBid, or with status 404 (Not Found)
     */
    @GetMapping("/job-bids/{id}")
    public ResponseEntity<JobBid> getJobBid(@PathVariable String id) {
        log.debug("REST request to get JobBid : {}", id);
        Optional<JobBid> jobBid = jobBidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobBid);
    }

    /**
     * DELETE  /job-bids/:id : delete the "id" jobBid.
     *
     * @param id the id of the jobBid to delete
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
    public ResponseEntity<List<JobBid>> searchJobBids(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JobBids for query {}", query);
        Page<JobBid> page = jobBidService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-bids");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

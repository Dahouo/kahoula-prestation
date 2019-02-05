package com.afrologix.kahoula.service.impl;

import com.afrologix.kahoula.service.BillService;
import com.afrologix.kahoula.domain.Bill;
import com.afrologix.kahoula.repository.BillRepository;
import com.afrologix.kahoula.repository.search.BillSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Bill.
 */
@Service
public class BillServiceImpl implements BillService {

    private final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    private final BillRepository billRepository;

    private final BillSearchRepository billSearchRepository;

    public BillServiceImpl(BillRepository billRepository, BillSearchRepository billSearchRepository) {
        this.billRepository = billRepository;
        this.billSearchRepository = billSearchRepository;
    }

    /**
     * Save a bill.
     *
     * @param bill the entity to save
     * @return the persisted entity
     */
    @Override
    public Bill save(Bill bill) {
        log.debug("Request to save Bill : {}", bill);
        Bill result = billRepository.save(bill);
        billSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bills.
     *
     * @return the list of entities
     */
    @Override
    public List<Bill> findAll() {
        log.debug("Request to get all Bills");
        return billRepository.findAll();
    }


    /**
     * Get one bill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Bill> findOne(String id) {
        log.debug("Request to get Bill : {}", id);
        return billRepository.findById(id);
    }

    /**
     * Delete the bill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Bill : {}", id);        billRepository.deleteById(id);
        billSearchRepository.deleteById(id);
    }

    /**
     * Search for the bill corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<Bill> search(String query) {
        log.debug("Request to search Bills for query {}", query);
        return StreamSupport
            .stream(billSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

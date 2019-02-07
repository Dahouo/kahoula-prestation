package com.afrologix.kahoula.domain.Bill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Bill.
 */
@Service
public class BillService {

    private final Logger log = LoggerFactory.getLogger(BillService.class);

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final BillSearchRepository billSearchRepository;

    public BillService(BillRepository billRepository, BillMapper billMapper, BillSearchRepository billSearchRepository) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
        this.billSearchRepository = billSearchRepository;
    }

    /**
     * Save a bill.
     *
     * @param billDTO the entity to save
     * @return the persisted entity
     */
    public BillDTO save(BillDTO billDTO) {
        log.debug("Request to save Bill : {}", billDTO);
        Bill bill = billMapper.toEntity(billDTO);
        bill = billRepository.save(bill);
        BillDTO result = billMapper.toDto(bill);
        billSearchRepository.save(bill);
        return result;
    }

    /**
     * Get all the bills.
     *
     * @return the list of entities
     */
    public List<BillDTO> findAll() {
        log.debug("Request to get all Bills");
        return billRepository.findAll().stream()
            .map(billMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<BillDTO> findOne(String id) {
        log.debug("Request to get Bill : {}", id);
        return billRepository.findById(id)
            .map(billMapper::toDto);
    }

    /**
     * Delete the bill by id.
     *
     * @param id the id of the entity
     */
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
    public List<BillDTO> search(String query) {
        log.debug("Request to search Bills for query {}", query);
        return StreamSupport
            .stream(billSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(billMapper::toDto)
            .collect(Collectors.toList());
    }
}

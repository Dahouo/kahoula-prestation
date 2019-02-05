package com.afrologix.kahoula.service.impl;

import com.afrologix.kahoula.service.PartnerService;
import com.afrologix.kahoula.domain.Partner;
import com.afrologix.kahoula.repository.PartnerRepository;
import com.afrologix.kahoula.repository.search.PartnerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Partner.
 */
@Service
public class PartnerServiceImpl implements PartnerService {

    private final Logger log = LoggerFactory.getLogger(PartnerServiceImpl.class);

    private final PartnerRepository partnerRepository;

    private final PartnerSearchRepository partnerSearchRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository, PartnerSearchRepository partnerSearchRepository) {
        this.partnerRepository = partnerRepository;
        this.partnerSearchRepository = partnerSearchRepository;
    }

    /**
     * Save a partner.
     *
     * @param partner the entity to save
     * @return the persisted entity
     */
    @Override
    public Partner save(Partner partner) {
        log.debug("Request to save Partner : {}", partner);
        Partner result = partnerRepository.save(partner);
        partnerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the partners.
     *
     * @return the list of entities
     */
    @Override
    public List<Partner> findAll() {
        log.debug("Request to get all Partners");
        return partnerRepository.findAll();
    }


    /**
     * Get one partner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Partner> findOne(String id) {
        log.debug("Request to get Partner : {}", id);
        return partnerRepository.findById(id);
    }

    /**
     * Delete the partner by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Partner : {}", id);        partnerRepository.deleteById(id);
        partnerSearchRepository.deleteById(id);
    }

    /**
     * Search for the partner corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<Partner> search(String query) {
        log.debug("Request to search Partners for query {}", query);
        return StreamSupport
            .stream(partnerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

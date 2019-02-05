package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.Partner;
import com.afrologix.kahoula.repository.PartnerRepository;
import com.afrologix.kahoula.repository.search.PartnerSearchRepository;
import com.afrologix.kahoula.service.dto.PartnerDTO;
import com.afrologix.kahoula.service.mapper.PartnerMapper;
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
 * Service Implementation for managing Partner.
 */
@Service
public class PartnerService {

    private final Logger log = LoggerFactory.getLogger(PartnerService.class);

    private final PartnerRepository partnerRepository;

    private final PartnerMapper partnerMapper;

    private final PartnerSearchRepository partnerSearchRepository;

    public PartnerService(PartnerRepository partnerRepository, PartnerMapper partnerMapper, PartnerSearchRepository partnerSearchRepository) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
        this.partnerSearchRepository = partnerSearchRepository;
    }

    /**
     * Save a partner.
     *
     * @param partnerDTO the entity to save
     * @return the persisted entity
     */
    public PartnerDTO save(PartnerDTO partnerDTO) {
        log.debug("Request to save Partner : {}", partnerDTO);
        Partner partner = partnerMapper.toEntity(partnerDTO);
        partner = partnerRepository.save(partner);
        PartnerDTO result = partnerMapper.toDto(partner);
        partnerSearchRepository.save(partner);
        return result;
    }

    /**
     * Get all the partners.
     *
     * @return the list of entities
     */
    public List<PartnerDTO> findAll() {
        log.debug("Request to get all Partners");
        return partnerRepository.findAll().stream()
            .map(partnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<PartnerDTO> findOne(String id) {
        log.debug("Request to get Partner : {}", id);
        return partnerRepository.findById(id)
            .map(partnerMapper::toDto);
    }

    /**
     * Delete the partner by id.
     *
     * @param id the id of the entity
     */
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
    public List<PartnerDTO> search(String query) {
        log.debug("Request to search Partners for query {}", query);
        return StreamSupport
            .stream(partnerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(partnerMapper::toDto)
            .collect(Collectors.toList());
    }
}

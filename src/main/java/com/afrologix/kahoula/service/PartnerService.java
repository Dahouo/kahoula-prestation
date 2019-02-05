package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.Partner;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Partner.
 */
public interface PartnerService {

    /**
     * Save a partner.
     *
     * @param partner the entity to save
     * @return the persisted entity
     */
    Partner save(Partner partner);

    /**
     * Get all the partners.
     *
     * @return the list of entities
     */
    List<Partner> findAll();


    /**
     * Get the "id" partner.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Partner> findOne(String id);

    /**
     * Delete the "id" partner.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the partner corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Partner> search(String query);
}

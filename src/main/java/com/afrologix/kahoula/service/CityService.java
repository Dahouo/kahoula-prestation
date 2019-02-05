package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.City;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing City.
 */
public interface CityService {

    /**
     * Save a city.
     *
     * @param city the entity to save
     * @return the persisted entity
     */
    City save(City city);

    /**
     * Get all the cities.
     *
     * @return the list of entities
     */
    List<City> findAll();


    /**
     * Get the "id" city.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<City> findOne(String id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the city corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<City> search(String query);
}

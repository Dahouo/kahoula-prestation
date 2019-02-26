package com.afrologix.kahoula.resources.City;

import com.afrologix.kahoula.repository.CityRepository;
import com.afrologix.kahoula.repository.search.CitySearchRepository;
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
 * Service Implementation for managing City.
 */
@Service
public class CityService {

    private final Logger log = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    private final CitySearchRepository citySearchRepository;

    public CityService(CityRepository cityRepository, CityMapper cityMapper, CitySearchRepository citySearchRepository) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.citySearchRepository = citySearchRepository;
    }

    /**
     * Save a city.
     *
     * @param cityDTO the entity to save
     * @return the persisted entity
     */
    public CityDTO save(CityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        CityDTO result = cityMapper.toDto(city);
        citySearchRepository.save(city);
        return result;
    }

    /**
     * Get all the cities.
     *
     * @return the list of entities
     */
    public List<CityDTO> findAll() {
        log.debug("Request to get all Cities");
        return cityRepository.findAll().stream()
            .map(cityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one city by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CityDTO> findOne(String id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id)
            .map(cityMapper::toDto);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete City : {}", id);        cityRepository.deleteById(id);
        citySearchRepository.deleteById(id);
    }

    /**
     * Search for the city corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    public List<CityDTO> search(String query) {
        log.debug("Request to search Cities for query {}", query);
        return StreamSupport
            .stream(citySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cityMapper::toDto)
            .collect(Collectors.toList());
    }
}

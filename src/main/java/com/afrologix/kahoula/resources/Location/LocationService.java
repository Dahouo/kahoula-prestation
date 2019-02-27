package com.afrologix.kahoula.resources.Location;

import com.afrologix.kahoula.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Location.
 */
@Service
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.toEntity(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.toDto(location);
//        locationSearchRepository.save(location);
        return result;
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    public List<LocationDTO> findAll() {
        log.debug("Request to get all Locations");
        return locationRepository.findAll().stream()
            .map(locationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one location by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<LocationDTO> findOne(String id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id)
            .map(locationMapper::toDto);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
//        locationSearchRepository.deleteById(id);
    }

    /**
     * Search for the location corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
//    public List<LocationDTO> search(String query) {
//        log.debug("Request to search Locations for query {}", query);
//        return StreamSupport
//            .stream(locationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .map(locationMapper::toDto)
//            .collect(Collectors.toList());
//    }
}

package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.Users;
import com.afrologix.kahoula.repository.UsersRepository;
import com.afrologix.kahoula.repository.search.UsersSearchRepository;
import com.afrologix.kahoula.service.dto.UsersDTO;
import com.afrologix.kahoula.service.mapper.UsersMapper;
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
 * Service Implementation for managing Users.
 */
@Service
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    private final UsersSearchRepository usersSearchRepository;

    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper, UsersSearchRepository usersSearchRepository) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.usersSearchRepository = usersSearchRepository;
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save
     * @return the persisted entity
     */
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        UsersDTO result = usersMapper.toDto(users);
        usersSearchRepository.save(users);
        return result;
    }

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    public List<UsersDTO> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll().stream()
            .map(usersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one users by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<UsersDTO> findOne(String id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id)
            .map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Users : {}", id);        usersRepository.deleteById(id);
        usersSearchRepository.deleteById(id);
    }

    /**
     * Search for the users corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    public List<UsersDTO> search(String query) {
        log.debug("Request to search Users for query {}", query);
        return StreamSupport
            .stream(usersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(usersMapper::toDto)
            .collect(Collectors.toList());
    }
}

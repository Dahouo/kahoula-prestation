package com.afrologix.kahoula.service.impl;

import com.afrologix.kahoula.service.UsersService;
import com.afrologix.kahoula.domain.Users;
import com.afrologix.kahoula.repository.UsersRepository;
import com.afrologix.kahoula.repository.search.UsersSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Users.
 */
@Service
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    private final UsersSearchRepository usersSearchRepository;

    public UsersServiceImpl(UsersRepository usersRepository, UsersSearchRepository usersSearchRepository) {
        this.usersRepository = usersRepository;
        this.usersSearchRepository = usersSearchRepository;
    }

    /**
     * Save a users.
     *
     * @param users the entity to save
     * @return the persisted entity
     */
    @Override
    public Users save(Users users) {
        log.debug("Request to save Users : {}", users);
        Users result = usersRepository.save(users);
        usersSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    @Override
    public List<Users> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll();
    }


    /**
     * Get one users by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Users> findOne(String id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity
     */
    @Override
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
    @Override
    public List<Users> search(String query) {
        log.debug("Request to search Users for query {}", query);
        return StreamSupport
            .stream(usersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

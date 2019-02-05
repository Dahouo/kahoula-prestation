package com.afrologix.kahoula.service;

import com.afrologix.kahoula.domain.Users;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Users.
 */
public interface UsersService {

    /**
     * Save a users.
     *
     * @param users the entity to save
     * @return the persisted entity
     */
    Users save(Users users);

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    List<Users> findAll();


    /**
     * Get the "id" users.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Users> findOne(String id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the users corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Users> search(String query);
}

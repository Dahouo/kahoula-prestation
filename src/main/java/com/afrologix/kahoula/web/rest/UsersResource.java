package com.afrologix.kahoula.web.rest;
import com.afrologix.kahoula.service.UsersService;
import com.afrologix.kahoula.web.rest.errors.BadRequestAlertException;
import com.afrologix.kahoula.web.rest.util.HeaderUtil;
import com.afrologix.kahoula.service.dto.UsersDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Users.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

    private final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private static final String ENTITY_NAME = "users";

    private final UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * POST  /users : Create a new users.
     *
     * @param usersDTO the usersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usersDTO, or with status 400 (Bad Request) if the users has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    public ResponseEntity<UsersDTO> createUsers(@Valid @RequestBody UsersDTO usersDTO) throws URISyntaxException {
        log.debug("REST request to save Users : {}", usersDTO);
        if (usersDTO.getId() != null) {
            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsersDTO result = usersService.save(usersDTO);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users : Updates an existing users.
     *
     * @param usersDTO the usersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usersDTO,
     * or with status 400 (Bad Request) if the usersDTO is not valid,
     * or with status 500 (Internal Server Error) if the usersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    public ResponseEntity<UsersDTO> updateUsers(@Valid @RequestBody UsersDTO usersDTO) throws URISyntaxException {
        log.debug("REST request to update Users : {}", usersDTO);
        if (usersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsersDTO result = usersService.save(usersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/users")
    public List<UsersDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return usersService.findAll();
    }

    /**
     * GET  /users/:id : get the "id" users.
     *
     * @param id the id of the usersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UsersDTO> getUsers(@PathVariable String id) {
        log.debug("REST request to get Users : {}", id);
        Optional<UsersDTO> usersDTO = usersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usersDTO);
    }

    /**
     * DELETE  /users/:id : delete the "id" users.
     *
     * @param id the id of the usersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable String id) {
        log.debug("REST request to delete Users : {}", id);
        usersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/users?query=:query : search for the users corresponding
     * to the query.
     *
     * @param query the query of the users search
     * @return the result of the search
     */
    @GetMapping("/_search/users")
    public List<UsersDTO> searchUsers(@RequestParam String query) {
        log.debug("REST request to search Users for query {}", query);
        return usersService.search(query);
    }

}

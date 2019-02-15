package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.User.UserSearchRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UserSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UserSearchRepositoryMockConfiguration {

    @MockBean
    private UserSearchRepository mockUserSearchRepository;

}

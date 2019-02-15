package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.Region.RegionSearchRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of RegionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RegionSearchRepositoryMockConfiguration {

    @MockBean
    private RegionSearchRepository mockRegionSearchRepository;

}

package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.Bill.BillSearchRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BillSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BillSearchRepositoryMockConfiguration {

    @MockBean
    private BillSearchRepository mockBillSearchRepository;

}

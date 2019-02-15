package com.afrologix.kahoula.resources.Customer;

import com.afrologix.kahoula.resources.Customer.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Customer entity.
 */
public interface CustomerSearchRepository extends ElasticsearchRepository<Customer, String> {
}

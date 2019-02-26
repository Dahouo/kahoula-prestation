package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.Bill.Bill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Bill entity.
 */
public interface BillSearchRepository extends ElasticsearchRepository<Bill, String> {
}

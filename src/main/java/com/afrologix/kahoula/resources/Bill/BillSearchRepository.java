package com.afrologix.kahoula.domain.Bill;

import com.afrologix.kahoula.domain.Bill.Bill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Bill entity.
 */
public interface BillSearchRepository extends ElasticsearchRepository<Bill, String> {
}

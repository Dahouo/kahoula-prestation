package com.afrologix.kahoula.resources.Partner;

import com.afrologix.kahoula.resources.Partner.Partner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Partner entity.
 */
public interface PartnerSearchRepository extends ElasticsearchRepository<Partner, String> {
}

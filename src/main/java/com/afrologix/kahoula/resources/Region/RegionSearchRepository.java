package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.Region;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Region entity.
 */
public interface RegionSearchRepository extends ElasticsearchRepository<Region, String> {
}

package com.afrologix.kahoula.resources.Region;

import com.afrologix.kahoula.resources.Region.Region;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Region entity.
 */
public interface RegionSearchRepository extends ElasticsearchRepository<Region, String> {
}

package com.afrologix.kahoula.resources.Location;

import com.afrologix.kahoula.resources.Location.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Location entity.
 */
public interface LocationSearchRepository extends ElasticsearchRepository<Location, String> {
}

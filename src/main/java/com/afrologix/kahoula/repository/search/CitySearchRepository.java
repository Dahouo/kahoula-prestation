package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.resources.City.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the City entity.
 */
public interface CitySearchRepository extends ElasticsearchRepository<City, String> {
}

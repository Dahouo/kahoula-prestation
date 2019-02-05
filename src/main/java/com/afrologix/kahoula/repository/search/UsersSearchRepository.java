package com.afrologix.kahoula.repository.search;

import com.afrologix.kahoula.domain.Users;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Users entity.
 */
public interface UsersSearchRepository extends ElasticsearchRepository<Users, String> {
}

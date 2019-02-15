package com.afrologix.kahoula.resources.JobBid;

import com.afrologix.kahoula.resources.JobBid.JobBid;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobBid entity.
 */
public interface JobBidSearchRepository extends ElasticsearchRepository<JobBid, String> {
}

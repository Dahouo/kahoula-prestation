package com.afrologix.kahoula.resources.JobBid;

import com.afrologix.kahoula.resources.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity JobBid and its DTO JobBidDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobBidMapper extends EntityMapper<JobBidDTO, JobBid> {



    default JobBid fromId(String id) {
        if (id == null) {
            return null;
        }
        JobBid jobBid = new JobBid();
        jobBid.setId(id);
        return jobBid;
    }
}

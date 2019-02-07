package com.afrologix.kahoula.service.mapper;

import com.afrologix.kahoula.resources.Partner.Partner;
import com.afrologix.kahoula.service.dto.PartnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Partner and its DTO PartnerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PartnerMapper extends EntityMapper<PartnerDTO, Partner> {



    default Partner fromId(String id) {
        if (id == null) {
            return null;
        }
        Partner partner = new Partner();
        partner.setId(id);
        return partner;
    }
}
